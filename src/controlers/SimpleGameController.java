package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import models.DeadPool;
import models.SimpleGameData;
import models.SystemFile;
import models.TombStone;
import models.map.Map;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.BalloonZombie;
import models.zombies.NormalZombie;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;

public class SimpleGameController {

	static void simpleGame(ApplicationContext context) {
		
		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		SimpleGameData dataBord = new SimpleGameData(); // datas used to start menus and not the game

		ArrayList<Plant> selectedPlant = PrincipalMenuController.startGame(context, dataBord); // read of the pla,t selected in the first menu
		
		HashMap<Zombie, Integer> normalWaveZombie = new HashMap<>();
		normalWaveZombie.put(new NormalZombie(), 1);
		normalWaveZombie.put(new BalloonZombie(), 5);

		// dataBord.generateZombies(1)
		HashMap<Zombie, Integer> superWaveZombie = dataBord.generateZombies(2);

		int yOrigin = 100; // margin top : 100px

		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.size(), 1, 2); // initialisation of the plant selection datas
		
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant); // initialisation of the plant selection view

		BordView.setWidth(width);
		BordView.setHeight(height);
		
		if (dataBord.getLoadChoice() == "start") { // if we have decided to play a new game
			dataBord = Map.dataBord(); //initialisation of the bord datas
		} else { // if we have decided to play from a save
			try {
				dataBord = (SimpleGameData) SystemFile.read("data"); //initialisation of the bord datas
				plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant); // changements of the plant selection view from the save
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		BordView view = Map.view(dataBord); //initialisation of the bord view

		int squareSize = BordView.getSquareSize();

		view.drawRectangle(context, 0, 0, width, height, "#cbd9ef"); // background
		plantSelectionView.draw(context, dataSelect);

		// ---------------WHAT DO I HAVE IN MY GAME ?---------------//
//		System.out.println(view + "\n\n" + plantSelectionView + "\n\n" + dataBord + "\n\n" + dataSelect);
		// ---------------------------------------------------------//

		Zombie.getSizeOfZombie();
		Pea.getSizeOfProjectile();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		boolean debug = false, pause = false, shift = false, ctrl = false;

		List<Zombie> myZombies = dataBord.getMyZombies();
		List<Projectile> myBullet = dataBord.getMyBullet();
		List<LawnMower> myLawnMower = dataBord.getMyLawnMower();
		List<TombStone> myTombstone = dataBord.getMyTombstone();

		dataBord.spawnLawnMower(view, context);

		try {
			SystemFile.readProperties(dataBord); // reading of some game properties modificables in file
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dataBord.createBord(SimpleGameData.getMap());

		while (true) {
			
			myZombies = dataBord.getMyZombies();
			System.out.println(myZombies);
			myBullet = dataBord.getMyBullet();
			myLawnMower = dataBord.getMyLawnMower();
			myTombstone = dataBord.getMyTombstone();
			
//			//test
//			for(Cell c: dataBord.getLineCell(5, 0)) {
//				System.out.println(c.getProjectilesInCell());
//			}
			if (pause) { //we pressed "p" to put the game in interlude
				pause = SecondaryMenuController.menu(context, view, dataBord, plantSelectionView);
			} else {
				/*-----------------------------CHECK CHRONO----------------------------*/

				plantSelectionView.checkCooldown();

				/*--------------------------------DRAWS--------------------------------*/
				view.drawAll(context, dataBord, view, myZombies, myBullet, myLawnMower, debug, dataSelect,
						dataBord.getActualMoney(), dataBord.getFertilizer(), plantSelectionView);

				/*---------------------------INITIALISATION-----------------------------*/

				DeadPool deadPoolE = new DeadPool();

				/*--------------------------------SUN SPAWNERS----------------------------*/

				dataBord.naturalSun(view);

				/*-------------------------------ZOMBIE SPAWNERS-----------------------------*/

				dataBord.spawnZombies(squareSize, str, myZombies, view, context, normalWaveZombie, superWaveZombie);

				/*------------------------------- CONFLICTS ----------------------------------*/

				Zombie.ZCheckConflict(myZombies, myBullet, dataBord.getMyPlants(), myLawnMower, deadPoolE, view,
						dataBord, str);

				/*-------------------------------- DEATHS ------------------------------------*/

				deadPoolE.deletingEverything(dataBord);

				/*--------------------------------SHOOTING------------------------------------*/

				dataBord.actionning(view);

				/*------------------------------- WIN / LOOSE --------------------------------*/
				dataBord.timeEnd(myZombies, str, context, superWaveZombie, view);
				/*---------------------------------DEBUG--------------------------------------*/

				if (debug == true) {
					if (dataBord.spawnRandomPlant(context, dataSelect, view, plantSelectionView, selectedPlant)) {
						str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
					}
				}

				/*------------------------------EVENTS----------------------------------*/

				Event event = context.pollOrWaitEvent(45); // modifier pour avoir un affichage fluide
				if (event == null) { // no event
					continue;
				}


				KeyboardKey KB = event.getKey();
				String mdp = null;
				if (KB != null) {
					mdp = KB.toString();
				}

				/*---------------------------------DEBUG 2--------------------------------------*/
				Action action = event.getAction();
				if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
					if (mdp == "Y" && debug == false) { //debug mod activated
						debug = true;
					} // debug ON
					else if (mdp == "N" && debug == true) { //debug mod desactivated
						debug = false;
					} // debug OFF
					else if (mdp == "SPACE") { //quit
						dataBord.gameStop();
					} else if (mdp == "P") { // break time
						pause = true;
					} else if (action == Action.KEY_PRESSED && mdp == "SHIFT") { // super action of plants
						shift = true;
						mdp = null;
					} else if (action == Action.KEY_RELEASED && mdp == "SHIFT") {
						shift = false;
						mdp = null;
					} else if (action == Action.KEY_PRESSED && mdp == "CTRL") { // diging plants
						ctrl = true;
						mdp = null;
					} else if (action == Action.KEY_RELEASED && mdp == "CTRL") {
						ctrl = false;
						mdp = null;
					} else if (action == Action.KEY_RELEASED && mdp == "M") { // low settings -> no more bullet draw
						dataBord.switchLowSetting();
					}

				}

				/*---Gestion de la selection de cellules et de la plante manuelle de plante---*/

				if (action != Action.POINTER_DOWN) {
					continue;
				}

				Point2D.Float location = event.getLocation();
				float x = location.x;
				float y = location.y;

				if (shift) {
					dataBord.feed(x,y); // lock the super action of a plant
				} else if(ctrl){
					dataBord.shovel(x, y); // dig a plant
				} else {
					dataBord.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, x, y); // select a plant
				}

			}
		}

	}

	public static void main(String[] args) {
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame);
	}
}

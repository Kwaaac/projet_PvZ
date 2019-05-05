package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import models.DeadPool;
import models.SimpleGameData;
import models.map.Map;
import models.plants.*;
import models.plants.day.*;
import models.plants.night.*;
import models.plants.pool.*;

import models.projectiles.*;

import models.zombies.*;

import views.BordView;
import views.SelectBordView;



public class SimpleGameController {
	
	static void simpleGame(ApplicationContext context) {
		ArrayList<Plant> selectedPlant = MenuController.startGame(context);
		
		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int)screenInfo.getWidth();
		int height = (int)screenInfo.getHeight();
		
		HashMap<Zombie, Integer> normalWaveZombie = new HashMap<Zombie, Integer>();
//		normalWaveZombie.put(new NewspaperZombie(), 1);
//		normalWaveZombie.put(new PoleVaultingZombie(), 1);
		
		HashMap<Zombie, Integer> superWaveZombie = new HashMap<Zombie, Integer>();
//		superWaveZombie.put(new ConeheadZombie(), 20);
		superWaveZombie.put(new FlagZombie(), 5);
		superWaveZombie.put(new NormalZombie(), 1);
//		BordView menuView = new BordView(0, 0, width, height, 100);
		SimpleGameData dataBord = Map.dataBord();
		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.size(), 1);

		int yOrigin = 150;
		int xOrigin = 450;

		BordView view = Map.view();
		
		
		int squareSize = BordView.getSquareSize();
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant);
		
		
		view.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);

		ArrayList<Zombie> myZombies = new ArrayList<>();
		ArrayList<Projectile> myBullet = new ArrayList<>();
		ArrayList<LawnMower> myLawnMower = new ArrayList<>();

		Zombie.getSizeOfZombie();
		Pea.getSizeOfProjectile();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		boolean debug = false, debuglock = false;
		
		int money = 0;
		
		dataBord.spawnLawnMower(view, context,myLawnMower);
		
		while (true) {
			
			/*-----------------------------CHECK CHRONO----------------------------*/
			plantSelectionView.checkCooldown();
			/*--------------------------------DRAWS--------------------------------*/
			
			view.draw(context, dataBord);
			debuglock = dataBord.movingZombiesAndBullets(context, view, myZombies, myBullet, myLawnMower, debug, debuglock);
			plantSelectionView.draw(context, dataSelect);
			view.drawRectangle(context, 250, 10, 160, 60, "#A77540");
			view.drawRectangle(context, 255, 15, 150, 50, "#CF9456");
			view.drawString(context, 260, 55, String.valueOf(money), "#FFFF00", 50); //SUN YOU HAVE
			view.drawEllipse(context, 350, 15, 45, 45, "#FEFF33");
			view.drawRectangle(context, 10, 10, 165, 55, "#A77540");
			view.drawRectangle(context, 15, 15, 155, 45, "#CF9456");
			view.drawString(context, 20, 55, "MENU", "#FFFF00", 50);//MENU BUTTON

			/*---------------------------INITIALISATION-----------------------------*/
			
			DeadPool deadPoolE = new DeadPool();
			
			/*--------------------------------SUN SPAWNERS----------------------------*/
			
			dataBord.naturalSun(view);

			/*-------------------------------ZOMBIE SPAWNERS-----------------------------*/
			
			SimpleGameData.spawnZombies(dataBord, squareSize, str, myZombies, view, context, normalWaveZombie, superWaveZombie);

			/*------------------------------- CONFLICTS ----------------------------------*/
			
			Zombie.ZCheckConflict(myZombies, myBullet, dataBord.getMyPlants(), myLawnMower, deadPoolE, view, dataBord, str);
			
			/*-------------------------------- DEATHS ------------------------------------*/
			
			deadPoolE.deletingEverything(myZombies, dataBord, myBullet, myLawnMower);
			
			/*--------------------------------SHOOTING------------------------------------*/
			
			dataBord.actionning(myBullet, view, myZombies, myLawnMower);
			
			/*------------------------------- WIN / LOOSE --------------------------------*/
			SimpleGameData.timeEnd(myZombies, str, context, superWaveZombie,view, myLawnMower);
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
				if (mdp == "Y" && debug == false) {
					debug = true;
				} // debug ON
				else if (mdp == "N" && debug == true) {
					debug = false;
				} // debug OFF
				else if (mdp == "SPACE") {
					dataBord.gameStop();
				}
			}

			/*---Gestion de la selection de cellules et de la plante manuelle de plante---*/
			
			if (action != Action.POINTER_DOWN) {
				continue;
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			
			dataBord.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, x, y);
			
			money = SimpleGameData.getActualMoney();
		}

	}

	public static void main(String[] args) {
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

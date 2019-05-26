package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
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
import models.SystemFile;
import models.map.Map;
import models.plants.*;
import models.projectiles.*;

import models.zombies.*;

import views.BordView;
import views.SelectBordView;



public class SimpleGameController {
	
	static void simpleGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		ArrayList<Plant> selectedPlant = PrincipalMenuController.startGame(context);
		SimpleGameData dataBord = new SimpleGameData(1,1);//no care but important
		HashMap<Zombie, Integer> normalWaveZombie = new HashMap<>();
		normalWaveZombie.put(new NormalZombie(), 4);
		//SimpleGameData.generateZombies(1);
		HashMap<Zombie, Integer> superWaveZombie = SimpleGameData.generateZombies(2);
		
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, 0, 900, dataBord, selectedPlant);//no care but important
		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.size(), 1);

		int yOrigin = 150;
		
		if (SimpleGameData.getLoadChoice() == "start") {
			BordView.setWidth(width);
			BordView.setHeight(height);
			dataBord = Map.dataBord();
			plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant);
		}
		else {
			try {
				dataBord = (SimpleGameData) SystemFile.read("data");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		BordView view = Map.view();
		
		int squareSize = BordView.getSquareSize();
		
		view.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);

		ArrayList<Zombie> myZombies = new ArrayList<>();
		ArrayList<Projectile> myBullet = new ArrayList<>();
		ArrayList<LawnMower> myLawnMower = new ArrayList<>();

		Zombie.getSizeOfZombie();
		Pea.getSizeOfProjectile();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		boolean debug = false, debuglock = false, pause = false;
		
		int money = 0;
		
		dataBord.spawnLawnMower(view, context, myLawnMower);
		
		
		while (true) {
			if (pause == true) {
				pause = SecondaryMenuController.menu(context, view, dataBord, plantSelectionView);
			}
			else {
				
				/*-----------------------------CHECK CHRONO----------------------------*/
				
				plantSelectionView.checkCooldown();
				
				/*--------------------------------DRAWS--------------------------------*/
				
				view.drawAll(context, dataBord, view, myZombies, myBullet, myLawnMower, debug, debuglock, dataSelect, money, plantSelectionView);
	
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
				
				/*--------------------------------SHOOTING------------------------------------*/
				
				dataBord.actionningZombie(myBullet, view, myZombies, dataBord);
				
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
					else if (mdp == "P") {
						pause = true;
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

	}

	public static void main(String[] args) {
		
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

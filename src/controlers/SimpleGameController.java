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
		HashMap<Zombie, Integer> normalWaveZombie = dataBord.generateZombies(1);
		HashMap<Zombie, Integer> superWaveZombie = dataBord.generateZombies(2);
		
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, 0, 900, dataBord, selectedPlant);//no care but important
		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.size(), 1);

		int yOrigin = 150;
		
		if (dataBord.getLoadChoice() == "start") {
			BordView.setWidth(width);
			BordView.setHeight(height);
			dataBord = Map.dataBord(dataBord);
			plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant);
		}
		else {
			try {
				dataBord = (SimpleGameData) SystemFile.read("data", dataBord);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		BordView view = Map.view(dataBord);
		
		int squareSize = BordView.getSquareSize();
		
		view.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);

		

		Zombie.getSizeOfZombie();
		Pea.getSizeOfProjectile();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		boolean debug = false, debuglock = false, pause = false;
		
		int money = 0;
		
		List<Zombie> myZombies = dataBord.getMyZombies();
		List<Projectile> myBullet = dataBord.getMyBullet();
		List<LawnMower> myLawnMower = dataBord.getMyLawnMower();
		
		dataBord.spawnLawnMower(view, context);
		
		
		while (true) {
			myZombies = dataBord.getMyZombies();
			myBullet = dataBord.getMyBullet();
			myLawnMower = dataBord.getMyLawnMower();
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
				
				dataBord.spawnZombies( squareSize, str, myZombies, view, context, normalWaveZombie, superWaveZombie);
	
				/*------------------------------- CONFLICTS ----------------------------------*/
				
				Zombie.ZCheckConflict(myZombies, myBullet, dataBord.getMyPlants(), myLawnMower, deadPoolE, view, dataBord, str);
				
				/*-------------------------------- DEATHS ------------------------------------*/
				
				deadPoolE.deletingEverything(dataBord);
				
				/*--------------------------------SHOOTING------------------------------------*/
				
				dataBord.actionning(view);
				
				
				/*------------------------------- WIN / LOOSE --------------------------------*/
				dataBord.timeEnd(myZombies, str, context, superWaveZombie,view);
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
				
				money = dataBord.getActualMoney();
			}
		}

	}

	public static void main(String[] args) {
		
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

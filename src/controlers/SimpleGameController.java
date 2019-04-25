package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.time.Instant;
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

import models.plants.CherryBomb;
import models.plants.Peashooter;
import models.plants.Plant;
import models.plants.PotatoMine;
import models.plants.WallNut;

import models.projectiles.Bullet;
import models.projectiles.Projectile;

import models.zombies.ConeheadZombie;
import models.zombies.FlagZombie;
import models.zombies.NormalZombie;
import models.zombies.Zombie;

import views.BordView;
import views.SelectBordView;

public class SimpleGameController {
	
	static void simpleGame(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		Plant[] selectedPlant = {new Peashooter(), new WallNut(), new CherryBomb(), new PotatoMine()}; 
		HashMap<Zombie, Integer> selectedZombie = new HashMap<Zombie, Integer>();
		selectedZombie.put(new ConeheadZombie(), 1);
		selectedZombie.put(new FlagZombie(), 1);
		selectedZombie.put(new NormalZombie(), 1);
		

		SimpleGameData dataBord = new SimpleGameData(5, 8);
		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.length, 1);

		dataBord.setRandomMatrix();
		dataSelect.setRandomMatrix();
		int yOrigin = 100;
		int xOrigin = 450;

		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 900, dataBord);
		int squareSize = BordView.getSquareSize();
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, squareSize * selectedPlant.length, dataSelect, selectedPlant);
		

		view.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);

		ArrayList<Zombie> myZombies = new ArrayList<>();
		ArrayList<Projectile> myBullet = new ArrayList<>();

		int spawnRate = 1;
		int ZombieSize = Zombie.getSizeOfZombie();
		Bullet.getSizeOfProjectile();
		int deathCounterZombie = 0;
//		int deathCounterPlant = 0;
		Instant time = Instant.now();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		int day = 0;
		boolean debug = false;

		while (true) {
			/*--------------------------------DRAWS--------------------------------*/
			
			view.draw(context, dataBord);
			dataBord.movingZombiesAndBullets(context, view, myZombies, myBullet);
			plantSelectionView.draw(context, dataSelect);

			/*---------------------------INITIALISATION-----------------------------*/
			
			DeadPool deadPoolE = new DeadPool();
			int n = dataBord.RandomPosGenerator(300);
			int n2 = dataBord.RandomPosGenerator(600);
			

			/*-------------------------------ZOMBIE SPAWNERS-----------------------------*/
			
			if (day == 0 || spawnRate == n2) {
				myZombies.add(new FlagZombie((int) width,
						yOrigin + dataBord.RandomPosGenerator(5) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new FlagZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				day += 1;
			}
			if (spawnRate == n) {
				myZombies.add(new NormalZombie((int) width,
						yOrigin + dataBord.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new NormalZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}
			if (spawnRate == n2) {
				myZombies.add(new ConeheadZombie((int) width,
						yOrigin + dataBord.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new ConeheadZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}

			/*------------------------------- CONFLICTS ----------------------------------*/
			
			Zombie.ZCheckConflict(myZombies, myBullet, dataBord.getMyPlants(), deadPoolE, view, dataBord, str);
			
			/*-------------------------------- DEATHS ------------------------------------*/
			
			deadPoolE.deletingEverything(myZombies, dataBord.getMyPlants(), myBullet);
			
			/*--------------------------------SHOOTING------------------------------------*/
			
			dataBord.actionning(myBullet, view, myZombies);
			
			/*---------------------------------DEBUG--------------------------------------*/
			
			if (debug == true) {
				if (dataBord.spawnRandomPlant(context, dataSelect, view, plantSelectionView, selectedPlant)) {
					str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				}
			}
			
			/*------------------------------EVENTS----------------------------------*/

			Event event = context.pollOrWaitEvent(25); // modifier pour avoir un affichage fluide
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
				if (mdp == "Y") {
					debug = true;
				} // debug ON
				if (mdp == "N") {
					debug = false;
				} // debug OFF
			}

			/*------------------------------- WIN / LOOSE --------------------------------*/

			SimpleGameData.timeEnd(myZombies, time, str, context, deathCounterZombie, mdp);

			/*---Gestion de la selection de cellules et de la plante manuelle de plante---*/
			
			if (action != Action.POINTER_DOWN) {
				continue;
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			
			dataBord.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, x, y);
			
			/*-----------------------------------ELSE-------------------------------------*/
			
			
			
			
		}

	}

//	static void endGame(ApplicationContext context) {
//
//		ScreenInfo screenInfo = context.getScreenInfo();
//		float width = screenInfo.getWidth();
//		float height = screenInfo.getHeight();
//
//		SimpleGameData data = new SimpleGameData(1, 1);
//
//		data.setRandomMatrix();
//		int yOrigin = (int) (height/2.5);
//		int xOrigin = (int) (width/2.5);
//
//		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 400, data);
//
//
//
//		int WL = SimpleGameData.getWL();
//		
//		while (true) {
//			view.draw(context, data);
//			Event event = context.pollOrWaitEvent(20);
//			if (event == null) {
//				continue;
//			}
//			Action action = event.getAction();
//			
//			
//			if (WL == 0) {
//				view.drawString(context, data, "LOOSE", (int)width/2, (int)height/2, Color.WHITE);
//			}
//			else {
//				view.drawString(context, data, "WIN", (int)width/2, (int)height/2, Color.WHITE);
//			}
//			
//			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
//				context.exit(0);
//				return;
//			}
//		}
//		
//	}

	public static void main(String[] args) {
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
//		Application.run(Color.BLACK, SimpleGameController::endGame); // attention, utilisation d'une lambda.
	}
}

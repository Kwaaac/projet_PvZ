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
import models.plants.Plant;
import models.plants.day.CherryBomb;
import models.plants.day.Chomper;
import models.plants.day.Peashooter;
import models.plants.day.PotatoMine;
import models.plants.day.SnowPea;
import models.plants.day.SunFlower;
import models.plants.day.WallNut;
import models.plants.pool.Cattails;
import models.plants.pool.LilyPad;
import models.plants.pool.SeaShroom;
import models.plants.pool.TangleKelp;
import models.projectiles.Bullet;
import models.projectiles.Projectile;

import models.zombies.ConeheadZombie;
import models.zombies.FlagZombie;
import models.zombies.NormalZombie;
import models.zombies.Zombie;

import views.BordView;
import views.SelectBordView;



public class SimpleGameController {
	
//	static int startGame(ApplicationContext context) {
//
//		ScreenInfo screenInfo = context.getScreenInfo();
//		float width = screenInfo.getWidth();
//		float height = screenInfo.getHeight();
//		int choice;
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
//				view.drawString(context, (int)width/2, (int)height/2, "LOOSE");
//			}
//			else {
//				view.drawString(context, (int)width/2, (int)height/2, "WIN");
//			}
//			
//			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
//				context.exit(0);
//				return choice = 3;
//			}
//		}
//		return choice;
//		
//	}
	
	static void simpleGame(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		screenInfo.getWidth();
		Plant[] selectedPlant = {new SunFlower(), new Peashooter(), new TangleKelp(), new WallNut(), new CherryBomb(), new PotatoMine(), new Chomper()}; 
		
		HashMap<Zombie, Integer> normalWaveZombie = new HashMap<Zombie, Integer>();
		normalWaveZombie.put(new ConeheadZombie(), 1);
		normalWaveZombie.put(new NormalZombie(), 1);
		
		HashMap<Zombie, Integer> superWaveZombie = new HashMap<Zombie, Integer>();
		superWaveZombie.put(new ConeheadZombie(), 1);
		superWaveZombie.put(new FlagZombie(), 1);
		superWaveZombie.put(new NormalZombie(), 1);
		

		SimpleGameData dataBord = new SimpleGameData(5, 8);
		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.length, 1);

		dataBord.setRandomMatrix();
		dataSelect.setRandomMatrix();
		int yOrigin = 150;
		int xOrigin = 450;

		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 900, dataBord);
		int squareSize = BordView.getSquareSize();
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant);
		

		view.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);

		ArrayList<Zombie> myZombies = new ArrayList<>();
		ArrayList<Projectile> myBullet = new ArrayList<>();

		Zombie.getSizeOfZombie();
		Bullet.getSizeOfProjectile();
		int deathCounterZombie = 0;

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");

		boolean debug = false, debuglock = false;

		while (true) {
			/*-----------------------------CHECK CHRONO----------------------------*/
			plantSelectionView.checkCooldown();
			/*--------------------------------DRAWS--------------------------------*/
			
			view.draw(context, dataBord);
			debuglock = dataBord.movingZombiesAndBullets(context, view, myZombies, myBullet, debug, debuglock);
			plantSelectionView.draw(context, dataSelect);

			/*---------------------------INITIALISATION-----------------------------*/
			
			DeadPool deadPoolE = new DeadPool();
			
			/*--------------------------------SUN SPAWNERS----------------------------*/
			
			dataBord.naturalSun(view);

			/*-------------------------------ZOMBIE SPAWNERS-----------------------------*/
			
			SimpleGameData.spawnZombies(dataBord, squareSize, str, myZombies, view, context, normalWaveZombie, superWaveZombie);

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
				if (mdp == "N" && debug == true) {
					debug = false;
				} // debug OFF
			}

			/*------------------------------- WIN / LOOSE --------------------------------*/

			SimpleGameData.timeEnd(myZombies, str, context, deathCounterZombie, mdp);

			/*---Gestion de la selection de cellules et de la plante manuelle de plante---*/
			
			if (action != Action.POINTER_DOWN) {
				continue;
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			
			dataBord.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, x, y);
			
			
			
		}

	}

	static void endGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();

		SimpleGameData data = new SimpleGameData(1, 1);

		data.setRandomMatrix();
		int yOrigin = (int) (height/2.5);
		int xOrigin = (int) (width/2.5);

		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 200, data);



		int WL = SimpleGameData.getWL();
		
		while (true) {
			view.draw(context, data);
			Event event = context.pollOrWaitEvent(20);
			if (event == null) {
				continue;
			}
			Action action = event.getAction();
			
			
//			if (WL == 0) {
//				view.drawString(context, (int)width/2, (int)height/2, "LOOSE");
//			}
//			if (WL == 1) {
//				view.drawString(context, (int)width/2, (int)height/2, "WIN");
//			}
//			else {
//				view.drawString(context, (int)width/2, (int)height/2, "STOP");
//			}
			
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				context.exit(0);
				return;
			}
		}
		
	}

	public static void main(String[] args) {
//		Application.run(Color.BLACK, SimpleGameController::startGame); // attention, utilisation d'une lambda.
//		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
		Application.run(Color.BLACK, SimpleGameController::endGame); // attention, utilisation d'une lambda.
	}
}

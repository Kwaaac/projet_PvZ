package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

import models.SimpleGameData;
import models.Coordinates;
import models.DeadPool;
import models.Entities;
import plants.Bullet;
import plants.CherryBomb;
import plants.Peashooter;
import plants.Plant;
import plants.Projectile;
import plants.WallNut;

import zombies.ConeheadZombie;
import zombies.FlagZombie;
import zombies.NormalZombie;
import zombies.Zombie;

import views.BordView;
import views.SelectBordView;

public class SimpleGameController {

	static void simpleGame(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();

		SimpleGameData data = new SimpleGameData(5, 8);
		SimpleGameData data2 = new SimpleGameData(3, 1);

		data.setRandomMatrix();
		data2.setRandomMatrix();
		int yOrigin = 100;
		int xOrigin = 450;

		SelectBordView PlantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 540, data2);
		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 900, data);
		int squareSize = BordView.getSquareSize();

		view.draw(context, data);
		PlantSelectionView.draw(context, data2);

		Point2D.Float location;
		ArrayList<Zombie> myZombies = new ArrayList<>();
		ArrayList<Plant> myPlants = new ArrayList<>();
		ArrayList<Projectile> myBullet = new ArrayList<>();
		HashMap<Integer, Integer> possibilityX = new HashMap<Integer, Integer>(); // Test Mod tkt
		HashMap<Integer, Integer> possibilityY = new HashMap<Integer, Integer>(); // Test Mod tkt

		int spawnRate = 1;
		int ZombieSize = Zombie.getSizeOfZombie();
		Bullet.getSizeOfProjectile();
		int sizeOfPlant = Plant.getSizeOfPlant();
		int ok = 0;
		int deathCounterZombie = 0;
//		int deathCounterPlant = 0;
		Instant time = Instant.now();

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");
		
		int day = 0;
		boolean debug = false;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 5; y++) {

				possibilityX.put(x, Coordinates.CenteredX(view.realCoordFromIndex(x, xOrigin)));
				possibilityY.put(y, Coordinates.CenteredY(view.realCoordFromIndex(y, yOrigin)));

			}
		}

		while (true) {
			view.draw(context, data);
			PlantSelectionView.draw(context, data2);
			
			DeadPool deadPoolE = new DeadPool();

			int n = data.RandomPosGenerator(300);
			int n2 = data.RandomPosGenerator(600);
			
			if (day == 0 || spawnRate == n2) {
				myZombies.add(new FlagZombie((int) width,
						yOrigin + data.RandomPosGenerator(5) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new FlagZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				day += 1;
			}
			if (spawnRate == n) {
				myZombies.add(new NormalZombie((int) width,
						yOrigin + data.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new NormalZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}
			if (spawnRate == n2) {
				myZombies.add(new ConeheadZombie((int) width,
						yOrigin + data.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new ConeheadZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}
			
			

			/*------------------------Gestion des conflits--------------------------------*/
			Zombie.ZCheckConflict(myZombies,myBullet,myPlants,deadPoolE,view,data,str);

			/*----------------------------------------------------------------------------*/
			/*------------------------------- WIN / LOOSE --------------------------------*/
			for (Entities z : myZombies) {
				if (((Zombie) z).isEatingBrain(xOrigin, squareSize)) {
					Duration timeEnd = Duration.between(time, Instant.now());

					int h = 0, m = 0, s = 0;
					h = (int) (timeEnd.getSeconds() / 3600);
					m = (int) ((timeEnd.getSeconds() % 3600) / 60);
					s = (int) (((timeEnd.getSeconds() % 3600) % 60));

					str.append("-+-+-+-+-+-+-+-+-+-\nVous avez perdu...\nLa partie a duree : " + h + " heure(s) " + m
							+ " minute(s) " + s + " seconde(s)");
					// System.out.println(str.toString());
					SimpleGameData.setWL(0);
					context.exit(0);
					return;
				}
			}
			/*----------------------------------------------------------------------------*/
			/*-------------------- je détruit tout les elements morts --------------------*/
			
			deadPoolE.deletingEverything(myZombies, myPlants, myBullet);

			/*----------------------------------------------------------------------------*/
			
			data.leMove(context, view, myZombies, myBullet);

			/*----------------------------Shooting in continue----------------------------*/
			
			data.actionning(myPlants, myBullet, view, myZombies);
			
			/*----------------------------------------------------------------------------*/

			if (debug == true) {
				if (data.spawnRandomPlant(possibilityX, possibilityY, myPlants, view, context)) {
					str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				}
			}
			if (SimpleGameData.win(deathCounterZombie)) {
				Duration timeEnd = Duration.between(time, Instant.now());

				int h = 0, m = 0, s = 0;
				h = (int) (timeEnd.getSeconds() / 3600);
				m = (int) ((timeEnd.getSeconds() % 3600) / 60);
				s = (int) (((timeEnd.getSeconds() % 3600) % 60));

				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez GAGNEE !!!\nLa partie a duree : " + h + " heure(s) " + m
						+ " minute(s) " + s + " seconde(s)");
				System.out.println(str.toString());
				SimpleGameData.setWL(1);
				context.exit(0);
				return;
			}
			Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
			if (event == null) { // no event
				continue;
			}

			KeyboardKey KB = event.getKey();
			String mdp = null;
			if (KB != null) {
				mdp = KB.toString();
			}
			Action action = event.getAction();

			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {

				if (mdp == "SPACE") {
					Duration timeEnd = Duration.between(time, Instant.now());
					
					int h = 0, m = 0, s = 0;
					h = (int) (timeEnd.getSeconds() / 3600);
					m = (int) ((timeEnd.getSeconds() % 3600) / 60);
					s = (int) (((timeEnd.getSeconds() % 3600) % 60));

					str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\nLa partie a duree : " + h
							+ " heure(s) " + m + " minute(s) " + s + " seconde(s)");
					System.out.println(str.toString());
					SimpleGameData.setWL(0);
					context.exit(0);
					return;
				}

				if (mdp == "Y") {
					debug = true;
				} // debug ON
				if (mdp == "N") {
					debug = false;
				} // debug OFF
			}

//			if (action == Action.POINTER_DOWN) {
//				location = event.getLocation();
//				float x = location.x;
//				float y = location.y;
//				StringBuilder str = new StringBuilder("Mon clic : (");
//				str.append(x);
//				str.append(", ");
//				str.append(y);
//				str.append(")");
//				str.append(str.toString());
//			}

			if (action != Action.POINTER_DOWN) {
				continue;
			}

			if (!data.hasASelectedCell()) { // no cell is selected
				location = event.getLocation();
				float x = location.x;
				float y = location.y;

				if (xOrigin <= x && x <= xOrigin + squareSize * 8) {
					if (yOrigin <= y && y <= yOrigin + squareSize * 5) {

						int xCentered = Coordinates.CenteredX(view.realCoordFromIndex(view.columnFromX(location.x), xOrigin));
						int yCentered = Coordinates.CenteredY(view.realCoordFromIndex(view.lineFromY(location.y), xOrigin));

						if (ok != 0 && !(data.hasPlant(view.lineFromY(y), view.columnFromX(x)))) {

							if (ok == 1) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawPeashooter(context, data, xCentered, yCentered, "#90D322");
								myPlants.add(new Peashooter(xCentered-2, yCentered));
								ok = 0;
								str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
							}
							if (ok == 2) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawCherryBomb(context, data, xCentered, yCentered, "#CB5050");
								myPlants.add(new CherryBomb(xCentered, yCentered));
								ok = 0;
								str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
							}
							if (ok == 3) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawWallNut(context, data, xCentered, yCentered, "#ECB428");
								myPlants.add(new WallNut(xCentered, yCentered));
								ok = 0;
								str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
							}

						}
					}
				}

			} else {
				data.unselect();
			}

			if (!data2.hasASelectedCell()) { // no cell is selected
				location = event.getLocation();
				float x = location.x;
				float y = location.y;

				if (0 <= x && x <= squareSize) {
					if (yOrigin <= y && y <= yOrigin + squareSize * 3) {

						data2.selectCell(PlantSelectionView.lineFromY(y), PlantSelectionView.columnFromX(x));

						if (yOrigin <= y && y <= yOrigin + squareSize) {
							// str.append("plant 1");
							ok = 1;
						}
						if (yOrigin + squareSize <= y && y <= yOrigin + squareSize * 2) {
							// str.append("plant 2");
							ok = 2;
						}
						if (yOrigin + squareSize * 2 <= y && y <= yOrigin + squareSize * 3) {
							// str.append("plant 3");
							ok = 3;
						}

					}
				}
			} else {
				data2.unselect();
			}

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

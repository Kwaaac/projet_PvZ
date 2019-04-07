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
import models.DeadPool;

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
		new ArrayList<>();
		ArrayList<Zombie> MyZombies = new ArrayList<>();
		ArrayList<Plant> MyPlants = new ArrayList<>();
		ArrayList<Projectile> MyBullet = new ArrayList<>();
		HashMap<Integer, Integer> possibilityX = new HashMap<Integer, Integer>(); //Test Mod tkt
		HashMap<Integer, Integer> possibilityY = new HashMap<Integer, Integer>(); //Test Mod tkt

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
		int debug = 0;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 5; y++) {
				
				float X = view.realCoordFromIndex(x,xOrigin);
				float Y = view.realCoordFromIndex(y,yOrigin);
				int xCentered = (int) (X + (squareSize / 2) - (sizeOfPlant / 2));
				int yCentered = (int) (Y + (squareSize / 2) - (sizeOfPlant / 2));
				
				possibilityX.put(x,xCentered);
				possibilityY.put(y,yCentered);
				
			}
		}

		while (true) {
			view.draw(context, data);
			PlantSelectionView.draw(context, data2);

			DeadPool deadPoolZ = new DeadPool();
			DeadPool deadPoolP = new DeadPool();
			DeadPool deadPoolBullet = new DeadPool();

			if (day == 0) {
				MyZombies.add(new FlagZombie((int) width,
						yOrigin + data.RandomPosGenerator(5) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new FlagZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				day += 1;
			}

			int n = data.RandomPosGenerator(1000);
			int n2 = data.RandomPosGenerator(2000);
			if (spawnRate == n) {
				MyZombies.add(new NormalZombie((int) width,
						yOrigin + data.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new NormalZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}
			if (spawnRate == n2) {
				MyZombies.add(new ConeheadZombie((int) width,
						yOrigin + data.RandomPosGenerator(4) * squareSize + (squareSize / 2) - ZombieSize / 2));
				str.append("new ConeheadZombie (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
			}

			for (Zombie z : MyZombies) {
				z.Go();
				z.incAS();
				for (Projectile b : MyBullet) {
					if (z.hit(b)) { // il faut ajouter aux getX le rayon des zombie et des bullet pour detecter la
									// collision entre l'extremiter des ellipse
						str.append("conflit start:\n\tzombie damage" + z.getDamage() + "zombie life " + z.getLife()
								+ "\n\tbullet damage" + b.getDamage() + " bullet life" + b.getLife() + "\n");

						b.conflict(z);
						if (b.getX() > xOrigin + squareSize * 8 || b.getLife() <= 0) {
							str.append(b + "meurt\n");
							deadPoolBullet.add(MyBullet.indexOf(b));
							str.append(deadPoolBullet);
						}
						str.append("conflit end:\n\tzombie damage" + z.getDamage() + "zombie life " + z.getLife()
								+ "\n\tbullet damage" + b.getDamage() + " bullet life" + b.getLife() + "\n");
					}
				}
				for (Plant p : MyPlants) { // a completer quand on aura des plante dans le plateau

					if (z.hit(p)) {
						z.Stop();
						if (z.readyToshot()) {
							str.append("conflit start:\n\tzombie damage" + z.getDamage() + "zombie life " + z.getLife()
									+ "\n\tplant damage" + p.getDamage() + " plant life" + p.getLife() + "\n");
							p.conflict(z);
							if (p.getLife() <= 0) {
								str.append(p + "meurt\n");
								deadPoolP.add(MyPlants.indexOf(p));
								data.plantOutBord(view.lineFromY(p.getY()), view.columnFromX(p.getX()));
								str.append(deadPoolP + "\n");
							}
							str.append("conflit end:\n\tzombie damage" + z.getDamage() + "zombie life " + z.getLife()
									+ "\n\tplant damage" + p.getDamage() + " plant life" + p.getLife() + "\n");
						}

					}
				}
				if (z.getX() < xOrigin - squareSize / 2 || z.getLife() <= 0) {
					str.append(z + "meurt\n");
					deadPoolZ.add(MyZombies.indexOf(z));
					str.append(deadPoolZ + "\n");
				}
			}
			/*----------------------------------------------------------------------------*/
			/*--------------- je place les element mort dans les dead pool ---------------*/

			for (Projectile b : MyBullet) {
				if (b.getX() > xOrigin + squareSize * 8) {
					str.append(b + "meurt\n");
					deadPoolBullet.add(MyBullet.indexOf(b));
					str.append(deadPoolBullet + "\n");
				}
			}

			/*----------------------------------------------------------------------------*/
			/*------------------------------- WIN / LOOSE --------------------------------*/
			for (Zombie b : MyZombies) {
				if (b.getX() < xOrigin - squareSize / 2) {
					Duration timeEnd = Duration.between(time, Instant.now());

					int h = 0, m = 0, s = 0;
					h = (int) (timeEnd.getSeconds() / 3600);
					m = (int) ((timeEnd.getSeconds() % 3600) / 60);
					s = (int) (((timeEnd.getSeconds() % 3600) % 60));

					str.append("-+-+-+-+-+-+-+-+-+-\nVous avez perdu...\nLa partie a duree : " + h + " heure(s) " + m
							+ " minute(s) " + s + " seconde(s)");
					System.out.println(str.toString());
					SimpleGameData.setWL(0);
					context.exit(0);
					return;
				}
			}
			/*----------------------------------------------------------------------------*/
			/*-------------------- je d�truit tout les elements morts --------------------*/
			if (!(deadPoolZ.empty())) {
				deadPoolZ.reverseSort();
				for (int d : deadPoolZ.getDeadPool()) {
					System.out.println("deadPoolZ" + deadPoolZ);
					if (MyZombies.contains(MyZombies.get(d))) {
						MyZombies.remove(d);
						deathCounterZombie += 1;
						str.append("zombie killed (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
					}
				}
			}
			
			if (!(deadPoolP.empty())) {
				deadPoolP.reverseSort();
				for (int p : deadPoolP.getDeadPool()) {
					System.out.println("deadPoolP" + deadPoolP);
					if (MyPlants.contains(MyPlants.get(p))) {
						MyPlants.remove(p);
						str.append("plant killed (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
					}
				}
			}
			
			if (!(deadPoolBullet.empty())) {
				deadPoolBullet.reverseSort();
				for (int d : deadPoolBullet.getDeadPool()) {
					System.out.println("deadPoolBullet" + deadPoolBullet);
					if (MyBullet.contains(MyBullet.get(d))) {
						MyBullet.remove(d);
					}
				}
			}

			/*----------------------------------------------------------------------------*/

			for (Zombie b : MyZombies) {
				if (b.getMove()) {
					view.moveAndDrawElement(context, data, b);
				}
			}

			for (Projectile b : MyBullet) {
				view.moveAndDrawElement(context, data, b);
			}

			/*----------------------------Shooting in continue----------------------------*/
			for (Plant p : MyPlants) {
				if (p instanceof Peashooter) {
					// str.append(timeS);
					p.incAS();
					if (p.readyToshot()) {
						MyBullet.add(new Bullet(p.getX() + sizeOfPlant, p.getY() + (sizeOfPlant / 2) - 10));
						p.resetAS();
					}
				}
				if (p instanceof CherryBomb) {
					
				}
			}
			/*----------------------------------------------------------------------------*/

			if (debug == 1) {
				if(data.spawnRandomPlant(possibilityX, possibilityY, MyPlants, view, context)) {
					str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
				}
				
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

					str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\nLa partie a duree : " + h + " heure(s) " + m + " minute(s) " + s
							+ " seconde(s)");
					System.out.println(str.toString());
					SimpleGameData.setWL(0);
					context.exit(0);
					return;
				}

				if (mdp == "Y") {
					debug = 1;
				} // debug ON
				if (mdp == "N") {
					debug = 0;
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

						float X = view.realCoordFromIndex(view.columnFromX(location.x), xOrigin);
						float Y = view.realCoordFromIndex(view.lineFromY(location.y), yOrigin);

						int xCentered = (int) (X + (squareSize / 2) - (sizeOfPlant / 2));
						int yCentered = (int) (Y + (squareSize / 2) - (sizeOfPlant / 2));
						
						if (ok != 0) {
							System.out.println(possibilityX);
							System.out.println(possibilityY);
							
							System.out.println(xCentered + " -///- " + yCentered);
							
							System.out.println();
							
							if (ok == 1) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawPeashooter(context, data, xCentered, yCentered, "#90D322");
								MyPlants.add(new Peashooter(xCentered, yCentered));
								ok = 0;
								str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
							}
							if (ok == 2) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawCherryBomb(context, data, xCentered, yCentered, "#CB5050");
								MyPlants.add(new CherryBomb(xCentered, yCentered));
								ok = 0;
								str.append("new plant (" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");
							}
							if (ok == 3) {
								data.plantOnBoard(view.lineFromY(y), view.columnFromX(x));
								view.drawWallNut(context, data, xCentered, yCentered, "#ECB428");
								MyPlants.add(new WallNut(xCentered, yCentered));
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

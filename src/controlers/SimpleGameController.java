package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.HorizontallyMovingElement;
import models.MovingElement;
import models.SimpleGameData;
import plants.Bullet;
import plants.Plant;
import zombies.NormalZombie;
import zombies.Zombie;
import views.BordView;
import views.SelectBordView;


public class SimpleGameController {

	public static int RandomPosGenerator() {
		Random random = new Random();
		int randomPosition = random.nextInt((4 - 0) + 1) + 0;
		return randomPosition;
	}
	
	static void simpleGame(ApplicationContext context) {
		// get the size of the screen
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		//System.out.println("size of the screen (" + width + " x " + height + ")");

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
		ArrayList<Zombie> MyZombies = new ArrayList<>();
		ArrayList<Plant> MyPlants = new ArrayList<>();
		ArrayList<HorizontallyMovingElement> MyBullet = new ArrayList<>();
        
        int n1 = 25;
        int ZombieSize = Zombie.getSizeOfZombie();
        int ok = 0;
//		int deathCounterZombie = 0;
//		int deathCounterPlant = 0;
//		int time = 0;
        System.out.println(("Journal de bord\n-+-+-+-+-+-+-+-+-+-"));
        
        while (true) {
//        	time++;
        	view.draw(context, data);
        	
        	PlantSelectionView.draw(context, data2);
        	
        	
        	ArrayList<Integer> deadPoolZ = new ArrayList<>();
        	ArrayList<Integer> deadPoolP = new ArrayList<>();
        	ArrayList<Integer> deadPoolBullet = new ArrayList<>();
            
            Random rand = new Random();
            
            int n = rand.nextInt(50);
            
            if(n1 == n) {
                MyZombies.add(new NormalZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2)); 
                System.out.println("new zombie ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            
            for (Zombie b : MyZombies) {
                if(b.getX() < xOrigin-squareSize/2){
                    deadPoolZ.add(MyZombies.indexOf(b));
                }
            }
            
            for (Plant p : MyPlants) {
                if(p.getX() < xOrigin-squareSize/2){
                    deadPoolP.add(MyPlants.indexOf(p));
                }
            }
            
            for (HorizontallyMovingElement b : MyBullet) {
                if(b.getX() < xOrigin-squareSize/2){
                    deadPoolBullet.add(MyBullet.indexOf(b));
                }
            }
            
            for (int d : deadPoolZ) {
            	MyZombies.remove(d);
//            	deathCounterZombie+=1;
            	System.out.println("zombie killed ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            
            for (int p : deadPoolP) {
            	MyPlants.remove(p);
//            	deathCounterPlant+=1;
            	System.out.println("plant killed ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            
            for (int d : deadPoolBullet) {
            	MyBullet.remove(d);
            }
                
            for (MovingElement b : MyZombies) {
                view.moveAndDrawElement(context, data, b);
            }
            
            for (MovingElement b : MyBullet) {
                view.moveAndDrawElement(context, data, b);
            }
			
			Event event = context.pollOrWaitEvent(50); // modifier pour avoir un affichage fluide
			if (event == null) { // no event
				continue;
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				context.exit(0);
				return;
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
//				System.out.println(str.toString());
//			}

			
			
			
			
			
			if (action != Action.POINTER_DOWN) {
				continue;
			}

			if (!data.hasASelectedCell()) { // no cell is selected
				location = event.getLocation();
				float x = location.x;
				float y = location.y;
				
				if (xOrigin<=x && x<=xOrigin+squareSize*8) {
					if (yOrigin<=y && y<=yOrigin+squareSize*5) {
						
						float X = view.realCoordFromIndex(view.columnFromX(location.x), xOrigin);
						float Y = view.realCoordFromIndex(view.lineFromY(location.y), yOrigin);
						int sizeOfPlant = Plant.getSizeOfPlant();
						
						int xCentered = (int) (X+(squareSize/2)-(sizeOfPlant/2));
						int yCentered = (int) (Y+(squareSize/2)-(sizeOfPlant/2));
						
						if (ok != 0) {
							
							if (ok == 1) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#90D322");
								MyBullet.add(new Bullet(xCentered+sizeOfPlant, yCentered+(sizeOfPlant/2)-10));
								ok = 0;
								System.out.println("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
							}
							if (ok == 2) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#CB5050");
								ok = 0;
								System.out.println("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
							}
							if (ok == 3) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#ECB428");
								ok = 0;
								System.out.println("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
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
				
				if (0<=x && x<=squareSize) {
					if (yOrigin<=y && y<=yOrigin+squareSize*3) {
						
						data2.selectCell(PlantSelectionView.lineFromY(y), PlantSelectionView.columnFromX(x));

						if (yOrigin<=y && y<=yOrigin+squareSize) {
							//System.out.println("plant 1");
							ok = 1;
						}
						if (yOrigin+squareSize<=y && y<=yOrigin+squareSize*2) {
							//System.out.println("plant 2");
							ok = 2;
						}
						if (yOrigin+squareSize*2<=y && y<=yOrigin+squareSize*3) {
							//System.out.println("plant 3");
							ok = 3;
						}
						
					}
				}
			} else {
				data2.unselect();
			}
			
			
		}
        
        
	}

	public static void main(String[] args) {
		// pour changer de jeu, remplacer stupidGame par le nom de la m�thode de jeu
		// (elle doit avoir extaement la mieme en-t�te).
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

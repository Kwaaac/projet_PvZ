package Controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import Models.MovingElement;
import Models.SimpleGameData;
import Models.Plants.Plant;
import Models.Zombies.NormalZombie;
import Models.Zombies.Zombie;
import Views.SimpleGameView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;


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
		
		SimpleGameView PlantSelectionView = SimpleGameView.initGameGraphics(0, yOrigin, 0, data2);
		SimpleGameView view = SimpleGameView.initGameGraphics(xOrigin, yOrigin, 900, data);
		int squareSize = SimpleGameView.getSquareSize();
		
		
		view.draw(context, data);
		PlantSelectionView.draw(context, data2);

		Point2D.Float location;
		ArrayList<Zombie> MyZombies = new ArrayList<>();
        
        int n1 = 25;
        int ZombieSize = Zombie.getSizeOfZombie();
        int ok = 0;
		//int deathCounter = 0;
        
        while (true) {
        	view.draw(context, data);
        	PlantSelectionView.draw(context, data2);
        	ArrayList<Integer> deadPool = new ArrayList<>();
            
            Random rand = new Random();
            
            int n = rand.nextInt(50);
            
            if(n1 == n) {
                MyZombies.add(new NormalZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2));            
            }
            
            for (Zombie b : MyZombies) {
                if(b.getX() < xOrigin-squareSize/2){
                    deadPool.add(MyZombies.indexOf(b));
                }
            }
            
            for (int d : deadPool) {
            	MyZombies.remove(d);
            	//deathCounter+=1;
            }
                
            for (MovingElement b : MyZombies) {
                view.moveAndDrawElement(context, data, b);
            }
			
			Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
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
						
						data.selectCell(view.lineFromY(y), view.columnFromX(x));
						
						float X = view.realCoordFromIndex(view.columnFromX(location.x), xOrigin);
						float Y = view.realCoordFromIndex(view.lineFromY(location.y), yOrigin);
						int sizeOfPlant = Plant.getSizeOfPlant();
						
						int xCentered = (int) (X+(squareSize/2)-(sizeOfPlant/2));
						int yCentered = (int) (Y-(squareSize/2)+(sizeOfPlant/2));
						
						if (ok != 0) {
							
							System.out.println(X+","+Y);
//							System.out.println(ok);
							if (ok == 1) {
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#90D322");
							}
							if (ok == 2) {
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#CB5050");
							}
							if (ok == 3) {
								view.drawOnlyOneCell(context, data, xCentered, yCentered, "#ECB428");
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

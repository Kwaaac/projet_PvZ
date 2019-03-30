package Controlers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import Models.MovingElement;
import Models.SimpleGameData;
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
		float height = screenInfo.getHeight();
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
        float C1X = 0;
        float C1Y = 0;
		//int deathCounter = 0;
        while (true) {
        	view.draw(context, data);
        	ArrayList<Integer> deadPool = new ArrayList<>();
            
            Random rand = new Random();
            
            int n = rand.nextInt(50);
            
            if(n1 == n) {
                MyZombies.add(new NormalZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2));            
            }
            
            for (Zombie b : MyZombies) {
                if(b.getX() < 3*squareSize-squareSize/2){
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
			
			
			
			if (action == Action.POINTER_DOWN) {
				location = event.getLocation();
				C1X = (int)location.x;
				C1Y = (int)location.y;
				StringBuilder str = new StringBuilder("Mon clic : (");
				str.append(C1X);
				str.append(", ");
				str.append(C1Y);
				str.append(")");
				System.out.println(str.toString());
			}

			if (0<C1X && C1X<=squareSize && yOrigin<=C1Y && C1Y<=yOrigin+(squareSize*3)) {
				if (yOrigin<=C1Y && C1Y<=yOrigin+squareSize) {
					//System.out.println("plant 1");
					ok = 1;
				}
				if (yOrigin+squareSize<=C1Y && C1Y<=yOrigin+squareSize*2) {
					//System.out.println("plant 2");
					ok = 2;
				}
				if (yOrigin+squareSize*2<=C1Y && C1Y<=yOrigin+squareSize*3) {
					//System.out.println("plant 3");
					ok = 3;
				}
			}
			
			
			if (ok > 0 && action == Action.POINTER_DOWN) {
				location = event.getLocation();
				if (xOrigin<=C1X && C1X<=xOrigin+squareSize*8) {
					if (yOrigin<=C1Y && C1Y<=yOrigin+squareSize*5) {
						int x = view.columnFromX(location.x);
						int y = view.lineFromY(location.y);
						
						if (ok == 1) {
							float X = view.realCoordFromIndex(x, xOrigin);
							float Y = view.realCoordFromIndex(y, yOrigin);
							view.drawOnlyOneCell(context, data, (int) X, (int) Y);
						}
						if (ok == 2) {
							System.out.println("2");
						}
						if (ok == 3) {
							System.out.println("3");
						}
										
					}
				}
			}
			
			if (action != Action.POINTER_DOWN) {
				continue;
			}

			if (!data.hasASelectedCell()) { // no cell is selected
				location = event.getLocation();
				if (xOrigin<=location.x && location.x<=xOrigin+squareSize*8) {
					if (yOrigin<=location.y && location.y<=yOrigin+squareSize*5) {
						data.selectCell(view.lineFromY(location.y), view.columnFromX(location.x));
					}
				}
			} else {
				data.unselect();
			}
			
			if (!data2.hasASelectedCell()) { // no cell is selected
				location = event.getLocation();
				if (0<=location.x && location.x<=squareSize) {
					if (yOrigin<=location.y && location.y<=yOrigin+squareSize*3) {
						data2.selectCell(PlantSelectionView.lineFromY(location.y), PlantSelectionView.columnFromX(location.x));
					}
				}
			} else {
				data2.unselect();
			}
			
			
			PlantSelectionView.draw(context, data2);
		}
        
	}

	public static void main(String[] args) {
		// pour changer de jeu, remplacer stupidGame par le nom de la m�thode de jeu
		// (elle doit avoir extaement la mieme en-t�te).
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

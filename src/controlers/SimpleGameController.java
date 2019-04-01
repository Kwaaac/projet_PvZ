package controlers;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TreeSet;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import models.Entities;
import models.MovingElement;
import models.SimpleGameData;

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

	public static int RandomPosGenerator() {
		Random random = new Random();
		int randomPosition = random.nextInt((4 - 0) + 1) + 0;
		return randomPosition;
	}
	
	static void simpleGame(ApplicationContext context) {
		// get the size of the screen
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		//str.append("size of the screen (" + width + " x " + height + ")");

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
		ArrayList<Entities> MyEntities = new ArrayList<>();
		ArrayList<Zombie> MyZombies = new ArrayList<>();
		ArrayList<Plant> MyPlants = new ArrayList<>();
		ArrayList<Projectile> MyBullet = new ArrayList<>();
        
		
        int spawnRate = 1;
        int ZombieSize = Zombie.getSizeOfZombie();
        int BulletSize = Bullet.getSizeOfProjectile();
        int sizeOfPlant = Plant.getSizeOfPlant();
        int ok = 0;
//		int deathCounterZombie = 0;
//		int deathCounterPlant = 0;
        Instant time = Instant.now();

        StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n");
        int day = 0;
        
        
        while (true) {
        	
        	view.draw(context, data);
        	PlantSelectionView.draw(context, data2);
        	
        	
        	ArrayList<Integer> deadPoolZ = new ArrayList<>();
        	ArrayList<Integer> deadPoolP = new ArrayList<>();
        	ArrayList<Integer> deadPoolBullet = new ArrayList<>();
            
        	
        	if (day == 0) {
        		MyZombies.add(new FlagZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2));
                str.append("new FlagZombie ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
                day+=1;
        	}
        	
        	
            Random rand = new Random();
            int n = rand.nextInt(1000);
            int n2 = rand.nextInt(2000);
            if(spawnRate == n) {
                MyZombies.add(new NormalZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2));
                str.append("new NormalZombie ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            if(spawnRate == n2) {
                MyZombies.add(new ConeheadZombie((int) width, yOrigin+RandomPosGenerator()*squareSize+(squareSize/2)-ZombieSize/2));
                str.append("new ConeheadZombie ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
 
            
/*---------------------------- je g�re les conflits --------------------------*/			
			int j=0;
            for(Zombie z : MyZombies) {
            	z.Go();
        		z.incAS();
            	
				j++;
				float Zx1 =  z.getX(); //centre zombie
				float Zx2 =  Zx1 - (ZombieSize/3)-2; //bordure gauche zombie
				int i=0;
				for(Projectile b : MyBullet) {
					i++;
					float Bx1 =  b.getX(); //centre bullet
					float Bx2 =  Bx1 + (BulletSize/3)+2 ; //bordure droite bullet
					
					if(view.lineFromY(z.getY()) == view.lineFromY(b.getY())) {
						if (Bx1 < Zx2 && Zx2 <= Bx2) { // il faut ajouter aux getX le rayon des zombie et des bullet pour detecter la collision entre l'extremiter des ellipse
							str.append("conflit start:\n\tzombie damage"+z.getDamage()+"zombie life "+z.getLife()+"\n\tbullet damage"+b.getDamage()+" bullet life"+b.getLife()+"\n");
							
							b.conflict(z);
							if(b.getX() > xOrigin+squareSize*8 || b.getLife() <= 0){
			                	str.append(b+"meurt\n");
			                    deadPoolBullet.add(MyBullet.indexOf(b));
			                    str.append(deadPoolBullet);
			                }
							str.append("conflit end:\n\tzombie damage"+z.getDamage()+"zombie life "+z.getLife()+"\n\tbullet damage"+b.getDamage()+" bullet life"+b.getLife()+"\n");
						}
					}
				}
				for(Plant p : MyPlants) {    // a completer quand on aura des plante dans le plateau
					
					float Px1 =  p.getX(); //centre bullet
					float Px2 =  Px1 + (sizeOfPlant/3)+2 ; //bordure droite bullet
					if(view.lineFromY(z.getY()) == view.lineFromY(p.getY())) {
						if(Px1 < Zx2 && Zx2 <= Px2) {
							z.Stop();
							if(z.readyToshot()) {
								str.append("conflit start:\n\tzombie damage"+z.getDamage()+"zombie life "+z.getLife()+"\n\tplant damage"+p.getDamage()+" plant life"+p.getLife()+"\n");           
								p.conflict(z);
								if(p.getLife() <= 0){
				                	str.append(p+"meurt\n");
				                    deadPoolP.add(MyPlants.indexOf(p));
				                    data.plantOutBord(view.lineFromY(p.getY()),view.columnFromX(p.getX())); 
				                    str.append(deadPoolP+"\n");
				                }
								str.append("conflit end:\n\tzombie damage"+z.getDamage()+"zombie life "+z.getLife()+"\n\tplant damage"+p.getDamage()+" plant life"+p.getLife()+"\n");
							}
							
						}
					}
				}
				if(z.getX() < xOrigin-squareSize/2 || z.getLife() <= 0) {
                	str.append(z+"meurt\\n");
                    deadPoolZ.add(MyZombies.indexOf(z));
                    str.append(deadPoolZ);
                }
			}
/*----------------------------------------------------------------------------*/ 		
/*--------------- je place les element mort dans les dead pool ---------------*/
            
            for (Projectile b : MyBullet) {
            	if(b.getX() > xOrigin+squareSize*8){
                	str.append(b+"meurt\n");
                    deadPoolBullet.add(MyBullet.indexOf(b));
                    str.append(deadPoolBullet);
                }
            }
            

/*----------------------------------------------------------------------------*/           
/*------------------------------- WIN / LOOSE --------------------------------*/
            for (Zombie b : MyZombies) {
                if(b.getX() < xOrigin-squareSize/2) {
                	Duration timeEnd = Duration.between(time,Instant.now());
    				
    				int h = 0, m = 0, s = 0;
    				h=(int) (timeEnd.getSeconds() / 3600);
    				m=(int) ((timeEnd.getSeconds() % 3600) / 60);
    				s=(int) (((timeEnd.getSeconds() % 3600) % 60));
    				
    				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez perdu...\nLa partie � dur�e : "+h+" heure(s) "+m+" minute(s) "+s+" seconde(s)");
    				System.out.println(str.toString());
    				context.exit(0);
    				return;
                }
            }
/*----------------------------------------------------------------------------*/           
/*-------------------- je d�truit tout les elements morts --------------------*/           
            
            for (int d : deadPoolZ) {
            	MyZombies.remove(d);
//            	deathCounterZombie+=1;
            	str.append("zombie killed ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            
            for (int p : deadPoolP) {
            	MyPlants.remove(p);
//            	deathCounterPlant+=1;
            	str.append("plant killed ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
            }
            
            for (int d : deadPoolBullet) {
            	MyBullet.remove(d);
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
//            		str.append(timeS);
            		p.incAS();
            		if (p.readyToshot()) {
            			MyBullet.add(new Bullet(p.getX()+sizeOfPlant, p.getY()+(sizeOfPlant/2)-10));
            			p.resetAS();
            		}
            	}
            }
/*----------------------------------------------------------------------------*/
            
            

			
			Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
			if (event == null) { // no event
				continue;
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				Duration timeEnd = Duration.between(time,Instant.now());
				
				int h = 0, m = 0, s = 0;
				h=(int) (timeEnd.getSeconds() / 3600);
				m=(int) ((timeEnd.getSeconds() % 3600) / 60);
				s=(int) (((timeEnd.getSeconds() % 3600) % 60));
				
				str.append("-+-+-+-+-+-+-+-+-+-\nLa partie � dur�e : "+h+" heure(s) "+m+" minute(s) "+s+" seconde(s)");
				System.out.println(str.toString());
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
//				str.append(str.toString());
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
						
						int xCentered = (int) (X+(squareSize/2)-(sizeOfPlant/2));
						int yCentered = (int) (Y+(squareSize/2)-(sizeOfPlant/2));
						
						if (ok != 0) {
							
							if (ok == 1) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawPeashooter(context, data, xCentered, yCentered, "#90D322");
								MyPlants.add(new Peashooter(xCentered,yCentered));
								MyBullet.add(new Bullet(xCentered+sizeOfPlant, yCentered+(sizeOfPlant/4)+10));
								ok = 0;
								str.append("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
							}
							if (ok == 2) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawCherryBomb(context, data, xCentered, yCentered, "#CB5050");
								MyPlants.add(new CherryBomb(xCentered,yCentered));
								ok = 0;
								str.append("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
							}
							if (ok == 3) {
								data.plantOnBoard(view.lineFromY(y),view.columnFromX(x));
								view.drawWallNut(context, data, xCentered, yCentered, "#ECB428");
								MyPlants.add(new WallNut(xCentered,yCentered));
								ok = 0;
								str.append("new plant ("+new SimpleDateFormat("hh:mm:ss").format(new Date())+")\n");
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
							//str.append("plant 1");
							ok = 1;
						}
						if (yOrigin+squareSize<=y && y<=yOrigin+squareSize*2) {
							//str.append("plant 2");
							ok = 2;
						}
						if (yOrigin+squareSize*2<=y && y<=yOrigin+squareSize*3) {
							//str.append("plant 3");
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

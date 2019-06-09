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
import models.TombStone;
import models.map.Map;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.NormalZombie;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;

public class SimpleGameController {

	static void simpleGame(ApplicationContext context) {
		
		ScreenInfo screenInfo = context.getScreenInfo(); //détection des informations de l'écran
		int width = (int) screenInfo.getWidth(); //largeur
		int height = (int) screenInfo.getHeight(); //hauteur

		SimpleGameData dataBord = new SimpleGameData(); //SimpleGameData ayant pour unique but de debugué le jeu en cas de sauvegarde. NE PAS TOUCHER !!

		ArrayList<Plant> selectedPlant = PrincipalMenuController.startGame(context, dataBord); //on récupère les plantes sélectionné
		
		HashMap<Zombie, Integer> normalWaveZombie = new HashMap<>();// initialisation d'un dictionnaire de zombie et du nombre de celui-ci qui apparaiteront
		normalWaveZombie.put(new NormalZombie(), 1);

		//dataBord.generateZombies(1)
		HashMap<Zombie, Integer> superWaveZombie = dataBord.generateZombies(2);

		int yOrigin = 100; // on défini un affichage qui ne dépassera pas 100 px au dessus de l'écran

		SimpleGameData dataSelect = new SimpleGameData(selectedPlant.size(), 1, 2); //initialistion d'une matrice de ?x7 contenant l'ensembles des plantes joués
		
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant); //initialistion d'une matrice de ixj qui sera le tableau de jeu

		BordView.setWidth(width);
		BordView.setHeight(height);
		
		if (dataBord.getLoadChoice() == "start") { // si on a décidé de lancer une nouvelle partie
			dataBord = Map.dataBord(); // on charge de nouvelle données de jeu
		} else { // si on a relancé une sauvegarde
			try {
				dataBord = (SimpleGameData) SystemFile.read("data"); // on charge les données de jeu de la sauvegarde
				plantSelectionView = SelectBordView.initGameGraphics(0, yOrigin, 900, dataSelect, selectedPlant); // on la vue de selection de plante de la sauvegarde
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		BordView view = Map.view(dataBord); // création de la vues du plateau de jeu

		int squareSize = BordView.getSquareSize(); // définition de la longeur de référence d'un carré

		view.drawRectangle(context, 0, 0, width, height, "#cbd9ef"); //background
		plantSelectionView.draw(context, dataSelect); //affichage des plantes selectionnables

		// ---------------WHAT DO I HAVE IN MY GAME ?---------------//
//		System.out.println(view + "\n\n" + plantSelectionView + "\n\n" + dataBord + "\n\n" + dataSelect);
		// ---------------------------------------------------------//

		Zombie.getSizeOfZombie();//définition de la taille de référence d'un zombie
		Pea.getSizeOfProjectile();//définition de la taille de référence d'un projectile

		StringBuilder str = new StringBuilder("Journal de bord\n-+-+-+-+-+-+-+-+-+-\n"); //initialisation du journal de bord

		boolean debug = false, pause = false, shift = false;

		//initialisation des arrayliste des entitées du jeu
		List<Zombie> myZombies = dataBord.getMyZombies(); //zombies
		List<Projectile> myBullet = dataBord.getMyBullet(); //boullettes
		List<LawnMower> myLawnMower = dataBord.getMyLawnMower(); //tondeuses
		List<TombStone> myTombstone = dataBord.getMyTombstone(); //tombes

		dataBord.spawnLawnMower(view, context);
		
		try {
			SystemFile.readProperties(dataBord); // lecture des propriétés modifiables du jeu
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dataBord.createBord(SimpleGameData.getMap()); //création de la map

		while (true) {
			//remplissage des listes
			myZombies = dataBord.getMyZombies();
			myBullet = dataBord.getMyBullet();
			myLawnMower = dataBord.getMyLawnMower();
			myTombstone = dataBord.getMyTombstone();
			
//			//test
//			for(Cell c: dataBord.getLineCell(5, 0)) {
//				System.out.println(c.getProjectilesInCell());
//			}
			if (pause) { //si on est dans le menu secondaire, on affiche celui ci 
				pause = SecondaryMenuController.menu(context, view, dataBord, plantSelectionView);
			} else { //sinon on affiche la continuité du jeu
				/*-----------------------------CHECK CHRONO----------------------------*/

				plantSelectionView.checkCooldown();

				/*--------------------------------DRAWS--------------------------------*/
				view.drawAll(context, dataBord, view, myZombies, myBullet, myLawnMower, debug, dataSelect,
						dataBord.getActualMoney(), dataBord.getFertilizer(), plantSelectionView);

				/*---------------------------INITIALISATION-----------------------------*/

				DeadPool deadPoolE = new DeadPool();

				/*--------------------------------SUN SPAWNERS----------------------------*/

				dataBord.naturalSun(view);

				/*-------------------------------ZOMBIE SPAWNERS-----------------------------*/

				dataBord.spawnZombies(squareSize, str, myZombies, view, context, normalWaveZombie, superWaveZombie);

				/*------------------------------- CONFLICTS ----------------------------------*/

				Zombie.ZCheckConflict(myZombies, myBullet, dataBord.getMyPlants(), myLawnMower, deadPoolE, view,
						dataBord, str);

				/*-------------------------------- DEATHS ------------------------------------*/

				deadPoolE.deletingEverything(dataBord);

				/*--------------------------------SHOOTING------------------------------------*/

				dataBord.actionning(view);

				/*------------------------------- WIN / LOOSE --------------------------------*/
				dataBord.timeEnd(myZombies, str, context, superWaveZombie, view);
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


				KeyboardKey KB = event.getKey(); //lecture du clavier
				String mdp = null;
				if (KB != null) {
					mdp = KB.toString();
				}

				/*---------------------------------DEBUG 2--------------------------------------*/
				Action action = event.getAction();
				if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) { //si on appui sur une touche
					if (mdp == "Y" && debug == false) { // si on appui sur "y" on lance le mod debug au prochain tour de boucle
						debug = true;
					} // debug ON
					else if (mdp == "N" && debug == true) { // si on appui sur "n" on arrete le mod debug au prochain tour de boucle
						debug = false;
					} // debug OFF
					else if (mdp == "SPACE") { // si on pauui sur "espace" on stop on ferme la fenetre
						dataBord.gameStop();
					} else if (mdp == "P") { // si on appui sur "p" on lance le menu PAUSE au prochain tour de boucle
						pause = true;
					} else if (action == Action.KEY_PRESSED && mdp == "SHIFT") { // si on fait sur "shift"+clique on active une super action
						shift = true;
						mdp = null;
					}else if (action == Action.KEY_RELEASED && mdp == "SHIFT") {
						shift = false;
						mdp = null;
					}
							
				}

				/*---Gestion de la selection de cellules et de la plante manuelle de plante---*/
				
				if (action != Action.POINTER_DOWN) {
					continue;
				}

				Point2D.Float location = event.getLocation(); //lecture des cordonnées du clique
				float x = location.x;
				float y = location.y;

				if (shift) {
					dataBord.feed(x,y);//super action des plantes
				} else {
					dataBord.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, x, y);
				}
			}
		}
	}

	public static void main(String[] args) {
		Application.run(Color.LIGHT_GRAY, SimpleGameController::simpleGame); // attention, utilisation d'une lambda.
	}
}

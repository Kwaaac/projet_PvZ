package controlers;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import models.SimpleGameData;
import models.SystemFile;
import models.plants.Plant;
import views.BordView;
import views.SelectBordView;

public class PrincipalMenuController {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Plant> startGame(ApplicationContext context, SimpleGameData noImportantData) {

		ScreenInfo screenInfo = context.getScreenInfo(); //lecture des informations de l'écran : largeur & hauteur
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		int yOrigin = 50; // on défini un affichage qui ne dépassera pas 50 px au dessus de l'écran
		int xOrigin = 500; // on défini un affichage qui ne dépassera pas 500 px à gauche de l'écran

		ArrayList<Plant> selectedPlant = new ArrayList<>();
		
		ArrayList<Plant> allPlants = new ArrayList<>();

		allPlants.addAll(Plant.getPlantList("day")); //ajout de l'ensemble des plante créer dans le jeu dans une arraylist
		allPlants.addAll(Plant.getPlantList("night"));
		allPlants.addAll(Plant.getPlantList("pool"));
		
		try {
			SystemFile.readPrincipalMenuProperties(); //défini le nombre de plant que l'on pourra jouer
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SimpleGameData dataBord = new SimpleGameData(7, 7); //initialistion d'une matrice de 7x7 contenant l'ensembles des plantes disponibles
		
		SimpleGameData dataSelect = new SimpleGameData(SimpleGameData.getSelectionCaseNumber(), 1); //initialistion d'une matrice de ?x7 contenant l'ensembles des plantes joués
		
		SelectBordView viewContent = SelectBordView.initGameGraphics(xOrigin, yOrigin, 100, dataBord, allPlants); //initialisation des vues de contenue et de selection
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(30, yOrigin, 900, dataSelect,
				selectedPlant);

		String choice = "menu"; //variable de guidage dans le choix d'affichage

		BordView view = new BordView(0, 0, width, height, height / 5); //initialisation d'une vue de menu avec "bouton"

		view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000"); //bouton quitter

		while (true) {
			switch (choice) {
				case "menu": //affichage du premier menu : play ou resume
					view.drawMenu(context, view, width, height);
					break;
					
				case "mapSelection": //affichage du second menu : choix de map
					view.drawMapSelection(context, view, width, height);
					break;
					
				case "plantSelection": //affichage du denier menu : selection de plante
					view.drawPlantSelection(context, view, viewContent, plantSelectionView, dataBord, dataSelect, width, height);
					break;
			}
			
			Event event = context.pollOrWaitEvent(45);

			if (event == null) {
				continue;
			}

			KeyboardKey KB = event.getKey(); // lecture des touches selectionnées au clavier
			String mdp = null;
			if (KB != null) {
				mdp = KB.toString();
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) { //si on appui sur une touche
				if (mdp == "A" && plantSelectionView.getSelectedPlants().size() == 7) { // si on a appuyé sur la touche "a" et que l'on a selectionné 7 plantes
					view.drawRectangle(context, 0, 0, width, height, "#cbd9ef"); //background anti bug visuel
					return plantSelectionView.getSelectedPlants(); // on renvoi la liste de plantes selectionnée
				} else if (choice == "resume") { //si on a choisi de jouer depuis une sauvegarde on va chercher la liste de plante à jouer depuis un fichier
					noImportantData.setLoadChoice("resume");
					try {
						return (ArrayList<Plant>) SystemFile.read("view");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					continue;
				}
			}

			if (action != Action.POINTER_DOWN) {
				continue;
			}

			Point2D.Float location = event.getLocation(); //détection des coordonnées de clique de souris
			float x = location.x;
			float y = location.y;
			int Y = view.indexFromReaCoord(y, 0);

			if (width - 65 <= x && x <= width - 15) { // on a cliqué sur le button rouge pour quitter et fermer la fenetre
				if (15 <= y && y <= 65) {
					System.exit(0);
				}
			}

			switch (choice) { // actions liées aux choix que l'on fait
				case "menu": // on clique sur jouer ou resume
					switch (Y) {
						case 1:
							choice = "mapSelection"; //on a cliquer sur jouer
							break;
						case 2:
							choice = "resume"; //on a cliquer sur jouer jouer depuis une sauvegarde
							break;
					}
					break;
					
				case "mapSelection": // on clique sur une map
					
					switch (Y) {
					case 0:
						dataBord.setMap("Day"); //on a cliqué sur la map "day"
						choice = "plantSelection";
						break;
	
					case 1:
						dataBord.setMap("Night"); //on a cliqué sur la map "night"
						dataBord.setDayTime("Night");
						choice = "plantSelection";
						break;
						
					case 2:
						dataBord.setMap("Pool"); //on a cliqué sur la map "pool"
						choice = "plantSelection";
						break;
						
					case 3:
						dataBord.setMap("NightPool"); //on a cliqué sur la map "night pool"
						dataBord.setDayTime("Night");
						choice = "plantSelection";
						break;
						
					case 4:
						dataBord.setMap("Roof"); //on a cliqué sur la map "roof"
						choice = "plantSelection";
						break;
					}
	
				case "plantSelection": // on clique sur les plantes que l'on veut jouer
	
					if (dataBord.isCorrectSelectLocation(viewContent, x, y)) { // on regarde si l'on clique au bon endroit
						viewContent.selectPlant(x, y, plantSelectionView, dataBord, dataSelect); // on selectionne la plante
	
					} else {
						if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)) { // si on a déjà selectionner une plante alors on la pose dans notre selection
							plantSelectionView.selectPlant(x, y, viewContent, dataSelect, dataBord);
						}
					}
					break;
			}

		}
	}
}

package controlers;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import models.SimpleGameData;
import models.SystemFile;
import models.plants.Plant;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;

public class PrincipalMenuController {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Plant> startGame(ApplicationContext context, SimpleGameData noImportantData, HashMap<Zombie, Integer> normalWaveZombie) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		int yOrigin = 50; //marging top 50 px
		int xOrigin = 500; //marging left 500 px

		ArrayList<Plant> selectedPlant = new ArrayList<>();
		
		ArrayList<Plant> allPlants = new ArrayList<>();

		//adding all the plant in the plant bord
		allPlants.addAll(Plant.getPlantList("day"));
		allPlants.addAll(Plant.getPlantList("night"));
		allPlants.addAll(Plant.getPlantList("pool"));
		
		try {
			SystemFile.readPrincipalMenuProperties(); // number of plant we can play
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SimpleGameData dataBord = new SimpleGameData(7, 7); // plant bord 
		
		SimpleGameData dataSelect = new SimpleGameData(SimpleGameData.getSelectionCaseNumber(), 1); // plant selected bord
		
		SelectBordView viewContent = SelectBordView.initGameGraphics(xOrigin, yOrigin, 100, dataBord, allPlants);
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(30, yOrigin, 900, dataSelect,
				selectedPlant);

		String choice = "menu";

		BordView view = new BordView(0, 0, width, height, height / 5);

		view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000"); //button quit

		
		/**
		 * - first you choose to play a new game or reload a save
		 * - if new game, you choose the map
		 * - then you select the plants you want ot play
		 */
		while (true) {
			switch (choice) {
				case "menu":
					view.drawMenu(context, view, width, height);
					break;
					
				case "mapSelection":
					view.drawMapSelection(context, view, width, height);
					break;
					
				case "plantSelection":
					view.drawPlantSelection(context, view, viewContent, plantSelectionView, dataBord, dataSelect, width, height, normalWaveZombie);
					break;
			}
			
			Event event = context.pollOrWaitEvent(45);

			if (event == null) {
				continue;
			}

			KeyboardKey KB = event.getKey();
			String mdp = null;
			if (KB != null) {
				mdp = KB.toString();
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				if (mdp == "A" && plantSelectionView.getSelectedPlants().size() == 7) {
					
					view.drawRectangle(context, 0, 0, width, height, "#cbd9ef");
					return plantSelectionView.getSelectedPlants(); //return the plants you have selected
					
				} else if (choice == "resume") {
					
					noImportantData.setLoadChoice("resume");
					try {
						return (ArrayList<Plant>) SystemFile.read("view"); //return the selected plants of the save
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

			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			int Y = view.indexFromReaCoord(y, 0);

			if (width - 65 <= x && x <= width - 15) { // quit
				if (15 <= y && y <= 65) {
					System.exit(0);
				}
			}

			switch (choice) { //choice is changing following the click you do
				case "menu":
					switch (Y) {
						case 1:
							choice = "mapSelection";
							break;
						case 2:
							choice = "resume";
							break;
						case 3:
							choice = "editor";
							break;
					}
					break;
					
				case "mapSelection":
					
					switch (Y) {
					case 0:
						dataBord.setMap("Day");
						choice = "plantSelection";
						break;
	
					case 1:
						dataBord.setMap("Night");
						dataBord.setDayTime("Night");
						choice = "plantSelection";
						break;
						
					case 2:
						dataBord.setMap("Pool");
						choice = "plantSelection";
						break;
						
					case 3:
						dataBord.setMap("NightPool");
						dataBord.setDayTime("Night");
						choice = "plantSelection";
						break;
						
					case 4:
						dataBord.setMap("Roof");
						choice = "plantSelection";
						break;
					}
	
				case "plantSelection":
	
					if (dataBord.isCorrectSelectLocation(viewContent, x, y)) {
						viewContent.selectPlant(x, y, plantSelectionView, dataBord, dataSelect);
	
					} else {
						if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)) {
							plantSelectionView.selectPlant(x, y, viewContent, dataSelect, dataBord);
						}
					}
					break;
			}

		}
	}
}

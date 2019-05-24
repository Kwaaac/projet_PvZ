package controlers;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.SimpleGameData;
import models.plants.Plant;
import views.BordView;
import views.SelectBordView;

public class SelectionController {
	public static ArrayList<Plant> startGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		int yOrigin = 50;
		int xOrigin = 500;

		ArrayList<Plant> selectedPlant = new ArrayList<>();
		
		ArrayList<Plant> allPlants = new ArrayList<>();

		allPlants.addAll(Plant.getPlantList("day"));
//		allPlants.addAll(Plant.getPlantList("night"));
//		allPlants.addAll(Plant.getPlantList("pool"));

		SimpleGameData dataBord = new SimpleGameData(7, 7);
		SimpleGameData dataSelect = new SimpleGameData(7, 1);

		SelectBordView viewContent = SelectBordView.initGameGraphics(xOrigin, yOrigin, 100, dataBord, allPlants);
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(30, yOrigin, 900, dataSelect,
				selectedPlant);

		String choice = "mapSelection";

		BordView view = new BordView(0, 0, width, height, height / 5);

		
		view.drawRectangle(context, 0, 0, width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 10, width - 10, (height / 5) - 15, "#61DB5F");
		view.drawString(context, (width / 2) - 100, 1 * (height / 10), "DAY", "000000", 50);

		view.drawRectangle(context, 0, (height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, (height / 5) + 5, width - 10, (height / 5) -10, "#5F79DB");
		view.drawString(context, (width / 2) - 100, 3 * (height / 10), "NIGHT", "000000", 50);

		view.drawRectangle(context, 0, 2*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 2*(height / 5) + 5, width - 10, (height / 5) -10, "#5FC1DB");
		view.drawString(context, (width / 2) - 100, 5 * (height / 10), "POOL", "000000", 50);
		
		view.drawRectangle(context, 0, 3*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 3*(height / 5) + 5, (width/2)-5, (height / 5) -10, "#5F79DB");
		view.drawRectangle(context, width/2, 3*(height / 5) + 5, (width/2) - 5, (height / 5) - 10, "#5FC1DB");
		view.drawString(context, (width / 2) - 155, 7 * (height / 10), "NIGHTPOOL", "000000", 50);
		
		view.drawRectangle(context, 0, 4*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 4*(height / 5) +5, width - 10, (height / 5) - 10, "#BC4C29");
		view.drawString(context, (width / 2) - 100, 9 * (height / 10), "ROOF", "000000", 50);

		view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000");

		while (true) {

			Event event = context.pollOrWaitEvent(20);

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
					return plantSelectionView.getSelectedPlants();
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

			switch (choice) {
			case "mapSelection":
				switch (Y) {
				case 0:
					SimpleGameData.setMap("Day");
					choice = "plantSelection";
					break;

				case 1:
					SimpleGameData.setMap("Night");
					choice = "plantSelection";
					break;
					
				case 2:
					SimpleGameData.setMap("Pool");
					choice = "plantSelection";
					break;
					
				case 3:
					SimpleGameData.setMap("NightPool");
					choice = "plantSelection";
					break;
					
				case 4:
					SimpleGameData.setMap("Roof");
					choice = "plantSelection";
					break;
				}

			case "plantSelection":

				if (dataBord.isCorrectSelectLocation(viewContent, x, y)) {
					viewContent.truc(x, y, plantSelectionView, dataBord, dataSelect);

				} else {
					if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)) {
						plantSelectionView.truc(x, y, viewContent, dataSelect, dataBord);
					}
				}
				
				view.drawRectangle(context, 0, 0, width, height, "#ffffff"); // background
				viewContent.draw(context, dataBord);
				plantSelectionView.draw(context, dataSelect);
				view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000"); // quit

			}

		}
	}
}

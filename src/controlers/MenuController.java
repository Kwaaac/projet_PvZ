package controlers;

import java.awt.geom.Point2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.SimpleGameData;
import models.plants.Plant;
import views.BordView;
import views.SelectBordView;

public class MenuController {
	public static void startGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		int yOrigin = 50;
		int xOrigin = 500;
		
		Plant[] selectedPlant = {};
		
		SimpleGameData dataBord = new SimpleGameData(7, 7);
		SimpleGameData dataSelect = new SimpleGameData(7, 1);

		dataBord.setRandomMatrix();
		dataSelect.setRandomMatrix();
		
		
		SelectBordView viewContent = SelectBordView.initGameGraphics(xOrigin, yOrigin, 100, dataBord, selectedPlant);
		SelectBordView plantSelectionView = SelectBordView.initGameGraphics(30, yOrigin, 900, dataSelect, selectedPlant);
		
		String choice = "mapSelection";
		
		Plant[] plantDay = Plant.getPlantList("day");
		Plant[] plantNight = Plant.getPlantList("night");
		Plant[] plantPool = Plant.getPlantList("pool");
		
		BordView view = new BordView(0, 0, width, height, height/3);
		
		view.drawRectangle(context, 0, 0, width, height/3, "#61DB5F");
		view.drawRectangle(context, 0, height/3, width, height/3, "#5F79DB");
		view.drawRectangle(context, 0, height/3 + height/3, width, height/3, "#5FC1DB");
		view.drawRectangle(context, width-65, 15, 50, 50, "#DE0000");
		
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
				if (mdp == "A") {
					view.drawRectangle(context, 0, 0, width, height, "#A9A9A9");
					return;
				}
				else {
					continue;
				}
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			int Y = view.indexFromReaCoord(y,0);
			int X = view.indexFromReaCoord(x,0);
			
			if (width-65 <= location.x && location.x <= width-15) { //quit
				if (15 <= location.y && location.y <= 65) {
					System.exit(0);
				}
			}
			
			switch (choice) {
				case "mapSelection":
					switch(Y) {
						case 0:
							SimpleGameData.setMap("garden");
							choice = "plantSelection";
							break;
							
						case 1:
							SimpleGameData.setMap("night");
							choice = "plantSelection";
							break;
						
						case 2:
							SimpleGameData.setMap("pool");
							choice = "plantSelection";
							break;
					}
					break;
					
				case "plantSelection":
					view.drawRectangle(context, 0, 0, width, height, "#ffffff"); //background
					viewContent.draw(context, dataBord);
					plantSelectionView.draw(context, dataSelect);
					view.drawRectangle(context, width-65, 15, 50, 50, "#DE0000"); //quit
					
					x = xOrigin+28;
					y = yOrigin+28;
					for (Plant p: plantDay) {
						p.createAndDrawNewPlant(viewContent, context, (int)x, (int)y);
						x += 128;
						if (x >= xOrigin+900) {
							y+=128;
							x=xOrigin+28;
						}
					}
					for (Plant p: plantNight) {
						p.createAndDrawNewPlant(viewContent, context, (int)x, (int)y);
						x += 128;
						if (x >= xOrigin+900) {
							y+=128;
							x=xOrigin+28;
						}
					}
					for (Plant p: plantPool) {
						p.createAndDrawNewPlant(viewContent, context, (int)x, (int)y);
						x += 128;
						if (x >= xOrigin+900) {
							y+=128;
							x=xOrigin+28;
						}
					}
					
					break;
			}
		}
	}
}


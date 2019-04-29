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

public class MenuController {
	static void startGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		BordView view = new BordView(0, 0, width, height, height/3);
		
		String choice = "map";
		
		Plant[] plantDay = Plant.getPlantList("day");
		Plant[] plantNight = Plant.getPlantList("night");
		Plant[] plantPool = Plant.getPlantList("pool");
		
		while (true) {
			
			view.drawRectangle(context, 0, 0, width, height/3, "#61DB5F");
			view.drawRectangle(context, 0, height/3, width, height/3, "#5F79DB");
			view.drawRectangle(context, 0, height/3 + height/3, width, height/3, "#5FC1DB");
			view.drawRectangle(context, width-65, 15, 50, 50, "#DE0000");
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
				if (mdp == "SPACE") {
					context.exit(0);
					return;
				}
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			int Y = view.indexFromReaCoord(y,0);
			int X = view.indexFromReaCoord(x,0);
			
			if (choice == "map") {
				
				switch(Y) {
				
					case 0:
						choice = "day";
						SimpleGameData.setMap("garden");
						break;
						
					case 1:
						choice = "night";
						SimpleGameData.setMap("night");
						break;
					
					case 2:
						choice = "pool";
						SimpleGameData.setMap("pool");
						break;
				}
			}
			
			if (choice == "day") {
				x = 100;
				y = height/6;
				for (Plant p: plantDay) {
					p.createAndDrawNewPlant(view, context, (int)x, (int)y);
					
					x += 100;
				}
			}
			
			if (choice == "night") {
				x = 100;
				y = height/2;
				for (Plant p: plantNight) {
					p.createAndDrawNewPlant(view, context, (int)x, (int)y);
					x += 100;
				}
			}
			
			if (choice == "pool") {
				x = 100;
				y = height-(height/6);
				for (Plant p: plantDay) {
					p.createAndDrawNewPlant(view, context, (int)x, (int)y);
					x += 100;
				}
				for (Plant p: plantNight) {
					p.createAndDrawNewPlant(view, context, (int)x, (int)y);
					x += 100;
				}
			}
			System.out.println(choice);
			
		}
	}
}


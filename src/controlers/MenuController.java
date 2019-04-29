package controlers;

import java.awt.geom.Point2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.SimpleGameData;
import views.BordView;

public class MenuController {
	static void startGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		BordView view = new BordView(0, 0, width, height, width/3);
		
		while (true) {
			view.drawRectangle(context, 0, 0, width, height/3, "#61DB5F");
			view.drawRectangle(context, 0, height/3, width, height/3, "#5F79DB");
			view.drawRectangle(context, 0, height/3 + height/3, width, height/3, "#5FC1DB");
			Event event = context.pollOrWaitEvent(20);
			if (event == null) {
				continue;
			}
			Action action = event.getAction();
			
			Point2D.Float location = event.getLocation();
			float y = location.y;
			
			int Y = view.indexFromReaCoord(y,0);
			
			switch(Y) {
				case 0:
					SimpleGameData.setMap("garden");
					System.out.println("1");
					context.exit(0);
					System.out.println("2");
					return;
					
				case 1:
					SimpleGameData.setMap("night");
					context.exit(0);
					return;
				
				case 2:
					SimpleGameData.setMap("pool");
					context.exit(0);
					return;
			}
			
		}
	}
}


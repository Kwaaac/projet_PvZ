package controlers;

import java.awt.geom.Point2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.SimpleGameData;
import views.BordView;

public class EndController {
	public static void endGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		BordView view = new BordView(0, 0, width, height, height / 3);

		int WL = SimpleGameData.getWL();
		
		view.drawRectangle(context, 0, 0, width, height , "#61DB5F");
		view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000");
		if (WL == 0) {
			view.drawString(context, (int)((width/2)-100), (int)height/2, "LOOSE", "#000000", 50);
		}
		if (WL == 1) {
			view.drawString(context, (int)((width/2)-50), (int)height/2, "WIN", "#000000", 50);
		}
		else {
			view.drawString(context, (int)((width/2)-150), (int)height/2, "YOU QUIT", "#000000", 50);
		}
		while (true) {
			
			Event event = context.pollOrWaitEvent(20);
			if (event == null) {
				continue;
			}
			
			Action action = event.getAction();
			if (action != Action.POINTER_DOWN) {
				continue;
			}
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;

			if (width - 65 <= x && x <= width - 15) { // quit
				if (15 <= y && y <= 65) {
					context.exit(0);
				}
			}
			
			
		}
		
	}
}

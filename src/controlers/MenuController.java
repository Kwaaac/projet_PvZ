package controlers;


import java.awt.geom.Point2D;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import views.BordView;

public class MenuController {
	public static boolean menu(ApplicationContext context, BordView view) {

		
		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		view.drawRectangle(context, 0, 0, width, height, "#61DB5F");
		view.drawString(context, (width/2)-125, (height/6)-50, "PAUSE", "#000000", 85);
		
		view.drawRectangle(context, 0, 200, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 205, width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6), "Resume", "000000", 50);
		
		view.drawRectangle(context, 0, 225+(height/6), width, (height/6), "#000000");
		view.drawRectangle(context, 5, 230+(height/6), width-10, (height/6)-10, "#22D398");
		
		view.drawRectangle(context, 0, 250+(height/6)*2, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 255+(height/6)*2, width-10, (height/6)-10, "#22D398");
		
		while (true) {
			
			
			
			Event event = context.pollOrWaitEvent(45); // modifier pour avoir un affichage fluide
			if (event == null) { // no event
				continue;
			}
			Action action = event.getAction();
			
			Point2D.Float location = event.getLocation();
			float x = location.x;
			float y = location.y;
			
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				if (((x<=0 && x<=width) && y<=200 && y<=(height/6))) {
					return false;
				}
			}
			
			
			
		}
		
	}
}

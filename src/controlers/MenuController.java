package controlers;


import java.awt.geom.Point2D;
import java.io.IOException;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import models.SystemFile;
import fr.umlv.zen5.Event.Action;
import views.BordView;

public class MenuController {
	public static boolean menu(ApplicationContext context, BordView view) {

		
		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		SystemFile file = new SystemFile();
		
		view.drawRectangle(context, 0, 0, width, height, "#61DB5F");
		view.drawString(context, (width/2)-125, (height/6)-50, "PAUSE", "#000000", 85);
		
		view.drawRectangle(context, 0, 200, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 205, width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6), "Continue", "000000", 50);
		
		view.drawRectangle(context, 0, 225+(height/6), width, (height/6), "#000000");
		view.drawRectangle(context, 5, 230+(height/6), width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6)*2, "Resume", "000000", 50);
		
		view.drawRectangle(context, 0, 250+(height/6)*2, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 255+(height/6)*2, width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6)*3, "Save", "000000", 50);
		
		while (true) {

			
			Event event = context.pollOrWaitEvent(45);
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
			
			if (0<=x && x<=width && 200<=y && y<=200+(height/6)) {
				return false;
			}  else if (0<=x && x<=width && 250+(height/6)*2<=y && y<=250+(height/6)*3) {
				
				try {
					file.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (0<=x && x<=width && 225+(height/6)<=y && y<=(height/6)*2+225) {
				try {
					file.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			
			
		}
		
	}
}

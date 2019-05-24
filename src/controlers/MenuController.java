package controlers;


import java.awt.geom.Point2D;
import java.io.IOException;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import models.SimpleGameData;
import models.SystemFile;
import fr.umlv.zen5.Event.Action;
import views.BordView;

public class MenuController {
	public static boolean menu(ApplicationContext context, BordView view, SimpleGameData data) {

		
		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
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
				//redraw background
				view.drawRectangle(context, 0, 0, width, height, "#cbd9ef");
				return false;
			}  else if (0<=x && x<=width && 250+(height/6)*2<=y && y<=250+(height/6)*3) {
				try {
					SystemFile.save(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			} else if (0<=x && x<=width && 225+(height/6)<=y && y<=(height/6)*2+225) {
				try {
					SystemFile.read();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return false;
			}
			
			
			
			
		}
		
	}
}

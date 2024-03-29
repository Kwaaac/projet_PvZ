package controlers;


import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import models.Chrono;
import models.SimpleGameData;
import views.BordView;

public class EndController {
	public static void endGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();
		
		Chrono timer = new Chrono();

		BordView view = new BordView(0, 0, width, height, height / 3);

		int WL = SimpleGameData.getWL();
		
		view.drawRectangle(context, 0, 0, width, height , "#61DB5F");
		
		if (WL == 0) {
			view.drawString(context, (int)((width/2)-100), (int)height/2, "LOOSE", "#000000", 50);
		}
		if (WL == 1) {
			view.drawString(context, (int)((width/2)-50), (int)height/2, "WIN", "#000000", 50);
		}
		else {
			view.drawString(context, (int)((width/2)-150), (int)height/2, "YOU QUIT", "#000000", 50);
		}
		timer.start();
		while (true) {
			
			if (timer.asReachTimer(1)) {
				System.exit(0);
			}
			
			
		}
		
	}
}

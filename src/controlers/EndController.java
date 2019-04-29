package controlers;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import models.SimpleGameData;
import views.BordView;

public class EndController {
	static void endGame(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();

		SimpleGameData data = new SimpleGameData(1, 1);

		data.setRandomMatrix();
		int yOrigin = (int) (height/2.5);
		int xOrigin = (int) (width/2.5);

		BordView view = BordView.initGameGraphics(xOrigin, yOrigin, 200, data);



		int WL = SimpleGameData.getWL();
		
		while (true) {
			view.draw(context, data);
			Event event = context.pollOrWaitEvent(20);
			if (event == null) {
				continue;
			}
			Action action = event.getAction();
			
			
//			if (WL == 0) {
//				view.drawString(context, (int)width/2, (int)height/2, "LOOSE");
//			}
//			if (WL == 1) {
//				view.drawString(context, (int)width/2, (int)height/2, "WIN");
//			}
//			else {
//				view.drawString(context, (int)width/2, (int)height/2, "STOP");
//			}
			
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				context.exit(0);
				return;
			}
		}
		
	}
}

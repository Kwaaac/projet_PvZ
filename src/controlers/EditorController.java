package controlers;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import views.BordView;

public class EditorController {
	public static void Editor(ApplicationContext context) {

		ScreenInfo screenInfo = context.getScreenInfo();
		int width = (int) screenInfo.getWidth();
		int height = (int) screenInfo.getHeight();

		BordView view = new BordView(0, 0, width, height);
		
		view.drawRectangle(context, 0, 0, width, height , "#61DB5F");
		view.drawString(context, width/2, height/2, "MyEditor", "#000000", 50);
		
		
		while (true) {
			
			
			
		}
		
	}
}

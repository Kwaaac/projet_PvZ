package plants;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;
import zombies.Zombie;

public class WallNut extends Plant {
	private final String name = "WallNut";
	private final String color = "#ECB428";

	public WallNut(int x, int y) {
		super(x, y, 0, 1000, 0);
	}
	
	public WallNut() {
		super(-10, -10, 0, 1, 1);
	}
	
	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Rectangle2D.Float draw() {
		return new Rectangle2D.Float(super.x, super.y, sizeOfPlant, sizeOfPlant);
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, SimpleGameData data, int x, int y) {
		view.drawWallNut(context, data, x,  y, color);
		
		return new WallNut(x, y);
		
	}

	@Override
	public void draw(SelectBordView view, Graphics2D graphics, SimpleGameData data, int x, int y) {
		view.drawWallNut(graphics, data, x,  y, color);
	}

}

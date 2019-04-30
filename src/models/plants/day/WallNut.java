package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class WallNut extends Plant {
	private final String name = "WallNut";
	private final String color = "#ECB428";

	public WallNut(int x, int y) {
		super(x, y, 0, 1000, 0, 50, "verySlow");
	}
	
	public WallNut() {
		this(-10, -10);
	}
	
	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawWallNut(context, x,  y, color);
		
		return new WallNut(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawWallNut(graphics, x,  y, color);
	}

}

package models.plants.pool;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Cattails extends Plant {
	private final String name = "Cattails";
	private final String color = "#8FD916";

	public Cattails(int x, int y) {
		super(x, y, 0, 300, 1500, 250, "verySlow");
	}
	
	public Cattails() {
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
		view.drawCattails(context, x,  y, color);
		
		return new Cattails(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawCattails(graphics, x,  y, color);
	}

}

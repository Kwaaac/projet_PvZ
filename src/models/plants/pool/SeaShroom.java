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

public class SeaShroom extends Plant {
	private final String name = "SeaShroom";
	private final String color = "#8FD916";

	public SeaShroom(int x, int y) {
		super(x, y, 0, 300, 180*3, 0, "slow");
	}
	
	public SeaShroom() {
		super(-10, -10, 0, 1, 1, 0, "slow");
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
		view.drawSeaShroom(context, x,  y, color);
		
		return new SeaShroom(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawSeaShroom(graphics, x,  y, color);
	}

}

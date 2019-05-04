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

public class TangleKelp extends Plant {
	private final String name = "TangleKelp";
	private final String color = "#517614";

	public TangleKelp(int x, int y) {
		super(x, y, 0, 2400, 90, 25, "slow");
	}
	
	public TangleKelp() {
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
		view.drawTangleKelp(context, x,  y, color);
		
		return new TangleKelp(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawTangleKelp(graphics, x,  y, color);
	}

	@Override
	public boolean canBePlantedOnWater() {
		return true;
	}
	
	@Override
	public boolean canBePlantedOnGrass() {
		return false;
	}
}

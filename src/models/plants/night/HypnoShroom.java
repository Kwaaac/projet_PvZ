package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class HypnoShroom extends Plant{
	private final String name = "HypnoShroom";
	private final String color = "#90D322";
	
	public HypnoShroom(int x, int y) {
		super(x, y, 0, 300, 0, 75, "fast");
	}
	
	public HypnoShroom() {
		super(-10, -10, 0, 1, 1, 75, "fast");
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawHypnoShroom(context, x,  y, color);
		
		return new HypnoShroom(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawHypnoShroom(graphics, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {}

}
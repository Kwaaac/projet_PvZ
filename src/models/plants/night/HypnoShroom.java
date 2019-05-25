package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class HypnoShroom extends Plant {
	private final String name = "HypnoShroom";
	private final String color = "#90D322";
	
	public HypnoShroom(int x, int y) {
		super(x, y, 0, 300, 0, 75, "fast");
	}
	
	public HypnoShroom() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	@Override
	public Plant createNewPlant(int x, int y) {
		return new HypnoShroom(x, y);
	}
	
	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));
		
		view.drawCost(graphics, x, y, cost.toString());
	}

}
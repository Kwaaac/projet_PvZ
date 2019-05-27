package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class GraveBuster extends Plant{
	private final String name = "GraveBuster";
	private final String color = "#000000";
	
	public GraveBuster(int x, int y) {
		super(x, y, 0, 300, 0, 75, "fast");
	}
	
	public GraveBuster() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	@Override
	public Plant createNewPlant(int x, int y) {
		return new GraveBuster(x, y);
	}
	
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

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, SimpleGameData dataBord) {}

}
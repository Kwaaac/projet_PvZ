package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class ScaredyShroom extends Plant{
	private final String name = "ScaredyShroom";
	private final String color = "#90D322";
	
	public ScaredyShroom(int x, int y) {
		super(x, y, 0, 300, 5100, 25, "fast");
		shootBar = shootBarMax;			// La plante tire dès qu'elle est posée
	}
	
	public ScaredyShroom() {
		super(-10, -10, 0, 1, 1, 25, "fast");
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawScaredyShroom(context, x,  y, color);
		
		return new ScaredyShroom(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawScaredyShroom(graphics, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {
		if(this.readyToshot()) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
		
		this.incAS();
	}

}
package models.plants;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SnowPea extends Plant{
	private final String name = "SnowPea";
	private final String color = "#33FFEA";
	
	public SnowPea(int x, int y) {
		super(x, y, 0, 300, 5100);
		shootBar = shootBarMax;			// La plante tire dès qu'elle est posée
	}
	
	public SnowPea() {
		super(-10, -10, 0, 1, 1);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	@Override
	public boolean readyToshot(ArrayList<Zombie> myZombies) {
		for(Entities z : myZombies) {
			if(this.sameLine(z)) {
				return shootBar >= shootBarMax;
			}
		}
		
		return false;
	}
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawSnowPea(context, x,  y, color);
		
		return new SnowPea(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawSnowPea(graphics, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		if(this.readyToshot(myZombies)) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
		
		this.incAS();
	}

}
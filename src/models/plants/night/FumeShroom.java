package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.plants.Plant;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class FumeShroom extends Plant{
	private final String name = "FumeShroom";
	private final String color = "#90D322";
	
	public FumeShroom(int x, int y) {
		super(x, y, 0, 300, 180*4, 75, "fast"); //180 = SquareSize
		shootBar = shootBarMax;			// La plante tire dès qu'elle est posée
	}
	
	public FumeShroom() {
		super(-10, -10, 0, 1, 1, 75, "fast");
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
		view.drawFumeShroom(context, x,  y, color);
		
		return new FumeShroom(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawFumeShroom(graphics, x, y, color);
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
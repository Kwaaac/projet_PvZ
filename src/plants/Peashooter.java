package plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.Entities;
import models.IPlant;
import views.BordView;
import zombies.Zombie;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 230);
	}


	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		this.incAS();
		
		if(this.readyToshot(myZombies)) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
	}
}

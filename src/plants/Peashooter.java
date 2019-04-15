package plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.IPlant;
import views.BordView;
import zombies.Zombie;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	
	public Peashooter(int x, int y) {
		super(x, y, 20, 300, 250);
	}


	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}


	public void action(ArrayList<Projectile> MyBullet, BordView view, ArrayList<Zombie> MyZombies) {
		this.incAS();
		
		if(this.readyToshot(MyZombies)) {
			MyBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
	}
	
	

}

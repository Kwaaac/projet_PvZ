package plants;

import models.Entities;
import models.MovingElement;

public abstract class Projectile extends Entities implements MovingElement{
	private final String type = "Projectile";
	private final double speed;
	private final static int sizeOfProjectile = 150;
	
	public Projectile(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = speed;
	}
	
	@Override
	public void move() {
		setX((int) (getX() + speed));
	}
	
	@Override
	public String toString() {
		return "Type: " + type; 
	}
	
	public static int getSizeOfProjectile() {
		return sizeOfProjectile;
	}
}

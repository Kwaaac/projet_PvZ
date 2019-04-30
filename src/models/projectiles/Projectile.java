package models.projectiles;

import models.Coordinates;
import models.Entities;
import models.MovingElement;

public abstract class Projectile extends Entities implements MovingElement{
	private final String type = "Projectile";
	private double speed;
	private static final int sizeOfProjectile = 25;
	
	public Projectile(float x, float y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = speed;
	}
	
	@Override
	public void move() {
		setX((int) (super.x + speed));
	}
	
	@Override
	public String toString() {
		return "Type: " + type; 
	}
	
	@Override
	public void incAS() {}

	@Override
	public void resetAS() {}
	
	public static int getSizeOfProjectile() {
		return sizeOfProjectile;
	}
	
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfProjectile);
	}
	
	public boolean isOutside(int xOrigin, int sqrSize, int nbrSqr) {
		return x > xOrigin + sqrSize * nbrSqr;
	}

	public void SpeedBoostON() {
		speed += 2;
	}
	
	public void SpeedBoostOFF() {
		speed -= 2;
	}
}

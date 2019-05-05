package models.projectiles;

import java.util.ArrayList;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.zombies.Zombie;

public abstract class Projectile extends Entities implements MovingElement, IProjectile{
	private final String type = "Projectile";
	private double speed;
	private static final int sizeOfProjectile = 25;
	
	public Projectile(float x, float y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.setSpeed(speed);
	}
	
	
	@Override
	public void move() {
		setX((int) (super.x + getSpeed()));
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
		setSpeed(getSpeed() + 2);
	}
	
	public void SpeedBoostOFF() {
		setSpeed(getSpeed() - 2);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public static void hasToDie(DeadPool DPe, ArrayList<Projectile> Mp, SimpleGameData data) {
		for(Projectile p : Mp) {
			if (p.isDead()) {
				DPe.add(p);
			}
		}
	}
	
	public void action() {}
}

package zombies;

import models.Coordinates;
import models.Entities;
import models.LivingEntities;
import models.MovingElement;
import plants.Plant;

public abstract class Zombie extends Entities implements MovingElement, LivingEntities {
	private final String type = "Zombie";
	private double speed;
	private final static int sizeOfZombie = 75;
	
	private int speedshoot;
	private int timerA = -1;

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = -1.7;
		this.speedshoot = (int) (speed * -75);
	}

	
	
	@Override
	public void move() {
		setX((int) (getX() + speed));
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public boolean getSpeed() {
		return speed != 0;
	}
	
	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}
	
	public void stop() {
		this.speed = 0;
	}
	
	public void go() {
		speed = -1.7;
	}
	
	public void incAS() {
		this.timerA += 1;
	}
	
	public void resetAS() {
		this.timerA = 0;
	}

	public boolean readyToshot() {
		return timerA % speedshoot == 0;
	}
	
	public void conflictAll(Plant p) {}
	
	public Coordinates hitBox() {
		return new Coordinates(x, x + sizeOfZombie);
	}
	
	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize / 2;
	}
}

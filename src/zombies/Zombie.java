package zombies;

import models.Entities;
import models.MovingElement;

public abstract class Zombie extends Entities implements MovingElement {
	private final String type = "Zombie";
	private double speed;
	private final static int sizeOfZombie = 75;
	private boolean moving = true;
	private double[] speedState = { 0.0, -2.0, -1.0 }; // [0] -> Stop; [1] -> Normal Speed; [2] -> Slow Speed

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = speedState[1];
	}

	@Override
	public void move() {
		setX((int) (getX() + speed));
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public boolean getMove() {
		return moving;
	}
	
	public void Stop() {
		this.speed = speedState[0];
		moving = false;
	}
	
	public void Slowed() {
		speed = speedState[2];
		moving = true;
	}
	
	public void Go() {
		speed = speedState[1];
		moving = true;
	}
}

package Models.Zombies;

import Models.Entities;
import Models.MovingElement;

public abstract class Zombie extends Entities implements MovingElement{
	private final String type = "Zombie";
	private final double speed;
	private final static int sizeOfZombie = 75;
	
	public Zombie(int x, int y, int damage, int life, double speed) {
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
	
	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}
}

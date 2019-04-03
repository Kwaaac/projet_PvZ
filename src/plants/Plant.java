package plants;

import java.awt.geom.Rectangle2D;

import models.Entities;
import zombies.Zombie;

public abstract class Plant extends Entities{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	private final int speedshoot = 200;
	private int timerA = 0;
	
	public Plant(int x, int y, int damage, int life) {
		super(x, y, damage, life);
	}
	
	public String toString() {
		return "Type: " + type;
	}

	public Rectangle2D.Float draw() {
		return null;
	}
	
	public static int getSizeOfPlant() {
		return sizeOfPlant;
	}

	public int getSpeedShooting() {
		return timerA;
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
	
	public void conflictAll(Zombie z) {}
	
}

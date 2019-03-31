package plants;

import java.awt.geom.Rectangle2D;

import models.Entities;

public abstract class Plant extends Entities{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	private int speedshoot;
	
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

	public void setSpeedShooting(int s) {
		this.speedshoot = s;
	}

	public boolean readyToshot(int s) {
		return s==speedshoot;
	}
	
}

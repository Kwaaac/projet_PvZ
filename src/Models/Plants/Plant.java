package Models.Plants;

import java.awt.geom.Rectangle2D;

import Models.Entities;

public abstract class Plant extends Entities{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	
	public Plant(int x, int y, int damage, int life) {
		super(x, y, damage, life);
	}
	
	public String toString() {
		return "Type: " + type;
	}

	public Rectangle2D.Float draw() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int getSizeOfPlant() {
		return sizeOfPlant;
	}

}

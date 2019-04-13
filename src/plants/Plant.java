package plants;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import models.Coordinates;
import models.Entities;
import models.IEntite;
import models.LivingEntities;


public abstract class Plant extends Entities{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	private final int speedshoot;
	private int timerA = -1;
	
	public Plant(int x, int y, int damage, int life, int speedshoot) {
		super(x, y, damage, life);
		this.speedshoot = speedshoot;
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
	
	public void setTimerA(int x) {
		this.timerA = x;
	}
	
	@Override
	public void go() {
	}
	
	public Coordinates hitBox() {
		return new Coordinates(x - sizeOfPlant/2, x + sizeOfPlant/2);
	}
}

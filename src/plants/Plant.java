package plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.Coordinates;
import models.Entities;
import models.IPlant;
import zombies.Zombie;


public abstract class Plant extends Entities implements IPlant{
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
	
	@Override
	public void incAS() {
		if(timerA != speedshoot) {
            this.timerA += 1;
        }
	}
	
	public void resetAS() {
		this.timerA = 1;
	}
	
	public int getTimer() {
		return timerA;
	}
	
	@Override
	public boolean readyToshot(ArrayList<Zombie> myZombies) {
		for(Entities z : myZombies) {
			if(this.sameLine(z)) {
				return timerA % speedshoot == 0;
			}
		}
		return false;
	}
	
	public boolean readyToshot() {
		return timerA % speedshoot == 0;
	}
	
	
	
	public void setTimerA(int x) {
		this.timerA = x;
	}
	
	public Coordinates hitBox() {
		return new Coordinates(x, x + sizeOfPlant);
	}
}

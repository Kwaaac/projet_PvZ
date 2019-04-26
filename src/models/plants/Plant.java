package models.plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.Coordinates;
import models.Entities;
import models.zombies.Zombie;


public abstract class Plant extends Entities implements IPlant{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	protected final int shootBarMax;
	protected long shootBar;
	protected long shootTime;
	private final int cost;
	
	public Plant(int x, int y, int damage, int life, int shootBarMax, int cost) {
		super(x, y, damage, life);
		this.shootBarMax = shootBarMax;
		this.cost = cost;
		shootTime = System.currentTimeMillis();
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

	public long getSpeedShooting() {
		return shootBar;
	}
	
	@Override
	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}
	
	@Override
	public void resetAS() {
		shootTime = System.currentTimeMillis();
		
		this.shootBar = 1 ;
	}
	
	public long getTimer() {
		return shootBar;
	}
	
	@Override
	public boolean readyToshot(ArrayList<Zombie> mz) {
		return shootBar >= shootBarMax;
	}
	
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}
}

package models.plants;

import java.awt.geom.Rectangle2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import models.Coordinates;
import models.Entities;
import models.IPlant;
import models.zombies.Zombie;


public abstract class Plant extends Entities implements IPlant{
	private final String type = "Plant"; 
	private final static int sizeOfPlant = 75;
	protected final int shootBarMax;
	protected long shootBar;
	protected Instant shootTime;
	
	public Plant(int x, int y, int damage, int life, int shootBarMax) {
		super(x, y, damage, life);
		this.shootBarMax = shootBarMax;
		shootTime = Instant.now();
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
		Duration betweenTimer = Duration.between(shootTime, Instant.now());
		
		shootBar = (int) betweenTimer.getSeconds();
	}
	
	@Override
	public void resetAS() {
		this.shootTime = Instant.now();
	}
	
	public long getTimer() {
		return shootBar;
	}
	
	@Override
	public boolean readyToshot(ArrayList<Zombie> mz) {
		return shootBar >= shootBarMax;
	}
	
	public void setTimerA(int x) {
		this.shootBar = x;
	}
	
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}
}

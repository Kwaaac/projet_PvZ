package models.plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private final Long cooldown;
	
	private static final HashMap<String, Long> mCooldown = new HashMap<String, Long>();
	
	static {
	mCooldown.put("free",(long)0);
	mCooldown.put("fast",(long)5);
	mCooldown.put("medium",(long)15);
	mCooldown.put("slow",(long)20);
	mCooldown.put("verySlow",(long)35);
	mCooldown.put("bigTime",(long)60);
	}

	
	public Plant(int x, int y, int damage, int life, int shootBarMax, int cost,String cooldown) {
		super(x, y, damage, life);
		this.shootBarMax = shootBarMax;
		this.cost = cost;
		this.cooldown = mCooldown.get(cooldown);
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
	
	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}
}

package models;

import java.util.ArrayList;

import plants.Projectile;

public interface IEntite {

	
	
	public Coordinates hitBox();
	
	public void takeDmg(int x);
	
	boolean hit(IEntite e);
	
	void incAS();
	
	void resetAS();

	boolean isDead();
	
	void conflict(DeadPool DPe,ArrayList<Projectile> Le);

	public int getDamage();
}

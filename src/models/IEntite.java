package models;

import java.util.ArrayList;

public interface IEntite {

	
	
	public Coordinates hitBox();
	
	public void takeDmg(int x);
	
	boolean hit(IEntite e);
	
	void incAS();
	
	void resetAS();

	boolean isDead();
	
	void conflict(DeadPool DPe,ArrayList<Entities> Le);
	
}

package models;

public interface IEntite {

	
	
	public Coordinates hitBox();
	
	public void takeDmg(int x);
	
	boolean hit(IEntite e);
	
	void incAS();
	
	void resetAS();

	boolean isDead();

	boolean isInConflict();
	
	void setConflictMode(boolean b);
	
	public int getDamage();
}

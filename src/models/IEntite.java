package models;

public interface IEntite {

	public void go();
	
	public Coordinates hitBox();
	
	public void takeDmg(int x);
	
	boolean hit(IEntite e);
	
	
}

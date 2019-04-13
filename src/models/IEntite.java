package models;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D.Float;

import plants.Projectile;

public interface IEntite {
	
	public void incAS();
	
	public void resetAS();

	public boolean readyToshot();

	public void go();
	
	public Coordinates hitBox();
	
	public void takeDmg(int x);
}

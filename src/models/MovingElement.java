package models;

import java.awt.geom.Ellipse2D;

public interface MovingElement {
	public void move();

	public Ellipse2D.Float draw();
	
	public int getX();
}

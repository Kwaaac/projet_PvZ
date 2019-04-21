package models;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public interface MovingElement {
	void move();

	Ellipse2D.Float draw();
	
	Color getColor();
}

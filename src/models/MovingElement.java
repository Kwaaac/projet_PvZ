package models;

import java.awt.Graphics2D;
import views.SimpleGameView;

public interface MovingElement {
	void move();
	
	String getColor();
	
	float getX();
	
	float getY();

	void draw(SimpleGameView view, Graphics2D graphics, float x, float y);
}

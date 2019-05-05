package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;

import views.SimpleGameView;

public interface IProjectile {

	void draw(SimpleGameView view, Graphics2D graphics, float x, float y);
	
	boolean isSlowing();
	
	String getColor();
	
	void action();
}

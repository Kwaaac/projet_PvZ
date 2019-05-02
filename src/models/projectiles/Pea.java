package models.projectiles;

import java.awt.Graphics2D;
import views.SimpleGameView;


public class Pea extends Projectile {

	private final String name = "Pea";
	private final String color = "#2DFF54";
	
	public Pea(float x, float y) {
		super(x, y, 75, 1 , 20.0);
	}
	
	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawPea(graphics, x, y, color);
	}

	@Override
	public boolean isSlowing() {
		return false;
	}
}

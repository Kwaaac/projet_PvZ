package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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
		return name; 
	}

	int  sizeofP = getSizeOfProjectile();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeofP, sizeofP));
	}

	@Override
	public int isSlowing() {
		return 0;
	}
}

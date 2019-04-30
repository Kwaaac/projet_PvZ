package models.projectiles;

import java.awt.Graphics2D;
import views.SimpleGameView;


public class Bullet extends Projectile {

	private final String name = "Bullet";
	private final String color = "#0830b2";
	
	public Bullet(float x, float y) {
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
		view.drawBullet(graphics, x, y, color);
	}

	

}

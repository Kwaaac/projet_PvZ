package models.projectiles;

import java.awt.Graphics2D;

import views.SimpleGameView;

public class Spike extends Projectile{

	public Spike(float x, float y, int damage, int life, double speed) {
		super(x, y, damage, life, speed, true);
	}

	@Override
	public String getColor() {
		return null;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		
	}

	@Override
	public int isSlowing() {
		return 0;
	}

}

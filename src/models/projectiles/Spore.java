package models.projectiles;

import java.awt.Graphics2D;

import views.SimpleGameView;

public class Spore extends Projectile {
	private float spawnX;
	private float distance;
	private String color = "#7714AD";

	public Spore(float x, float y, float distance) {
		super(x, y, 50, 1, 20.0);
		this.spawnX = x;
		this.distance = distance;
	}

	@Override
	public String getColor() {
		return null;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawSpore(graphics, x, y, color);
	}

	@Override
	public boolean isSlowing() {
		return false;
	}

	@Override
	public void action() {
		if (distance != -1) {
			if ((spawnX + distance) < getX()) {
				setLife(1);
			}
		}
	}
}

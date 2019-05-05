package models.projectiles;

import java.awt.Graphics2D;

import views.BordView;
import views.SimpleGameView;

public class WeakSpore extends Projectile {
	private float spawnX;
	private float distance;
	private String color = "#7714AD";

	public WeakSpore(float x, float y) {
		super(x, y, 30, 1, 20.0);
		this.spawnX = x;
		this.distance = BordView.getSquareSize() * 3;
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
			System.out.println((spawnX + distance) + "; " + getX());
			System.out.println((spawnX + distance) < getX());
			if ((spawnX + distance) < getX()) {
				setLife(1);
			}
		}
	}
}

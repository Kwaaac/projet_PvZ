package models.projectiles;

import java.awt.Graphics2D;

import views.SimpleGameView;

public class FrozenPea extends Projectile {
	private final String name = "Frozen Pea";
	private final String color = "#3A7DF2";

	public FrozenPea(float x, float y) {
		super(x, y, 2, 1, 20.0);
	}

	@Override
	public String getColor() {
		return null;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawFrozenPea(graphics, x, y, color);
	}

	@Override
	public boolean isSlowing() {
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}

package models.projectiles;

import java.awt.Graphics2D;

import views.BordView;
import views.SimpleGameView;

public class Spore extends Projectile {
	private String color = "#7714AD";

	public Spore(float x, float y) {
		super(x, y, 75, 1, 20.0);
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
	public int isSlowing() {
		return 0;
	}
}

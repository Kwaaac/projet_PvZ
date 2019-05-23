package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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

	int sizeofP = getSizeOfProjectile();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 3, y - 3, sizeofP - 6, sizeofP - 6));
	}

	@Override
	public int isSlowing() {
		return 0;
	}
}

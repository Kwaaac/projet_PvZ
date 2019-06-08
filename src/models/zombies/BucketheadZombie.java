package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import views.SimpleGameView;

public class BucketheadZombie extends Zombie {

	private final String name = "BucketheadZombie";
	private final String color = "#000000";

	public BucketheadZombie(int x, int y) {
		super(x, y, 100, 1300, 2, "slow", false);
	}

	public BucketheadZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 1300, 2, "slow", gifted);
	}

	public BucketheadZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean magnetizable() {
		if (life > 300) {
			setLife(300);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new BucketheadZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {

		if (life > 300) {
			graphics.setColor(Color.decode("#666666"));
			graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, 180, 2));

			graphics.setColor(Color.decode(color));
			graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, -180, 2));
		} else {

			graphics.setColor(Color.decode(color));
			graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		}
		
		
		super.draw(view, graphics);
	}
}

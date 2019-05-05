package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;
import models.zombies.Zombie;
public class PeashooterZombie extends Zombie {

	private final String name = "PeashooterZombie";
	private final String color = "#000000";

	public PeashooterZombie(int x, int y) {
		super(x, y, 100, 200, 1, "slow");
	}

	public PeashooterZombie() {
		super(50, 50, 100, 200, 1, "slow");
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	public void go() {
		super.go((float) -0.93);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPeashooterZombie(context, x, y, color);

		return new PeashooterZombie(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawPeashooterZombie(graphics, x, y, color);
	}


}

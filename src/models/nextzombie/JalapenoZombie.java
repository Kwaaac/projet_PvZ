package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;
import models.zombies.Zombie;
public class JalapenoZombie extends Zombie {

	private final String name = "JalapenoZombie";
	private final String color = "#000000";

	public JalapenoZombie(int x, int y) {
		super(x, y, 100, 340, 1, "slow");
	}

	public JalapenoZombie() {
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

	public void go() {
		super.go((float) -0.93);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawJalapenoZombie(context, x, y, color);

		return new JalapenoZombie(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawJalapenoZombie(graphics, x, y, color);
	}

}

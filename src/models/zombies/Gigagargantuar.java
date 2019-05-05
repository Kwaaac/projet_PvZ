package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class Gigagargantuar extends Zombie {

	private final String name = "Gigagarantuar";
	private final String color = "#000000";
	
	public Gigagargantuar(int x, int y) {
		super(x, y, 300, 6000, 1, "slow");
	}

	public Gigagargantuar() {
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
		view.drawGigagargantuar(context, x, y, color);
		
		return new Gigagargantuar(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawGigagargantuar(graphics, x, y, color);
	}
	
}

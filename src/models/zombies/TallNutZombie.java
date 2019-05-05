package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class TallNutZombie extends Zombie {

	private final String name = "TallNutZombie";
	private final String color = "#000000";
	
	public TallNutZombie(int x, int y) {
		super(x, y, 100, 2400, 1, "slow");
	}

	public TallNutZombie() {
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
		view.drawTallNutZombie(context, x, y, color);
		
		return new TallNutZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawTallNutZombie(graphics, x, y, color);
	}
	
}

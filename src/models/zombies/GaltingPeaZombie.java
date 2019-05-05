package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class GaltingPeaZombie extends Zombie {

	private final String name = "GaltingPeaZombie";
	private final String color = "#000000";
	
	public GaltingPeaZombie(int x, int y) {
		super(x, y, 100, 200, 1, "slow");
	}

	public GaltingPeaZombie() {
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
		view.drawGaltingPeaZombie(context, x, y, color);
		
		return new GaltingPeaZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawGaltingPeaZombie(graphics, x, y, color);
	}
	
}

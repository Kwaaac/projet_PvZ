package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class DancingZombie extends Zombie {

	private final String name = "DancingZombie";
	private final String color = "#000000";
	
	public DancingZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow");
	}

	public DancingZombie() {
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
		view.drawDancingZombie(context, x, y, color);
		
		return new DancingZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDancingZombie(graphics, x, y, color);
	}
	
}

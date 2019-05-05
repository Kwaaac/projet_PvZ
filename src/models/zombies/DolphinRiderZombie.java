package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class DolphinRiderZombie extends Zombie {

	private final String name = "DolphinRiderZombie";
	private final String color = "#000000";
	
	public DolphinRiderZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow");
	}

	public DolphinRiderZombie() {
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
		view.drawDolphinRiderZombie(context, x, y, color);
		
		return new DolphinRiderZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDolphinRiderZombie(graphics, x, y, color);
	}
	
}

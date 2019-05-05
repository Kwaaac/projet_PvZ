package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class CatapultZombie extends Zombie {

	private final String name = "CatapultZombie";
	private final String color = "#000000";
	
	public CatapultZombie(int x, int y) {
		super(x, y, 1200, 660, 1, "fast");
	}

	public CatapultZombie() {
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
		view.drawCatapultZombie(context, x, y, color);
		
		return new CatapultZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawCatapultZombie(graphics, x, y, color);
	}
	
}

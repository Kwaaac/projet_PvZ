package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class ScreenDoorZombie extends Zombie {

	private final String name = "ScreenDoorZombie";
	private final String color = "#000000";
	
	public ScreenDoorZombie(int x, int y) {
		super(x, y, 100, 1300, 1, "slow");
	}

	public ScreenDoorZombie() {
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
		view.drawScreenDoorZombie(context, x, y, color);
		
		return new ScreenDoorZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawScreenDoorZombie(graphics, x, y, color);
	}
	
}

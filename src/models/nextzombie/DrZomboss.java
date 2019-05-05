package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.zombies.Zombie;
import views.SimpleGameView;

public class DrZomboss extends Zombie {

	private final String name = "DrZomboss";
	private final String color = "#000000";
	
	public DrZomboss(int x, int y) {
		super(x, y, 0, 31660, 1, "slow");
	}

	public DrZomboss() {
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
		view.drawDrZomboss(context, x, y, color);
		
		return new DrZomboss(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDrZomboss(graphics, x, y, color);
	}
	
}

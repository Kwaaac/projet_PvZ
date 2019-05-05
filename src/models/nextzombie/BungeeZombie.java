package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.zombies.Zombie;
import views.SimpleGameView;

public class BungeeZombie extends Zombie {

	private final String name = "Normal Zombie";
	private final String color = "#000000";
	
	public BungeeZombie(int x, int y) {
		super(x, y, 100, 460, 1, "slow");
	}

	public BungeeZombie() {
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
		view.drawBungeeZombie(context, x, y, color);
		
		return new BungeeZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawBungeeZombie(graphics, x, y, color);
	}
	
}

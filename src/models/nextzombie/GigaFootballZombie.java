package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.zombies.Zombie;
import views.SimpleGameView;

public class GigaFootballZombie extends Zombie {

	private final String name = "GigaFootballZombie";
	private final String color = "#000000";
	
	public GigaFootballZombie(int x, int y) {
		super(x, y, 100, 3000, 1, "verySlow");//2.5
	}

	public GigaFootballZombie() {
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
		view.drawGigaFootballZombie(context, x, y, color);
		
		return new GigaFootballZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawGigaFootballZombie(graphics, x, y, color);
	}
	
}

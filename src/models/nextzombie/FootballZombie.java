package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.zombies.Zombie;
import views.SimpleGameView;

public class FootballZombie extends Zombie {

	private final String name = "FootballZombie";
	private final String color = "#000000";
	
	public FootballZombie(int x, int y) {
		super(x, y, 100, 1600, 1, "verySlow");//2.5
	}

	public FootballZombie() {
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
		view.drawFootballZombie(context, x, y, color);
		
		return new FootballZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawFootballZombie(graphics, x, y, color);
	}
	
}

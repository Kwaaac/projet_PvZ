package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;
import models.zombies.Zombie;
public class ZombieYeti extends Zombie {

	private final String name = "ZombieYeti";
	private final String color = "#000000";
	
	public ZombieYeti(int x, int y) {
		super(x, y, 100, 920,  1,"medium");//5 or 2.1
	}

	public ZombieYeti() {
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
		view.drawZombieYeti(context, x, y, color);
		
		return new ZombieYeti(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawZombieYeti(graphics, x, y, color);
	}
	
}
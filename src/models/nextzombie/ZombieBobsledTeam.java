package models.nextzombie;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;
import models.zombies.Zombie;
public class ZombieBobsledTeam extends Zombie {

	private final String name = "Normal Zombie";
	private final String color = "#000000";
	
	public ZombieBobsledTeam(int x, int y) {
		super(x, y, 100, 1100, 1, "slow");
	}

	public ZombieBobsledTeam() {
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
		view.drawZombieBobsledTeam(context, x, y, color);
		
		return new ZombieBobsledTeam(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawZombieBobsledTeam(graphics, x, y, color);
	}
	
}

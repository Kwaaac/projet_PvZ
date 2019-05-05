package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class JackintheBoxZombie extends Zombie {

	private final String name = "JackintheBoxZombie";
	private final String color = "#000000";
	
	public JackintheBoxZombie(int x, int y) {
		super(x, y, 100, 340, 1, "verySlow");//2.2
	}

	public JackintheBoxZombie() {
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
		view.drawJackintheBoxZombie(context, x, y, color);
		
		return new JackintheBoxZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawJackintheBoxZombie(graphics, x, y, color);
	}
	
}

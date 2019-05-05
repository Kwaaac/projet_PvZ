package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class Imp extends Zombie {

	private final String name = "Imp";
	private final String color = "#000000";
	
	public Imp(int x, int y) {
		super(x, y, 100, 200,  1,"slow");
	}

	public Imp() {
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
		view.drawImp(context, x, y, color);
		
		return new Imp(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawImp(graphics, x, y, color);
	}
	
}

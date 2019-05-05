package models.zombies;

import java.awt.Graphics2D;
import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class FlagZombie extends Zombie{
	private final String name = "Flag Zombie";
	private final String color = "#FFFFFF";

	public FlagZombie(int x, int y) {
		super(x, y, 100, 200, 1, "slow");
	}

	public FlagZombie() {
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
		super.go((float) -0.95);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawFlagZombie(context, x, y, color);
		
		return new FlagZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawFlagZombie(graphics, x, y, color);
	}
	
}

package models.zombies;

import java.awt.Graphics2D;
import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class NormalZombie extends Zombie {

	private final String name = "Normal Zombie";
	private final String color = "#000000";

	public NormalZombie(int x, int y) {
		super(x, y, 100, 200, -0.73);
	}

	public NormalZombie() {
		super(50, 50, 100, 200, -0.73);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	public void go() {
		super.setSpeed((float) -0.73);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawNormalZombie(context, x, y, color);
		
		return new NormalZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawNormalZombie(graphics, x, y, color);
	}

}

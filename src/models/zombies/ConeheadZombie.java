package models.zombies;

import java.awt.Graphics2D;
import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class ConeheadZombie extends Zombie {

	private final String name = "Conehead Zombie";
	private final String color = "#CB5050";
	public ConeheadZombie(int x, int y) {
		super(x, y, 100, 560, -0.7);
	}

	public ConeheadZombie() {
		super(50, 50, 100, 560, -0.7);
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
		super.setSpeed((float) -0.7);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawConeheadZombie(context, x, y, color);
		
		return new ConeheadZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawConeheadZombie(graphics, x, y, color);
	}
}

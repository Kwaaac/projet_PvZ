package models.zombies;

import java.awt.Graphics2D;
import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class FlagZombie extends Zombie{
	private final String name = "Flag Zombie";
	private final String color = "#FFFFFF";
	private final int threat = 1;
	public FlagZombie(int x, int y) {
		super(x, y, 100, 200, -0.75);
	}

	public FlagZombie() {
		super(50, 50, 100, 200, -0.75);
	}

	@Override
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	public Integer getProb(int difficulty) {
		return (int) (((100/threat)*(difficulty))*0.55+0.10*threat);
	}
	
	public boolean canSpawn(int difficulty) {
		return threat<=difficulty;
	}
	
	public void go() {
		super.setSpeed((float) -0.75);
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

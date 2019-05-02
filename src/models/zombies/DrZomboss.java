package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class DrZomboss extends Zombie {

	private final String name = "DrZomboss";
	private final String color = "#000000";
	private final int threat = 1;
	
	public DrZomboss(int x, int y) {
		super(x, y, 0, 31660, -0.93);
	}

	public DrZomboss() {
		super(50, 50, 0, 31660, -0.93);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	public Integer getProb(int difficulty) {
		return (int) (((100/threat)*(difficulty))*0.55+0.10*threat);
	}
	
	public boolean canSpawn(int difficulty) {
		return threat<=difficulty;
	}
	
	public void go() {
		super.go((float) -0.93);
	}
	
	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawDrZomboss(context, x, y, color);
		
		return new DrZomboss(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDrZomboss(graphics, x, y, color);
	}

}

package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class BucketheadZombie extends Zombie {

	private final String name = "BucketheadZombie";
	private final String color = "#CB5050";
	private final int threat = 2;
	public BucketheadZombie(int x, int y) {
		super(x, y, 100, 1300, "slow");
	}

	public BucketheadZombie() {
		super(50, 50, 100, 1300, "slow");
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
		super.go((float) -0.7);
	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawBucketheadZombie(context, x, y, color);
		
		return new BucketheadZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawBucketheadZombie(graphics, x, y, color);
	}
}

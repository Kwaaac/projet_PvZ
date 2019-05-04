package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class TallNutZombie extends Zombie {

	private final String name = "TallNutZombie";
	private final String color = "#000000";
	private final int threat = 1;
	
	public TallNutZombie(int x, int y) {
		super(x, y, 100, 2400, -0.93);
	}

	public TallNutZombie() {
		super(50, 50, 100, 2400, -0.93);
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
		view.drawTallNutZombie(context, x, y, color);
		
		return new TallNutZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawTallNutZombie(graphics, x, y, color);
	}
	
	@Override
	public void action() {}
}

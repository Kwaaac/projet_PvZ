package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class Gigagargantuar extends Zombie {

	private final String name = "Gigagarantuar";
	private final String color = "#000000";
	private final int threat = 1;
	
	public Gigagargantuar(int x, int y) {
		super(x, y, 300, 6000, -0.93);
	}

	public Gigagargantuar() {
		super(50, 50, 300, 6000, -0.93);
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
		view.drawGigagargantuar(context, x, y, color);
		
		return new Gigagargantuar(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawGigagargantuar(graphics, x, y, color);
	}
	
	@Override
	public void action() {}
}

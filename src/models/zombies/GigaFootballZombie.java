package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class GigaFootballZombie extends Zombie {

	private final String name = "GigaFootballZombie";
	private final String color = "#000000";
	private final int threat = 1;
	
	public GigaFootballZombie(int x, int y) {
		super(x, y, 100, 3000, -0.93);//2.5
	}

	public GigaFootballZombie() {
		super(50, 50, 100, 3000, -0.93);
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
		super.setSpeed((float) -0.93);
	}
	
	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawGigaFootballZombie(context, x, y, color);
		
		return new GigaFootballZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawGigaFootballZombie(graphics, x, y, color);
	}

}

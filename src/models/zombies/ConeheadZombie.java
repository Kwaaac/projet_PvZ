package models.zombies;

import java.awt.Graphics2D;
import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class ConeheadZombie extends Zombie {

	private final String name = "Conehead Zombie";
	private final String color = "#CB5050";
	private final int threat = 2;
	public ConeheadZombie(int x, int y) {
		super(x, y, 100, 560, "slow");
	}

	public ConeheadZombie() {
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
	
	public Integer getProb(int difficulty) {
		return (int) (((100/threat)*(difficulty))*0.55+0.10*threat);
	}
	
	public boolean canSpawn(int difficulty) {
		return threat<=difficulty;
	}
	
	public void go() {
		super.go((float) -0.95);
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

package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.SimpleGameView;

public class NewspaperZombie extends Zombie {

	private final String name = "NewspaperZombie";
	private String color = "#FFFFFF";
	private final int threat = 1;
	
	public NewspaperZombie(int x, int y) {
		super(x, y, 100, 340, "slow");
	}

	public NewspaperZombie() {
		super(50, 50, 100, 320, "slow");
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
		view.drawNewspaperZombie(context, x, y, color);
		
		return new NewspaperZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawNewspaperZombie(graphics, x, y, color);
	}
	
	@Override
	public boolean action(SimpleGameData dataBord) {
		if (this.life <= 200) {
			setSpeed(-1.70);
			shootBarMax = (int) (3 * -7500);
			color = "#000000";
		}
		return true;
	}
	
	
}

package models.nextzombie;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import views.BordView;
import views.SimpleGameView;
import models.SimpleGameData;
import models.zombies.Zombie;
public class SquashZombie extends Zombie {

	private final String name = "SquashZombie";
	private final String color = "#000000";
	
	public SquashZombie(int x, int y) {
		super(x, y, 100000, 200, 1, "verySlow");
	}

	public SquashZombie() {
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
		return (int) (((100/this.getThreat())*(difficulty))*0.55+0.10*this.getThreat());
	}
	
	public boolean canSpawn(int difficulty) {
		return this.getThreat()<=difficulty;
	}
	
	public void go() {
		super.go((float) -0.93);
	}
	
	@Override
	public Zombie createNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawSquashZombie(context, x, y, color);
		
		return new SquashZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawSquashZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		// TODO Auto-generated method stub
		
	}
	
}

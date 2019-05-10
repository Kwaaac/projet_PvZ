package models.nextzombie;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import views.BordView;
import views.SimpleGameView;
import models.SimpleGameData;
import models.zombies.Zombie;
public class LadderZombie extends Zombie {

	private final String name = "LadderZombie";
	private final String color = "#000000";

	
	public LadderZombie(int x, int y) {
		super(x, y, 100, 840, 1 , "slow");
	}

	public LadderZombie() {
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
	
	public void go() {
		super.go((float) -0.93);
	}
	
	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawLadderZombie(context, x, y, color);
		
		return new LadderZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawLadderZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		// TODO Auto-generated method stub
		
	}
	
}

package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class BucketheadZombie extends Zombie {

	private final String name = "BucketheadZombie";
	private final String color = "#CB5050";
	
	public BucketheadZombie(int x, int y) {
		super(x, y, 100, 1300, 2, "slow");
	}

	public BucketheadZombie() {
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

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}
}

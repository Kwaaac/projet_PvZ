package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class NormalZombie extends Zombie {

	private final String type = "Normal Zombie";
	private final String color = "#000000";
	
	public NormalZombie(int x, int y) {
		super(x, y, 100, 200, 1 , "slow");
	}

	public NormalZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return type;
	}

	
	public void go() {
		super.go((float) -.93);
	}
	
	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawNormalZombie(context, x, y, color);
		
		return new NormalZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		view.drawNormalZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}


}

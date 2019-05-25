package models.nextzombie;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class DiggerZombie extends Zombie {

	private final String name = "DiggerZombie";
	private final String color = "#000000";
	
	public DiggerZombie(int x, int y) {
		super(x, y, 100, 300, 1, "verySlow");//2 or 7
	}

	public DiggerZombie() {
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
	public Zombie createNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawDiggerZombie(context, x, y, color);
		
		return new DiggerZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDiggerZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		// TODO Auto-generated method stub
		
	}
	
}

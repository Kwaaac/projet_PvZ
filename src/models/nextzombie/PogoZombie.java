package models.nextzombie;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;
public class PogoZombie extends Zombie {

	private final String name = "PogoZombie";
	private final String color = "#000000";
	
	public PogoZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow");
	}

	public PogoZombie() {
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
		view.drawPogoZombie(context, x, y, color);
		
		return new PogoZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawPogoZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		// TODO Auto-generated method stub
		
	}
	
}

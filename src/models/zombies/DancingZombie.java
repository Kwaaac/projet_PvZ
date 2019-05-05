package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class DancingZombie extends Zombie {

	private final String name = "DancingZombie";
	private final String color = "#000000";
	private Chrono dance = new Chrono();
	
	public DancingZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow");
	}

	public DancingZombie() {
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
		view.drawDancingZombie(context, x, y, color);
		
		return new DancingZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDancingZombie(graphics, x, y, color);
	}
	
	@Override
	public void action(BordView view,SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		int scareSize = BordView.getSquareSize();
		if (dance.asReachTimer(5)) {
			System.out.println("ajgvzejavze");
			myZombies.add(new BackupDancerZombie((int)super.getX()+scareSize,(int)super.getY()));//avant
			myZombies.add(new BackupDancerZombie((int)super.getX(),(int)super.getY()+scareSize));//haut
			myZombies.add(new BackupDancerZombie((int)super.getX()-scareSize,(int)super.getY()));//arriere
			myZombies.add(new BackupDancerZombie((int)super.getX(),(int)super.getY()-scareSize));//bas
			
			this.resetAS();
		}
		
		this.incAS();
	}
}

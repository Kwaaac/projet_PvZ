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
	private boolean lock = false;
	
	public DancingZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow");
		dance.start();
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
	public void draw(SimpleGameView view, Graphics2D graphics) {
		view.drawDancingZombie(graphics, x, y, color);
	}
	
	@Override
	public void action(BordView view,SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		int scareSize = BordView.getSquareSize();
		ArrayList<Zombie> zombieInQueu = new ArrayList<Zombie>();
		
		
		if (dance.asReachTimer(10) && lock == false) {
			if (dataBord.isCorrectBordLocation(view, (float)super.getX()+scareSize,(float)super.getY())) {
				zombieInQueu.add(new BackupDancerZombie((int)super.getX()+scareSize,(int)super.getY()));//avant
			}
			
			if (dataBord.isCorrectBordLocation(view, (float)super.getX(),(float)super.getY()+scareSize)) {
				zombieInQueu.add(new BackupDancerZombie((int)super.getX(),(int)super.getY()+scareSize));//haut
			}
			
			if (dataBord.isCorrectBordLocation(view, (float)super.getX()+scareSize,(float)super.getY())) {
				zombieInQueu.add(new BackupDancerZombie((int)super.getX()-scareSize,(int)super.getY()));//arriere
			}
			
			if (dataBord.isCorrectBordLocation(view, (float)super.getX(),(float)super.getY()-scareSize)) {
				zombieInQueu.add(new BackupDancerZombie((int)super.getX(),(int)super.getY()-scareSize));//bas
			}
			lock = true;
			dataBord.setZombieInQueu(zombieInQueu);
			this.resetAS();
		}
		
		this.incAS();
	}
}

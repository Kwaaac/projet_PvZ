package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class BackupDancerZombie extends Zombie {

	private final String name = "BackupDancerZombie";
	private final String color = "#000000";
	
	public BackupDancerZombie(int x, int y) {
		super(x, y, 100, 200, 4, "medium");
	}

	public BackupDancerZombie() {
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
		view.drawBackupDancerZombie(context, x, y, color);
		
		return new BackupDancerZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		view.drawBackupDancerZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}
	
	
	
	
	
	
	
	
}

package models.plants;

import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	private final String color = "#90D322";
	private Instant shootTime;
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 6);
		shootTime = Instant.now();
		shootBar = shootBarMax;
	}
	
	public Peashooter() {
		super(-10, -10, 0, 1, 1);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	@Override
	public void incAS() {
		Duration betweenTimer = Duration.between(shootTime, Instant.now());
		
		shootBar = (int) betweenTimer.getSeconds() + 1;
		
		System.out.println(shootBar + "///" + shootBarMax);
	}
	
	@Override
	public boolean readyToshot(ArrayList<Zombie> myZombies) {
		for(Entities z : myZombies) {
			if(this.sameLine(z)) {
				return shootBar % shootBarMax == 0;
			}
		}
		
		return false;
	}
	
	@Override
	public void resetAS() {
		this.shootTime = Instant.now();
	}
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPeashooter(context, x,  y, color);
		
		return new Peashooter(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawPeashooter(graphics, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		if(this.readyToshot(myZombies)) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
		
		this.incAS();
	}

}

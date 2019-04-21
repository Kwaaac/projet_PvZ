package models.plants;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.SimpleGameData;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	private final String color = "#90D322";
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 230);
		setTimerA(230);
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
	public boolean readyToshot(ArrayList<Zombie> myZombies) {
		for(Entities z : myZombies) {
			if(this.sameLine(z)) {
				return timerA % speedshoot == 0;
			}
		}
		return false;
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
		this.incAS();
		
		if(this.readyToshot(myZombies)) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
	}

}

package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Cell;
import models.Entities;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Bullet;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	private final String color = "#90D322";
	
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 5000, 100, "fast");

		shootBar = shootBarMax;			// La plante tire dès qu'elle est posée
	}
	
	public Peashooter() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name + cooldown; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	public boolean readyToshot(ArrayList<Cell> cells) {
		for(Cell c : cells) {
			if(c.isThereEntity()) {
				return shootBar >= shootBarMax;
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
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {
		if(this.readyToshot(dataBord.getLineCell(this.getCaseI(), this.getCaseJ()))) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
		
		this.incAS();
	}

}

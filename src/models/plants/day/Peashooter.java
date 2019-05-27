package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Peashooter extends Plant {
	private final String name = "Peashooter";
	private final String color = "#90D322";
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 5000, 100, "fast");

		shootBar = shootBarMax;			// La plante tire d�s qu'elle est pos�e
	}
	
	public Peashooter() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	public boolean readyToshot(ArrayList<Cell> cells) {
		for(Cell c : cells) {
			if(c.isThereBadZombies()) {
				return shootBar >= shootBarMax;
			}
		}
		
		return false;
	}
	
	@Override
	public Plant createNewPlant(int x, int y) {
		return new Peashooter(x, y);
	}
	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, SimpleGameData dataBord) {
		if(this.readyToshot(dataBord.getLineCell( this.getCaseJ(), this.getCaseI()))) {
			myBullet.add(new Pea(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
		
		this.incAS();
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}
	
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));
		
		view.drawCost(graphics, x, y, cost.toString());
	}


}

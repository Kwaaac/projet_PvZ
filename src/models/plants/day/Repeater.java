package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.Chrono;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Repeater extends Plant {
	private final String name = "Repeater";
	private final String color = "#90D322";
	private boolean firstShoot = false;
	private Chrono delayAttack = new Chrono();

	public Repeater(int x, int y) {
		super(x, y, 0, 300, 5000, 0, "fast");

		delayAttack.steady();
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
	}

	public Repeater() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereBadZombies()) {
				return shootBar >= shootBarMax;
			}
		}

		return false;
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Repeater(x, y);
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
		if(firstShoot && delayAttack.asReachTimerMs(50)) {
			myBullet.add(
					new Pea(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			firstShoot = false;
			delayAttack.steady();
			this.resetAS();
		}
		
		if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
			myBullet.add(
					new Pea(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));

			firstShoot = true;
			delayAttack.start();
			this.resetAS();
		}

		this.incAS();
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x-5, y, 25, 15));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));
		
		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x-20, y + sizeOfSPlant / 2, 25, 15));

		view.drawCost(graphics, x, y, cost.toString());
	}
	
}
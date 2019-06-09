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

public class SplitPea extends Plant {
	private final String name = "SplitPea";
	private final String color = "#90D322";
	private Chrono delayAttack = new Chrono();
	private int row = 0;

	public SplitPea(int x, int y) {
		super(x, y, 0, 300, 5000, 125, "fast");

		delayAttack.steady();
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
	}

	public SplitPea() {
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
		return new SplitPea(x, y);
	}

	private void superAction(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {
		shootBar = shootBarMax;
		delayAttack.startChronoIfReset();

		if (delayAttack.asReachTimerMs(100) || row == 0) {
			myBullet.add(new Pea(super.getX() + super.getSizeOfPlant(),
					super.getY() + (super.getSizeOfPlant() / 2) - 10));
			myBullet.add(new Pea(super.getX() + super.getSizeOfPlant(),
					super.getY() + (super.getSizeOfPlant() / 2) - 10, -20.0));
			delayAttack.start();
			row++;
			
			if(row == 10) {
				row = 0;
				shootBar = 0;
				unFeed();
			}
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone, SimpleGameData dataBord) {
		if (super.isFertilized()) {
			superAction(myBullet, view, myZombies, dataBord);
			return;
		} else {
			if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
				myBullet.add(new Pea(super.getX() + super.getSizeOfPlant(),
						super.getY() + (super.getSizeOfPlant() / 2) - 10));
				myBullet.add(new Pea(super.getX() + super.getSizeOfPlant(),
						super.getY() + (super.getSizeOfPlant() / 2) - 10, -20.0));
				this.resetAS();
			}

			this.incAS();
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));

		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x - 5, y, 20, 15));
		
		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x + sizeOfPlant - 15, y, 20, 15));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));

		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x - 20, y + sizeOfSPlant / 2, 20, 15));
		
		graphics.setColor(Color.decode("#6ea01b"));
		graphics.fill(new Rectangle2D.Float(x + 35, y + sizeOfSPlant / 2, 20, 15));

		view.drawCost(graphics, x, y, cost.toString());
	}

}
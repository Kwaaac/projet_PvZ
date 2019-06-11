package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.Chrono;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Cabbage;
import models.projectiles.KernelNButter;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class KernelPult extends Plant {
	private final String name = "KernelPult";
	private final String color = "#e6f200";
	private int row = 0;
	private Chrono delayAttack = new Chrono();

	public KernelPult(int x, int y) {
		super(x, y, 0, 300, 5000, 100, "fast");

		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
		delayAttack.steady();
	}

	public KernelPult() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	private Zombie getTarget(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereBadZombies()) {
				ArrayList<Zombie> zInCell = c.getBadZombiesInCell();
				return zInCell.get(zInCell.size() - 1);
			}
		}

		return null;
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new KernelPult(x, y);
	}

	private void superAction(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {
		shootBar = shootBarMax;
		delayAttack.startChronoIfReset();

		if (delayAttack.asReachTimerMs(100) || row == 0) {
			myBullet.add(
					new Pea(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			delayAttack.start();
			row++;

			if (row == 10) {
				row = 0;
				shootBar = 0;
				unFeed();
			}
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
		if (super.isFertilized()) {
			superAction(myBullet, view, myZombies, dataBord);
			return;
		} else {
			if (this.readyToshot()) {
				Zombie target = getTarget(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()));
				if (target != null) {
					Random rand = new Random();
					boolean butter = false;
					if(rand.nextInt(100) <=  25) { butter = true; }
					myBullet.add(new KernelNButter(super.getX() + super.getSizeOfPlant(),
							super.getY() + (super.getSizeOfPlant() / 2) - 10, target, butter));

					this.resetAS();
				}
			}

			this.incAS();
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y + 20, sizeOfPlant, sizeOfPlant-20));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2 + 20, sizeOfSPlant, sizeOfSPlant-20));

		view.drawCost(graphics, x, y, cost.toString());
	}

}

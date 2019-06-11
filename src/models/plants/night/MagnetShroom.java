package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import models.Chrono;
import models.SimpleGameData;
import models.TombStone;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class MagnetShroom extends Plant {
	private final String name = "PuffShroom";
	private final String color = "#af0505";
	private final String stealingColor = "#a04949";
	private boolean steal = false;
	private Chrono delayAttack = new Chrono();
	private int row = 0;
	private int rowLimit;
	private final int stealLimit = 20;

	public MagnetShroom(int x, int y) {
		super(x, y, 0, 300, 15_000, 100, "fast");
		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
	}

	public MagnetShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new MagnetShroom(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x + 10, y + sizeOfPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		if (steal) {
			graphics.setColor(Color.decode(stealingColor));
			graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));
		} else {
			graphics.setColor(Color.decode(color));
			graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));
		}
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 25, sizeOfSPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(
				new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2, sizeOfSPlant - 5, sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

	private void superAction(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {

		shootBar = shootBarMax;
		delayAttack.startChronoIfReset();

		if (!steal) {
			rowLimit = 0;
			for (Zombie z : myZombies) {
				if (z.magnetizable()) {
					this.resetAS();
					rowLimit++;
					if (rowLimit == stealLimit) {
						break;
					}
				}
			}
			steal = true;
		} else {
			if (delayAttack.asReachTimerMs(100) || row == 0) {
				myBullet.add(new Pea(super.getX() + super.getSizeOfPlant() - 25,
						super.getY() + (super.getSizeOfPlant() / 2) - 10 - 25, 75, 300, 1));
				delayAttack.start();
				row++;

			}
		}

		if (row == rowLimit) {
			steal = false;
			row = 0;
			shootBar = 0;
			unFeed();
		}

	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
		if (super.isFertilized()) {
			superAction(myBullet, view, myZombies, dataBord);
			return;
		} else {
			if (dataBord.getDayTime() == "Night") {

				if (readyToshot()) {
					steal = false;
					if (!myZombies.isEmpty()) {
						for (Zombie z : myZombies) {
							if (z.magnetizable()) {
								steal = true;
								this.resetAS();
								break;
							}
						}
					}

					this.incAS();
				}
			}
		}
	}
}

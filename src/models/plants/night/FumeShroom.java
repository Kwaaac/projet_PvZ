package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import models.Chrono;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Fume;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class FumeShroom extends Plant {
	private final String name = "FumeShroom";
	private final String color = "#901bd1";
	private Chrono delayAttack = new Chrono();
	private int row = 0;

	public FumeShroom(int x, int y) {
		super(x, y, 0, 300, 2500, 75, "fast");
		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
	}

	public FumeShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new FumeShroom(x, y);
	}

	private boolean detect(SimpleGameData dataBord) {
		int caseI = getCaseI();
		List<Cell> cells = dataBord.getLineCell(getCaseJ(), caseI, caseI + 5);
		for (Cell c : cells) {
			if (c.isThereBadZombies()) {
				return true;
			}
		}

		return false;
	}

	int sqrS = BordView.getSquareSize();

	private void superAction(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {
		shootBar = shootBarMax;
		delayAttack.startChronoIfReset();

		if (delayAttack.asReachTimer(1) || row == 0) {

			for (int i = 0; i < 5; i++) {
				myBullet.add(new Fume(x + (i * sqrS), y, 500));
			}

			int caseI = getCaseI();
			List<Cell> cells = dataBord.getLineCell(getCaseJ(), caseI, caseI + 5);
			for (Cell c : cells) {
				for (Zombie z : c.getBadZombiesInCell()) {
					z.setX(z.getX() + 50);
				}
			}

			delayAttack.start();
			row++;

			if (row == 3) {
				row = 0;
				shootBar = 0;
				unFeed();
			}
		}

	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (dataBord.getDayTime() == "Night") {
			if (super.isFertilized()) {
				superAction(myBullet, view, myZombies, dataBord);
				return;
			} else {
				if (this.readyToshot() && detect(dataBord)) {
					for (int i = 0; i < 5; i++) {
						myBullet.add(new Fume(x + (i * sqrS), y));
					}

					this.resetAS();
				}

				this.incAS();
			}
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x + 15, y + sizeOfSPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 9, y, sizeOfPlant + 25, sizeOfPlant - 25, 10, 10));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 35, sizeOfSPlant - 35));

		graphics.setColor(Color.decode(color));
		graphics.fill(
				new RoundRectangle2D.Float(x - 18, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

}
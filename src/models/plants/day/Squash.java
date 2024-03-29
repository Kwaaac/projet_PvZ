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
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Squash extends Plant {
	private final String name = "Repeater";
	private final String color = "#FFFFFF";
	private Chrono delayAttack = new Chrono();
	private int row = 0;
	private final int maxRow = 2;

	public Squash(int x, int y) {
		super(x, y, 0, 300, 5100, 50, "free");

		delayAttack.steady();
		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
	}

	public Squash() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Squash(x, y);
	}

	private ArrayList<Cell> zone(SimpleGameData dataBord) {
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i = -1; i < 2; i++) {

			Cell cell = dataBord.getCell(getCaseJ(), getCaseI() + i);

			if (cell != null) {
				cells.add(cell);
			}
		}

		return cells;
	}

	private ArrayList<Zombie> detect(ArrayList<Cell> cells) {
		ArrayList<Zombie> zombies = new ArrayList<>();

		for (Cell c : cells) {
			ArrayList<Zombie> lstz = c.getBadZombiesInCell();

			if (!lstz.isEmpty()) {
				for (Zombie z : lstz) {
					zombies.add(z);
				}
				return zombies;
			}
		}

		return zombies;
	}

	private void startDelay() {
		if (delayAttack.isReset()) {
			delayAttack.start();
		}
	}

	private void superAction(List<Zombie> myZombies, SimpleGameData data) {
		List<Cell> cells = null;

		int rdmZ = data.RandomPosGenerator(myZombies.size() - 1);

		Zombie z = myZombies.get(rdmZ);

		Cell cell = data.getCell(z.getCaseJ(), z.getCaseI());

		for (Zombie zed : cell.getBadZombiesInCell()) {
			zed.takeDmg(1800);
		}

		row++;
		if (row == maxRow) {
			unFeed();
			row = 0;
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone, SimpleGameData dataBord) {

		if (super.isFertilized()) {
			superAction(myZombies, dataBord);
			return;
		} else {
			if (readyToshot()) {
				ArrayList<Zombie> zombie = this.detect(this.zone(dataBord));

				if (!zombie.isEmpty()) {
					startDelay();

					if (delayAttack.asReachTimer(2)) {

						for (Zombie z : zombie) {
							z.takeDmg(1800);

							this.setLife(0);
						}
					}
				}
			}

			incAS();

		}
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
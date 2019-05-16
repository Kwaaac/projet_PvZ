package models.plants.pool;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class TangleKelp extends Plant {
	private final String name = "TangleKelp";
	private final String color = "#517614";
	private Chrono delayAttack = new Chrono();
	private boolean eating = false;

	public TangleKelp(int x, int y) {
		super(x, y, 0, 2400, 90, 25, "slow");
	}

	public TangleKelp() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	private void startDelay() {
		if (delayAttack.isReset()) {
			delayAttack.start();
		}
	}

	private ArrayList<Cell> zone(SimpleGameData dataBord) {
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i = 0; i < 2; i++) {

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
			ArrayList<Zombie> lstz = c.getZombiesInCell();

			if (!lstz.isEmpty()) {
				zombies.add(lstz.get(0));
				break;
			}
		}

		return zombies;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {

		if (this.readyToshot()) {
			eating = false;
			ArrayList<Zombie> zombie = this.detect(this.zone(dataBord));

			if (!zombie.isEmpty()) {
				startDelay();

				if (delayAttack.asReachTimer(1)) {

					zombie.get(0).setLife(1800);
					eating = true;
					resetAS();

					this.life = 0;
				}
			}
		}

		incAS();

	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawTangleKelp(context, x, y, color);

		return new TangleKelp(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawTangleKelp(graphics, x, y, color);
	}
}

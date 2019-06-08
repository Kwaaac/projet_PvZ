package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.Chrono;
import models.DeadPool;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Chomper extends Plant {
	private final String name = "Chomper";
	private final String eatingColor = "#681D8C";
	private final String notEatingColor = "#BE33FF";
	private Chrono delayAttack = new Chrono();
	private boolean eating = false;

	public Chomper(int x, int y) {
		super(x, y, 0, 350, 15_000, 150, "free");

		delayAttack.steady();
		shootBar = shootBarMax; // Allow the plant to fire instantly
	}

	public Chomper() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

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
			ArrayList<Zombie> lstz = c.getBadZombiesInCell();

			if (!lstz.isEmpty()) {
				zombies.add(lstz.get(lstz.size() - 1));
				break;
			}
		}

		return zombies;
	}

	@Override
	public String toString() {
		return name;
	}


	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (this.readyToshot()) {
			eating = false;
			ArrayList<Zombie> zombie = this.detect(this.zone(dataBord));

			if (!zombie.isEmpty()) {
				delayAttack.startChronoIfReset();

				if (delayAttack.asReachTimer(1)) {

					zombie.get(zombie.size()-1).takeDmg(1800);
					eating = true;
					resetAS();
				}
			}
		}

		incAS();

	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Chomper(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		if (eating) {
			graphics.setColor(Color.decode(eatingColor));
		} else {
			graphics.setColor(Color.decode(notEatingColor));
		}
		graphics.fill(new Rectangle2D.Float(x - 10, y - 10, sizeOfPlant + 20, sizeOfPlant + 20));
		
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(notEatingColor));
		graphics.fill(new Rectangle2D.Float(x - 25, y + sizeOfSPlant / 2 - 10, sizeOfSPlant + 20, sizeOfSPlant + 20));

		view.drawCost(graphics, x, y, cost.toString());
	}

}

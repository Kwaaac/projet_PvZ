package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Squash extends Plant {
	private final String name = "Repeater";
	private final String color = "#FFFFFF";
	private Chrono delayAttack = new Chrono();

	public Squash(int x, int y) {
		super(x, y, 0, 300, 5100, 0, "free");

		delayAttack.steady();
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
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

			}
		}

		return zombies;
	}

	private void startDelay() {
		if (delayAttack.isReset()) {
			delayAttack.start();
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {

		if (readyToshot()) {
			ArrayList<Zombie> zombie = this.detect(this.zone(dataBord));

			if (!zombie.isEmpty()) {
				startDelay();

				if (delayAttack.asReachTimer(2)) {

					for (Zombie z : zombie) {
						z.setLife(1800);

						this.setLife(life);
					}
				}
			}
		}

		incAS();

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
package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.SimpleGameData;
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
		super(x, y, 0, 350, 15_000, 0, "free");

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
			ArrayList<Zombie> lstz = c.getZombiesInCell();

			if (!lstz.isEmpty()) {
				zombies.add(lstz.get(lstz.size() - 1));
				break;
			}
		}

		return zombies;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}
	
	private void startDelay() {
		if(delayAttack.isReset()) {
			delayAttack.start();
		}
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
				}
			}
		}

		incAS();

	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawChomper(context, x, y, eatingColor);

		return new Chomper(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		if (eating) {
			view.drawChomper(graphics, x, y, eatingColor);
		} else {
			view.drawChomper(graphics, x, y, notEatingColor);
		}
	}

}

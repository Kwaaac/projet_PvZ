package models.plants.pool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.cells.WaterCell;
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
		return name;
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
			ArrayList<Zombie> lstz = c.getBadZombiesInCell();

			if (!lstz.isEmpty()) {
				zombies.add(lstz.get(0));
				break;
			}
		}

		return zombies;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
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
	public Plant createNewPlant(int x, int y) {
		return new TangleKelp(x, y);

	}
	
	@Override
	public boolean plantingCondition(Cell cell) {
		if(cell.equals(new WaterCell()) && !cell.isGroundPlantPlanted()) {
			return cell.addPlant(this);
		}
		
		return false;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));

		graphics.setColor(Color.decode("#16D9B6"));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));

		graphics.setColor(Color.decode("#16D9B6"));
		graphics.fill(new Ellipse2D.Float(x - 7, y + sizeOfSPlant / 2 + 8, sizeOfSPlant - 15, sizeOfSPlant - 15));
		
		view.drawCost(graphics, x, y, cost.toString());
	}
}

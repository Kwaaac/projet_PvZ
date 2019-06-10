package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Plantern extends Plant {

	private final String name = "Plantern";
	private final String color = "#f6ff00";
	private final ArrayList<Cell> zone = new ArrayList<>();
	private boolean flag = false;

	public Plantern(int x, int y) {
		super(x, y, 0, 1, 1200, 25, "slow");
		this.shootTime = System.currentTimeMillis();
	}

	public Plantern() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	private void couverture(SimpleGameData dataBord) {
		int caseXPlantern = BordView.caseXFromX(x);
		int caseYPlantern = BordView.caseYFromY(y);
		for (int i = -3; i <= 3; i++) {
			for (int j = -2; j <= 2; j++) {
				Cell cell = dataBord.getCell(caseYPlantern + j, caseXPlantern + i);
				if (cell != null) {
					zone.add(cell);
				}
			}
		}
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (!flag) {
			couverture(dataBord);
		}

		for (Cell c : zone) {
			c.disableFog(1);
		}
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Plantern(x, y);

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
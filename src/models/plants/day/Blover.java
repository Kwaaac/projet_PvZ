package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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

public class Blover extends Plant {

	private final String name = "Plantern";
	private final String color = "#43ff00";

	public Blover(int x, int y) {
		super(x, y, 0, 1, 1200, 100, "fast");
		this.shootTime = System.currentTimeMillis();
	}

	public Blover() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	private void couverture(SimpleGameData dataBord) {
		int caseXPlantern = BordView.caseXFromX(x);
		int caseYPlantern = BordView.caseYFromY(y);

		int m = dataBord.getNbLines();
		int p = dataBord.getNbColumns();
		System.out.println(m + " : " + p);

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= p; j++) {
				Cell cell = dataBord.getCell(i, j);
				if (cell != null) {
					cell.disableFog(20_000);
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
		System.out.println(shootBar + " con " + shootBarMax);
		if (readyToshot()) {
			couverture(dataBord);

			for (Zombie z : myZombies) {
				if (z.isFlying()) {
					z.setLife(0);
				}
			}

			setLife(0);
			dataBord.getCell(getCaseJ(), getCaseI()).removePlant(this);
		}
		incAS();
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Blover(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));

		view.drawCost(graphics, x, y, cost.toString());
	}

	@Override
	public void hasToDie(DeadPool DPe, SimpleGameData data) {
		if (this.isDead()) {
			data.getCell(this.getCaseJ(), this.getCaseI()).removePlant(this);
			DPe.add(this);
		}
	}
}
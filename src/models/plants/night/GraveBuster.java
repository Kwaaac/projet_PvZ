package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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

public class GraveBuster extends Plant {
	private final String name = "GraveBuster";
	private final String color = "#000000";
	private Chrono eatingTomb = new Chrono();

	public GraveBuster(int x, int y) {
		super(x, y, 0, 300, 0, 75, "fast");

		eatingTomb.steady();
	}

	public GraveBuster() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new GraveBuster(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x-5, y - 25, sizeOfPlant+10, sizeOfPlant+25));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - sizeOfSPlant/4, y + sizeOfSPlant / 2 - 10, sizeOfSPlant, sizeOfSPlant+10));

		view.drawCost(graphics, x, y, cost.toString());
	}

	@Override
	public boolean plantingCondition(Cell cell) {
		if (cell.isTombstone()) {
			eatingTomb.start();
			return true;

		}

		return false;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone, SimpleGameData dataBord) {
		if (!eatingTomb.isReset()) {
			if (!eatingTomb.asReachTimer(5)) {
				setY((float) (y + 0.2));
			} else {
				Cell thisCell = dataBord.getCell(this.getCaseJ(), this.getCaseI());
				thisCell.removePlant(this);
				myTombStone.remove(thisCell.removeTombstone());
				eatingTomb.stop();
				setLife(0);
				
			}
		}
	}
	
	@Override
	public void hasToDie(DeadPool DPe, SimpleGameData data) { 
			if (this.isDead()) { 
				data.getCell(this.getCaseJ(), this.getCaseI()).removePlant(this); 
				DPe.add(this); 
			} 
	} 
	
	

}
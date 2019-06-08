package models.plants.pool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.SimpleGameData;
import models.cells.Cell;
import models.cells.WaterCell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.projectiles.WeakSpore;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SeaShroom extends Plant {
	private final String name = "SeaShroom";
	private final String color = "#8FD916";

	public SeaShroom(int x, int y) {
		super(x, y, 0, 300, 180 * 3, 0, "slow");
	}

	public SeaShroom() {
		super(-10, -10, 0, 1, 1, 0, "slow");
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return name;
	}

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereBadZombies()) {
				return shootBar >= shootBarMax;
			}
		}
		return false;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {
		if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
			myBullet.add(new WeakSpore(super.getX() + super.getSizeOfPlant(),
					super.getY() + (super.getSizeOfPlant() / 2) - 10));

			this.resetAS();
		}

		this.incAS();
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new SeaShroom(x, y);

	}

	@Override
	public boolean plantingCondition(Cell cell) {
		if (cell.isWater() && !cell.isGroundPlantPlanted()) {
			return cell.addPlant(this);
		}

		return false;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x + 10, y , sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x , y + sizeOfSPlant , sizeOfSPlant - 35, sizeOfSPlant - 35));
		
		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2 + 15, sizeOfSPlant - 15, sizeOfSPlant - 25, 10, 10));
		
		view.drawCost(graphics, x, y, cost.toString());
	}

}

package models.plants.pool;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import models.cells.WaterCell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.projectiles.Spore;
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
		return super.toString() + "--" + name;
	}

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereZombies()) {
				return shootBar >= shootBarMax;
			}
		}
		return false;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
		if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
			myBullet.add(new WeakSpore(super.getX() + super.getSizeOfPlant(),
					super.getY() + (super.getSizeOfPlant() / 2) - 10));

			this.resetAS();
		}

		this.incAS();
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawSeaShroom(context, x, y, color);

		return new SeaShroom(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawSeaShroom(graphics, x, y, color);
	}
	
	@Override
	public boolean plantingCondition(Cell cell) {
		if(cell.equals(new WaterCell()) && !cell.isGroundPlantPlanted()) {
			return cell.addPlant(this);
		}
		
		return false;
	}

}

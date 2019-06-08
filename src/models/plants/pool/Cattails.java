package models.plants.pool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Cattails extends Plant {
	private final String name = "Cattails";
	private final String color = "#8FD916";

	public Cattails(int x, int y) {
		super(x, y, 0, 300, 1500, 0, "verySlow");
	}

	public Cattails() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Cattails(x, y);
	}

	@Override
	public boolean plantingCondition(Cell cell) {

		if (cell.isGroundPlantPlanted() && cell.getGroundPlant().toString() == "LilyPad") {
			return cell.addPlant(this);
		}

		return false;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));

		graphics.setColor(Color.decode("#DB5FBD"));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));

	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));

		graphics.setColor(Color.decode("#DB5FBD"));
		graphics.fill(new Ellipse2D.Float(x - 7, y + sizeOfSPlant / 2 + 8, sizeOfSPlant - 15, sizeOfSPlant - 15));

		view.drawCost(graphics, x, y, cost.toString());
	}

}

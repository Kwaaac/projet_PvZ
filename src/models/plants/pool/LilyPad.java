package models.plants.pool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import models.cells.GrassCell;
import models.cells.WaterCell;
import models.plants.Plant;
import models.plants.day.Pot;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class LilyPad extends Plant {
	private final String name = "LilyPad";
	private final String color = "#90D322";

	public LilyPad(int x, int y) {
		super(x, y, 0, 300, 0, 0, "free");
	}

	public LilyPad() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new LilyPad(x, y);

	}

	@Override
	public int getTypeOfPlant() {
		return 0;
	}

	/**
	 * Can be planted on Water
	 * 
	 * @param the cell where the plant will be planted
	 * @return True if the cell is a correct location for the plant, false otherwise
	 */
	@Override
	public boolean plantingCondition(Cell cell) {
		if (cell.equals(new WaterCell())) {
			return cell.addPlant(this);

		} else if (cell.isGroundPlantPlanted() && cell.isMainPlantPlanted()) {
			return cell.addPlant(this);
		}

		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LilyPad)) {
			return false;
		}
		LilyPad s = (LilyPad) o;
		return name.equals(s.name) && color.equals(s.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y - 15, sizeOfPlant + 30, sizeOfPlant + 30));
		
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));
		
		view.drawCost(graphics, x, y, cost.toString());
	}
}

package models.plants.pool;

import java.awt.Graphics2D;
import java.util.ArrayList;
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
		return super.toString() + "--" + name;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawLilyPad(context, x, y, color);

		return new LilyPad(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawLilyPad(graphics, x, y, color);
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
}

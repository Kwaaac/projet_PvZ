package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Objects;

import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.cells.TileCell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Pot extends Plant {
	private final String name = "Pot";
	private final String color = "#C98C79";

	public Pot(int x, int y) {
		super(x, y, 0, 300, 0, 25, "fast");
	}

	public Pot() {
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
		return new Pot(x, y);

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


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pot)) {
			return false;
		}
		Pot p = (Pot) o;
		return name.equals(p.name) && color.equals(p.color);
	}
	
	@Override
	public boolean plantingCondition(Cell cell) {
		if(!cell.isGroundPlantPlanted() && cell.equals(new TileCell())) {
			return cell.addPlant(this);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}

	@Override
	public int getTypeOfPlant() {
		return 0;
	}

}

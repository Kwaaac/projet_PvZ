package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class DoomShroom extends Plant {
	private final String name = "DoomShroom";
	private final String color = "#1e1b1b";

	public DoomShroom(int x, int y) {
		super(x, y, 0, 1, 1200, 125, "verySlow");
		this.shootTime = System.currentTimeMillis();
	}

	public DoomShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new DoomShroom(x, y);

	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {

			if (this.readyToshot()) {
				for (Zombie z : myZombies) {
					z.takeDmg(1800);
				}

				Cell cell = dataBord.getCell(getCaseJ(), getCaseI());

				cell.removeAllPlant();
				cell.crater();
			}

			this.incAS();
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#5b5252"));
		graphics.fill(new Rectangle2D.Float(x + 15, y + sizeOfSPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 9, y, sizeOfPlant + 25, sizeOfPlant - 25, 10, 10));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#5b5252"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 35, sizeOfSPlant - 35));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 18, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

}

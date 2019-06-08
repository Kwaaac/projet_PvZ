package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class HypnoShroom extends Plant {
	private final String name = "HypnoShroom";
	private final String color = "#db32b6";

	public HypnoShroom(int x, int y) {
		super(x, y, 0, 300, 0, 75, "fast");
	}

	public HypnoShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new HypnoShroom(x, y);
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, SimpleGameData dataBord) {
		if (dataBord.getDayTime() == "Night") {
			Cell cell = dataBord.getCell(getCaseJ(), getCaseI());

			for (Zombie z : cell.getBadZombiesInCell()) {
				if (this.hit(z)) {
					z.reverseTeam(dataBord);

					cell.removePlant(this);
				}
			}
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x + 10, y + sizeOfPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 5, 10, 10));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#f7caed"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 25, sizeOfSPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(
				new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2, sizeOfSPlant - 5, sizeOfSPlant - 5, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

}
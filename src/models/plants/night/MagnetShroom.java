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
import models.projectiles.WeakSpore;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class MagnetShroom extends Plant {
	private final String name = "PuffShroom";
	private final String color = "#af0505";
	private final String stealingColor = "#a04949";
	private boolean steal = false;

	public MagnetShroom(int x, int y) {
		super(x, y, 0, 300, 15_000, 0, "fast");
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
	}

	public MagnetShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new MagnetShroom(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x + 10, y + sizeOfPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		if (steal) {
			graphics.setColor(Color.decode(stealingColor));
			graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));
		} else {
			graphics.setColor(Color.decode(color));
			graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));
		}
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 25, sizeOfSPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(
				new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2, sizeOfSPlant - 5, sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, SimpleGameData dataBord) {

		if (dataBord.getDayTime() == "Night") {

			if (readyToshot()) {
				steal = false;
				if (!myZombies.isEmpty()) {
					if (myZombies.get(myZombies.size() - 1).magnetizable()) {
						steal = true;
						this.resetAS();
					}
				}

				this.incAS();
			}
		}
	}
}

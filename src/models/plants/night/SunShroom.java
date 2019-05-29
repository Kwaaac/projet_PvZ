package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SunShroom extends Plant {
	private final String name = "SunShroom";
	private final String color = "#ffff00";
	private Chrono growing = new Chrono();
	private boolean grow = false;

	public SunShroom(int x, int y) {
		super(x, y, 0, 300, 24_000, 0, "fast");
		growing.start();
		this.shootTime = System.currentTimeMillis();
	}

	public SunShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new SunShroom(x, y);
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, SimpleGameData dataBord) {
		if (dataBord.getDayTime() == "Night") {
			if (this.readyToshot()) {

				if (grow == true) {

					dataBord.spawnSun(view, x + 20, y + 20, 25, 85);
					this.resetAS();
				} else {

					dataBord.spawnSun(view, x + 20, y + 20, 15, 55);
					this.resetAS();
				}
			}

			if (growing.asReachTimer(120)) {
				grow = true;
			}

			this.incAS();
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x + 10, y, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25, 10, 10));

	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#ffffff"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 35, sizeOfSPlant - 35));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2 + 15, sizeOfSPlant - 15,
				sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

}
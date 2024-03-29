package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import models.Chrono;
import models.SimpleGameData;
import models.TombStone;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SunShroom extends Plant {
	private final String name = "SunShroom";
	private String color = "#cece02";
	private Chrono growing = new Chrono();
	private boolean grow = false;
	private int sunSize = 85;
	private Chrono delaySun = new Chrono();
	private int row = 0;

	public SunShroom(int x, int y) {
		super(x, y, 0, 300, 24_000, 25, "fast");
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

	private void superAction(BordView view, SimpleGameData dataBord) {
		shootBar = shootBarMax;
		delaySun.startChronoIfReset();

		if (delaySun.asReachTimerMs(100) || row == 0) {

			int rdmPos = SimpleGameData.RandomPosGenerator(-25, 25);
			dataBord.spawnSun(view, x + rdmPos, y + 20, 25, sunSize);

			row++;
			if (row == 5) {
				grow = true;
				color = "#ffff00";
				row = 0;
				shootBar = 0;
				unFeed();
			}
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (dataBord.getDayTime() == "Night") {
			if (super.isFertilized()) {
				superAction(view, dataBord);
				return;
			} else {
				if (this.readyToshot()) {
					int rdmPos = SimpleGameData.RandomPosGenerator(-25, 25);
					if (grow == true) {
						sunSize = 45;
					}
					dataBord.spawnSun(view, x + rdmPos, y + rdmPos, 15, sunSize);
					this.resetAS();
				}

				if (!grow && growing.asReachTimer(120)) {
					grow = true;
					color = "#ffff00";
				}

				this.incAS();
			}
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

		graphics.setColor(Color.decode("#ffff00"));
		graphics.fill(new RoundRectangle2D.Float(x - 10, y + sizeOfSPlant / 2 + 15, sizeOfSPlant - 15,
				sizeOfSPlant - 25, 10, 10));

		view.drawCost(graphics, x, y, cost.toString());
	}

}
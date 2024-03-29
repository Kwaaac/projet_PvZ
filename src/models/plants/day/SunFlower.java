package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

import models.Chrono;
import models.DeadPool;
import models.SimpleGameData;
import models.TombStone;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SunFlower extends Plant {
	private final String name = "SunFlower";
	private final String color = "#FEFF33";
	private Chrono delaySun = new Chrono();
	private int row = 0;

	public SunFlower(int x, int y) {
		// x, y, damage, life, shootBarMax, cost, cooldown
		super(x, y, 0, 300, 10_000, 25, "fast");
		this.shootTime = System.currentTimeMillis();
	}

	public SunFlower() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new SunFlower(x, y);
	}

	private void superAction(BordView view, SimpleGameData dataBord) {
		shootBar = shootBarMax;
		delaySun.startChronoIfReset();

		if (delaySun.asReachTimerMs(100) || row == 0) {

			int rdmPos = SimpleGameData.RandomPosGenerator(-25, 25);
			dataBord.spawnSun(view, x + rdmPos, y + 20, 25, 85);

			row++;
			if (row == 5) {
				row = 0;
				shootBar = 0;
				unFeed();
			}
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
		if (super.isFertilized()) {
			superAction(view, dataBord);
			return;
		} else {
			if (this.readyToshot()) {
				int rdmPos = SimpleGameData.RandomPosGenerator(-25, 25);
				dataBord.spawnSun(view, x + rdmPos, y + 20, 25, 85);
				this.resetAS();
			}

			this.incAS();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SunFlower)) {
			return false;
		}
		SunFlower s = (SunFlower) o;
		return name.equals(s.name) && color.equals(s.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#4D8939"));
		graphics.fill(new Rectangle2D.Float(x + sizeOfPlant / 4 - 5, y + sizeOfPlant / 2, 5, sizeOfPlant));

		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y + sizeOfPlant / 2 - 12, sizeOfPlant / 2, sizeOfPlant / 2));

		graphics.setColor(Color.decode("#AF6907"));
		graphics.fill(new Ellipse2D.Float(x - 12 + sizeOfPlant / 4, y + sizeOfPlant / 2 - 5, sizeOfPlant / 2 - 15,
				sizeOfPlant / 2 - 15));
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#4D8939"));
		graphics.fill(new Rectangle2D.Float(x + sizeOfPlant / 4 - 5, y + sizeOfPlant / 2, 5, sizeOfPlant));

		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y + sizeOfPlant / 2 - 12, sizeOfPlant / 2, sizeOfPlant / 2));

		graphics.setColor(Color.decode("#AF6907"));
		graphics.fill(new Ellipse2D.Float(x - 12 + sizeOfPlant / 4, y + sizeOfPlant / 2 - 5, sizeOfPlant / 2 - 15,
				sizeOfPlant / 2 - 15));

		view.drawCost(graphics, x, y, cost.toString());
	}

	@Override
	public void hasToDie(DeadPool DPe, SimpleGameData data) {
		if (this.isDead()) {
			data.getCell(this.getCaseJ(), this.getCaseI()).removePlant(this);
			DPe.add(this);
		}
	}

}
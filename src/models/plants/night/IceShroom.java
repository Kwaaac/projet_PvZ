package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import models.DeadPool;
import models.SimpleGameData;
import models.TombStone;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class IceShroom extends Plant {
	private final String name = "IceShroom";
	private final String color = "#4286f4";

	public IceShroom(int x, int y) {
		super(x, y, 0, 1, 1200, 75, "verySlow");
		this.shootTime = System.currentTimeMillis();
	}

	public IceShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new IceShroom(x, y);
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (dataBord.getDayTime() == "Night") {
			if (this.readyToshot()) {
				for (Zombie z : myZombies) {
					if (z.isBad()) {
						z.stunned(3500);
						z.slowed(19);
					}
				}

				this.life = 0;
				dataBord.getCell(getCaseJ(), getCaseI()).removePlant(this);
			}

			this.incAS();
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#5997ff"));
		graphics.fill(new Rectangle2D.Float(x + 15, y + sizeOfSPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x - 9, y, sizeOfPlant + 25, sizeOfPlant - 25, 10, 10));
	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#5997ff"));
		graphics.fill(new Rectangle2D.Float(x, y + sizeOfSPlant, sizeOfSPlant - 35, sizeOfSPlant - 35));

		graphics.setColor(Color.decode(color));
		graphics.fill(
				new RoundRectangle2D.Float(x - 18, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant - 25, 10, 10));

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
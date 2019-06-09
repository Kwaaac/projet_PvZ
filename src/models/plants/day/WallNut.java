package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import models.SimpleGameData;
import models.TombStone;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class WallNut extends Plant {
	private final String name = "WallNut";
	private String color = "#ECB428";
	private boolean fed = false;

	public WallNut(int x, int y) {
		super(x, y, 0, 1000, 0, 50, "free");
	}

	public WallNut() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return name;
	}
	

	private void superAction() {
		System.out.println(life);
		takeDmg(-500);
		System.out.println(life);
		color = "#8c9aaf";
		fed = true;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {
		if (super.isFertilized() && !fed) {
			superAction();
			return;
		}
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new WallNut(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));

	}

	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {

		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfSPlant / 2, sizeOfSPlant, sizeOfSPlant));

		view.drawCost(graphics, x, y, cost.toString());
	}

}

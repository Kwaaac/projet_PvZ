package plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.Entities;
import views.BordView;
import zombies.Zombie;

public class WallNut extends Plant {
	private final String name = "WallNut";

	public WallNut(int x, int y) {
		super(x, y, 0, 1000, 0);
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	@Override
	public Rectangle2D.Float draw() {
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
	}

}

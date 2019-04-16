package plants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.Coordinates;
import models.Entities;
import views.BordView;
import zombies.Zombie;

public class CherryBomb extends Plant {

	private final String name = "CheeryBomb"; 

	public CherryBomb(int x, int y) {
		super(x, y, 0, 1, 50);
		setTimerA(1);
	}

	private ArrayList<Coordinates> zone(BordView view) {
		int caseXCherry = view.columnFromX(x);
		int caseYCherry = view.lineFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (caseXCherry + i >= 0 && caseXCherry + i <= view.getLength() && caseYCherry + j >= 0
						&& caseYCherry + j <= view.getWidth()) {
					zone.add(new Coordinates(caseXCherry + i, caseYCherry + j));
				}
			}
		}

		return zone;
	}

	private ArrayList<Entities> detect(BordView view, ArrayList<Zombie> myZombies) {
		ArrayList<Coordinates> cherry = this.zone(view);
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Entities z : myZombies) {
			int caseXZombie = view.columnFromX(z.getX());
			int caseYzombie = view.lineFromY(z.getY());
			Coordinates zombie = new Coordinates(caseXZombie, caseYzombie);
			for (Coordinates c : cherry) {
				if (zombie.equals(c)) {
					Lz.add(z);
				}
			}
		}
		return Lz;
	}

	public void explosion(BordView view, ArrayList<Zombie> myZombies) {
		if(this.readyToshot()) {
			for (Entities z : this.detect(view, myZombies)) {
				z.takeDmg(1800);
			}
			this.life = 0;
		}
		this.incAS();
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
		this.incAS();
		
		this.explosion(view, myZombies);
	}

}

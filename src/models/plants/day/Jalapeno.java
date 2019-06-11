package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Jalapeno extends Plant {

	private final String name = "Jalapeno";
	private final String color = "#FF0000";
	private final ArrayList<Coordinates> zone;

	public Jalapeno(int x, int y) {
		super(x, y, 0, 1, 1800, 125, "verySlow");
		this.zone = zone();
		this.shootTime = System.currentTimeMillis();
	}

	public Jalapeno() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	private ArrayList<Coordinates> zone() {
		int caseXPeno = BordView.caseXFromX(x);
		int caseYPeno = BordView.caseYFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -9; i <= 9; i++) {
			zone.add(new Coordinates(caseXPeno + i, caseYPeno));
		}
		return zone;
	}

	// Detection des zombies dans les cellules donn�es
	private ArrayList<Entities> detect(BordView view, SimpleGameData dataBord) {
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Coordinates c : zone) {
			Cell cell = dataBord.getCell(c.getJ(), c.getI());

			if (cell != null && cell.isThereBadZombies()) {
				for (Entities e : cell.getBadZombiesInCell()) {
					Lz.add(e);
				}
			}
		}

		return Lz;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone,
			SimpleGameData dataBord) {

		if (this.readyToshot()) {
			for (Entities z : this.detect(view, dataBord)) {
				z.takeDmg(1800);
			}

			setLife(0);
			dataBord.getCell(getCaseJ(), getCaseI()).removePlant(this);
		}

		this.incAS();
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new Jalapeno(x, y);

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
	
	@Override
	public void hasToDie(DeadPool DPe, SimpleGameData data) { 
			if (this.isDead()) { 
				data.getCell(this.getCaseJ(), this.getCaseI()).removePlant(this); 
				DPe.add(this); 
			} 
	} 
	
}

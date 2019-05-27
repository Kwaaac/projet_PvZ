package models.plants.day;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.Coordinates;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class PotatoMine extends Plant {

	private final String name = "Mine";
	private final String color1 = "#727507";
	private final String color2 = "#EDF404";
	private boolean activate = false;

	public PotatoMine(int x, int y) {
		super(x, y, 0, 120, 14_000, 25, "slow");
		this.shootTime = System.currentTimeMillis();
	}

	public PotatoMine() {
		this(-10, -10);
		activate = true;
	}

	@Override
	public String toString() {
		return name;
	}

	private void activation() {
		if (activate == false) {
			activate = true;
		}
	}

	@Override
	public void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies,
			SimpleGameData dataBord) {

		if (this.readyToshot()) {
			activation();
		}

		if (activate) {
			ArrayList<Entities> lz = this.detect(dataBord);

			if (!lz.isEmpty()) {
				for (Entities z : lz) {
					z.takeDmg(1800);
				}

				this.life = 0;
			}
		}

		this.incAS();
	}

	private ArrayList<Entities> detect(SimpleGameData dataBord) {
		ArrayList<Entities> Lz = new ArrayList<>();

		Cell cell = dataBord.getCell(getCaseJ(), getCaseI());

		if (cell != null && cell.isThereBadZombies()) {
			for (Entities z : cell.getBadZombiesInCell()) {
				Lz.add(z);
			}
		}
		
		return Lz;
	}

	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	@Override
	public Plant createNewPlant(int x, int y) {
		return new PotatoMine(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		if (activate) {
			graphics.setColor(Color.decode(color2));
		} else {
			graphics.setColor(Color.decode(color1));
		}
		
		graphics.fill(new Rectangle2D.Float(x + 10, y + 10, sizeOfPlant - 20, sizeOfPlant - 20));
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color2));
		graphics.fill(new Rectangle2D.Float(x - 10, y + sizeOfSPlant / 2 + 7, sizeOfSPlant - 15, sizeOfSPlant - 15));
		
		view.drawCost(graphics, x, y, cost.toString());
	}

	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + Plant.getSizeOfPlant() - 10);
	}
}

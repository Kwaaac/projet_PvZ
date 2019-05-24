package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
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

	}

	public PotatoMine() {
		this(-10, -10);
		activate = true;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	private void activation() {
		if (activate == false) {
			activate = true;
		}
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
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

		if (cell != null && cell.isThereZombies()) {
			for (Entities z : cell.getZombiesInCell()) {
				Lz.add(z);
			}
		}
		
		return Lz;
	}

	@Override
	public Plant createNewPlant(int x, int y) {
		return new PotatoMine(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		if (activate) {
			view.drawPotatoMine(graphics, x, y, color2);
		} else {
			view.drawPotatoMine(graphics, x, y, color1);
		}
	}

	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + Plant.getSizeOfPlant() - 10);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}
}

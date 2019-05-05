package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.projectiles.Spore;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class ScaredyShroom extends Plant {
	private final String name = "ScaredyShroom";
	private final String color = "#901bd1";

	public ScaredyShroom(int x, int y) {
		super(x, y, 0, 300, 5100, 25, "fast");
		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
	}

	public ScaredyShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawScaredyShroom(context, x, y, color);

		return new ScaredyShroom(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawScaredyShroom(graphics, x, y, color);
	}

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereZombies()) {
				return shootBar >= shootBarMax;
			}
		}

		return false;
	}

	public boolean hiding(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c != null) {

				System.out.println(c.isThereZombies() + "COOOONAARD");
				if (c.isThereZombies()) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {

		if (SimpleGameData.getMap() == "Night") {

			int y = this.getCaseJ();
			int x = this.getCaseI();

			if (hiding(dataBord.getLineCell(y, x - 1, x + 2))) {

				if (this.readyToshot(dataBord.getLineCell(y, x))) {
					myBullet.add(new Spore(super.getX() + super.getSizeOfPlant(),
							super.getY() + (super.getSizeOfPlant() / 2) - 10));

					this.resetAS();
				}

				this.incAS();
			}
		}
	}

}
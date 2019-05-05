package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.projectiles.Spore;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class PuffShroom extends Plant {
	private final String name = "PuffShroom";
	private final String color = "#901bd1";

	public PuffShroom(int x, int y) {
		super(x, y, 0, 300, 2500, 0, "fast");
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
	}

	public PuffShroom() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPuffShroom(context, x, y, color);

		return new PuffShroom(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawPuffShroom(graphics, x, y, color);
	}

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereZombies()) {
				return shootBar >= shootBarMax;
			}
		}

		return false;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
		if (true) {
			if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
				myBullet.add(new Spore(super.getX() + super.getSizeOfPlant(),
						super.getY() + (super.getSizeOfPlant() / 2) - 10, BordView.getSquareSize() * 3 - x));

				this.resetAS();
			}

			this.incAS();
		}
	}

}

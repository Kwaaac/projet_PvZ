package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Pea;
import models.projectiles.Projectile;
import models.projectiles.WeakSpore;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class PuffShroom extends Plant {
	private final String name = "PuffShroom";
	private final String color = "#901bd1";

	public PuffShroom(int x, int y) {
		super(x, y, 0, 300, 3500, 0, "fast");
		shootBar = shootBarMax; // La plante tire d�s qu'elle est pos�e
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
	public Plant createNewPlant(int x, int y) {
		return new PuffShroom(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x + 10, y , sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25));
		
	}
	
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x , y + sizeOfSPlant , sizeOfSPlant - 35, sizeOfSPlant - 35));
		
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 10, y + sizeOfSPlant / 2 + 15, sizeOfSPlant - 15, sizeOfSPlant - 25));
		
		view.drawCost(graphics, x, y, cost.toString());
	}

	public boolean readyToshot(ArrayList<Cell> cells) {
		for (Cell c : cells) {
			if (c.isThereBadZombies()) {
				return shootBar >= shootBarMax;
			}
		}

		return false;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {

		if (dataBord.getMap() == "Night") {
			if (true) {
				if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI(), this.getCaseI() + 4))) {
					myBullet.add(new WeakSpore(super.getX() + super.getSizeOfPlant(),
							super.getY() + (super.getSizeOfPlant() / 2) - 10));

					this.resetAS();
				}

				this.incAS();
			}
		}
	}

}
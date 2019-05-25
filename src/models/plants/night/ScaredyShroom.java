package models.plants.night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
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
	public Plant createNewPlant(int x, int y) {
		return new ScaredyShroom(x, y);
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

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x + 10, y + sizeOfPlant / 2, sizeOfPlant - 25, sizeOfPlant - 15));

		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y - 5, sizeOfPlant - 5, sizeOfPlant - 25));
	}
	
	int sizeOfSPlant = super.getSizeOfPlant() - 10;
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode("#bfbf00"));
		graphics.fill(new Rectangle2D.Float(x , y + sizeOfSPlant , sizeOfSPlant - 25, sizeOfSPlant - 15));
		
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 10, y + sizeOfSPlant / 2 , sizeOfSPlant - 5, sizeOfSPlant - 25));
		
		view.drawCost(graphics, x, y, cost.toString());
	}

}
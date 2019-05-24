package models.plants.day;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.FrozenPea;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class SnowPea extends Plant {
	private final String name = "SnowPea";
	private final String color = "#33FFEA";
	private final String cost = "175"; 

	public SnowPea(int x, int y) {
		super(x, y, 0, 300, 5000, 0, "free");
		shootBar = shootBarMax; // La plante tire dès qu'elle est posée
	}

	public SnowPea() {
		this(-10, -10);
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public Plant createNewPlant(int x, int y) {
		return new SnowPea(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant - 10, sizeOfPlant - 10));
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Afterglow", Font.PLAIN, 20));
		graphics.drawString(cost, x+10+sizeOfPlant/2, y+SelectBordView.getSquareSize()-5);
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
		
		if (this.readyToshot(dataBord.getLineCell(this.getCaseJ(), this.getCaseI()))) {
			
			myBullet.add(new FrozenPea(super.getX() + super.getSizeOfPlant(),
					super.getY() + (super.getSizeOfPlant() / 2) - 10));

			this.resetAS();
		}

		this.incAS();
	}

}
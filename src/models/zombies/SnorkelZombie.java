package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.DeadPool;
import models.SimpleGameData;
import models.cells.Cell;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;

public class SnorkelZombie extends Zombie {

	private final String name = "SnorkelZombie";
	private final String color = "#000000";
	private boolean outOfWater = false;

	public SnorkelZombie(int x, int y) {
		super(x, y, 100, 200, 1, "slow", false);
	}
	
	public SnorkelZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 200, 1, "slow", gifted);
	}

	public SnorkelZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	public void go() {
		super.go((float) -0.93);
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new SnorkelZombie(x, y, gift);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		super.draw(view, graphics);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		Cell cell = dataBord.getCell(getCaseI(), getCaseJ());
		if (dataBord.isCorrectBordLocation(view, getCaseI(), getCaseJ()) && cell != null) {
			this.outOfWater = cell.isPlantedPlant();
		}
		return outOfWater;
	}

	@Override
	public boolean isCommon() {
		return false;
	}

	@Override
	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data) {
		ArrayList<Projectile> Le;
		if (data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).isPlantedPlant()) {
			Le = data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).getProjectilesInCell();
			for (Projectile e : Le) {
				e.action(data);
				if (this.outOfWater == true && this.hit(e) && !(e.isInConflict()) && this.isBad()) {
					this.slowed(e.isSlowing());
					e.setConflictMode(true);
					this.mortalKombat(e);
					if (e.isDead()) {
						DPe.addInDP(e);
					}
					/*
					 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
					 * attaquant ne sois mort on empeche des echange de d�gats(on en a besoin pour
					 * pas qu'une plante morte soit capable de tu� apr�s sa mort)
					 */
					else if (this.isDead()) {
						e.setConflictMode(false);
						DPe.addInDP(this);
						break;
					}
				}

			}
		}
	}

}

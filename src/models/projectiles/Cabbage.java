package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import models.Coordinates;
import models.SimpleGameData;
import models.cells.Cell;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Cabbage extends Projectile {

	private final String name = "Pea";
	private final String color = "#25c442";
	private final Zombie target;
	int sizeofP;

	public Cabbage(float x, float y, Zombie target) {
		super(x, y, 75, 1, 20.0);
		sizeofP = getSizeOfProjectile();
		this.target = target;
	}

	/**
	 * The cabbage does not have a case until it's the same as it's target
	 */
	@Override
	public void setCase(SimpleGameData data) {
		if (!target.isDead()) {
			// Inscrit dans une cellule si la target est dans la cellule
			Coordinates caseTarget = target.getCase();
			int cX = BordView.caseXFromX(x);
			int cY = BordView.caseYFromY(y);

			Coordinates caseP = new Coordinates(cX, cY);

			if (caseP.equals(caseTarget)) {
				Cell actCell = data.getCell(cY, cX);
				actCell.addProjectile(this);
			}
		} else {
			setLife(0);
		}
	}

	@Override
	public void move() {
		int x = (int) (super.x + getSpeed());
		setX(x);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeofP + 10, sizeofP));
	}

	@Override
	public int isSlowing() {
		return 0;
	}
}

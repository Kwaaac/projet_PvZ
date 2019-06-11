package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import models.Coordinates;
import models.SimpleGameData;
import models.cells.Cell;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Melon extends Projectile {

	private final String name = "Melon";
	private final String color = "#19d800";
	private final Zombie target;
	int sizeofP;

	public Melon(float x, float y, Zombie target) {
		super(x, y, 125, 1, 20.0);
		sizeofP = getSizeOfProjectile();
		this.target = target;
	}

	/**
	 * The projectile does not have a case until it's the same as it's target
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
				caseXY = caseP;
			}
		} else {
			setLife(0);
		}
	}

	@Override
	public void move() {
		int x = (int) (super.x + speed);
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
		graphics.fill(new Ellipse2D.Float(x, y, sizeofP + 10, sizeofP + 10));
	}

	@Override
	public int isSlowing() {
		return 0;
	}
	
	@Override
	public void action(SimpleGameData data) {
		ArrayList<Zombie> zombies = data.getCell(getCaseJ(), getCaseI()).getBadZombiesInCell();
		for(Zombie z: zombies) {
			z.takeDmg(30);
		}
	}
	
}

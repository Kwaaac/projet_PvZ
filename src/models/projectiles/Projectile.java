package models.projectiles;

import java.util.ArrayList;
import java.util.Objects;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.MovingElement;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;

public abstract class Projectile extends Entities implements MovingElement, IProjectile{
	private double speed;
	private static final int sizeOfProjectile = 25;
	
	public Projectile(float x, float y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.setSpeed(speed);
	}
	
	@Override
	/**
	 * give the zombie a case and add the zombie on the zombieList of the cell
	 * 
	 * @param data Data of the main Bord
	 */
	public void setCase(SimpleGameData data) {
		int cX = BordView.caseXFromX(x);
		int cY = BordView.caseYFromY(y);

		Coordinates caseZ = new Coordinates(cX, cY);

		if (!caseZ.equals(caseXY)) {

			Cell actCell = data.getCell(cY, cX);
			if (actCell != null) {

				if (!(cX == data.getNbColumns()) || cX < 0) {
					data.getCell(caseXY.getJ(), caseXY.getI()).removeProjectile(this);
				}
				
				actCell.addProjectile(this);

				caseXY = caseZ;
			}
		}
	}
	
	@Override
	public void move() {
		setX((int) (super.x + getSpeed()));
	}
		
	@Override
	public void incAS() {}

	@Override
	public void resetAS() {}
	
	public static int getSizeOfProjectile() {
		return sizeOfProjectile;
	}
	
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfProjectile);
	}
	
	public boolean isOutside(int xOrigin, int sqrSize, int nbrSqr) {
		return x > xOrigin + sqrSize * nbrSqr;
	}

	public void SpeedBoostON() {
		setSpeed(getSpeed() + 2);
	}
	
	public void SpeedBoostOFF() {
		setSpeed(getSpeed() - 2);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public static void hasToDie(DeadPool DPe, ArrayList<Projectile> Mp, SimpleGameData data) {
		for(Projectile p : Mp) {
			if (p.isDead()) {
				DPe.add(p);
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Projectile)) {
			return false;
		}
		Projectile z = (Projectile) o;
		return super.equals(z) && speed == z.speed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), speed);
	}
	
	public void action() {}
}

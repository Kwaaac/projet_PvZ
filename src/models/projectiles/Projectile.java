
/**
 * EACH PROJECTILE HAS :
 * A NAME
 * A COLOR
 * COORDINATES
 * A METHODE FOR MOVING THIS ONE
 * A DRAW FOR THE BORD
 */

package models.projectiles;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.MovingElement;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;

public abstract class Projectile extends Entities implements MovingElement, IProjectile, Serializable {
	protected double speed;
	private static final int sizeOfProjectile = 25;
	private final boolean flying;

	public Projectile(float x, float y, int damage, int life, double speed, boolean flying) {
		super(x, y, damage, life, true);
		this.setSpeed(speed);
		this.flying = flying;
	}

	public Projectile(float x, float y, int damage, int life, double speed) {
		this(x, y, damage, life, speed, false);
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

		if (!caseZ.equals(caseXY)) { // Changing case

			Cell actCell = data.getCell(cY, cX);

			if (actCell != null) {

				if (!(cX == data.getNbColumns()) || cX < 0) {
					data.getCell(caseXY.getJ(), caseXY.getI()).removeProjectile(this);
				}

				if (actCell.isLeanned()) {
					setLife(0);
				} else {
					actCell.addProjectile(this);

					caseXY = caseZ;
				}
			}
		}
	}

	/**
	 * move the projectile
	 */
	@Override
	public void move() {
		setX((int) (super.x + speed));
	}

	/**
	 * increase the shootBar
	 */
	@Override
	public void incAS() {
	}

	/**
	 * reset the shootBar
	 */
	@Override
	public void resetAS() {
	}

	/**
	 * 
	 * @return size of projectile
	 */
	public static int getSizeOfProjectile() {
		return sizeOfProjectile;
	}

	/**
	 * @return the projectile's hitbox
	 */
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfProjectile);
	}

	/**
	 * check if the projectile is outside the board
	 */
	public boolean isOutside(int xOrigin, int sqrSize, int nbrSqr) {
		return x > xOrigin + sqrSize * nbrSqr;
	}

	/**
	 * TestMode On
	 */
	public void SpeedBoostON() {
		setSpeed(speed + 2);
	}

	/**
	 * TestMode Off
	 */
	public void SpeedBoostOFF() { // slow the speed of the projectile
		setSpeed(speed - 2);
	}

	/**
	 * set the new speed
	 * 
	 * @param speed new speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * when a list of projectile has to die
	 * 
	 * @param DPe      deadpool
	 * @param myBullet list of projectile
	 * @param data     data of the main board
	 */
	public static void hasToDie(DeadPool DPe, List<Projectile> myBullet, SimpleGameData data) {
		for (Projectile p : myBullet) {
			if (p.isDead()) {
				DPe.add(p);
			}
		}
	}

	/**
	 * return if the projectile is flying
	 */
	public boolean isFlying() {
		return flying;
	}

	/**
	 * return if the projectile is sharp
	 */
	public boolean isSharp() {
		return false;
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

	/**
	 * activate the action of the projectile
	 */
	@Override
	public void action(SimpleGameData data) {
	}

}

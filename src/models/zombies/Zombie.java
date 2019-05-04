package models.zombies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import models.Chrono;
import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.MovingElement;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import views.BordView;

public abstract class Zombie extends Entities implements MovingElement, IZombie {
	private double speed;
	private final static int sizeOfZombie = 75;

	protected int shootBarMax;
	protected long shootBar;
	protected long shootTime;

	protected Chrono slowedTime = new Chrono();

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		speed = -1.7;
		shootBarMax = (int) (speed * -7500);
		shootTime = System.currentTimeMillis();
		slowedTime.steady();
	}

	public float getX() {
		return super.getX();
	}

	@Override
	public void setCase(SimpleGameData data) {
		int cX = BordView.caseXFromX(x);
		int cY = BordView.caseYFromY(y);

		Coordinates caseZ = new Coordinates(cX, cY);

		if (!caseZ.equals(caseXY)) {

			Cell actCell = data.getCell(cY, cX);
			if (actCell != null) {

				if (!(cX == data.getNbColumns() - 1)) {
					data.getCell(caseXY.getJ(), caseXY.getI()).removeZombie(this);
				}

				actCell.addZombie(this);

				caseXY = caseZ;
			}
		}
	}

	public float getY() {
		return super.getY();
	}

	@Override
	public void move() {
		setX((float) (getX() + getSpeed()));
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double d) {
		speed=d;
	}
	
	public void go(float x) {
		if (slowedTime.isReset()) {
			setSpeed(x);
		} else {
			setSpeed(x / 2);
			shootBarMax = (int) (getSpeed() * -7500);

			if (slowedTime.asReachTimer(6)) {
				slowedTime.steady();
			}
		}

	}

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public void stop() {
		this.setSpeed(0);
	}

	public void SpeedBoostON() {
		setSpeed(getSpeed() - 2);
	}

	public void SpeedBoostOFF() {
		setSpeed(getSpeed() + 2);
	}

	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfZombie);
	}

	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize;
	}

	public boolean soonEatingBrain(int xOrigin, int squareSize) {
		return (xOrigin - squareSize < x && x < xOrigin);
	}

	public int whereIsHeEatingBrain(int xOrigin, int squareSize, float y, int Yorigin, BordView view) {
		if (this.soonEatingBrain(xOrigin, squareSize)) {
			return view.indexFromReaCoord(y, Yorigin);
		}
		return -1;
	}

	/**
	 * cette m�thode a pour but de r�partir les d�gats aux diff�rentes
	 * entit�es du jeu, une fois les d�gat correctement attribuer et la vie des
	 * entit�es mise a jour elle aide par la suite a les redistribuer dans les
	 * diff�rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entit�es qui subiront les d�gats de l'entit�e
	 *                 objet utilisant la m�thode et qui attaqueront cette meme
	 *                 entit�es tous ensemble
	 */

	public void conflictBvZ(DeadPool DPe, ArrayList<Projectile> Le, SimpleGameData data) {
		for (Projectile e : Le) {
			if (this.hit(e) && !(e.isInConflict())) {
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

	public void conflictPvZ(DeadPool deadPoolE, ArrayList<Plant> myPlants, BordView view, SimpleGameData data,
			StringBuilder str) {
		for (Plant p : myPlants) {

			if (this.hit(p)) {
				this.stop();
				if (this.readyToshot()) {
					(p).mortalKombat(this);

					this.resetAS();
				}

			}
			if (p.isDead()) {
				str.append(p + "meurt\n");
				deadPoolE.add(p);
				data.getCell(view.lineFromY(p.getY()), view.columnFromX(p.getX())).removePlant();
			}
		}
	}

	public void conflictLvZ(DeadPool deadPoolE, ArrayList<LawnMower> myLawnMower, BordView view, SimpleGameData data,
			StringBuilder str) {
		for (LawnMower l : myLawnMower) {
			if (this.hit(l)) {
				if (!(l.isMoving())) {
					l.go();
				}
				life = 0;
				l.setLife(100000);
			}
		}
	}

	public static void ZCheckConflict(ArrayList<Zombie> myZombies, ArrayList<Projectile> myBullet,
			ArrayList<Plant> myPlants, ArrayList<LawnMower> myLawnMower, DeadPool deadPoolE, BordView view,
			SimpleGameData data, StringBuilder str) {
		LawnMower.hasToDie(myLawnMower, deadPoolE, data, view);
		Plant.hasToDie(deadPoolE, myPlants, myZombies, data); // gere les mort si il n'y a aucun zombie sur le plateau
		for (Zombie z : myZombies) {
			z.go();

			z.incAS();
			z.conflictBvZ(deadPoolE, myBullet, data);
			if (z.action(data)) {
				z.conflictPvZ(deadPoolE, myPlants, view, data, str);
			}
			z.conflictLvZ(deadPoolE, myLawnMower, view, data, str);
			if (z.isDead()) {
				deadPoolE.add(z);
				str.append(z + " meurt\n");
			}

		}

	}

	public void slowed(Boolean slowing) {
		if (slowing) {
			slowedTime.start();
		}
	}

	public abstract Integer getProb(int difficulty);

	/*
	 * For zombies that don't have actions
	 * 
	 */
	@Override
	public boolean action(SimpleGameData dataBord) {
		return true;
	}
}

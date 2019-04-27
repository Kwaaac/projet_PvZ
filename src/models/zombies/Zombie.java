package models.zombies;

import java.util.ArrayList;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import views.BordView;

public abstract class Zombie extends Entities implements MovingElement, IZombie {
	private final String type = "Zombie";
	private double speed;
	private final static int sizeOfZombie = 75;

	protected final int shootBarMax;
	protected long shootBar;
	protected long shootTime;

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = -1.7;
		this.shootBarMax = (int) (speed * -7500);
		shootTime = System.currentTimeMillis();
	}

	public float getX() {
		return super.getX();
	}

	public float getY() {
		return super.getY();
	}

	@Override
	public void move() {
		setX((float) (getX() + speed));
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(float x) {
		speed = x;
	}

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public void stop() {
		this.speed = 0;
	}

	public void go() {
		speed = -1.7;
	}

	public void SpeedBoostON() {
		speed -= 2;
	}

	public void SpeedBoostOFF() {
		speed += 2;
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

	public void conflictAll(Plant p) {
	}

	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfZombie);
	}

	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize / 2;
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

	public void conflictBvZ(DeadPool DPe, ArrayList<Projectile> Le) {
		for (IEntite e : Le) {
			if (this.hit(e) && !(e.isInConflict())) {
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
				data.plantOutBord(view.lineFromY(p.getY()), view.columnFromX(p.getX()));
			}
		}
	}

	public static void ZCheckConflict(ArrayList<Zombie> myZombies, ArrayList<Projectile> myBullet,
			ArrayList<Plant> myPlants, DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		for (Zombie z : myZombies) {
			z.go();
			z.incAS();
			z.conflictBvZ(deadPoolE, myBullet);
			z.conflictPvZ(deadPoolE, myPlants, view, data, str);
			if (z.isEatingBrain(view.getXOrigin(), BordView.getSquareSize()) || z.isDead()) {
				deadPoolE.add(z);
				str.append(z + " meurt\n");
			}
		}
	}

	public abstract Integer getProb(int difficulty);

}

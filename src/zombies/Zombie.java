package zombies;

import java.util.ArrayList;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.LivingEntities;
import models.MovingElement;
import models.SimpleGameData;
import plants.Plant;
import plants.Projectile;
import views.BordView;

public abstract class Zombie extends Entities implements MovingElement, LivingEntities {
	private final String type = "Zombie";
	private double speed;
	private final static int sizeOfZombie = 75;

	private int speedshoot;
	private int timerA = -1;

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.speed = -1.7;
		this.speedshoot = (int) (speed * -75);
	}

	@Override
	public void move() {
		setX((float) (getX() + speed));
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public boolean getSpeed() {
		return speed != 0;
	}

	public void setSpeed( float x ) {
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

	public void incAS() {
		this.timerA += 1;
	}

	public void resetAS() {
		this.timerA = 0;
	}

	public boolean readyToshot() {
		return timerA % speedshoot == 0;
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
			if (this.hit(e)) {

				this.mortalKombat(e);
				if (e.isDead()) {
					DPe.addInDP(e);
				} 
				/*
				 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
				 * attaquant ne sois mort on empeche des echange de dégats(on en a besoin pour
				 * pas qu'une plante morte soit capable de tué après sa mort)
				 */
				else if (this.isDead()) {
					
					DPe.addInDP(this);
					break;
				}
			}

		}
	}
	
	public void conflictPvZ(DeadPool deadPoolE, ArrayList<Plant> myPlants,BordView view,SimpleGameData data, StringBuilder str) {
		for (Plant p : myPlants) {

			if (this.hit(p)) {
				this.stop();
				if  (this.readyToshot()) {
					(p).mortalKombat(this);
				}

			}
			if (p.isDead()) {
				str.append(p + "meurt\n");
				deadPoolE.add(p);
				data.plantOutBord(view.lineFromY(p.getY()), view.columnFromX(p.getX()));
			}

		}
	}
	
	public static void ZCheckConflict(ArrayList<Zombie> myZombies,ArrayList<Projectile> myBullet, ArrayList<Plant> myPlants  ,DeadPool deadPoolE,BordView view,SimpleGameData data, StringBuilder str) {
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
}

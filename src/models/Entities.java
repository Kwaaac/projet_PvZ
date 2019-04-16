package models;

import java.util.ArrayList;

import plants.Plant;
import plants.Projectile;
import zombies.Zombie;

public abstract class Entities implements IEntite {

	protected int x;
	protected int y;
	protected int damage;
	protected int life;

	public Entities(int x, int y, int damage, int life) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.life = life;
	}

	public Entities(int x, int y, double speed) {
		this.x = x;
		this.y = y;
	}

	public int lineY() {
		return (int) ((y - 100) / 180);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDamage() {
		return damage;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isDead() {
		if (life <= 0) {
			return true;
		}

		return false;
	}

	public void takeDmg(int x) {
		this.life = life - x;
	}

	public boolean sameLine(Entities e) {
		return this.lineY() == e.lineY();
	}

	public boolean intersect(IEntite e) {
		return this.lineY() == ((Entities) e).lineY() && (e.hitBox().checkHitBox(this.hitBox()));
	}

	public void mortalKombat(Entities e) {
		this.takeDmg(e.damage);
		e.takeDmg(damage);
	}

	/**
	 * check if the object hit the entity
	 * 
	 * @param e is the entity we want to compare the edge's positions
	 * @return if this as touch e
	 */
	@Override
	public boolean hit(IEntite e) {
		return this.intersect(e);
	}

	/**
	 * premier type conflit qui dois vite etre changer et améliorer
	 * 
	 * @param e entit�es qui subira les d�gats de l'entitée objet utilisant la
	 *          méthode et qui attaquera cette meme entitées par la suite
	 */
	public void conflict(ArrayList<Entities> entities) {
		if (!entities.isEmpty()) {
			for (Entities e : entities) {
				this.mortalKombat(e);
			}
		}
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
	public void conflict(DeadPool DPe, ArrayList<Entities> Le) {
		for (Entities e : Le) {
			if (this.hit(e)) {

				this.mortalKombat(e);
				if (e.isDead()) {
					DPe.addInDP(e);
				} else if (this.isDead()) {
					/*
					 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
					 * attaquant ne sois mort on empeche des echange de dégats(on en a besoin pour
					 * pas qu'une plante morte soit capable de tué après sa mort)
					 */
					
					DPe.addInDP(this);
					break;
				}
			}

		}
	}
}

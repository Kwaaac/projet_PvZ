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
			return life <= 0;
	}
	
	public void takeDmg(int x) {
		this.life = life - x;
	}
	
	public boolean sameLine(Entities e) {
			return this.lineY() == e.lineY();
	}

	/**
	 * put an entitie in is respective deadPool
	 * 
	 * @param DPz deadPool for Zombies
	 * @param DPp deadPool for Plant
	 * @param DPb deadPool for Projectile
	 * 
	 */
	private void addInDP(ArrayList<Zombie> Lz, ArrayList<Plant> Lp, ArrayList<Projectile> Lb, DeadPool DPz,
			DeadPool DPp, DeadPool DPb) {
		if (this instanceof Zombie) {
			DPz.add(Lz.indexOf((Zombie) this));
		} else if (this instanceof Plant) {
			DPp.add(Lp.indexOf((Plant) this));
		} else if (this instanceof Projectile) {
			DPb.add(Lb.indexOf((Projectile) this));
		}
	}
	
	public boolean intersect(IEntite e) {
		return this.lineY() == ((Entities) e).lineY() && (e.hitBox().checkHitBox(this.hitBox()));
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
		if (entities.size() < 0) {
			for (Entities e : entities) {
				this.conflict(e);
			}
		}
	}

	public void conflict(Entities e) {
		this.takeDmg(e.damage);
		e.takeDmg(damage);
	}

	/**
	 * cette m�thode a pour but de r�partir les d�gats aux diff�rentes entit�es du
	 * jeu, une fois les d�gat correctement attribuer et la vie des entit�es mise a
	 * jour elle aide par la suite a les redistribuer dans les diff�rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entit�es qui subiront les d�gats de l'entit�e objet
	 *                 utilisant la m�thode et qui attaqueront cette meme entit�es
	 *                 tous ensemble
	 */
	public void conflict(ArrayList<Zombie> Lz, ArrayList<Plant> Lp, ArrayList<Projectile> Lb, DeadPool DPz,
			DeadPool DPp, DeadPool DPb, ArrayList<Entities> entities) {
		for (Entities e : entities) {
			if (this.hit(e)) {

				life -= e.getDamage();
				e.life -= damage;
				if (e.isDead()) {
					e.addInDP(Lz, Lp, Lb, DPz, DPp, DPb);
				} else if (this.isDead()) {
					/*
					 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
					 * attaquant ne sois mort on empeche des echange de dégats(on en a besoin pour
					 * pas qu'une plante morte soit capable de tué après sa mort)
					 */
					break;
				}
			}

		}
		this.addInDP(Lz, Lp, Lb, DPz, DPp, DPb);

	}
}

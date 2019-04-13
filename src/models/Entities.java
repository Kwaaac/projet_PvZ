package models;

import java.util.ArrayList;

import plants.Plant;
import plants.Projectile;
import zombies.Zombie;

public abstract class Entities implements IEntite{

	protected int x;
	protected int y;
	protected int damage;
	protected int life;

	public Entities(int x, int y, int damage, int life) {
		this.setX(x);
		this.y = y;
		this.damage = damage;
		this.life = life;
	}

	public Entities(int x, int y, double speed) {
		this.setX(x);
		this.y = y;
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

	public int getLife() {
		return life;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setLife(int life) {
		this.life = life;
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

	/**
	 * check if the object hit the entity
	 * 
	 * @param e is the entity we want to compare the edge's positions
	 * @return if this as touch e
	 */
	public boolean hit(Entities e) {

		if (this instanceof Zombie) { // on gere une premiere possiblit� --> une entit� zombie touche une plante
										// ou un
										// projectile
			Zombie z = (Zombie) this;
			if (e instanceof Projectile) {

				if (z.draw().getBounds2D().intersects(((Projectile) e).draw().getBounds2D())) {
					return true;
				}

			} else if (e instanceof Plant) {
				if (z.draw().getBounds2D().intersects(((Plant) e).draw().getBounds2D())) {
					return true;
				}
			}
		} else if (this instanceof Plant && e instanceof Zombie) { // on gere une seconde possiblit� --> une entit�
																	// plante touche un zombie
			Plant p = (Plant) this;
			if (p.draw().getBounds2D().intersects(((Zombie) e).draw().getBounds2D())) {
				return true;
			}
		} else if (this instanceof Projectile && e instanceof Zombie) {
			Projectile p = (Projectile) this;
			if (p.draw().getBounds2D().intersects(((Zombie) e).draw().getBounds2D())) {
				return true;
			}
		}
		return false;
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
				life -= e.getDamage();
				e.life -= damage;
			}
		}
	}
	
	public void conflict(Entities e) {
				life -= e.getDamage();
				e.life -= damage;
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
	public void conflict(ArrayList<Zombie> Lz, ArrayList<Plant> Lp, ArrayList<Projectile> Lb, DeadPool DPz,
			DeadPool DPp, DeadPool DPb, ArrayList<Entities> entities) {
		for (Entities e : entities) {
			if (this.hit(e)) {

				life -= e.getDamage();
				e.life -= damage;
				if (e.getLife() <= 0) {
					e.addInDP(Lz, Lp, Lb, DPz, DPp, DPb);
				} else if (this.getLife() <= 0) { 
												  /* si ils sont plusieur a le taper et que sa vie tombe a zero avant
												   * que les attaquant ne sois mort on empeche des echange de
												   * dégats(on en a besoin pour pas qu'une plante morte soit capable
												   * de tué après sa mort)
												   */
					break;
				}
			}

		}
		this.addInDP(Lz, Lp, Lb, DPz, DPp, DPb);

	}

}

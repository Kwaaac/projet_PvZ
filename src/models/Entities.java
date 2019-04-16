package models;

import java.util.ArrayList;
import plants.Projectile;
import views.BordView;

public abstract class Entities implements IEntite {

	protected float x;
	protected float y;
	protected Coordinates caseXY;
	protected int damage;
	protected int life;

	public Entities(float x, float y, int damage, int life) {
		this.x = x;
		this.y = y;
		this.caseXY = new Coordinates(BordView.caseYFromY(y), BordView.caseXFromX(x));
		this.damage = damage;
		this.life = life;
	}

	public int lineY() {
		return (int) ((y - 100) / 180);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getDamage() {
		return damage;
	}

	public void setX(float x) {
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

	public void mortalKombat(IEntite e) {
		this.takeDmg(e.getDamage());
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
	
	@Override
	public void conflict(DeadPool DPe, ArrayList<Projectile> Le) {
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
}

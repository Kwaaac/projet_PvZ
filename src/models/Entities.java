package models;

import java.util.ArrayList;

import plants.Bullet;
import plants.Plant;
import plants.Projectile;
import views.BordView;
import zombies.Zombie;

public abstract class Entities {

	private int x;
	private int y;
	private int damage;
	private int life;

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

	public void setDamage(int damage) {
		this.damage = damage;
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
	 */
	private void addInDP(ArrayList<Zombie> DPz, ArrayList<Plant> DPp, ArrayList<Projectile> DPb) {
		if (this instanceof Zombie) {
			DPz.add((Zombie) this);
		} else if (this instanceof Plant) {
			DPp.add((Plant) this);
		} else if (this instanceof Projectile) {
			DPb.add((Projectile) this);
		}
	}
	
	/**
	 * check if the object hit the e entitie
	 * @param e is the entitie we want to compare the edge's positions
	 * @return if this as touch e
	 */
	private boolean hit(Entities e,BordView view) {
		
		if (this instanceof Zombie) { // on gere une premiere possiblité --> une entité zombie touche une plante ou un projectile
			float Zx1 =  this.getX(); //centre zombie
			float Zx2 =  Zx1 - (Zombie.getSizeOfZombie()/3)-2; //bordure gauche zombie
			if(e instanceof Projectile) {
				float Bx1 =  e.getX(); //centre bullet
				float Bx2 =  Bx1 + (Bullet.getSizeOfProjectile()/3)+2 ; //bordure droite bullet
				if(view.lineFromY(this.getY()) == view.lineFromY(e.getY()) && (Bx1 < Zx2 && Zx2 <= Bx2) ) {
					return true;
				}
			
			}else if(e instanceof Plant) {
				float Px1 =  e.getX(); //centre plante
				float Px2 =  Px1 + (Plant.getSizeOfPlant()/3)+2 ; //bordure droite plante
				if(view.lineFromY(this.getY()) == view.lineFromY(e.getY()) && (Px1 < Zx2 && Zx2 <= Px2)) {
					return true;					
				}
			}
		} else if ( this instanceof Plant || this instanceof Projectile  && e instanceof Zombie) { // on gere une seconde possiblité --> une entité plante ou projectile touche un zombie
			return true;
		}
		return false;
	}
	/**
	 * premier type conflit qui dois vite etre changer et améliorer
	 * @param e entitées qui subira les dégats de l'entitée objet
	 *                 utilisant la méthode et qui attaquera cette meme entitées
	 *                 par la suite
	 */
	public void conflict(Entities e) { 
		life -= e.getDamage(); 
		e.life -= damage; 
	} 
	
	/**
	 * cette méthode a pour but de répartir les dégats aux différentes entitées du
	 * jeu, une fois les dégat correctement attribuer et la vie des entitées mise a
	 * jour elle aide par la suite a les redistribuer dans les différente deadpools
	 * 
	 * @param view vue sur la quelle ce joue le conflict (si on en met plusieur je saurai la faire marcher sur plusieur vues)
	 * @param DPz deadPool for Zombies
	 * @param DPp deadPool for Plant
	 * @param DPb deadPool for Projectile
	 * @param entities suite d'entitées qui subiront les dégats de l'entitée objet
	 *                 utilisant la méthode et qui attaqueront cette meme entitées
	 *                 tous ensemble
	 */
	public void conflict(BordView view,ArrayList<Zombie> DPz, ArrayList<Plant> DPp, ArrayList<Projectile> DPb, Entities... entities) {
		for (Entities e : entities) {
			if(this.hit(e, view)) {

			life -= e.getDamage();
			e.life -= damage;
			if (e.getLife() <= 0) {
				e.addInDP(DPz, DPp, DPb);
			}else if(this.getLife() <= 0){ //si ils sont plusieur a le taper et que sa vie tombe a zero avant que les attaquant ne sois mort on empeche des echange de dégats(on en a besoin pour pas qu'une plante morte soit capable de tué après sa mort)
				break;
			}
			}
			
		}
		this.addInDP(DPz, DPp, DPb);

	}

}

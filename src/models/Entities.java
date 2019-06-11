package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import views.BordView;

public abstract class Entities implements IEntite, Serializable {

	protected float x;
	protected float y;
	protected Coordinates caseXY;
	protected int damage;
	protected Integer life;
	protected boolean inConflict;
	protected boolean Team;

	public Entities(float x, float y, int damage, int life, boolean Team) {
		this.x = x;
		this.y = y;
		this.caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
		this.damage = damage;
		this.life = life;
		this.Team = Team;
	}

	/**
	 * give the coordinate of case where is situate the entity
	 */
	public void setCase(SimpleGameData data) {
		caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
	}

	public Coordinates getCase() {
		return caseXY;
	}

	public int getCaseI() {
		return caseXY.getI();
	}

	public int getCaseJ() {
		return caseXY.getJ();
	}

	public int lineY() {
		return getCaseI();
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

	public void setY(float y) {
		this.y = y;
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
		this.life -= x;
	}

	/**
	 * check if there is an other entity in this line
	 */
	public boolean sameLine(Entities e) {
		return this.lineY() == e.lineY();
	}

	/**
	 * check if the other entity if hiting the hitbox of this one
	 */
	public boolean intersect(IEntite e) {
		return this.lineY() == ((Entities) e).lineY() && (e.hitBox().checkHitBox(this.hitBox()));
	}

	/**
	 * The entities are fighting, taking damages and loosing life
	 */
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
	 * @param e entities qui subira les degats de l'entite objet utilisant la
	 *          methode et qui attaquera cette meme entite par la suite
	 */

	@Override
	public boolean isInConflict() {
		return this.inConflict;
	}

	@Override
	public void setConflictMode(boolean b) {
		this.inConflict = b;
	}

	public void conflict(ArrayList<Entities> entities) {
		if (!entities.isEmpty()) {
			for (Entities e : entities) {
				this.mortalKombat(e);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Entities)) {
			return false;
		}
		Entities t = (Entities) o;
		return x == t.x && y == t.y && caseXY.equals(t.caseXY) && damage == t.damage && life == t.life;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, caseXY, damage, life);
	}

	/**
	 * There is always some traitors... Sometimes, zombies can return there self
	 * aginst the others due to some effects
	 */
	@Override
	public void reverseTeam(SimpleGameData data) {
		this.Team = !(Team);
	}

	/**
	 * not a traitor
	 */
	@Override
	public boolean isGood() {
		return Team == true;
	}

	/**
	 * traitor
	 */
	@Override
	public boolean isBad() {
		return Team == false;
	}
}

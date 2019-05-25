package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import views.BordView;

public abstract class Entities implements IEntite, Serializable{

	protected float x;
	protected float y;
	protected Coordinates caseXY;
	protected int damage;
	protected Integer life;
	protected boolean inConflict;
	protected boolean Team;

	public Entities(float x, float y, int damage, int life,boolean Team) {
		this.x = x;
		this.y = y;
		this.caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
		this.damage = damage;
		this.life = life;
		this.Team = Team;
	}
	
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
		this.life = this.life - life;
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
		if(!(o instanceof Entities)) { return false; }
		Entities t = (Entities) o;
		return x == t.x && y == t.y && caseXY.equals(t.caseXY) && damage == t.damage && life == t.life;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y, caseXY, damage, life);
	}
	
	@Override
	public void reverseTeam() {
		this.Team = !(Team);
	}
	
	@Override
	public boolean isGood() {
		return Team == true;
	}
	
	@Override
	public boolean isBad() {
		return Team ==false;
	}
}

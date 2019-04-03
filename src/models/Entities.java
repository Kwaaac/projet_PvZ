package models;

import plants.Plant;
import plants.Projectile;
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

	public void conflict(Entities e) {
		life -= e.getDamage();
		e.life -= damage;
	}


}

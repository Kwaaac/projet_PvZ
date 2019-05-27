package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.zombies.Zombie;
import views.SimpleGameView;

public class Tombstone extends Entities {
	private final Zombie burriedDead;
	private final int TombstoneSize = 50;
	private final String color = "#606875";
	
	private Tombstone(float x, float y, int damage, int life, boolean Team , Zombie burriedDead) {
		super(x, y, damage, life, Team);
		this.burriedDead = burriedDead;

	}
	
	private Tombstone(float x, float y, Zombie burriedDead) {
		this(x,y,0,500,false,burriedDead);
	}
	
	
	public void wakeUp(SimpleGameData data) {
		data.addZ(burriedDead);
		this.life = 0;
	}
	
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y+20, TombstoneSize, TombstoneSize));
	}
	
	
	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + TombstoneSize);
	}

	@Override
	public void incAS() {}

	@Override
	public void resetAS() {}
	
}

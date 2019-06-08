package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Tombstone {
	private final int x;
	private final int y;
	private Zombie burriedDead;
	private final int TombstoneSize = 50;
	private final String color = "#606875";

	private Tombstone(int x, int y, Zombie burriedDead) {
		this.x = x;
		this.y = y;
		this.burriedDead = burriedDead;

	}


	public void wakeUp(SimpleGameData data, BordView view, ArrayList<Zombie> allZombies) {
		burriedDead = SimpleGameData.getRandomZombie(allZombies);
		burriedDead.setX(x);
		burriedDead.setY(y);
		data.addZ(burriedDead);
	}
	
	
	public static Tombstone createTombstone(int x, int y) {
		return new Tombstone(x,y,null);
		
	}

}

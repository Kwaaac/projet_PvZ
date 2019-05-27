package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class BackupDancerZombie extends Zombie {

	private final String name = "BackupDancerZombie";
	private final String color = "#000000";

	public BackupDancerZombie(int x, int y) {
		super(x, y, 100, 200, 4, "medium",false);
	}
	
	public BackupDancerZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 200, 4, "medium", gifted);
	}

	public BackupDancerZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	public void go() {
		super.go((float) -0.93);
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new BackupDancerZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		super.draw(view, graphics);
	}
}

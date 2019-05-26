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

public class BucketheadZombie extends Zombie {

	private final String name = "BucketheadZombie";
	private final String color = "#CB5050";

	public BucketheadZombie(int x, int y) {
		super(x, y, 100, 1300, 2, "slow",false);
	}
	

	public BucketheadZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 1300, 2, "slow", gifted);
	}

	public BucketheadZombie() {
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
		super.go((float) -0.7);
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new BucketheadZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		super.draw(view, graphics);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {

	}
}

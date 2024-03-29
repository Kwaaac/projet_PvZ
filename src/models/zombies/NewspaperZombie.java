package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.List;

import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class NewspaperZombie extends Zombie {

	private final String name = "NewspaperZombie";
	private String color = "#FFFFFF";

	public NewspaperZombie(int x, int y) {
		super(x, y, 100, 340, 1, "slow",false);
	}

	public NewspaperZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 340, 1, "slow", gifted);
	}

	public NewspaperZombie() {
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

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new NewspaperZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 90, 180, 2));

		graphics.setColor(Color.decode("#000000"));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 90, -180, 2));

		super.draw(view, graphics);
	}

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		if (this.life <= 200) {
			setBasicSpeed("fast");
			shootBarMax = 4000;
			color = "#000000";
		}
		return true;
	}
}

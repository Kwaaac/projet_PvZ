package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;
import views.SimpleGameView;

public class Zomboni extends Zombie {

	private final String type = "Zomboni";
	private final String color = "#000000";

	public Zomboni(int x, int y) {
		super(x, y, 100, 200, 1, "slow", false);
	}

	public Zomboni(int x, int y, boolean gifted) {
		super(x, y, 100, 200, 1, "slow", gifted);
	}

	public Zomboni() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return type;
	}

	@Override
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new Zomboni(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		if (isSlowed()) {
			super.draw(view, graphics);
		}
	}

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		Cell cell = dataBord.getCell(getCaseJ(), getCaseI());

		if (cell != null) {
			cell.enableIce();
		}
		return true;
	}
}

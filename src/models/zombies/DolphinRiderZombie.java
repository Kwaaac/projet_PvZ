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

public class DolphinRiderZombie extends Zombie {

	private final String name = "DolphinRiderZombie";
	private final String color = "#000000";
	private final double[] diffSpeed = { -2, -0.93 };

	private boolean swim = true;

	public DolphinRiderZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow",false);
	}
	

	public DolphinRiderZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 340, 1, "ultraSlow", gifted);
	}

	public DolphinRiderZombie() {
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
		return new DolphinRiderZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		super.draw(view, graphics);
	}

	private boolean detect(SimpleGameData dataBord) {
		Cell cell = dataBord.getCell(getCaseJ(), getCaseI());
		if (cell != null) {
			return cell.isPlantedPlant();
		}

		return false;
	}

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		if (detect(dataBord) && swim) {
			swim = false;
			setX(x - BordView.getSquareSize() - 50);
			setBasicSpeed("slow");

			return false;
		}

		return true;
	}

	@Override
	public boolean isCommon() {
		return false;
	}

}

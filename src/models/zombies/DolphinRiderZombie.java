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
		super(x, y, 100, 340, 1, "ultraSlow");
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
	
	public void go() {
		if (swim) {
			super.go((float) diffSpeed[0]);
		} else {
			super.go((float) diffSpeed[1]);
		}
	}
	
	@Override
	public Zombie createNewZombie(int x, int y) {
		return new DolphinRiderZombie(x, y);
	}

	int sizeOfZombie = super.getSizeOfZombie();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	private boolean detect(SimpleGameData dataBord) {
		Cell cell = dataBord.getCell(getCaseJ(), getCaseI());
		if (cell != null) {
			return cell.isPlantedPlant();
		}
		
		return false;
	}

	@Override
	public boolean action(SimpleGameData dataBord) {
		if (detect(dataBord) && swim) {
			swim = false;
			setX(x - BordView.getSquareSize() - 50);
			
			return false;
		}
		
		return true;
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		
	}
	
	@Override
	public boolean isCommon() {
		return false;
	}
	
}

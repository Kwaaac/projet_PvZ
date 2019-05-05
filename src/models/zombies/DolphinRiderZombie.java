package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawDolphinRiderZombie(context, x, y, color);
		
		return new DolphinRiderZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawDolphinRiderZombie(graphics, x, y, color);
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
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}
	
}

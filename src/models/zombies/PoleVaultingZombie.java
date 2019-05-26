package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;
import views.SimpleGameView;

public class PoleVaultingZombie extends Zombie {

	private final String name = "PoleVaultingZombie";
	private final String color = "#000000";
	private final double[] diffSpeed = { -1.23, -0.93 };

	private boolean jump = true;

	public PoleVaultingZombie(int x, int y) {
		super(x, y, 100, 340, 1, "slow" , false);
	}
	
	public PoleVaultingZombie(int x, int y , boolean gifted) {
		super(x, y, 100, 340, 1, "slow" , gifted);
	}

	public PoleVaultingZombie() {
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
		if (jump) {
			super.go((float) diffSpeed[0]);
		} else {
			super.go((float) diffSpeed[1]);
		}
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new PoleVaultingZombie(x, y, gift);
	}
	
	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, 180, 2));

		graphics.setColor(Color.red);
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, -180, 2));
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
		if (detect(dataBord) && jump) {
			jump = false;
			setX(x - BordView.getSquareSize() - 50);
			
			return false;
		}
		
		return true;
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		
	}

}

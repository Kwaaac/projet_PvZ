package models.zombies;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.Cell;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class PoleVaultingZombie extends Zombie {

	private final String name = "PoleVaultingZombie";
	private final String color = "#000000";
	private final int threat = 1;
	private final double[] diffSpeed = { -1.23, -0.93 };

	private boolean jump = true;

	public PoleVaultingZombie(int x, int y) {
		super(x, y, 100, 340, -0.93);
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

	public Integer getProb(int difficulty) {
		return (int) (((100 / threat) * (difficulty)) * 0.55 + 0.10 * threat);
	}

	public boolean canSpawn(int difficulty) {
		return threat <= difficulty;
	}

	public void go() {
		if (jump) {
			super.go((float) diffSpeed[0]);
		} else {
			super.go((float) diffSpeed[1]);
		}

	}

	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPoleVaultingZombie(context, x, y, color);

		return new PoleVaultingZombie(x, y);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawPoleVaultingZombie(graphics, x, y, color);
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
			setX(x - BordView.getSquareSize() - 20);
			
			return false;
		}
		
		return true;
	}

}

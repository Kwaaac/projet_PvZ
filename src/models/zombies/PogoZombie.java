package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.List;

import models.Chrono;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;
import views.SimpleGameView;

public class PogoZombie extends Zombie {

	private final String name = "Pogo Zombie";
	private final String color = "#000000";
	private Chrono jumpexeisloading = new Chrono();

	private boolean jump = true;

	public PogoZombie(int x, int y) {
		super(x, y, 100, 340, 1, "fast", false);
	}

	public PogoZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 340, 1, "fast", gifted);
	}

	public PogoZombie() {
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
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new PogoZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, 180, 2));

		graphics.setColor(Color.red);
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, -180, 2));

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
		if (detect(dataBord) && jump && !(dataBord.getCell(getCaseJ(), getCaseI()).getMainPlant().isTall())) {
			stop();
			jumpexeisloading.startChronoIfReset();
			if(jumpexeisloading.asReachTimerAndStop(5)) {
				jumpexeisloading.steady();
				setX(x - BordView.getSquareSize() - 50);
			}
			go();
			return false;
		}else if((dataBord.getCell(getCaseJ(), getCaseI()).getMainPlant().isTall())){
			jump = false;
		}

		return true;
	}
	
	@Override
	public boolean magnetizable() {
		if(jump) {
		jump =false;
		return true;
	}
		return false;
	}
}

package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import models.Chrono;
import models.Coordinates;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import views.BordView;
import views.SimpleGameView;

public class JackintheBoxZombie extends Zombie {
	private final String name = "JackintheBoxZombie";
	private final String color = "#000000";
	private final Chrono explosion = new Chrono();
	private boolean box = true;

	public JackintheBoxZombie(int x, int y) {
		super(x, y, 100, 340, 1, "fast", false);
		explosion.start();
	}

	public JackintheBoxZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 340, 1, "fast", gifted);
		explosion.start();
	}

	public JackintheBoxZombie() {
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
	public boolean magnetizable() {
		if(box) {
			box = false;
			return true;
		}
		
		return false;
	}

	@Override
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new JackintheBoxZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		
		if(box) {
			graphics.setColor(Color.white);
			graphics.fill(new Ellipse2D.Float(x, y+20, 30, 30));
		}

		super.draw(view, graphics);
	}

	private ArrayList<Coordinates> zone() {
		int caseXJack = BordView.caseXFromX(x);
		int caseYJack = BordView.caseYFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				zone.add(new Coordinates(caseXJack + i, caseYJack + j));
			}
		}
		return zone;
	}

	private ArrayList<Plant> detect(BordView view, SimpleGameData dataBord, ArrayList<Coordinates> zone) {
		ArrayList<Plant> Lz = new ArrayList<>();
		for (Coordinates c : zone) {
			Cell cell = dataBord.getCell(c.getJ(), c.getI());

			if (cell != null && cell.isPlantedPlant()) {
				for (Plant p : cell.getPlantsInCell()) {
					Lz.add(p);
				}
			}
		}

		return Lz;
	}

	public boolean readyToDetonate() {
		return explosion.asReachTimerMs(13500);
	}

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		if (box) {
			Cell cell = dataBord.getCell(this.getCaseJ(), this.getCaseI());

			if (dataBord.isCorrectBordLocation(view, x, y) && cell != null) {

				if (this.readyToDetonate()) {
					ArrayList<Coordinates> zone = zone();

					for (Plant p : detect(view, dataBord, zone)) {
						Cell zCell = dataBord.getCell(p.getCaseJ(), p.getCaseI());

					}

					this.life = 0;
					cell.removeZombie(this);
				}
			}

			this.incAS();

		}
		return true;
	}

}

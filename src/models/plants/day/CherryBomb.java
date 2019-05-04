package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class CherryBomb extends Plant {

	private final String name = "CheeryBomb";
	private final String color = "#CB5050";
	private final ArrayList<Coordinates> zone;

	public CherryBomb(int x, int y) {
		super(x, y, 0, 1, 1200, 0, "free");
		this.zone = zone();
		this.shootTime = System.currentTimeMillis();
	}

	public CherryBomb() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	private ArrayList<Coordinates> zone() {
		int caseXCherry = BordView.caseXFromX(x);
		int caseYCherry = BordView.caseYFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				zone.add(new Coordinates(caseXCherry + i, caseYCherry + j));
			}
		}
		return zone;
	}

	
	// Detection des zombies dans les cellules données
	private ArrayList<Entities> detect(BordView view, SimpleGameData dataBord) {
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Coordinates c : zone) {
			Cell cell = dataBord.getCell(c.getJ(), c.getI());
			
//			System.out.println(c);
//			System.out.println(cell != null);
//			if(cell != null) {
//				System.out.println(cell.isThereZombies());
//				System.out.println("\n");
//			}
			if(cell != null && cell.isThereZombies()) {
				for(Entities e: cell.getZombiesInCell()) {
					Lz.add(e);
				}
			}
		}
		
		return Lz;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
		
		if (this.readyToshot()) {
			for (Entities z : this.detect(view, dataBord)) {
				z.takeDmg(1800);
			}
			
			this.life = 0;
			System.out.println(dataBord.getNbLines() + "; " + dataBord.getNbColumns());
			System.out.println(getCaseJ() + "; " + getCaseI());
			dataBord.getCell(getCaseJ(), getCaseI()).removePlant();
		}

		this.incAS();
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawCherryBomb(context, x, y, color);

		return new CherryBomb(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawCherryBomb(graphics, x, y, color);
	}
}

package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.IEntite;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;
import views.SimpleGameView;

public class JackintheBoxZombie extends Zombie {

	private final String name = "JackintheBoxZombie";
	private final String color = "#000000";
	
	public JackintheBoxZombie(int x, int y) {
		super(x, y, 100, 340, 1, "verySlow");//2.2
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
	
	public void go() {
		super.go((float) -0.93);
	}
	
	@Override
	public Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawJackintheBoxZombie(context, x, y, color);
		
		return new JackintheBoxZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawJackintheBoxZombie(graphics, x, y, color);
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

	
	private ArrayList<Entities> detect(BordView view, SimpleGameData dataBord, ArrayList<Coordinates> zone) {
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Coordinates c : zone) {
			Cell cell = dataBord.getCell(c.getJ(), c.getI());
			
			if(cell != null && cell.isTherePlant()) {
				for(Entities e: cell.getPlantInCell()) {
					Lz.add(e);
				}
			}
		}
		
		return Lz;
	}
	
	public boolean readyToExplosed(Cell cell) {
		return cell.isPlantedPlant();
	}
	
	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
		Cell cell = dataBord.getCell(this.getCaseJ(), this.getCaseI());
		
		
		if (dataBord.isCorrectBordLocation(view,  x, y) && cell != null) {
			
			if (this.readyToExplosed(cell)) {
				ArrayList<Coordinates> zone = zone();
				System.out.println(detect(view, dataBord, zone));
				
				for (Entities z : detect(view, dataBord, zone)) {
					System.out.println(z.getCase());
					dataBord.getCell(z.getCaseJ(), z.getCaseI()).removePlant(z);
				}
				
				this.life = 0;
				cell.removeZombie(this);
			}
		}

		this.incAS();
	}
	
}

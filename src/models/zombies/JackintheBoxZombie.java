package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.IEntite;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import views.BordView;
import views.SimpleGameView;

public class JackintheBoxZombie extends Zombie {

	/*
	 * 
	 * 
	 * Le fonctionnement de ce Zombie n'a pas �t� compris, maxime le referra
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
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
	public Zombie createNewZombie(int x, int y) {
		return new JackintheBoxZombie(x, y);
	}
	
	int sizeOfZombie = super.getSizeOfZombie();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
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
			
			if(cell != null && cell.isPlantedPlant()) {
				for(Plant p: cell.getPlantsInCell()) {
					Lz.add(p);
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
				
				for (Plant p : detect(view, dataBord, zone)) {
					Cell zCell = dataBord.getCell(p.getCaseJ(), p.getCaseI());
					
				}
				
				this.life = 0;
				cell.removeZombie(this);
			}
		}

		this.incAS();
	}
	
}

package models.projectiles;

import java.awt.Graphics2D;
import java.util.ArrayList;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class LawnMower extends Projectile {
	private final String name = "Tondeuse";
	private final String color = "#B44A4A";
	private final String color2 = "#4AAFB4";
	private final int id;
	private static final int[] SizeOfLawnMower = {100,80};
	
	public LawnMower(float x, float y, int id) {
		super(x, y, 100000, 100000 , 0);
		this.id = id;
	}
	
	private int getID() {
		return id;
	}
	
	public void SpeedBoostON() {
		this.setSpeed(this.getSpeed()+2);
	}

	public void SpeedBoostOFF() {
		this.setSpeed(this.getSpeed()-2);
	}
	
	public static boolean containsID(ArrayList<LawnMower> lawnMower,int ID) {
		return lawnMower.stream().filter(l -> l.getID() == ID).findFirst().isPresent();
	}
	
	@Override
	public String getColor() {
		if (SimpleGameData.getMap() == "Pool") {
			return color2;
		}
		return color;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + id +" "+ name; 
	}


	public static int[] getSizeOfLawnMower() {
		return SizeOfLawnMower;
	}
	
	public void go() {
		this.setSpeed(20.0);
	}
	
	public boolean outBoard(BordView view,SimpleGameData dataBord) {
		int xOrigin = view.getXOrigin();
		int squareSize = BordView.getSquareSize();
		
		return x > (xOrigin + (squareSize * dataBord.getNbColumns()));
	}
	
	public static void hasToDie(ArrayList<LawnMower> lm,DeadPool DPe, SimpleGameData data,BordView view) {
		for(LawnMower l : lm) {
			if (l.outBoard(view, data)) {
				if(data.getCell(l.getCaseJ(), l.getCaseI()) != null) {
				data.getCell(l.getCaseJ(), l.getCaseI()).removeZombie(l);
				}
				DPe.add(l);
			}
		}
	}
	
	private ArrayList<Entities> detect(SimpleGameData dataBord) {
		ArrayList<Entities> Lz = new ArrayList<>();

		Cell cell = dataBord.getCell(this.getCaseJ(), this.getCaseI());

		if (cell != null && cell.isThereZombies()) {
			for (Entities z : cell.getZombiesInCell()) {
				Lz.add(z);
			}
		}
		
		return Lz;
	}
	
	public boolean isMoving() {
		return this.getSpeed() < 0;
	}
	
	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + SizeOfLawnMower[0]);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		if (SimpleGameData.getMap() == "Pool") {
			view.drawLawnMower(graphics, x, y, color2);
		}
		else {
			view.drawLawnMower(graphics, x, y, color);
		}
		
	}

	@Override
	public int isSlowing() {
		return 0;
	}

	

}

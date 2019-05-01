package models.projectiles;

import java.awt.Graphics2D;
import java.util.ArrayList;

import models.Cell;
import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.SimpleGameData;
import models.plants.Plant;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class LawnMower extends Projectile {
	private final String name = "Tondeuse";
	private final String color = "#B44A4A";
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
		this.setSpeed(10.0);
	}
	
	public boolean outBoard(BordView view,SimpleGameData dataBord) {
		int xOrigin = view.getXOrigin();
		int squareSize = SelectBordView.getSquareSize();
		System.out.println(x+"--"+xOrigin + squareSize * dataBord.getNbColumns()+(x > (xOrigin + squareSize * dataBord.getNbColumns())));
		
		return x > (xOrigin + squareSize * dataBord.getNbColumns());
	}
	
	public void tondeuse(BordView view,SimpleGameData dataBord) {
		if(this.outBoard(view, dataBord) == false) {
			ArrayList<Entities> le = this.detect(dataBord);
			for(Entities e : le) {
				if(this.hit(e) && this.getSpeed() < 0) {
					this.mortalKombat(e);
					life = 100000;
				}
			}
		}else {
		System.out.println(this+"i'm free");
		life = 0;
		}
	}
	
	public static void hasToDie(ArrayList<LawnMower> lm,DeadPool DPe, SimpleGameData data) {
		for(LawnMower l : lm) {
				if (l.isDead()) {
					if(data.getCell(l.getCaseJ(), l.getCaseI()) != null) {
					data.getCell(l.getCaseJ(), l.getCaseI()).removeEntity(l);
					}
					DPe.add(l);
				}
		}
	}
	
	private ArrayList<Entities> detect(SimpleGameData dataBord) {
		ArrayList<Entities> Lz = new ArrayList<>();

		Cell cell = dataBord.getCell(getCaseJ(), getCaseI());

		if (cell != null && cell.isThereEntity()) {
			for (Entities z : cell.getEntitiesInCell()) {
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
		view.drawLawnMower(graphics, x, y, color);
	}

	

}

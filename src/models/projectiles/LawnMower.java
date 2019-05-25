package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.DeadPool;
import models.SimpleGameData;
import models.cells.Cell;
import views.BordView;
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
	
	public boolean isMoving() {
		return this.getSpeed() < 0;
	}
	
	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + SizeOfLawnMower[0]);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		if (SimpleGameData.getMap() == "Pool") {
			
			graphics.setColor(Color.decode(color2));
			graphics.fill(new RoundRectangle2D.Float(x, y + (SizeOfLawnMower[1] / 3), SizeOfLawnMower[0],
					SizeOfLawnMower[1], 10, 10));
			graphics.setColor(Color.BLACK);
			graphics.fill(new Ellipse2D.Float(x + SizeOfLawnMower[0] / 4,
					y + SizeOfLawnMower[1] / 4 + (SizeOfLawnMower[1] / 3), SizeOfLawnMower[0] / 2, SizeOfLawnMower[1] / 2));
			graphics.fill(new Rectangle2D.Float(x + 10, y + 3 + (SizeOfLawnMower[1] / 3), 3, SizeOfLawnMower[1] - 5));
			
		}
		else {
			
			graphics.setColor(Color.decode(color));
			graphics.fill(new RoundRectangle2D.Float(x, y + (SizeOfLawnMower[1] / 3), SizeOfLawnMower[0],
					SizeOfLawnMower[1], 10, 10));
			graphics.setColor(Color.BLACK);
			graphics.fill(new Ellipse2D.Float(x + SizeOfLawnMower[0] / 4,
					y + SizeOfLawnMower[1] / 4 + (SizeOfLawnMower[1] / 3), SizeOfLawnMower[0] / 2, SizeOfLawnMower[1] / 2));
			graphics.fill(new Rectangle2D.Float(x + 10, y + 3 + (SizeOfLawnMower[1] / 3), 3, SizeOfLawnMower[1] - 5));
		}
		
	}

	@Override
	public int isSlowing() {
		return 0;
	}
	
	@Override
	/**
	 * give the zombie a case and add the zombie on the zombieList of the cell
	 * 
	 * @param data Data of the main Bord
	 */
	public void setCase(SimpleGameData data) {
	}
	

}

package models.plants;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class CherryBomb extends Plant {

	private final String name = "CheeryBomb";
	private final String color = "#CB5050";
	private long shootTime = System.currentTimeMillis();
	
	public CherryBomb(int x, int y) {
		super(x, y, 0, 1, 1200);
		shootTime = System.currentTimeMillis();
	}
	
	public CherryBomb() {
		super(-10, -10, 0, 1, 1);
	}
	
	@Override
	public void incAS() {
		
		shootBar = System.currentTimeMillis() - shootTime;
		
		System.out.println(shootBar + "///" + shootBarMax);
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	private ArrayList<Coordinates> zone(BordView view) {
		int caseXCherry = view.columnFromX(x);
		int caseYCherry = view.lineFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (caseXCherry + i >= 0 && caseXCherry + i <= view.getLength() && caseYCherry + j >= 0
						&& caseYCherry + j <= view.getWidth()) {
					zone.add(new Coordinates(caseXCherry + i, caseYCherry + j));
				}
			}
		}

		return zone;
	}

	private ArrayList<Entities> detect(BordView view, ArrayList<Zombie> myZombies) {
		ArrayList<Coordinates> cherry = this.zone(view);
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Entities z : myZombies) {
			Coordinates zombie = z.getCase();
			for (Coordinates c : cherry) {
				if (zombie.equals(c)) {
					Lz.add(z);
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
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		
		if(this.readyToshot(myZombies)) {
			for (Entities z : this.detect(view, myZombies)) {
				z.takeDmg(1800);
			}
			this.life = 0;
		}
		
		this.incAS();
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawCherryBomb(context, x,  y, color);
		
		return new CherryBomb(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawCherryBomb(graphics, x,  y, color);
	}

}

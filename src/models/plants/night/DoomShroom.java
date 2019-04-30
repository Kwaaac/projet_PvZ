package models.plants.night;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class DoomShroom extends Plant{
	private final String name = "DoomShroom";
	private final String color = "#90D322";
	
	public DoomShroom(int x, int y) {
		super(x, y, 0, 1, 0, 125, "fast");
	}
	
	public DoomShroom() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	
	private ArrayList<Coordinates> zone(BordView view) {
		int caseXDoom = view.columnFromX(x);
		int caseYDoom = view.lineFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (caseXDoom + i >= 0 && caseXDoom + i <= view.getLength() && caseYDoom + j >= 0
						&& caseYDoom + j <= view.getWidth()) {
					zone.add(new Coordinates(caseXDoom + i, caseYDoom + j));
				}
			}
		}

		return zone;
	}

	private ArrayList<Entities> detect(BordView view, ArrayList<Zombie> myZombies) {
		ArrayList<Coordinates> doomShroom = this.zone(view);
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Entities z : myZombies) {
			Coordinates zombie = z.getCase();
			for (Coordinates c : doomShroom) {
				if (zombie.equals(c)) {
					Lz.add(z);
				}
			}
		}
		return Lz;
	}
	
	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {
		
		if(this.readyToshot()) {
			for (Entities z : this.detect(view, myZombies)) {
				z.takeDmg(1800);
			}
			this.life = 0;
		}
		
		this.incAS();
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawDoomShroom(context, x,  y, color);
		
		return new DoomShroom(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawDoomShroom(graphics, x,  y, color);
	}

}

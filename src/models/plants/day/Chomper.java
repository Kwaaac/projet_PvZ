package models.plants.day;

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

public class Chomper extends Plant{
	private final String name = "Chomper";
	private final String color = "#BE33FF";
	private boolean activate = false;
	
	public Chomper(int x, int y) {
		super(x, y, 0, 1, 1200, 150, "fast");
	}
	
	public Chomper() {
		super(-10, -10, 0, 1, 1, 150, "fast");
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	private ArrayList<Coordinates> zone(BordView view) {
		int caseXChomper = view.columnFromX(x);
		int caseYChomper = view.lineFromY(y);
		ArrayList<Coordinates> zone = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (caseXChomper + i >= 0 && caseXChomper + i <= view.getLength() && caseYChomper + j >= 0
						&& caseYChomper + j <= view.getWidth()) {
					zone.add(new Coordinates(caseXChomper + i, caseYChomper + j));
				}
			}
		}

		return zone;
	}

	private ArrayList<Entities> detect(BordView view, ArrayList<Zombie> myZombies) {
		ArrayList<Coordinates> chomper = this.zone(view);
		ArrayList<Entities> Lz = new ArrayList<>();
		for (Entities z : myZombies) {
			Coordinates zombie = z.getCase();
			for (Coordinates c : chomper) {
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
	
	private void activation() {
		if (activate == false) {
			activate = true;
		}
	}
	
	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {
		if (this.readyToshot()) {
			activation();
		}

		if (activate) {
			ArrayList<Entities> lz = this.detect(view, myZombies);

			if (!lz.isEmpty()) {
				for (Entities z : lz) {
					z.takeDmg(1800);
				}

				this.life = 0;
			}
		}

		this.incAS();
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawChomper(context, x,  y, color);
		
		return new Chomper(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawChomper(graphics, x,  y, color);
	}

}

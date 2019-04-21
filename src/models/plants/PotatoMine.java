package models.plants;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.Entities;
import models.SimpleGameData;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class PotatoMine extends Plant {

	private final String name = "Mine";
	private final String color1 = "#727507";
	private final String color2 = "#EDF404";
	private boolean activate = false;

	public PotatoMine(int x, int y) {
		super(x, y, 0, 120, 80);
		setTimerA(1);
	}

	public PotatoMine() {
		super(-10, -10, 0, 1, 1);
		activate = true;
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
	public boolean readyToshot(ArrayList<Zombie> myZombies) {
		return timerA % speedshoot == 0;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		this.incAS();

		if (this.readyToshot()) {
			activation();
		}
		
		if (activate) {
			ArrayList<Entities> Lz = this.detect(view, myZombies);
			if (!Lz.isEmpty()) {
				this.explosion(view, myZombies);
			}

		}

	}

	private ArrayList<Entities> detect(BordView view, ArrayList<Zombie> myZombies) {
		Coordinates PotatoMine = super.caseXY;
		ArrayList<Entities> Lz = new ArrayList<>();

		for (Entities z : myZombies) {
			Coordinates zombie = z.getCase();

			if (zombie.equals(PotatoMine)) {
				Lz.add(z);
			}

		}
		return Lz;
	}

	private void explosion(BordView view, ArrayList<Zombie> myZombies) {

		for (Entities z : this.detect(view, myZombies)) {
			z.takeDmg(1800);
		}

		this.life = 0;

	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPotatoMine(context, x, y, color2);

		return new PotatoMine(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		if (activate) {
			view.drawPotatoMine(graphics, x, y, color2);
		} else {
			view.drawPotatoMine(graphics, x, y, color1);
		}
	}
}

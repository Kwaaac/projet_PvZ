package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.DeadPool;
import models.SimpleGameData;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;

public class SnorkelZombie extends Zombie {

	private final String name = "SnorkelZombie";
	private final String color = "#000000";
	
	public SnorkelZombie(int x, int y) {
		super(x, y, 100, 200, 1, "slow");
	}

	public SnorkelZombie() {
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
		view.drawSnorkelZombie(context, x, y, color);
		
		return new SnorkelZombie(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawSnorkelZombie(graphics, x, y, color);
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}
	
	@Override
	public boolean isCommon() {
		return false;
	}
	
	@Override
	public void conflictBvZ(DeadPool DPe, ArrayList<Projectile> Le, SimpleGameData data) {
		
		for (Projectile e : Le) {
			e.action();
			if (this.hit(e) && !(e.isInConflict())) {
				this.slowed(e.isSlowing());
				e.setConflictMode(true);
				this.mortalKombat(e);
				if (e.isDead()) {
					DPe.addInDP(e);
				}
				/*
				 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
				 * attaquant ne sois mort on empeche des echange de d�gats(on en a besoin pour
				 * pas qu'une plante morte soit capable de tu� apr�s sa mort)
				 */
				else if (this.isDead()) {
					e.setConflictMode(false);
					DPe.addInDP(this);
					break;
				}
			}

		}
	}
	
}

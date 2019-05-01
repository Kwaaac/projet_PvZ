package models.plants.pool;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class LilyPad extends Plant {
	private final String name = "LilyPad";
	private final String color = "#8FD916";

	public LilyPad(int x, int y) {
		super(x, y, 0, 300, 0, 25, "fast");
	}
	
	public LilyPad() {
		super(-10, -10, 0, 1, 1, 25, "fast");
	}
	
	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawLilyPad(context, x,  y, color);
		
		return new LilyPad(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawLilyPad(graphics, x,  y, color);
	}

}

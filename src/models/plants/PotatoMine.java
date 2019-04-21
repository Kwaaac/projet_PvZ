package models.plants;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class PotatoMine extends Plant{

	private final String name = "Mine";
	private final String color = "#EDF404";
	
	public PotatoMine(int x, int y) {
		super(x, y, 0, 1, 80);
	}
	
	public PotatoMine() {
		super(-10, -10, 0, 1, 1);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {

	}
	
	public ArrayList<Zombie> detect(ArrayList<Zombie> myZombies){
		
		
		
		return myZombies;
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPotatoMine(context, x,  y, color);
		
		return new PotatoMine(x, y);
		
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawPotatoMine(graphics, x,  y, color);
	}
}

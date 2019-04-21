package plants;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;
import zombies.Zombie;

public class Mine extends Plant{

	private final String name = "Mine";
	private final String color = "#CB5050";
	
	public Mine(int x, int y) {
		super(x, y, 1800, 0, 999999);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	@Override
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}
	
	public void conflictAll(Zombie... z) {}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {

	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, SimpleGameData data, int x, int y) {
		view.drawPotatoMine(context, data, x,  y, color);
		
		return new Mine(x, y);
		
	}

	@Override
	public void draw(SelectBordView view, Graphics2D graphics, SimpleGameData data, int x, int y) {
		view.drawPotatoMine(graphics, data, x,  y, color);
	}
}

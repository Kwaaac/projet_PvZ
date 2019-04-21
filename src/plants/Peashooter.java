package plants;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;
import zombies.Zombie;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	private final String color = "#90D322";
	
	public Peashooter(int x, int y) {
		super(x, y, 0, 300, 230);
		setTimerA(230);
	}

	public Peashooter() {
		super(-10, -10, 0, 1, 1);
	}

	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	int sizeOfPlant = super.getSizeOfPlant();
	
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, SimpleGameData data, int x, int y) {	
		view.drawPeashooter(context, data, x,  y, color);
		
		return new Peashooter(x, y);
	}
	
	@Override
	public void draw(SelectBordView view, Graphics2D graphics, SimpleGameData data, int x, int y) {
		view.drawPeashooter(graphics, data, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {
		this.incAS();
		
		if(this.readyToshot(myZombies)) {
			myBullet.add(new Bullet(super.getX() + super.getSizeOfPlant(), super.getY() + (super.getSizeOfPlant() / 2) - 10));
			
			this.resetAS();
		}
	}

}

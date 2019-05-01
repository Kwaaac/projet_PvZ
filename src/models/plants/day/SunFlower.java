package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class SunFlower extends Plant{
	private final String name = "SunFlower";
	private final String color = "#FEFF33";
	
	private final Coordinates placeSelect = new Coordinates(0,1);
	
	public SunFlower(int x, int y) {
		//    x, y, damage,  life,  shootBarMax,  cost, cooldown
		super(x, y, 0, 300, 10_000, 25, "fast");
	}
	
	public SunFlower() {
		this(-10, -10);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	int sizeOfPlant = super.getSizeOfPlant();
	
	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawSunFlower(context, x,  y, color);
		
		return new SunFlower(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawSunFlower(graphics, x, y, color);
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord) {
		if(this.readyToshot()) {
			SimpleGameData.spawnSun(view, x + 20, y + 20);
			this.resetAS();
		}
		
		this.incAS();
	}

}
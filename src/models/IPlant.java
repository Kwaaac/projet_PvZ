package models;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import plants.Plant;
import plants.Projectile;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;
import zombies.Zombie;

 public interface IPlant {
	
	void incAS();
	
	void resetAS();

	boolean isDead();

	float getY();

	float getX();
	
	void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies);
	
	boolean readyToshot(ArrayList<Zombie> MZ);
	
	Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, SimpleGameData data, int x, int y);
	
	void draw(SelectBordView view, Graphics2D graphics, SimpleGameData data, int x, int y);
}

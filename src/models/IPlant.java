package models;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

 public interface IPlant {
	
	void incAS();
	
	void resetAS();

	boolean isDead();

	float getY();

	float getX();
	
	void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies);
	
	boolean readyToshot(ArrayList<Zombie> MZ);
	
	Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y);
	
	void draw(SimpleGameView view, Graphics2D graphics, int x, int y);
}

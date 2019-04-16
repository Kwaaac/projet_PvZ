package models;

import java.util.ArrayList;

import plants.Projectile;
import views.BordView;
import zombies.Zombie;

 public interface IPlant {
	
	void incAS();
	
	void resetAS();

	boolean isDead();

	float getY();

	float getX();
	
	void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies);
	
	boolean readyToshot(ArrayList<Zombie> MZ);
}

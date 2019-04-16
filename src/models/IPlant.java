package models;

import java.util.ArrayList;

import plants.Projectile;
import views.BordView;
import zombies.Zombie;

 public interface IPlant {
	
	void incAS();
	
	void resetAS();

	boolean isDead();

	int getY();

	int getX();
	
	void action(ArrayList<Entities> myBullet, BordView view, ArrayList<Entities> myZombies);
	
	boolean readyToshot(ArrayList<Entities> MZ);
}

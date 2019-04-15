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
	
	void action(ArrayList<Projectile> MyBullet, BordView view, ArrayList<Zombie> MyZombies);
	
	boolean readyToshot(ArrayList<Zombie> MZ);
}

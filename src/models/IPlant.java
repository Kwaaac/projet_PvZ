package models;

import java.util.ArrayList;

import plants.Projectile;
import views.BordView;
import zombies.Zombie;

public interface IPlant {
	public void incAS();
	
	public void resetAS();

	public boolean readyToshot();

	public boolean isDead();

	public int getY();

	public int getX();
	
	void action(ArrayList<Projectile> MyBullet, BordView view, ArrayList<Zombie> MyZombies);
}

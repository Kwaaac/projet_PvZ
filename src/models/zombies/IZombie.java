package models.zombies;

import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.DeadPool;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;
 
public interface IZombie {

	Zombie createNewZombie(int x, int y);
	
	boolean canSpawn(int difficulty);
	
	void go();
	
	int getThreat();
	
	Integer getProb(int difficulty); 
	
	boolean action(SimpleGameData dataBord);
	
	void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies);
	
	boolean isCommon();
	
	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data);
}

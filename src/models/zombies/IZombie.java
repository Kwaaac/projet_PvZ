package models.zombies;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.DeadPool;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;
 
public interface IZombie {

	Zombie createNewZombie(int x, int y, boolean b);
	
	boolean canSpawn(int difficulty);
	
	void go();
	
	int getThreat();
	
	Integer getProb(int difficulty); 
	
	boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies);
	
	boolean isCommon();
	
	void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data);
	
	void giftZombie();
	
	boolean magnetizable();
}

package models.zombies;

import java.util.List;

import models.DeadPool;
import models.SimpleGameData;
import views.BordView;
 
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

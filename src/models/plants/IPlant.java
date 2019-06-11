package models.plants;

import java.awt.Graphics2D;
import java.util.List;

import models.SimpleGameData;
import models.TombStone;
import models.cells.Cell;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public interface IPlant {
	
	int getLife();

	void incAS();

	void resetAS();

	void action(List<Projectile> myBullet, BordView view, List<Zombie> myZombies, List<TombStone> myTombStone, SimpleGameData dataBord);

	boolean readyToshot();

	Plant createNewPlant(int x, int y);

	void draw(SimpleGameView view, Graphics2D graphics);
	
	void draw(SimpleGameView view, Graphics2D graphics, int x, int y);
	
	boolean isTall();

	/*
	 * 0 -> GroundPlant 
	 * 1 -> MainPlant 
	 * 2 -> SupportPlant
	 */
	int getTypeOfPlant();
	
	boolean plantingCondition(Cell cell);
	

}

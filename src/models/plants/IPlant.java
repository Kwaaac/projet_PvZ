package models.plants;

import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.cells.Cell;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public interface IPlant {

	void incAS();

	void resetAS();

	void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies, SimpleGameData dataBord);

	boolean readyToshot();

	Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y);

	void draw(SimpleGameView view, Graphics2D graphics, int x, int y);

	/*
	 * 0 -> GroundPlant 
	 * 1 -> MainPlant 
	 * 2 -> SupportPlant
	 */
	int getTypeOfPlant();
	
	boolean plantingCondition(Cell cell);

}

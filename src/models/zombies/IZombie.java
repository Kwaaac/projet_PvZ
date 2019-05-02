package models.zombies;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;
 
public interface IZombie {

	Zombie createAndDrawNewZombie(SimpleGameView view, ApplicationContext context, int x, int y);
	
	boolean canSpawn(int difficulty); 
	
	void go();
}

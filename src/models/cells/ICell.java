package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;
import views.SimpleGameView;

public interface ICell {
	
	boolean addPlant(Plant plant);
	
	void drawBoardCell(Graphics2D graphics, float i, float j, int darker);
	
	void removePlant(Plant dPe);
	
	boolean isPlantedPlant();

	boolean isGroundPlantPlanted();

	boolean isMainPlantPlanted();

	boolean isSupportPlantPlanted();

	void addZombie(Zombie z);

	void removeZombie(IEntite dPe);

	boolean isThereZombies();

	boolean isCellEmpty();

	ArrayList<Zombie> getZombiesInCell();

	Color getColor();

	Color getColorDarker();
}

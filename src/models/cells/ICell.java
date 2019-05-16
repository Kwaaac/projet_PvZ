package models.cells;

import java.awt.Color;
import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;

public interface ICell {
	
	boolean addPlant(Plant plant);
	
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

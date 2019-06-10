package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;
import views.BordView;

public interface ICell {
	
	boolean addPlant(Plant plant);
	
	void drawBoardCell(Graphics2D graphics, float i, float j, int darker, int squareSize, BordView view);
	
	void removePlant(Plant dPe);
	
	boolean isPlantedPlant();

	boolean isGroundPlantPlanted();

	boolean isMainPlantPlanted();

	boolean isSupportPlantPlanted();

	void addZombie(Zombie z);

	void removeZombie(IEntite dPe);

	boolean isThereBadZombies();

	boolean isCellEmpty();

	ArrayList<Zombie> getBadZombiesInCell();

	Color getColor();

	Color getColorDarker();
	
	boolean isLeanned();
	
	void fog(Graphics2D graphics, float i, float j, int squareSize);
	
	boolean isGrass();
	
	boolean isWater();
	
	boolean isFog();
	
	void actualiseFog();
	
	void enableFog();
	
	void disableFog(long fogTimer);
}

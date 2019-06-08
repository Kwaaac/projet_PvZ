package models;

import java.util.List;

import models.plants.Plant;

public interface IEntite {

	Coordinates hitBox();

	void takeDmg(int x);

	boolean hit(IEntite e);

	void incAS();

	void resetAS();

	boolean isDead();

	boolean isInConflict();

	void setConflictMode(boolean b);

	int getDamage();

	int getCaseJ();

	int getCaseI();
	
	void reverseTeam(SimpleGameData data);
	
	boolean isGood();
	
	boolean isBad();
	
}

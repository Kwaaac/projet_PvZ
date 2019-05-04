package models.cells;

import java.awt.Color;
import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;

public class WaterCell extends Cell {
	private Color color = Color.decode("#0fa5c6");
	private Color colorDarker = Color.decode("#0d9dbc");

	private boolean lilypad = false;
	private boolean plantedPlant = false;

	public WaterCell() {
		super();
	}

	@Override
	public boolean addPlant(Plant plant) {
		
		if(plantedPlant) {
			return false;
		}
		
		if (lilypad == true) {
			if (plant.canBePlantedOnGrass()) {
				plantedPlant = true;
				return true;
			}
			return false;
			
		} else if(lilypad == false){
			if (plant.canBePlantedOnWater()) {
				if (plant.isLilyPad()) {
					lilypad = true;
					
				} else {
					plantedPlant = true;
				}
				return true;
			}

			return false;
		}
		
		return false;

	}

	public Color getColor() {
		return color;
	}

	public Color getColorDarker() {
		return colorDarker;
	}
}

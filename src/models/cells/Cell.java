package models.cells;

import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;

public abstract class Cell implements ICell {

	private final ArrayList<Zombie> zombiesInCell;
	private final ArrayList<Plant> plantInCell;
	private boolean plantedPlant = false;

	public Cell() {
		zombiesInCell = new ArrayList<>();
		plantInCell = new ArrayList<>();
	}

	/**
	 * Add a plant on the cell
	 */
	public boolean addPlant(Plant plant) {
		if(plantedPlant) {
			return false;
		}
		
		if(plant.canBePlantedOnGrass()) {
			plantedPlant = true;
			plantInCell.add(plant);
			return true;
		}
		return false;
	}

	/**
	 * Remove the plant from the cell
	 */
	public void removePlant(IEntite dPe) {
		plantedPlant = false;
		plantInCell.remove(dPe);
	}

	/**
	 * 
	 * @return True is a Plant is on the cell, False otherwise
	 */
	public boolean isPlantedPlant() {
		return plantedPlant;
	}

	/**
	 * Add a zombie to the cell list of zombie
	 * 
	 * @param z Zombie you want to add on the cell
	 */
	public void addZombie(Zombie z) {
		zombiesInCell.add(z);
	}

	/**
	 * Remove a zombie to the cell list of zombie
	 * 
	 * @param dPe Zombie in the cell
	 */
	public void removeZombie(IEntite dPe) {
		zombiesInCell.remove(dPe);
	}

	/**
	 * 
	 * @return The list of zombie from the cell
	 */
	public ArrayList<Zombie> getZombiesInCell() {
		return zombiesInCell;
	}
	
	public ArrayList<Plant> getPlantInCell() {
		return plantInCell;
	}

	/**
	 * 
	 * @return False if the list of zombie is empty, True otherwise
	 */

	public boolean isThereZombies() {
		return !zombiesInCell.isEmpty();
	}
	
	public boolean isTherePlant() {
		return !plantInCell.isEmpty();
	}
	/**
	 * 
	 * @return True if a Plant is on the cell OR if there is zombies on the cell,
	 *         False if both of the statement are False
	 */
	public boolean isCellEmpty() {
		return isPlantedPlant() || isThereZombies();
	}

	@Override
	public String toString() {
		return (plantedPlant == true ? "Il y a une plante, " : "Il n'y a pas de plante, ")
				+ (!zombiesInCell.isEmpty() ? "Voici les zombies présents \n" + zombiesInCell + ", "
						: "Il n'y a pas de zombie");
	}

	
}

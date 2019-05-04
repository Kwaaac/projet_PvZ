package models.cells;

import java.util.ArrayList;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;

public abstract class Cell implements ICell {

	private final ArrayList<Zombie> zombiesInCell;
	private boolean plantedPlant = false;

	public Cell() {
		zombiesInCell = new ArrayList<>();
	}

	/**
	 * Add a plant on the cell
	 */
	public boolean addPlant(Plant plant) {
			plantedPlant = true;
			return true;
	}

	/**
	 * Remove the plant from the cell
	 */
	public void removePlant() {
		plantedPlant = false;
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

	/**
	 * 
	 * @return False if the list of zombie is empty, True otherwise
	 */

	public boolean isThereZombies() {
		return !zombiesInCell.isEmpty();
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

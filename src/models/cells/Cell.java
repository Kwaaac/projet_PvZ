package models.cells;

import java.util.ArrayList;
import java.util.Collections;

import models.IEntite;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public abstract class Cell implements ICell {

	private final ArrayList<Zombie> zombiesInCell;
	private final ArrayList<Plant> plantInCell;
	private final ArrayList<Projectile> projectileInCell;
	private boolean plantedPlant = false;

	public Cell() {
		zombiesInCell = new ArrayList<>();
		plantInCell = new ArrayList<>();
		projectileInCell = new ArrayList<>();
	}
	/**
	 * Add a projectile in it's array
	 * 
	 * @param The projectile added to the cell
	 */
	public void addProjectile(Projectile p) {
		projectileInCell.add(p);
	}
	
	public void removeProjectile(IEntite p) {
		projectileInCell.remove(p);
	}

	/**
	 * Add a plant on the cell
	 */
	public boolean addPlant(Plant plant) {
		if (plantedPlant) {
			return false;
		}

		if (plant.canBePlantedOnGrass()) {
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
	
	public ArrayList<Projectile> getProjectilesInCell(){
		return new ArrayList<Projectile>(projectileInCell);
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
	 * @return The list of zombie on the cell
	 */
	public ArrayList<Zombie> getZombiesInCell() {
		return new ArrayList<Zombie>(zombiesInCell);
	}

	/**
	 * 
	 * @return The list of plant on the cell
	 */
	public ArrayList<Plant> getPlantInCell() {
		return new ArrayList<Plant>(plantInCell);
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
	 * @return False if the list of plants is empty, True otherwise
	 */
	public boolean isTherePlant() {
		return !plantInCell.isEmpty();
	}

	/**
	 * 
	 * @return True if a main plant is planted, False otherwise
	 */
	public boolean isThereAPlantedPlant() {
		return plantedPlant;
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

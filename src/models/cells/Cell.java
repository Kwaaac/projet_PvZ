package models.cells;

import java.util.ArrayList;
import java.util.Collections;

import models.IEntite;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public abstract class Cell implements ICell {

	private Plant groundPlant;
	private Plant mainPlant;
	private Plant supportPlant;

	private final ArrayList<Zombie> zombiesInCell;
	private final ArrayList<Projectile> projectileInCell;

	private boolean plantedGroundPlant = false;
	private boolean plantedMainPlant = false;
	private boolean plantedSupportPlant = false;

	public Cell() {
		groundPlant = null;
		mainPlant = null;
		supportPlant = null;

		zombiesInCell = new ArrayList<>();
		projectileInCell = new ArrayList<>();
	}
	
	/**
	 * Add a main plant on the cell, wont add it if a plant is already planted
	 */
	private boolean addMainPlant(Plant plant) {
		if (plantedMainPlant) {
			return false;
		}

		plantedMainPlant = true;
		mainPlant = plant;
		return true;
	}

	/**
	 * Remove the main plant from the cell
	 */
	private void removeMainPlant(IEntite dPe) {
		plantedMainPlant = false;
		mainPlant = null;
	}
	
	/**
	 * Add a ground plant on the cell, wont add it if a plant is already planted
	 */
	private boolean addGroundPlant(Plant plant) {
		if (plantedGroundPlant) {
			return false;
		}

		plantedGroundPlant = true;
		groundPlant = plant;
		return true;
	}

	/**
	 * Remove the ground plant from the cell
	 */
	private void removeGroundPlant(IEntite dPe) {
		plantedGroundPlant = false;
		groundPlant = null;
	}
	
	/**
	 * Add a support plant on the cell, wont add it if a plant is already planted
	 */
	private boolean addSupportPlant(Plant plant) {
		if (plantedSupportPlant) {
			return false;
		}

		plantedSupportPlant = true;
		supportPlant = plant;
		return true;
	}

	/**
	 * Remove the support plant from the cell
	 */
	private void removeSupportPlant(IEntite dPe) {
		plantedSupportPlant = false;
		supportPlant = null;
	}
	
	/**
	 * Return true if a plant is planted, false otherwise
	 */
	public boolean addPlant(Plant plant) {
		switch(plant.getTypeOfPlant()) {
		case 0:
			return(addGroundPlant(plant));
			
		case 1:
			return(addMainPlant(plant));
			
		case 2:
			return(addSupportPlant(plant));
		}
		
		// On veut que la plante soit l'un des 3 types
		throw new IllegalStateException("La plante est d'un type inconnue");
	}
	
	public void removePlant(Plant plant) {
		switch(plant.getTypeOfPlant()) {
		case 0:
			removeGroundPlant(plant);
			break;
			
		case 1:
			removeMainPlant(plant);
			break;
			
		case 2:
			removeSupportPlant(plant);
			break;
		}
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
	 * 
	 * @return True is a groundPlant is on the cell, False otherwise
	 */
	public boolean isGroundPlantPlanted() {
		return plantedMainPlant;
	}

	/**
	 * 
	 * @return True is a mainPlant is on the cell, False otherwise
	 */
	public boolean isMainPlantPlanted() {
		return plantedMainPlant;
	}

	/**
	 * 
	 * @return True is a supportPlant is on the cell, False otherwise
	 */
	public boolean isSupportPlantPlanted() {
		return plantedSupportPlant;
	}

	public ArrayList<Projectile> getProjectilesInCell() {
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
	 * @return A plant to attack or null if there is no plant
	 */
	public Plant getPlantToAttack() {

		if (plantedSupportPlant) {
			return supportPlant;

		} else if (plantedMainPlant) {
			return mainPlant;

		} else {
			return groundPlant;
		}

	}

	/**
	 * 
	 * @return An array of all plants in the Cell
	 */
	public ArrayList<Plant> getPlantsInCell() {
		ArrayList<Plant> lstP = new ArrayList<>();

		if (plantedSupportPlant) {
			lstP.add(supportPlant);
		}

		if (plantedMainPlant) {
			lstP.add(mainPlant);
		}

		if (plantedGroundPlant) {
			lstP.add(groundPlant);
		}

		return lstP;
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
	 * @return True if a plant is planted, False otherwise
	 */
	public boolean isPlantedPlant() {
		return plantedGroundPlant || plantedSupportPlant || plantedMainPlant;
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
		return (isPlantedPlant() == true ? "Il y des plantes, " : "Il n'y a pas de plante, ")
				+ (!zombiesInCell.isEmpty() ? "Voici les zombies présents \n" + zombiesInCell + ", "
						: "Il n'y a pas de zombie");
	}

}

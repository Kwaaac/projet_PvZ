package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import java.awt.geom.Ellipse2D;

import models.Chrono;
import models.IEntite;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public abstract class Cell implements ICell, Serializable {
	
	protected final boolean dayTime;

	private Plant groundPlant;
	private Plant mainPlant;
	private Plant supportPlant;

	private final ArrayList<Zombie> badZombiesInCell;
	private final ArrayList<Zombie> goodZombiesInCell;
	private final ArrayList<Projectile> projectileInCell;

	private boolean plantedGroundPlant = false;
	private boolean plantedMainPlant = false;
	private boolean plantedSupportPlant = false;

	private boolean crash = false;
	private Chrono crashChrono = new Chrono();

	private boolean ice = false;
	private Chrono iceChrono = new Chrono();

	public Cell(boolean dayTime) {
		this.dayTime = dayTime; //True -> day; false -> night
		
		groundPlant = null;
		mainPlant = null;
		supportPlant = null;

		badZombiesInCell = new ArrayList<>();
		goodZombiesInCell = new ArrayList<>();
		projectileInCell = new ArrayList<>();

		crashChrono.steady();
		iceChrono.steady();
	}

	/**
	 * Into the class, the color of the cell given according dayTime and like a chess Board
	 * This method allow to draw the cell and add changes (crater from doomShroom...)
	 */
	@Override
	public void drawBoardCell(Graphics2D graphics, float i, float j, int darker, int squareSize) {
		graphics.fill(new Rectangle2D.Float(j, i, squareSize, squareSize));
		drawCellChanges(graphics, i, j, squareSize);
	}

	private void drawCellChanges(Graphics2D graphics, float i, float j, int squareSize) {
		//crater drawing
		if (crash) {
			crashChrono.startChronoIfReset();

			graphics.setColor(Color.decode("#bc6b38"));
			int adjust1 = (int) ((int) squareSize - (squareSize * 0.40));
			int adjust2 = (int) ((int) squareSize / 8.64);
			graphics.fill(new Ellipse2D.Float(j + adjust2, i + adjust2, adjust1, adjust1));

			graphics.setColor(Color.decode("#874d29"));
			adjust1 = (int) ((int) squareSize - (squareSize * 0.50));
			adjust2 = (int) ((int) squareSize / 8.64);
			graphics.fill(new Ellipse2D.Float(j + 35, i + 35, adjust1, adjust1));

			if (crashChrono.asReachTimer(90)) {
				crater();
			}
		}
		
		//ice drawing
		if(ice) {
			iceChrono.startChronoIfReset();

			graphics.setColor(Color.decode("#63c5ff"));
			graphics.fill(new Rectangle2D.Float(j, i + squareSize/4, squareSize, squareSize/3));

			if (iceChrono.asReachTimer(90)) {
				ice();
			}
		}
	}

	/**
	 * add or remove the crater effect
	 */
	public void crater() {
		if (!crash) {
			crash = true;
		} else {
			crash = false;
		}
	}
	
	/**
	 * add or remove the ice effect
	 */
	public void ice() {
		if (!ice) {
			ice = true;
		} else {
			ice = false;
		}
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
	private void removeMainPlant() {
		if (mainPlant != null) {
			mainPlant.setLife(0);
			plantedMainPlant = false;
			mainPlant = null;
		}
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
	private void removeGroundPlant() {
		if (groundPlant != null) {
			groundPlant.setLife(0);
			plantedGroundPlant = false;
			groundPlant = null;
		}
	}

	public Plant getGroundPlant() {
		return groundPlant;
	}
	
	public Plant getMainPlant() {
		return mainPlant;
	}
	
	public Plant getSupportPlant() {
		return supportPlant;
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
	private void removeSupportPlant() {
		if (supportPlant != null) {
			supportPlant.setLife(0);
			plantedSupportPlant = false;
			supportPlant = null;
		}
	}

	/**
	 * Return true if a plant is planted, false otherwise
	 */
	public boolean addPlant(Plant plant) {
		if (!crash && !ice) {
			switch (plant.getTypeOfPlant()) {
			case 0:
				return (addGroundPlant(plant));

			case 1:
				return (addMainPlant(plant));

			case 2:
				return (addSupportPlant(plant));
			}

			// On veut que la plante soit l'un des 3 types
			throw new IllegalStateException("La plante est d'un type inconnue");
		}
		return false;
	}

	public void removePlant(Plant plant) {
		switch (plant.getTypeOfPlant()) {
		case 0:
			removeGroundPlant();
			break;

		case 1:
			removeMainPlant();
			break;

		case 2:
			removeSupportPlant();
			break;
		}
	}

	/**
	 * Method that remove all the plant of the cell in one hit (Gargantua,
	 * DoomShroom ...)
	 */
	public void removeAllPlant() {
		removeGroundPlant();
		removeMainPlant();
		removeSupportPlant();
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
		return plantedGroundPlant;
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
	 * Add a good zombie to the cell list of zombie
	 */
	private void addGoodZombie(Zombie z) {
		goodZombiesInCell.add(z);
	}
	
	/**
	 * Add a bad zombie to the cell list of zombie
	 */
	private void addBadZombie(Zombie z) {
		badZombiesInCell.add(z);
	}

	/**
	 * Add a zombie to the cell
	 * 
	 * @param z Zombie you want to add on the cell
	 */
	public void addZombie(Zombie z) {
		if(z.isBad()) {
			addBadZombie(z);
		} else {
			addGoodZombie(z);
		}
	}

	/**
	 * Remove a zombie from the cell list of zombie
	 * 
	 * @param dPe Zombie in the cell
	 */
	public void removeZombie(IEntite z) {
		if(z.isBad()) {
			removebadZombie(z);
		} else {
			removeGoodZombie(z);
		}
	}
	
	/**
	 * Remove a bad zombie from the cell list of zombie
	 * 
	 * @param dPe Zombie in the cell
	 */
	private void removebadZombie(IEntite dPe) {
		badZombiesInCell.remove(dPe);
	}
	
	/**
	 * Remove a bad zombie from the cell list of zombie
	 * 
	 * @param dPe Zombie in the cell
	 */
	private void removeGoodZombie(IEntite dPe) {
		goodZombiesInCell.remove(dPe);
	}

	/**
	 * 
	 * @return The list of bad zombie on the cell
	 */
	public ArrayList<Zombie> getBadZombiesInCell() {
		return new ArrayList<Zombie>(badZombiesInCell);
	}
	
	/**
	 * 
	 * @return The list of good zombie on the cell
	 */
	public ArrayList<Zombie> getGoodZombiesInCell() {
		return new ArrayList<Zombie>(goodZombiesInCell);
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
	 * @return the list of zombie to attack
	 */
	public ArrayList<Zombie> getZombiesToAttack(Zombie z){
		if(z.isBad()) {
			return getGoodZombiesInCell();
		}
		
		return getBadZombiesInCell();
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
	 * @return False if the list of bad zombie is empty, True otherwise
	 */

	public boolean isThereBadZombies() {
		return !badZombiesInCell.isEmpty();
	}
	
	/**
	 * 
	 * @return False if the list of good zombie is empty, True otherwise
	 */

	public boolean isThereGoodZombies() {
		return !goodZombiesInCell.isEmpty();
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
		return isPlantedPlant() || isThereBadZombies();
	}
	
	/**
	 * Return if a cell is leanned
	 */
	public boolean isLeanned() {
		return false;
	}

	@Override
	public String toString() {
		return (isPlantedPlant() == true ? "Il y des plantes, " : "Il n'y a pas de plante, ")
				+ (!badZombiesInCell.isEmpty() ? "Voici les zombies présents \n" + badZombiesInCell + ", "
						: "Il n'y a pas de zombie");
	}

}

package models;

import java.awt.Color;
import java.util.ArrayList;
import models.zombies.Zombie;

public class Cell {
	private Color color; //Later
	private boolean dirt;  //Later
	
	private final ArrayList<Zombie> zombiesInCell;
	private boolean plantedPlant = false;

	public Cell() {
		zombiesInCell = new ArrayList<>();
	}
	
	// Lorsqu'on pose une plante sur la cellule, on indique qu'une plante est posée
	public void addPlant() {
		plantedPlant = true;
	}
	
	// Lorsqu'une plante meurt ou qu'elle se fait deplanter, on indique qu'il n'y a pas de plante
	public void removePlant() {
		plantedPlant = false;
	}
	
	// Renvoie si une plante est plantée sur la cellule
	public boolean isPlantedPlant() {
		return plantedPlant;
	}
	
	public void addZombie(Zombie z) {
		zombiesInCell.add(z);
	}
	
	public void removeZombie(Zombie z) {
		zombiesInCell.remove(z);
	}
	
	public ArrayList<Zombie> getZombiesInCell(){
		return zombiesInCell;
	}
	
	// Renvoie si il y a des zombies sur la case
	public boolean isThereZombies() {
		return !zombiesInCell.isEmpty();
	}
	
	// Renvoie si la cellules contient des entites
	public boolean isCellEmpty() {
		return isPlantedPlant() && isThereZombies();
	}

	public static Cell randomGameCell() {
		return new Cell();
	}

	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return  (plantedPlant == true ? "Il y a une plante, " : "Il n'y a pas de plante, ")
				+ (!zombiesInCell.isEmpty() ? "Voici les zombies présents \n" + zombiesInCell + ", " : "Il n'y a pas de zombie");
	}
}

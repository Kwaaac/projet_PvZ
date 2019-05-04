package models;

import java.awt.Color;
import java.util.ArrayList;
import models.zombies.Zombie;

public class Cell {
	private Color color = Color.decode("#55c920"); 
	private Color colorDarker = Color.decode("#4dba1b"); 
	private boolean dirt;  //Later
	
	private final ArrayList<Zombie> entitiesInCell;
	private boolean plantedPlant = false;

	public Cell() {
		entitiesInCell = new ArrayList<>();
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
	
	public void addEntity(Zombie z) {
		entitiesInCell.add(z);
	}
	
	public void removeEntity(IEntite dPe) {
		entitiesInCell.remove(dPe);
	}
	
	public ArrayList<Zombie> getEntitiesInCell(){
		return entitiesInCell;
	}
	
	// Renvoie si il y a des zombies sur la case
	public boolean isThereEntity() {
		return !entitiesInCell.isEmpty();
	}
	
	// Renvoie si la cellules contient des entites
	public boolean isCellEmpty() {
		return isPlantedPlant() || isThereEntity();
	}

	// Renvoie si la cellules contient des entites
//	public boolean isWater() {
//		return isPlantedLilyPad();
//	}
	
	public static Cell randomGameCell() {
		return new Cell();
	}

	public Color getColor() {
		return color;
	}
	
	public Color getColorDarker() {
		return colorDarker;
	}
	
	@Override
	public String toString() {
		return  (plantedPlant == true ? "Il y a une plante, " : "Il n'y a pas de plante, ")
				+ (!entitiesInCell.isEmpty() ? "Voici les zombies présents \n" + entitiesInCell + ", " : "Il n'y a pas de zombie");
	}
}

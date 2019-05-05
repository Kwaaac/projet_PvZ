package models.cells;

import java.awt.Color;
import java.util.ArrayList;

import models.zombies.Zombie;

public class NightCell extends Cell {
	private Color color = Color.decode("#6025c6"); 
	private Color colorDarker = Color.decode("#5621b2"); 
	
	private boolean plantedPlant = false;

	public NightCell() {
		super();
	}
	
	public Color getColor() {
		return color;
	}
	
	public Color getColorDarker() {
		return colorDarker;
	}
	
}
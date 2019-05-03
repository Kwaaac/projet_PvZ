package models.map;

import java.util.ArrayList;

public abstract class Map {
	private final ArrayList<Integer> MapProperties = new ArrayList<Integer>();
	private final int column;
	private final int line;
	private final int xOrigine;
	private final int yOrigine;
	private final int width;
	private final String backgroundColor;
	


	public Map(int line, int xOrigine, int yOrigine, int width, String backgroundColor) {
		this.column = 8;
		this.line = line;
		this.xOrigine = xOrigine;
		this.yOrigine = yOrigine;
		this.width = width;
		this.backgroundColor = backgroundColor;
	}
	

	public ArrayList<Integer> stuffing(String s) {
		MapProperties.add(column);
		MapProperties.add(line);
		MapProperties.add(xOrigine);
		MapProperties.add(yOrigine);
		MapProperties.add(width);
		return MapProperties;
	}
	
	public ArrayList<Integer> getMapProperties() {
		return MapProperties;
	}
	
	
	
}

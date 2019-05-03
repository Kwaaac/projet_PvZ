package models.map;

import java.util.ArrayList;

public class Pool extends Map{
	private final static ArrayList<Integer> MapProperties = new ArrayList<Integer>();
	private static int column = 8;
	private final static int line = 6;
	private final static int xOrigine = 400;
	private final static int yOrigine = 150;
	private final static int width = 800;
	private final String backgroundColor = "#6B6B6B";
	

	
	public static ArrayList<Integer> getMapProperties() {
		MapProperties.add(line);
		MapProperties.add(column);
		MapProperties.add(xOrigine);
		MapProperties.add(yOrigine);
		MapProperties.add(width);
		return MapProperties;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
}

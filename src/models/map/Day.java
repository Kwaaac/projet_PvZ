package models.map;

import java.util.ArrayList;

public class Day extends Map{
	private final static ArrayList<Integer> MapProperties = new ArrayList<Integer>();
	private static int column = 8;
	private final static int line = 5;
	private final static int xOrigine = 450;
	private final static int yOrigine = 150;
	private final static int width = 900;
	private final String backgroundColor = "#BBBBBB";
	
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

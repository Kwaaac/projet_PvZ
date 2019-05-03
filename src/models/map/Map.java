package models.map;

import java.util.Arrays;

public abstract class Map {
	private final Map[] MapsList = {new Day(), new Night(), new Pool()};
	private final int column;
	private final int line;
	private final int scareSize;
	private final int xOrigine;
	private final int yOrigine;
	private final int width;
	private final String backgroundColor;
	
	public Map(int line, int xOrigine, int yOrigine, int width, String backgroundColor) {
		this.column = 9;
		this.line = line;
		this.scareSize = (int) (width * 1.0 / line);
		this.xOrigine = xOrigine;
		this.yOrigine = yOrigine;
		this.width = width;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public String toString() {
		return "Map [MapsList=" + Arrays.toString(MapsList) + ", column=" + column + ", line=" + line + ", scareSize="
				+ scareSize + ", xOrigine=" + xOrigine + ", yOrigine=" + yOrigine + ", width=" + width
				+ ", backgroundColor=" + backgroundColor + "]";
	}

	public Map getMapProperties(String s) {
		switch(s) {
		case "Day":
			return new Day();
			
		case "Night":
			return new Night();
			
		case "Pool":
			return new Pool();
		}
		return null;
	}
	
	
	
	
	
	
	
}

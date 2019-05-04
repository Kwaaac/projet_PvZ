package models.cells;

import java.awt.Color;

public class GrassCell extends Cell {
	private Color color = Color.decode("#55c920");
	private Color colorDarker = Color.decode("#4dba1b");
	
	public GrassCell() {
		super();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Color getColorDarker() {
		return colorDarker;
	}
	
}

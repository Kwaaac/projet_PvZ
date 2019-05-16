package models.cells;

import java.awt.Color;
import java.util.Objects;

import models.plants.Plant;

public class TileCell extends Cell {
	private final String type = "Tile";
	private Color color = Color.decode("#BC4C29");
	private Color colorDarker = Color.decode("#AB2900");

	public TileCell() {
		super();
	}

	public Color getColor() {
		return color;
	}

	public Color getColorDarker() {
		return colorDarker;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof WaterCell)) {
			return false;
		}
		TileCell wc = (TileCell) o;
		return type.equals(wc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
}
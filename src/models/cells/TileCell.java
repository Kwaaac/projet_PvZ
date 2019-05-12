package models.cells;

import java.awt.Color;
import java.util.Objects;

public class TileCell extends Cell {
	private final String type = "Tile";
	private Color color = Color.decode("#BC4C29");
	private Color colorDarker = Color.decode("#AB2900");

	public TileCell() {
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GrassCell)) {
			return false;
		}
		TileCell tc = (TileCell) o;
		return type.equals(tc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

}

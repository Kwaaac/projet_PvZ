package models.cells;

import java.awt.Color;
import java.util.Objects;

public class GrassCell extends Cell {
	private final String type = "Grass";
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GrassCell)) {
			return false;
		}
		GrassCell gc = (GrassCell) o;
		return type.equals(gc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
	
	@Override
    public String toString() {
        return type + ": " + super.toString();
    }
}

package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import views.BordView;

public class GrassCell extends Cell {
	private final String type = "Grass";
	private Color dayColor = Color.decode("#55c920");
	private Color dayColorDarker = Color.decode("#4dba1b");

	private Color nightColor = Color.decode("#6025c6");
	private Color nightColorDarker = Color.decode("#5621b2");

	public GrassCell(boolean dayTime) {
		super(dayTime);
	}
	
	public GrassCell(boolean dayTime, boolean fog) {
		super(dayTime, fog);
	}
	
	public GrassCell() {
		this(true);
	}

	@Override
	public void drawBoardCell(Graphics2D graphics, float i, float j, int darker, int squareSize, BordView view) {
		if (darker == 0) {

			if (dayTime) {
				graphics.setColor(dayColor);
			} else {
				graphics.setColor(nightColor);
			}

		} else {

			if (dayTime) {
				graphics.setColor(dayColorDarker);
			} else {
				graphics.setColor(nightColorDarker);
			}
		}

		super.drawBoardCell(graphics, i, j, darker, squareSize, view);
	}
	
	@Override
	public void fog(Graphics2D graphics, float i, float j, int squareSize) {
		super.fog(graphics, i, j, squareSize);
	}

	@Override
	public Color getColor() {
		return dayColor;
	}

	@Override
	public Color getColorDarker() {
		return dayColorDarker;
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
		return type;
	}

	@Override
	public boolean isGrass() {
		return true;
	}

	@Override
	public boolean isWater() {
		return false;
	}
}

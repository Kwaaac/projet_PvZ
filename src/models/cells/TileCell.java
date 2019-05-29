package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import models.plants.Plant;

public class TileCell extends Cell {
	private final String type = "Tile";
	private Color dayColor = Color.decode("#BC4C29");
	private Color dayColorDarker = Color.decode("#AB2900");
	
	private Color nightColor = Color.decode("#a03f21");
	private Color nightColorDarker = Color.decode("#7c1e01");

	public TileCell(boolean dayTime) {
		super(dayTime);
	}
	
	public TileCell() {
		this(true);
	}

	@Override
	public void drawBoardCell(Graphics2D graphics, float i, float j, int darker, int squareSize) {
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

		super.drawBoardCell(graphics, i, j, darker, squareSize);
	}

	public Color getColor() {
		return dayColor;
	}

	public Color getColorDarker() {
		return dayColorDarker;
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
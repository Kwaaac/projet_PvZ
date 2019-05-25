package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;

import models.zombies.Zombie;

public class NightCell extends Cell {
	private final String type = "Night";
	private Color color = Color.decode("#6025c6");
	private Color colorDarker = Color.decode("#5621b2");

	private boolean plantedPlant = false;

	@Override
	public void drawBoardCell(Graphics2D graphics, float i, float j, int darker) {
		if (darker == 0) {
			graphics.setColor(color);
		} else {
			graphics.setColor(colorDarker);
		}

		super.drawBoardCell(graphics, i, j, darker);
	}

	public NightCell() {
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
		if (!(o instanceof NightCell)) {
			return false;
		}
		NightCell nc = (NightCell) o;
		return type.equals(nc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

}

package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;

import models.IEntite;
import models.plants.Plant;
import models.zombies.Zombie;

public class WaterCell extends Cell {
	private final String type = "Water";
	private Color color = Color.decode("#0fa5c6");
	private Color colorDarker = Color.decode("#0d9dbc");

	public WaterCell() {
		super();
	}

	@Override
	public void drawBoardCell(Graphics2D graphics, float i, float j, int darker, int squareSize) {
		if (darker == 0) {
			graphics.setColor(color);
		} else {
			graphics.setColor(colorDarker);
		}

		super.drawBoardCell(graphics, i, j, darker, squareSize);
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
		WaterCell wc = (WaterCell) o;
		return type.equals(wc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
}

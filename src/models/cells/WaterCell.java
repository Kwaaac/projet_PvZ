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
	private Color dayColor = Color.decode("#0fa5c6");
	private Color dayColorDarker = Color.decode("#0d9dbc");
	
	private Color nightColor = Color.decode("#0e94b2");
	private Color nightColorDarker = Color.decode("#0c7d96");

	public WaterCell(boolean dayTime) {
		super(dayTime);
	}
	
	public WaterCell() {
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
		WaterCell wc = (WaterCell) o;
		return type.equals(wc.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
}

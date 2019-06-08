package models.cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import views.BordView;

public class WaterCell extends Cell {
	private final String type = "Water";
	private Color dayColor = Color.decode("#0fa5c6");
	private Color dayColorDarker = Color.decode("#0d9dbc");
	
	private Color nightColor = Color.decode("#0e94b2");
	private Color nightColorDarker = Color.decode("#0c7d96");

	public WaterCell(boolean dayTime) {
		super(dayTime);
	}
	
	public WaterCell(boolean dayTime, boolean fog) {
		super(dayTime, fog);
	}
	
	public WaterCell() {
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

	@Override
	public boolean isGrass() {
		return false;
	}

	@Override
	public boolean isWater() {
		return true;
	}
}

package models;

import java.awt.Color;
import java.util.Objects;
import java.util.Random;

public class Cell {
	private Color color;
	private int value;
	private final static Color[] colors = { Color.RED, Color.BLUE };
	private final static Random random = new Random();
	public static final int valueMax = 5;

	public Cell(int i, int value) {
		this.color = colors[Objects.checkIndex(i, colors.length)];
		this.value = Objects.checkIndex(value, valueMax + 1);
	}

	public static Cell randomGameCell() {
		return new Cell(random.nextInt(colors.length), random.nextInt(valueMax + 1));
	}

	public Color getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return color.toString();
	}
}

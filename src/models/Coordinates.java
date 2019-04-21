package models;

import java.util.Objects;

import models.plants.Plant;
import views.BordView;

/**
 * The Coordinates class defines a couple of integers.
 */
public class Coordinates {
	private final int i;
	private final int j;

	/**
	 * Constructs and initializes a couple with the specified coordinates.
	 * @param i the first coordinate
	 * @param j the second coordinate
	 */
	public Coordinates(int i, int j) {
		this.i = i;
		this.j = j;
	}

	/**
	 * The first coordinate of this couple.
	 * @return the first coordinate of this couple.
	 */
	public int getI() {
		return i;
	}

	/**
	 * The second coordinate of this couple.
	 * @return the second coordinate of this couple.
	 */
	public int getJ() {
		return j;
	}
	
	public boolean checkHitBox(Coordinates c) {
			return this.i <= c.i && c.i <= this.j && this.j <= c.j;
	}

	@Override
	public String toString() {
		return "(" + i + "," + j + " )";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinates)) {
			return false;
		}
		Coordinates cc = (Coordinates) o;
		return i == cc.i && j == cc.getJ();
	}

	@Override
	public int hashCode() {
		return Objects.hash(i, j);
	}
	
	public static int CenteredX(float x) {
		int squareSize = BordView.getSquareSize();
		int sizeOfPlant = Plant.getSizeOfPlant();
		int xCentered = (int)(x + (squareSize / 2) - (sizeOfPlant / 2));
		return xCentered;
	}
	
	public static int CenteredY(float y) {
		int squareSize = BordView.getSquareSize();
		int sizeOfPlant = Plant.getSizeOfPlant();
		int yCentered = (int)(y + (squareSize / 2) - (sizeOfPlant / 2));
		return yCentered;
	}
}

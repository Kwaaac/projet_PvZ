package models;

import java.util.Objects;

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
}

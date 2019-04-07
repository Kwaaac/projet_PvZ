package models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.umlv.zen5.ApplicationContext;
import plants.CherryBomb;
import plants.Peashooter;
import plants.Plant;
import plants.WallNut;
import views.BordView;

public class SimpleGameData {
	private final static int score = 5;
	private static int WL = 0; // pour le end menu tkt mgl :v)
	private final Cell[][] matrix;
	private Coordinates selected;
	private final ArrayList<Coordinates> placedPlant = new ArrayList<Coordinates>();

	public SimpleGameData(int nbLines, int nbColumns) {
		matrix = new Cell[nbLines][nbColumns];

	}

	/**
	 * Genere une position aléatoire pour les lignes du plateau
	 * 
	 * @return Ligne du plateau
	 */
	public int RandomPosGenerator(int x) {
		Random random = new Random();
		int pos = random.nextInt(x);
		return pos;
	}

	/**
	 * The number of lines in the matrix contained in this GameData.
	 * 
	 * @return the number of lines in the matrix.
	 */
	public int getNbLines() {
		return matrix.length;
	}

	/**
	 * The number of columns in the matrix contained in this GameData.
	 * 
	 * @return the number of columns in the matrix.
	 */
	public int getNbColumns() {
		return matrix[0].length;
	}

	/**
	 * The color of the cell specified by its coordinates.
	 * 
	 * @param i the first coordinate of the cell.
	 * @param j the second coordinate of the cell.
	 * @return the color of the cell specified by its coordinates
	 */
	public Color getCellColor(int i, int j) {
		return matrix[i][j].getColor();
	}

	/**
	 * The value of the cell specified by its coordinates.
	 * 
	 * @param i the first coordinate of the cell.
	 * @param j the second coordinate of the cell.
	 * @return the value of the cell specified by its coordinates
	 */
	public int getCellValue(int i, int j) {
		return matrix[i][j].getValue();
	}

	/**
	 * The coordinates of the cell selected, if a cell is selected.
	 * 
	 * @return the coordinates of the selected cell; null otherwise.
	 */
	public Coordinates getSelected() {
		return selected;
	}

	/**
	 * Tests if at least one cell is selected.
	 * 
	 * @return true if and only if at least one cell is selected; false otherwise.
	 */
	public boolean hasASelectedCell() {
		return selected != null;
	}

	/**
	 * Selects, as the first cell, the one identified by the specified coordinates.
	 * 
	 * @param i the first coordinate of the cell.
	 * @param j the second coordinate of the cell.
	 * @throws IllegalStateException if a first cell is already selected.
	 */
	public void selectCell(int i, int j) {
		if (selected != null) {
			throw new IllegalStateException("First cell already selected");
		}
		selected = new Coordinates(i, j);
	}

	/**
	 * Unselects both the first and the second cell (whether they were selected or
	 * not).
	 */
	public void unselect() {
		selected = null;
	}

	public void setRandomMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = Cell.randomGameCell();
			}
		}
	}

	/**
	 * Updates the data contained in the GameData.
	 */
	public void updateData() {
		// update (attention traitement different si des cases sont
		// selectionnÃ©es ou non...)
	}

	public void plantOnBoard(int i, int j) {
		placedPlant.add(new Coordinates(i, j));
	}

	public void plantOutBord(int i, int j) {
		placedPlant.remove(new Coordinates(i, j));
	}

	public boolean hasPlant(int i, int j) {
		if(placedPlant.contains(new Coordinates(i, j))) {	
			return true;		
		}
		return false;
	}

	public static boolean win(int x) {
		return x == score;
	}

	public static void setWL(int x) {
		WL = x;
	}

	public static int getWL() {
		return WL;
	}

	public boolean spawnRandomPlant(HashMap<Integer, Integer> possibilityX, HashMap<Integer, Integer> possibilityY,
			ArrayList<Plant> MyPlants, BordView view, ApplicationContext context) {

		boolean result = false;

		int target = this.RandomPosGenerator(20); // timer
		int spawnRate2 = 1; // timer
		int xRandomPosition = this.RandomPosGenerator(8); // random position x dans matrice
		int yRandomPosition = this.RandomPosGenerator(5); // random position y dans matrice
		int randomPlantType = this.RandomPosGenerator(3); // random type plant

		if (spawnRate2 == target) {

			if (randomPlantType == 0) {

				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawPeashooter(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#90D322");

				MyPlants.add(new Peashooter(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
			}

			if (randomPlantType == 1) {
				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawCherryBomb(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#CB5050");

				MyPlants.add(new CherryBomb(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
			}

			if (randomPlantType == 2) {
				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawWallNut(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#ECB428");

				MyPlants.add(new WallNut(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
			}

		}

		return result;
	}

}

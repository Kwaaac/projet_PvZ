package models;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import fr.umlv.zen5.ApplicationContext;
import plants.CherryBomb;
import plants.Peashooter;
import plants.Plant;
import plants.Projectile;
import plants.WallNut;
import views.BordView;
import zombies.Zombie;

public class SimpleGameData {
	private final static int score = 5;
	private static int WL = 0;
	private final Cell[][] matrix;
	private Coordinates selected;
	private final ArrayList<Coordinates> placedPlant = new ArrayList<Coordinates>();

	public SimpleGameData(int nbLines, int nbColumns) {
		matrix = new Cell[nbLines][nbColumns];

	}

	/**
	 * Genere une position al�atoire pour les lignes du plateau
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
		// selectionnées ou non...)
	}

	public void plantOnBoard(int i, int j) {
		placedPlant.add(new Coordinates(i, j));
	}

	public void plantOutBord(int i, int j) {
		placedPlant.remove(new Coordinates(i, j));
	}

	public boolean hasPlant(int i, int j) {
		if (placedPlant.contains(new Coordinates(i, j))) {
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
			ArrayList<Plant> myPlants, BordView view, ApplicationContext context) {

		boolean result = false;

		int target = this.RandomPosGenerator(20); // timer
		int spawnRate2 = 1; // timer
		int xRandomPosition = this.RandomPosGenerator(8); // random position x dans matrice
		int yRandomPosition = this.RandomPosGenerator(5); // random position y dans matrice
		int randomPlantType = this.RandomPosGenerator(3); // random type plant

		if (spawnRate2 == target
				&& !(this.hasPlant(view.lineFromY(yRandomPosition), view.columnFromX(xRandomPosition)))) {

			switch (randomPlantType) {

			case 0:

				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawPeashooter(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#90D322");

				myPlants.add(new Peashooter(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
				break;

			case 1:

				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawCherryBomb(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#CB5050");

				myPlants.add(new CherryBomb(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
				break;

			case 2:

				this.plantOnBoard(yRandomPosition, xRandomPosition);

				view.drawWallNut(context, this, possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition),
						"#ECB428");

				myPlants.add(new WallNut(possibilityX.get(xRandomPosition), possibilityY.get(yRandomPosition)));

				result = true;
				break;

			}

		}

		return result;
	}

	public void actionning(ArrayList<Plant> myPlants, ArrayList<Projectile> myBullet, BordView view,
			ArrayList<Zombie> myZombies) {

		for (IPlant p : myPlants) {
			 p.action(myBullet, view, myZombies);
		}

	}
	
	public void leMove(ApplicationContext context, BordView view, ArrayList<Zombie> myZombies, ArrayList<Projectile> myBullet) {
		
		for (Zombie z : myZombies) {
			if (z.getSpeed()) {
				view.moveAndDrawElement(context, this, z);
			}
		}

		for (Projectile b : myBullet) {
			view.moveAndDrawElement(context, this, b);
		}
	}
	
	public static void timeEnd(ArrayList<Zombie> myZombies,Temporal time, StringBuilder str, ApplicationContext context, int deathCounterZombie, String mdp) {
		int xOrigin = 450;
		int squareSize = BordView.getSquareSize();
		String choice = null, finalChoice = null;
		int h = 0, m = 0, s = 0;
		
		
		for (Entities z : myZombies) {
			if (((Zombie) z).isEatingBrain(xOrigin, squareSize)) {
				choice = "Stop";
				finalChoice = "Win";
			}
		}
		
		if (SimpleGameData.win(deathCounterZombie)) {
			choice = "Stop";
			finalChoice = "Loose";
		}
		
		if (mdp == "SPACE") {
			choice = "Stop";
			finalChoice = "Stop";
		}
		
		switch (choice) {
			case "Continue":
				break;
			case "Stop":
				Duration timeEnd = Duration.between(time, Instant.now());
				h = (int) (timeEnd.getSeconds() / 3600);
				m = (int) ((timeEnd.getSeconds() % 3600) / 60);
				s = (int) (((timeEnd.getSeconds() % 3600) % 60));
				switch (finalChoice) {
					case "Win":
						str.append("-+-+-+-+-+-+-+-+-+-\nVous avez gagne!!!\nLa partie a duree : " + h + " heure(s) " + m
								+ " minute(s) " + s + " seconde(s)");
						break;
					case "Loose":
						str.append("-+-+-+-+-+-+-+-+-+-\nVous avez perdu...\nLa partie a duree : " + h + " heure(s) " + m
								+ " minute(s) " + s + " seconde(s)");
						break;
					case "Stop":
						str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\nLa partie a duree : " + h
								+ " heure(s) " + m + " minute(s) " + s + " seconde(s)");
						break;
				}
				System.out.println(str.toString());
				SimpleGameData.setWL(0);
				context.exit(0);
		}
	}
}

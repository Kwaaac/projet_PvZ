package models;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fr.umlv.zen5.ApplicationContext;
import models.plants.IPlant;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;
import views.SimpleGameView;

public class SimpleGameData {
	private final static int score = 5;
	private static int WL = 0;
	private final Cell[][] matrix;
	private Coordinates selected;
	private final ArrayList<Coordinates> placedPlant = new ArrayList<Coordinates>();
	private final ArrayList<Plant> myPlants = new ArrayList<>();

	private static long spawnTime;
	private static long timeLimit;
	private static long difficultyTime;

	private static int difficulty = 1;
	private static int superWave = 0;

	public SimpleGameData(int nbLines, int nbColumns) {
		matrix = new Cell[nbLines][nbColumns];

		spawnTime = System.currentTimeMillis();
		timeLimit = 5_000;

		difficultyTime = System.currentTimeMillis();
	}
	
	

	public ArrayList<Plant> getMyPlants() {
		return myPlants;
	}

	/**
	 * Genere un nombre aléatoire entre 0 et x
	 * 
	 * @return Ligne du plateau
	 */
	public int RandomPosGenerator(int valeurMax) {
		Random random = new Random();
		return random.nextInt(valeurMax);
	}

	public int RandomPosGenerator(int valeurMin, int valeurMax) {
		Random r = new Random();
		return valeurMin + r.nextInt(valeurMax - valeurMin);
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

	private boolean isCorrectBordLocation(BordView view, float x, float y) {

		int xOrigin = view.getXOrigin();
		int yOrigin = view.getYOrigin();
		int squareSize = BordView.getSquareSize();

		if (xOrigin <= x && x <= xOrigin + squareSize * this.getNbColumns()) {
			return yOrigin <= y && y <= yOrigin + squareSize * this.getNbLines();
		}

		return false;
	}
	
	private boolean isCorrectSelectLocation(SelectBordView view, float x, float y) {

		int xOrigin = view.getXOrigin();
		int yOrigin = view.getYOrigin();
		int squareSize = SelectBordView.getSquareSize();

		if (xOrigin <= x && x <= xOrigin + squareSize * this.getNbColumns()) {
			return yOrigin <= y && y <= yOrigin + squareSize * this.getNbLines();
		}

		return false;
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

	public void actionning(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies) {

		for (IPlant p : myPlants) {
			p.action(myBullet, view, myZombies);
		}

	}

	public void movingZombiesAndBullets(ApplicationContext context, BordView view, ArrayList<Zombie> myZombies,
			ArrayList<Projectile> myBullet) {

		for (Zombie z : myZombies) {
			view.moveAndDrawElement(context, this, z);
			z.setCase(z.x, z.y);
		}

		for (Projectile b : myBullet) {
			view.moveAndDrawElement(context, this, b);
		}
	}

	public static void timeEnd(ArrayList<Zombie> myZombies, Temporal time, StringBuilder str,
			ApplicationContext context, int deathCounterZombie, String mdp) {

		int xOrigin = 450;
		int squareSize = BordView.getSquareSize();
		String choice = "Continue", finalChoice = null;
		int h = 0, m = 0, s = 0;

		for (Zombie z : myZombies) {
			if (z.isEatingBrain(xOrigin, squareSize)) {
				choice = "Stop";
				finalChoice = "Loose";
			}
		}

		if (SimpleGameData.win(deathCounterZombie)) {
			choice = "Stop";
			finalChoice = "Win";
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
			s = (int) timeEnd.getSeconds();
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
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\nLa partie a duree : " + h + " heure(s) "
						+ m + " minute(s) " + s + " seconde(s)");
				break;
			}
			System.out.println(str.toString());
			SimpleGameData.setWL(0);
			context.exit(0);

		}
	}

	public void planting(ApplicationContext context, SimpleGameData dataSelect, BordView view, SelectBordView psView,
			float x, float y) {

		if (this.isCorrectBordLocation(view, x, y) && dataSelect.hasASelectedCell()) {

			this.selectCell(view.lineFromY(y), view.columnFromX(x));

			Coordinates Scell = dataSelect.getSelected();
			Coordinates cell = this.getSelected();

			int p = Scell.getI();

			int i = cell.getI();
			int j = cell.getJ();

			int x2 = Coordinates.CenteredX(view.realCoordFromIndex(j, view.getXOrigin()));
			int y2 = Coordinates.CenteredY(view.realCoordFromIndex(i, view.getYOrigin()));

			if (!this.hasPlant(i, j)) {
				this.plantOnBoard(i, j);
				myPlants.add(psView.getSelectedPlants()[p].createAndDrawNewPlant(view, context, x2, y2));
			}

		}
	}

	public void selectingCellAndPlanting(ApplicationContext context, SimpleGameData dataSelect, BordView view,
			SelectBordView plantSelectionView, float x, float y) {
		if (!this.hasASelectedCell()) {

			this.planting(context, dataSelect, view, plantSelectionView, x, y);

		} else {
			this.unselect();
		}

		if (!dataSelect.hasASelectedCell()) {

			if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)) {
				dataSelect.selectCell(plantSelectionView.lineFromY(y), plantSelectionView.columnFromX(x));
			}

		} else {
			dataSelect.unselect();
		}
	}

	public boolean spawnRandomPlant(ApplicationContext context, SimpleGameData dataSelect, BordView view,
			SelectBordView plantSelectionView, Plant[] selectedPlant) {

		boolean result = false;

		int target = this.RandomPosGenerator(15); // 1 chance sur x
		int xRandomPosition = this.RandomPosGenerator(view.getXOrigin(), view.getLength()); // random position x dans
																							// matrice
		int yRandomPosition = this.RandomPosGenerator(view.getYOrigin(), view.getWidth()); // random position y dans
																							// matrice
		int randomPlantType = this.RandomPosGenerator(selectedPlant.length); // random type plant

		if (target == 1) {
			result = true;
			if (!dataSelect.hasASelectedCell()) {
				dataSelect.selectCell(randomPlantType, 0);

			} else {
				dataSelect.unselect();
				dataSelect.selectCell(randomPlantType, 0);
			}

			this.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, xRandomPosition,
					yRandomPosition);
		}

		return result;
	}

	public static void updateDifficulty() {
		if (System.currentTimeMillis() - difficultyTime >= 15_000) {
			difficulty += 1;

			difficultyTime = System.currentTimeMillis();
		}
	}

	public static void spawnNormalWave(SimpleGameData dataBord, int squareSize, StringBuilder str,
			ArrayList<Zombie> myZombies, BordView view, ApplicationContext context,
			HashMap<Zombie, Integer> zombieList) {

		if (System.currentTimeMillis() - spawnTime >= timeLimit) {

			int sqrS = BordView.getSquareSize();

			int endWave = 0;
			int x = view.getXOrigin() + view.getWidth();
			int y = view.getYOrigin() + dataBord.RandomPosGenerator(5) * sqrS + (sqrS / 2)
					- (Zombie.getSizeOfZombie() / 2);
			ArrayList<Zombie> zombieAvailable = new ArrayList<>();
			ArrayList<Integer> probList = new ArrayList<>();

			for (Map.Entry<Zombie, Integer> entry : zombieList.entrySet()) {
				Zombie z = entry.getKey();
				Integer spawn = entry.getValue();

				if (spawn == 0) {
					endWave += 1;
				}

				if (z.canSpawn(difficulty) && spawn > 0) {
					probList.add(dataBord.RandomPosGenerator(z.getProb(difficulty)));
					zombieAvailable.add(z);

					entry.setValue(spawn - 1);
				}
			}

			if (!probList.isEmpty()) {
				int valeurSympa = probList.get(0), selecteur = 0;

				for (int i = 0; i != probList.size(); i++) {
					if (probList.get(i) < valeurSympa) {
						valeurSympa = probList.get(i);
						selecteur = i;
					}
				}
				
				myZombies.add(zombieAvailable.get(selecteur).createAndDrawNewZombie(view, context, x, y));
				str.append("new " + zombieAvailable.get(selecteur) + new SimpleDateFormat("hh:mm:ss").format(new Date())
						+ ")\n");

			} else {
				if (endWave == zombieList.size() && myZombies.isEmpty()) {
					superWave = 1;
				}
			}

			if (timeLimit >= 4000) {
				timeLimit -= 1000;

				spawnTime = System.currentTimeMillis();
			} else {
				spawnTime = System.currentTimeMillis();
			}

			updateDifficulty();
			
		}
	}

	public static void spawnSuperWave(SimpleGameData dataBord, int squareSize, StringBuilder str,
			ArrayList<Zombie> myZombies, BordView view, ApplicationContext context,
			HashMap<Zombie, Integer> zombieList) {

		int sqrS = BordView.getSquareSize();
		int endWave = 0;
		int x = view.getXOrigin() + view.getWidth();
		

		for (Map.Entry<Zombie, Integer> entry : zombieList.entrySet()) {
			Zombie z = entry.getKey();
			Integer spawn = entry.getValue();
			
			int y = view.getYOrigin() + dataBord.RandomPosGenerator(5) * sqrS + (sqrS / 2) - (Zombie.getSizeOfZombie() / 2);
			
			if (spawn == 0) {
				endWave += 1;
			}
			
			if (spawn > 0) {
				myZombies.add(z.createAndDrawNewZombie(view, context, x, y));
				str.append("new " + z + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");

				entry.setValue(spawn - 1);
			}
		}
		
		if(endWave == zombieList.size()) {
			superWave = 2;
		}

	}

	public static void spawnZombies(SimpleGameData dataBord, int squareSize, StringBuilder str,
			ArrayList<Zombie> myZombies, BordView view, ApplicationContext context, HashMap<Zombie, Integer> zombieList,
			HashMap<Zombie, Integer> superZombieList) {

		if (superWave == 0) {
			spawnNormalWave(dataBord, squareSize, str, myZombies, view, context, zombieList);
		} else if (superWave == 1) {
			
			spawnSuperWave(dataBord, squareSize, str, myZombies, view, context, superZombieList);

		}

	}

}

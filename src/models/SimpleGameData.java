package models;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import controlers.EndController;
import fr.umlv.zen5.ApplicationContext;
import models.cells.Cell;
import models.cells.WaterCell;
import models.cells.GrassCell;
import models.cells.NightCell;
import models.plants.IPlant;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import models.zombies.IZombie;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;

public class SimpleGameData {
	private static int WL = 0;
	private final Cell[][] matrix;
	private final int nbLines;
	private final int nbColumns;
	private Coordinates selected;
	private final ArrayList<Coordinates> placedPlant = new ArrayList<Coordinates>();
	private final ArrayList<Plant> myPlants = new ArrayList<>();
	private final static ArrayList<Soleil> mySun = new ArrayList<>();
	private ArrayList<Zombie> zombieInQueu = new ArrayList<>();// Dancing Zombie

	private static boolean stop = false;

	private static long spawnTime;
	private static long timeLimit;
	private static long difficultyTime;

	private static int difficulty = 1;
	private static int superWave = 0;

	private static int actualMoney = 0;
	private Chrono sunSpawn = new Chrono();

	static Chrono time = new Chrono();

	private static String map = "";

	public SimpleGameData(int nbLines, int nbColumns) {
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;

		matrix = new Cell[nbLines][nbColumns];

		createBord(map);

		// Spawn des zombies et leurs limite de temps avant spawn
		spawnTime = System.currentTimeMillis();
		timeLimit = 5_000;

		// Temps pour augmenter la difficulté
		difficultyTime = System.currentTimeMillis();

		// Temps du spawn des soleil
		sunSpawn.start();

		// Temps du jeu
		time.start();
	}

	private void dayBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new GrassCell();
			}
		}
	}

	private void poolBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (i == 2 || i == 3) {
					matrix[i][j] = new WaterCell();
				} else {
					matrix[i][j] = new GrassCell();
				}
			}
		}
	}

	private void NightBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new NightCell();
			}
		}
	}

	private void createBord(String map) {
		if (map == "Day") {
			dayBord();
		} else if (map == "Pool") {
			poolBord();
		} else if (map == "Night") {
			NightBord();
		}

	}

	/**
	 * Renvoie une cellule du plateau sinon renvoie null si la cellule n'existe pas
	 * 
	 * @param y La ligne du plateau
	 * @param x La colonne du plateau
	 * @return La cellule correspondantes au coordonnï¿½es
	 * 
	 */
	public Cell getCell(int y, int x) {
		if (y >= 0 && y < nbLines && x >= 0 && x < nbColumns) {
			return matrix[y][x];
		}

		return null;
	}

	/**
	 * Allow you to get the y line starting at the x column (start from the left
	 * side end at the right side)
	 * 
	 * @param x The starting column
	 * @param y The line
	 * @return Return an ArrayList<Cell> of cells from the y line, starting at the x
	 *         column and ending to the right side of the bord
	 */

	public ArrayList<Cell> getLineCell(int y, int x) {
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i = x; i < nbColumns; i++) {
			cells.add(getCell(y, i));
		}

		return cells;
	}

	/**
	 * Allow you to get the y line starting at the x column (start from the left
	 * side to the max define)
	 * 
	 * @param x   The starting column
	 * @param y   The line
	 * @param max The
	 * @return Return an ArrayList<Cell> of cells from the y line, starting at the x
	 *         column and ending to the right side of the bord
	 */

	public ArrayList<Cell> getLineCell(int y, int x, int max) {
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i = x; i < max; i++) {
			cells.add(getCell(y, i));
		}

		return cells;
	}

	public ArrayList<Plant> getMyPlants() {
		return myPlants;
	}

	/**
	 * Genere un nombre alï¿½atoire entre 0 et x
	 * 
	 * @return Ligne du plateau
	 */
	public int RandomPosGenerator(int valeurMax) {
		Random random = new Random();
		return random.nextInt(valeurMax);
	}

	public static int RandomPosGenerator(int valeurMin, int valeurMax) {
		Random r = new Random();
		return valeurMin + r.nextInt(valeurMax - valeurMin);
	}

	public void gameStop() {
		stop = true;
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

	public boolean isCorrectBordLocation(BordView view, float x, float y) {

		int xOrigin = view.getXOrigin();
		int yOrigin = view.getYOrigin();
		int squareSize = BordView.getSquareSize();

		if (xOrigin <= x && x <= xOrigin + squareSize * this.getNbColumns()) {
			return yOrigin <= y && y <= yOrigin + squareSize * this.getNbLines();
		}

		return false;
	}

	public boolean isCorrectSelectLocation(SelectBordView view, float x, float y) {

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

	public static boolean win(HashMap<Zombie, Integer> superWaveZombie, ArrayList<Zombie> myZombies) {
		for (int nbrZ : superWaveZombie.values()) {
			if (nbrZ != 0) {
				return false;
			}
		}

		return myZombies.isEmpty();
	}

	public static void setWL(int x) {
		WL = x;
	}

	public static int getWL() {
		return WL;
	}

	public void actionning(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			ArrayList<LawnMower> myLawnMower) {

		for (IPlant p : myPlants) {
			p.action(myBullet, view, myZombies, this);
		}

	}

	public void actionningZombie(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
		System.out.println(myZombies);
		for (IZombie z : myZombies) {

			z.action(view, dataBord, myZombies);
		}

		if (zombieInQueu.size() > 0) {
			myZombies.addAll(zombieInQueu);
			zombieInQueu = new ArrayList<Zombie>();
		}

	}

	public static void spawnSun(BordView view, float x, float y, int sunny, int size) {
		if (x == -1) {
			if (SimpleGameData.getMap() != "Night") {
				int xOrigin = view.getXOrigin();

				float xRandom = RandomPosGenerator(xOrigin, xOrigin + view.getLength());

				mySun.add(new Soleil(xRandom, 0, 1.5, 25, 85));
			}
		} else {
			mySun.add(new Soleil(x, y, 0, sunny, size));
		}
	}

	public void naturalSun(BordView view) {
		if (sunSpawn.asReachTimer(5)) {
			spawnSun(view, -1, -1, -1, -1);
		}
	}

	public boolean movingZombiesAndBullets(ApplicationContext context, BordView view, ArrayList<Zombie> myZombies,
			ArrayList<Projectile> myBullet, ArrayList<LawnMower> myLawnMower, boolean debug, boolean debuglock) {

		for (Zombie z : myZombies) {
			if (debug == true) {
				z.SpeedBoostON();
				debuglock = true;
			}
			if (debug == false && debuglock == true) {
				z.SpeedBoostOFF();
			}
			view.moveAndDrawElement(context, this, z);
			z.setCase(this);
		}

		for (Projectile b : myBullet) {
			if (debug == true) {
				b.SpeedBoostON();
				debuglock = true;
			}
			if (debug == false && debuglock == true) {
				b.SpeedBoostOFF();
			}
			view.moveAndDrawElement(context, this, b);
		}

		for (LawnMower l : myLawnMower) {

			view.moveAndDrawElement(context, this, l);
			l.setCase(this);
		}

		for (Soleil s : mySun) {
			view.moveAndDrawElement(context, this, s);
			s.setCase();
		}
		return debuglock = false;
	}

	public static void timeEnd(ArrayList<Zombie> myZombies, StringBuilder str, ApplicationContext context,
			HashMap<Zombie, Integer> superWaveZombie, BordView view, ArrayList<LawnMower> myLawnMower) {

		int xOrigin = view.getXOrigin();
		int squareSize = BordView.getSquareSize();
		String choice = "Continue", finalChoice = null;

		for (Zombie z : myZombies) {
			if (z.isEatingBrain(xOrigin, squareSize)) {
				choice = "Stop";
				finalChoice = "Loose";
			}
		}

		if (SimpleGameData.win(superWaveZombie, myZombies)) {
			choice = "Stop";
			finalChoice = "Win";
		}

		if (stop) {
			choice = "Stop";
			finalChoice = "Stop";
		}

		switch (choice) {
		case "Continue":
			break;
		case "Stop":
			time.stop();
			switch (finalChoice) {
			case "Loose":
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez perdu...\n");
				SimpleGameData.setWL(0);
				break;
			case "Win":
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez gagne!!!\n");
				SimpleGameData.setWL(1);
				break;
			case "Stop":
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\n");
				SimpleGameData.setWL(2);
				break;
			}
			str.append("La partie ï¿½ durï¿½e: " + time.getDureeTxt());
			System.out.println(str.toString());
			EndController.endGame(context);

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

			Cell actualCell = this.getCell(i, j);

			Plant actualPlant = psView.getSelectedPlants().get(p);

			if (actualCell.addPlant(actualPlant)) {
				myPlants.add(actualPlant.createAndDrawNewPlant(view, context, x2, y2));
				actualMoney -= psView.getSelectedPlants().get(p).getCost();
				System.out.println("Vous avez " + actualMoney);
				psView.startCooldown(p);
			}

		}
	}

	public void selectingCellAndPlanting(ApplicationContext context, SimpleGameData dataSelect, BordView view,
			SelectBordView plantSelectionView, float x, float y) {
		if (!this.hasASelectedCell()) {

			if (!clickSun(x, y)) {
				this.planting(context, dataSelect, view, plantSelectionView, x, y);
			}

		} else {
			this.unselect();
		}

		if (!dataSelect.hasASelectedCell()) {
			if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)
					&& plantSelectionView.isThisChronoReset(y, view.getYOrigin()) && actualMoney >= plantSelectionView
							.getSelectedPlants().get(plantSelectionView.lineFromY(y)).getCost()) {
				dataSelect.selectCell(plantSelectionView.lineFromY(y), plantSelectionView.columnFromX(x));
			}

		} else {
			dataSelect.unselect();
			if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)
					&& plantSelectionView.isThisChronoReset(y, view.getYOrigin()) && actualMoney >= plantSelectionView
							.getSelectedPlants().get(plantSelectionView.lineFromY(y)).getCost()) {
				dataSelect.selectCell(plantSelectionView.lineFromY(y), plantSelectionView.columnFromX(x));
			}
		}
	}

	private boolean clickSun(float x, float y) {
		boolean res = false;
		int position = -1;

		for (int i = 0; i < mySun.size(); i++) {
			if (mySun.get(i).isClicked(x, y)) {
				res = true;
				position = i;
				break;
			}
		}

		if (res) {
			actualMoney += mySun.remove(position).getSunny();
			System.out.println("Vous avez " + actualMoney);
		}

		return res;
	}

	public boolean spawnRandomPlant(ApplicationContext context, SimpleGameData dataSelect, BordView view,
			SelectBordView plantSelectionView, ArrayList<Plant> selectedPlant) {

		boolean result = false;

		int target = this.RandomPosGenerator(15); // 1 chance sur x
		int xRandomPosition = SimpleGameData.RandomPosGenerator(view.getXOrigin(), view.getLength()); // random position
																										// x dans
		// matrice
		int yRandomPosition = SimpleGameData.RandomPosGenerator(view.getYOrigin(), view.getWidth()); // random position
																										// y dans
		// matrice
		int randomPlantType = this.RandomPosGenerator(selectedPlant.size()); // random type plant

		if (target == 1 && actualMoney >= plantSelectionView.getSelectedPlants().get(randomPlantType).getCost()) {
			result = true;
			if (!dataSelect.hasASelectedCell()) {
				dataSelect.selectCell(randomPlantType, 0);

			} else {
				dataSelect.unselect();
				dataSelect.selectCell(randomPlantType, 0);
			}

			this.selectingCellAndPlanting(context, dataSelect, view, plantSelectionView, xRandomPosition,
					yRandomPosition);
		} else {
			actualMoney += 1;
		}

		return result;
	}

	public static void updateDifficulty() {
		if (System.currentTimeMillis() - difficultyTime >= 15_000) {
			difficulty += 1;

			difficultyTime = System.currentTimeMillis();
		}
	}

	public void spawnLawnMower(BordView view, ApplicationContext context, ArrayList<LawnMower> myLawnMower) {
		int staticX = view.getXOrigin() - BordView.getSquareSize();
		for (int i = 1; i <= nbLines; i++) {
			myLawnMower.add(new LawnMower(staticX, BordView.getSquareSize() * i, i - 1));
			view.drawLawnMower(context, staticX, BordView.getSquareSize() * i, "#B44A4A");
		}
	}

	private static Zombie getRandomZombie(ArrayList<Zombie> Mz) {
		Random rand = new Random();
		return Mz.get(rand.nextInt(Mz.size()));
	}

	private static void addInMap(HashMap<Zombie, Integer> zombiesMap, Zombie z) {
		Integer count = zombiesMap.get(z);

		if (count == null) {
			count = 0;
		}

		zombiesMap.put(z, count + 1);

	}

	public static HashMap<Zombie, Integer> generateZombies(int type) {

		ArrayList<Zombie> allZombies = Zombie.getZombieList(getMap());

		HashMap<Zombie, Integer> zombiesMap = new HashMap<Zombie, Integer>();
		int waveSize = 0;
		int maxWaveSize = (type == 1 ? 15 : 35);
		while (waveSize < maxWaveSize) {
			Random rand = new Random();
			Zombie choosenOne = getRandomZombie(allZombies);
			if ((choosenOne.getThreat() * 100) < rand.nextInt(500)) {
				addInMap(zombiesMap, choosenOne);
				waveSize++;
			}
		}
		return zombiesMap;
	}

	public static void spawnNormalWave(SimpleGameData dataBord, int squareSize, StringBuilder str,
			ArrayList<Zombie> myZombies, BordView view, ApplicationContext context,
			HashMap<Zombie, Integer> zombieList) {

		if (System.currentTimeMillis() - spawnTime >= timeLimit) {

			int sqrS = BordView.getSquareSize();

			int endWave = 0;
			int x = view.getXOrigin() + view.getWidth();
			int y = view.getYOrigin() + dataBord.RandomPosGenerator(dataBord.getNbLines()) * sqrS + (sqrS / 2)
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

					System.out.println(z);

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
				Zombie z = zombieAvailable.get(selecteur);

				zombieList.put(z, zombieList.get(z) - 1);
				if (map == "Pool") {
					if (z.isCommon()) {
						System.out.println(view.indexFromReaCoord(y, view.getYOrigin()));
						while(view.indexFromReaCoord(y, view.getYOrigin()) == 2 || view.indexFromReaCoord(y, view.getYOrigin()) == 3) {
							y = view.getYOrigin() + dataBord.RandomPosGenerator(dataBord.getNbLines()) * sqrS + (sqrS / 2);
						}
						myZombies.add(z.createAndDrawNewZombie(view, context, x, y));

					} else {
						y = view.getYOrigin() + SimpleGameData.RandomPosGenerator(2, 4) * sqrS + (sqrS / 2);
						myZombies.add(z.createAndDrawNewZombie(view, context, x, y));
					}
				} else {
					
					myZombies.add(z.createAndDrawNewZombie(view, context, x, y));
				}
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

			int y = view.getYOrigin() + dataBord.RandomPosGenerator(dataBord.getNbLines()) * sqrS + (sqrS / 2)
					- (Zombie.getSizeOfZombie() / 2);

			if (spawn == 0) {
				endWave += 1;
			}

			if (spawn > 0) {
				if (map == "Pool") {
					if (z.isCommon()) {
						while(view.indexFromReaCoord(y, view.getYOrigin()) == 2 || view.indexFromReaCoord(y, view.getYOrigin()) == 3) {
							y = view.getYOrigin() + dataBord.RandomPosGenerator(dataBord.getNbLines()) * sqrS + (sqrS / 2);
						}
						myZombies.add(z.createAndDrawNewZombie(view, context, x, y));

					} else {
						y = view.getYOrigin() + SimpleGameData.RandomPosGenerator(2, 4) * sqrS + (sqrS / 2);
						myZombies.add(z.createAndDrawNewZombie(view, context, x, y));
					}
				} else {
					
					myZombies.add(z.createAndDrawNewZombie(view, context, x, y));
				}
				str.append("new " + z + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");

				entry.setValue(spawn - 1);
			}
		}

		if (endWave == zombieList.size()) {
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

	public static void setMap(String mape) {
		map = mape;
	}

	public static String getMap() {
		return map;
	}

	public static int getActualMoney() {
		return actualMoney;
	}

	public void setZombieInQueu(ArrayList<Zombie> zombieList) {
		zombieInQueu = zombieList;
	}

}

package models;

import java.awt.Color;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controlers.EndController;
import fr.umlv.zen5.ApplicationContext;
import models.cells.Cell;
import models.cells.GrassCell;
import models.cells.TileCell;
import models.cells.WaterCell;
import models.plants.IPlant;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import models.zombies.IZombie;
import models.zombies.Zombie;
import views.BordView;
import views.SelectBordView;

public class SimpleGameData implements Serializable {
	private static int WL = 0;
	private Cell[][] matrix;
	private final int nbLines;
	private final int nbColumns;
	private Coordinates selected;
	private final ArrayList<Coordinates> placedPlant = new ArrayList<Coordinates>();
	private final ArrayList<Plant> myPlants = new ArrayList<>();
	private final ArrayList<Soleil> mySun = new ArrayList<>();
	private ArrayList<Zombie> zombieInQueu = new ArrayList<>();// Dancing Zombie
	private final ArrayList<Zombie> myZombies = new ArrayList<>();
	private final ArrayList<Projectile> myBullet = new ArrayList<>();
	private final ArrayList<LawnMower> myLawnMower = new ArrayList<>();
	private final ArrayList<Tombstone> myTombstone = new ArrayList<>();

	private boolean stop = false;

	private long spawnTime;
	private long timeLimit;
	private long difficultyTime;

	private int difficulty = 1;
	private int superWave = 0;

	private int actualMoney = 999;
	private int actualfertilizer = 3;
	private int fertilizerChance = 10;
	private final int nbTombstone = 4;
	private Chrono sunSpawn = new Chrono();

	static Chrono time = new Chrono();

	private static String map;
	private static String dayTime = "Day";
	private String loadChoice = "start";

	public SimpleGameData(int nbLines, int nbColumns) {
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;

		matrix = new Cell[nbLines][nbColumns];

		createBord(map);

		// Spawn des zombies et leurs limite de temps avant spawn
		spawnTime = System.currentTimeMillis();
		timeLimit = 5_000;

		// Temps pour augmenter la difficult�
		difficultyTime = System.currentTimeMillis();

		// Temps du spawn des soleil
		sunSpawn.start();

		// Temps du jeu
		time.start();
	}
	
	public SimpleGameData() {
		this.nbLines = 1;
		this.nbColumns = 1;
		
		// Spawn des zombies et leurs limite de temps avant spawn
		spawnTime = System.currentTimeMillis();
		timeLimit = 5_000;

		// Temps pour augmenter la difficult�
		difficultyTime = System.currentTimeMillis();

		// Temps du spawn des soleil
		sunSpawn.start();

		// Temps du jeu
		time.start();
	}
	
	public SimpleGameData(int nbLines, int nbColumns, int con) {
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;

		matrix = new Cell[nbLines][nbColumns];
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("---SIMPLE_GAME_DATA---\n");
		str.append("nbLines = " + nbLines + "\n");
		str.append("nbColumns = " + nbColumns + "\n");
		str.append("selected = " + selected + "\n");
		str.append("map = " + map + "\n");
		str.append("difficulty = " + difficulty + "\n");
		str.append("actualMoney = " + actualMoney + "\n");
		str.append("actualfertilizer = " + actualfertilizer + "\n");
		str.append("placedPlant = " + placedPlant + "\n");
		str.append("myPlants = " + myPlants + "\n");
		str.append("mySun = " + mySun + "\n");
		str.append("zombieInQueu = " + zombieInQueu + "\n");
		str.append("myZombies = " + myZombies + "\n");
		str.append("myBullet = " + myBullet + "\n");
		str.append("myLawnMower = " + myLawnMower);
		return str.toString();
	}

	private void dayBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new GrassCell(true);
			}
		}
	}

	private void poolBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (i == 2 || i == 3) {
					matrix[i][j] = new WaterCell(true);
				} else {
					matrix[i][j] = new GrassCell(true);
				}
			}
		}
	}

	private void NightBord() {
		System.out.println("con" + matrix.length);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new GrassCell(false);
			}
			System.out.println(matrix[i]);
			Tombstone t = Tombstone.createTombstone(i,4);
			myTombstone.add(t);
			matrix[i][4].addTombstone(t);
			
		}
	}

	private void NightPoolBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (j < 4) {
					if (i == 2 || i == 3) {
						matrix[i][j] = new WaterCell(false);
					} else {
						matrix[i][j] = new GrassCell(false);
					}
				} else {
					if (i == 2 || i == 3) {
						matrix[i][j] = new WaterCell(false, true);
					} else {
						matrix[i][j] = new GrassCell(false, true);
					}
				}
			}
		}

	}

	private void RoofBord() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (j < 5) {
					matrix[i][j] = new TileCell(true, true);
				} else {
					matrix[i][j] = new TileCell(true, false);
				}

			}
		}
	}

	/*
	 * @return the matrix of cell
	 */
	public Cell[][] getMatrix() {
		return matrix;
	}

	private void createBord(String map) {
		if (map == "Day") {
			dayBord();
		} else if (map == "Pool") {
			poolBord();
		} else if (map == "Night") {
			NightBord();
		} else if (map == "NightPool") {
			NightPoolBord();
		} else if (map == "Roof") {
			RoofBord();
		}

	}

	/**
	 * Renvoie une cellule du plateau sinon renvoie null si la cellule n'existe pas
	 * 
	 * @param y La ligne du plateau
	 * @param x La colonne du plateau
	 * @return La cellule correspondantes au coordonn�es
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

	public List<Cell> getLineCell(int y, int x, int max) {
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i = x; i < max; i++) {
			cells.add(getCell(y, i));
		}

		return Collections.unmodifiableList(cells);
	}

	public List<Plant> getMyPlants() {
		return Collections.unmodifiableList(myPlants);
	}

	public List<Zombie> getMyZombies() {
		return Collections.unmodifiableList(myZombies);
	}

	public List<Projectile> getMyBullet() {
		return Collections.unmodifiableList(myBullet);
	}

	public List<LawnMower> getMyLawnMower() {
		return Collections.unmodifiableList(myLawnMower);
	}

	/**
	 * Genere un nombre al�atoire entre 0 et x
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

	public boolean win(HashMap<Zombie, Integer> superWaveZombie) {
		for (int nbrZ : superWaveZombie.values()) {
			if (nbrZ != 0) {
				return false;
			}
		}

		return myZombies.isEmpty();
	}

	public void setWL(int x) {
		WL = x;
	}

	public static int getWL() {
		return WL;
	}

	public void actionning(BordView view) {

		for (IPlant p : myPlants) {
			p.action(myBullet, view, myZombies, this);
		}

	}

	public void actionningZombie(BordView view, SimpleGameData dataBord) {
		for (IZombie z : myZombies) {

			z.action(view, dataBord, myZombies);
		}

		if (zombieInQueu.size() > 0) {
			myZombies.addAll(zombieInQueu);
			zombieInQueu = new ArrayList<Zombie>();
		}

	}

	public void spawnSun(BordView view, float x, float y, int sunny, int size) {
		if (x == -1) {
			if (dayTime == "Day") {
				int xOrigin = view.getXOrigin();

				float xRandom = RandomPosGenerator(xOrigin, xOrigin + BordView.getHeight());

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

	public void movingZombiesAndBullets(ApplicationContext context, BordView view, boolean debug) {

		for (Zombie z : myZombies) {
			if (debug == true) {
				z.SpeedBoostON();
			}

			if (debug == false) {
				z.SpeedBoostON();
				z.SpeedBoostOFF();
			}
			z.move();
			Cell cell = this.getCell(z.getCaseJ(), z.getCaseI());
			if (cell != null && !cell.isFog()) {
				view.moveAndDrawElement(context, this, z);
			}
			z.setCase(this);
		}

		for (Projectile b : myBullet) {
			if (debug == true) {
				b.SpeedBoostON();
			}
			if (debug == false) {
				b.SpeedBoostON();
				b.SpeedBoostOFF();
			}
			b.action(this);
			b.move();
			Cell cell = this.getCell(b.getCaseJ(), b.getCaseI());
			if (cell != null && !cell.isFog()) {
				view.moveAndDrawElement(context, this, b);
			}
			b.setCase(this);
		}

		for (LawnMower l : myLawnMower) {
			l.move();
			view.moveAndDrawElement(context, this, l);
			l.setCase(this);
		}

		for (Soleil s : mySun) {
			s.move();
			view.moveAndDrawElement(context, this, s);
			s.setCase();
		}
	}

	public void timeEnd(List<Zombie> myZombies, StringBuilder str, ApplicationContext context,
			HashMap<Zombie, Integer> superWaveZombie, BordView view) {

		int xOrigin = view.getXOrigin();
		int squareSize = BordView.getSquareSize();
		String choice = "Continue", finalChoice = null;

		for (Zombie z : myZombies) {
			if (z.isEatingBrain(xOrigin, squareSize)) {
				choice = "Stop";
				finalChoice = "Loose";
			}
		}

		if (this.win(superWaveZombie)) {
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
				this.setWL(0);
				break;
			case "Win":
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez gagne!!!\n");
				this.setWL(1);
				break;
			case "Stop":
				str.append("-+-+-+-+-+-+-+-+-+-\nVous avez quitte la partie !\n");
				this.setWL(2);
				break;
			}
			str.append("La partie � dur�e: " + time.getDureeTxt());
			System.out.println(str.toString());
			EndController.endGame(context);

		}
	}

	public void generateTombstone() {
//		Random rand = new Random();
//		ArrayList<Zombie> allZombies = Zombie.getZombieList(SimpleGameData.getMap());		
//		Tombstone t = Tombstone.createTombstone(x, y);
//		Cell cell = this.getCell(t.getCaseJ(), t.getCaseI());
//		cell.addTombstone(t);
//		myTombstone.add(t);
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

			Plant actualPlant = psView.getSelectedPlants().get(p).createNewPlant(x2, y2);

			if (actualPlant.plantingCondition(actualCell)) {
				myPlants.add(actualPlant);
				actualMoney -= psView.getSelectedPlants().get(p).getCost();
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

		if (dataSelect.hasASelectedCell()) { // cell selected
			dataSelect.unselect();
		}

		int yLine = plantSelectionView.lineFromY(y);

		// clic in the select bord and the choosen plant is not in cooldown and the
		// player has enough money
		if (dataSelect.isCorrectSelectLocation(plantSelectionView, x, y)
				&& plantSelectionView.isThisChronoReset(y, view.getYOrigin())
				&& actualMoney >= plantSelectionView.getSelectedPlants().get(yLine).getCost()) {

			dataSelect.selectCell(yLine, plantSelectionView.columnFromX(x));
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
		}

		return res;
	}

	public boolean spawnRandomPlant(ApplicationContext context, SimpleGameData dataSelect, BordView view,
			SelectBordView plantSelectionView, ArrayList<Plant> selectedPlant) {

		boolean result = false;

		int target = this.RandomPosGenerator(15); // 1 chance sur x
		int xRandomPosition = SimpleGameData.RandomPosGenerator(view.getXOrigin(), BordView.getHeight()); // random
																											// position
																											// x dans
		// matrice
		int yRandomPosition = SimpleGameData.RandomPosGenerator(view.getYOrigin(), BordView.getWidth()); // random
																											// position
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

	public void updateDifficulty() {
		if (System.currentTimeMillis() - difficultyTime >= 15_000) {
			difficulty += 1;

			difficultyTime = System.currentTimeMillis();
		}
	}

	public void spawnLawnMower(BordView view, ApplicationContext context) {
		int staticX = view.getXOrigin() - BordView.getSquareSize();
		for (int i = 1; i <= nbLines; i++) {
			myLawnMower.add(new LawnMower(staticX, BordView.getSquareSize() * i, i - 1));
		}
	}

	static Zombie getRandomZombie(ArrayList<Zombie> Mz) {
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

	private static int getMaximumThreat(ArrayList<Zombie> Zombies) {
		int max = 0;
		for (Zombie z : Zombies) {
			max = (z.getThreat() >= max ? z.getThreat() : max);
		}
		return max * 100;
	}

	public HashMap<Zombie, Integer> generateZombies(int type) {

		ArrayList<Zombie> allZombies = Zombie.getZombieList(SimpleGameData.getMap());
		Random rand = new Random();

		HashMap<Zombie, Integer> zombiesMap = new HashMap<Zombie, Integer>();
		int waveSize = 0;
		int maxWaveSize = (type == 1 ? 15 : 35);
		int MaximumThreat = SimpleGameData.getMaximumThreat(allZombies);
		while (waveSize < maxWaveSize) {
			Zombie choosenOne = getRandomZombie(allZombies);
			if ((choosenOne.getThreat() * 100) <= rand.nextInt(MaximumThreat)) {

				addInMap(zombiesMap, choosenOne);
				waveSize++;
			}
		}

		return zombiesMap;
	}

	public void spawnNormalWave(int squareSize, StringBuilder str, BordView view, ApplicationContext context,
			HashMap<Zombie, Integer> zombieList) {
		Random rand2 = new Random();

		if (System.currentTimeMillis() - spawnTime >= timeLimit) {

			int sqrS = BordView.getSquareSize();

			int endWave = 0;
			int x = view.getXOrigin() + BordView.getWidth();
			int y = view.getYOrigin() + this.RandomPosGenerator(this.getNbLines()) * sqrS + (sqrS / 2)
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
					probList.add(this.RandomPosGenerator(z.getProb(difficulty)));
					zombieAvailable.add(z);

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
				if (map == "Pool" || map == "NightPool") {
					if (z.isCommon()) {
						while (view.indexFromReaCoord(y, view.getYOrigin()) == 2
								|| view.indexFromReaCoord(y, view.getYOrigin()) == 3) {
							y = view.getYOrigin() + this.RandomPosGenerator(this.getNbLines()) * sqrS + (sqrS / 2);
						}

						if (rand2.nextInt(100) <= fertilizerChance) {
							myZombies.add(z.createNewZombie(x, y, true));
						} else {
							myZombies.add(z.createNewZombie(x, y, false));
						}

					} else {
						y = view.getYOrigin() + SimpleGameData.RandomPosGenerator(2, 4) * sqrS + (sqrS / 2);
						if (rand2.nextInt(100) <= fertilizerChance) {
							myZombies.add(z.createNewZombie(x, y, true));
						} else {
							myZombies.add(z.createNewZombie(x, y, false));
						}
					}
				} else {

					if (rand2.nextInt(100) <= fertilizerChance) {
						myZombies.add(z.createNewZombie(x, y, true));
					} else {
						myZombies.add(z.createNewZombie(x, y, false));
					}
				}
				str.append("new " + zombieAvailable.get(selecteur) + new SimpleDateFormat("hh:mm:ss").format(new Date())
						+ ")\n");

			} else {
				if (endWave == zombieList.size() && myZombies.isEmpty()) {
					superWave = 1;
				}
			}

			if (timeLimit >= 3000) {
				timeLimit -= 500;

				spawnTime = System.currentTimeMillis();
			} else {
				spawnTime = System.currentTimeMillis();
			}

			updateDifficulty();

		}
	}

	public void spawnSuperWave(int squareSize, StringBuilder str, BordView view, ApplicationContext context,
			HashMap<Zombie, Integer> zombieList) {

		Random rand2 = new Random();
		int sqrS = BordView.getSquareSize();
		int endWave = 0;
		int x = view.getXOrigin() + BordView.getWidth();

		for (Map.Entry<Zombie, Integer> entry : zombieList.entrySet()) {
			Zombie z = entry.getKey();
			Integer spawn = entry.getValue();

			int y = view.getYOrigin() + this.RandomPosGenerator(this.getNbLines()) * sqrS + (sqrS / 2)
					- (Zombie.getSizeOfZombie() / 2);

			if (spawn == 0) {
				endWave += 1;
			}

			if (spawn > 0) {
				if (map == "Pool" || map == "NightPool") {
					if (z.isCommon()) {
						while (view.indexFromReaCoord(y, view.getYOrigin()) == 2
								|| view.indexFromReaCoord(y, view.getYOrigin()) == 3) {
							y = view.getYOrigin() + this.RandomPosGenerator(this.getNbLines()) * sqrS + (sqrS / 2);
						}
						if (rand2.nextInt(100) <= fertilizerChance) {
							myZombies.add(z.createNewZombie(x, y, true));
						} else {
							myZombies.add(z.createNewZombie(x, y, false));
						}

					} else {
						y = view.getYOrigin() + SimpleGameData.RandomPosGenerator(2, 4) * sqrS + (sqrS / 2);
						if (rand2.nextInt(100) <= fertilizerChance) {
							myZombies.add(z.createNewZombie(x, y, true));
						} else {
							myZombies.add(z.createNewZombie(x, y, false));
						}
					}
				} else {

					if (rand2.nextInt(100) <= fertilizerChance) {
						myZombies.add(z.createNewZombie(x, y, true));
					} else {
						myZombies.add(z.createNewZombie(x, y, false));
					}
				}
				str.append("new " + z + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ")\n");

				entry.setValue(spawn - 1);
			}
		}

		if (endWave == zombieList.size()) {
			superWave = 2;
		}

	}

	public void spawnZombies(int squareSize, StringBuilder str, List<Zombie> myZombies, BordView view,
			ApplicationContext context, HashMap<Zombie, Integer> zombieList, HashMap<Zombie, Integer> superZombieList) {

		if (superWave == 0) {
			spawnNormalWave(squareSize, str, view, context, zombieList);
		} else if (superWave == 1) {

			spawnSuperWave(squareSize, str, view, context, superZombieList);

		}

	}

	public void setDayTime(String dayTime) {
		SimpleGameData.dayTime = dayTime;
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setMap(String map) {
		SimpleGameData.map = map;
	}

	public static String getMap() {
		return map;
	}

	public void setLoadChoice(String choice) {
		loadChoice = choice;
	}

	public String getLoadChoice() {
		return loadChoice;
	}

	public int getActualMoney() {
		return actualMoney;
	}

	public void setZombieInQueu(ArrayList<Zombie> zombieList) {
		zombieInQueu = zombieList;
	}

	public void setMatrix(Cell[][] x) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = x[i][j];
			}
		}
	}

	public void removeB(IEntite dPe) {
		myBullet.remove(dPe);
	}

	public void removeP(IEntite dPe) {
		myPlants.remove(dPe);
	}

	public void removeZ(IEntite dPe) {
		myZombies.remove(dPe);
	}

	public void removeLM(IEntite dPe) {
		myLawnMower.remove(dPe);
	}

	public void addB(Projectile... dPe) {
		for (Projectile i : dPe) {
			myBullet.add(i);
		}
	}

	public void addP(Plant... dPe) {
		for (Plant i : dPe) {
			myPlants.add(i);
		}
	}

	public void addZ(Zombie... dPe) {
		for (Zombie i : dPe) {
			myZombies.add(i);
		}
	}

	public void addLM(LawnMower... dPe) {
		for (LawnMower i : dPe) {
			myLawnMower.add(i);
		}
		;
	}

	public Integer getFertilizer() {
		return (Integer) actualfertilizer;
	}

	public void addFertilizer() {
		actualfertilizer = (actualfertilizer < 3 ? actualfertilizer += 1 : actualfertilizer);
	}

	private void removeFertilizer() {
		if (actualfertilizer > 0) {
			actualfertilizer--;
		} else {
			actualfertilizer = 0;
		}
	}

	public void feed(float x, float y) {
		if (actualfertilizer > 0) {
			Cell cell = this.getCell(BordView.caseYFromY(y), BordView.caseXFromX(x));
			if (cell != null) {
				Plant plant = cell.getMainPlant();
				if (plant != null && plant.feedPlant()) {
					this.removeFertilizer();
				}
			}
		}
	}

	public List<Tombstone> getMyTombstone() {
		return Collections.unmodifiableList(myTombstone);
	}

}

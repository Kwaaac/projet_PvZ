package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import models.Chrono;
import models.Coordinates;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;

public class SelectBordView extends SimpleGameView {
	private final ArrayList<Plant> selectedPlants;

	private static int squareSize;
	private int xOrigin;
	private int yOrigin;
	private int length;
	private int width;
	
	private final Chrono[] caseChrono = { new Chrono(), new Chrono(), new Chrono(), new Chrono(), new Chrono(),
			new Chrono(), new Chrono(), new Chrono(), new Chrono() };

	public SelectBordView(int xOrigin, int yOrigin, int length, int width, int squareSize,
			ArrayList<Plant> selectedPlants) {
		
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.length = length;
		this.width = width;
		SelectBordView.squareSize = squareSize;
		this.selectedPlants = selectedPlants;
	}
	
	public int getXOrigin() {
		return xOrigin;
	}

	public int getYOrigin() {
		return yOrigin;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public static SelectBordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data,
			ArrayList<Plant> selectedPlants) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new SelectBordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize,
				selectedPlants);
	}

	public void startCooldown(int numCase) {
		Chrono askedChrono = caseChrono[numCase];
		if (askedChrono.isReset()) {
			askedChrono.start();
		}

	}

	/**
	 * Check si le chono est lanc� ou en stand-by
	 * 
	 * @param coord  Coordonn� y du clic
	 * @param origin yOrigin
	 * 
	 * @return True si le chrono est en stand-by, false si le chrono tourne
	 * 
	 */
	public boolean isThisChronoReset(float coord, int origin) {
		int numCase = this.indexFromReaCoord(coord, origin);
		Chrono askedChrono = caseChrono[numCase];
		return askedChrono.isReset();
	}

	public void checkCooldown() {
		for (int i = 0; i < selectedPlants.size(); i++) {
			Chrono askedChrono = caseChrono[i];
			Plant askedPlant = selectedPlants.get(i);
			if (askedChrono.asReachTimerAndStop(askedPlant.getCooldown())) {
				askedChrono.stop();
			}
		}
	}

	/**
	 * This function will allow the player to pick a plant from the global list of
	 * plants
	 * 
	 * @param x                  x of the clic location
	 * @param y                  y of the clic location
	 * @param plantSelectionView View of the player list
	 * @param thisData           Data from the global list of plants
	 * @param theOtherData         Data from the player list of plants
	 */

	public void truc(float x, float y, SelectBordView plantSelectionView, SimpleGameData thisData,
			SimpleGameData theOtherData) {

		int X = this.indexFromReaCoord(x, xOrigin);
		int Y = this.indexFromReaCoord(y, yOrigin);

		int plantIndex = (Y * thisData.getNbColumns() + X);
		
		if (plantIndex < selectedPlants.size()) {
			
			// Plants from the other bord
			ArrayList<Plant> lstPlants = plantSelectionView.getSelectedPlants();
			
			if (lstPlants.size() < theOtherData.getNbLines() * theOtherData.getNbColumns()) {

				Plant clickedPlant = selectedPlants.remove(plantIndex);
				lstPlants.add(clickedPlant);
			}
		}
	}

	public static int getSquareSize() {
		return squareSize;
	}

	public int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validité des
		return (int) ((coord - origin) / squareSize); // coordonnées!
	}

	public float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 *
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int lineFromY(float y) {
		return indexFromReaCoord(y, yOrigin);
	}

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x a float x-coordinate
	 * @return the index of the corresponding column.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int columnFromX(float x) {
		return indexFromReaCoord(x, xOrigin);
	}

	public ArrayList<Plant> getSelectedPlants() {
		return selectedPlants;
	}

	public float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	public float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	protected RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j) + 2, yFromJ(i) + 2, squareSize - 4, squareSize - 4);
	}

	protected RectangularShape drawSelectedCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j), yFromJ(i), squareSize, squareSize);
	}

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 */

	@Override
	public void draw(Graphics2D graphics, SimpleGameData data) {

		if (selectedPlants.size() <= data.getNbLines()) {
			graphics.setColor(Color.GRAY);
			graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, width + 2,
					length - 2));
		}
		Coordinates c = data.getSelected();
		if (c != null) {
			if (!data.hasPlant(c.getI(), c.getJ())) {
				graphics.setColor(Color.BLACK);
				graphics.fill(drawSelectedCell(c.getI(), c.getJ()));
			}
		}

		for (int i = 0; i < data.getNbLines(); i++) {
			for (int j = 0; j < data.getNbColumns(); j++) {
				if (caseChrono[i].isReset()) {
					graphics.setColor(Color.GREEN.darker());
					graphics.fill(drawCell(i, j));
				} else {
					graphics.setColor(Color.GREEN.darker().darker());
					graphics.fill(drawCell(i, j));
				}
			}
		}

		int sizeOfPlant = Plant.getSizeOfPlant();
		int i = 0;
		int j = 0;

		if (selectedPlants.size() <= data.getNbLines()) {
			for (Plant p : selectedPlants) {
				p.draw(this, graphics, (int) xOrigin + (squareSize / 2) - sizeOfPlant / 4,
						(int) yOrigin + squareSize * i);

				i += 1;
			}
		} else {
			for (Plant p : selectedPlants) {
				p.draw(this, graphics, (int) xOrigin + (squareSize / 2) - sizeOfPlant / 4 + squareSize * i,
						(int) (squareSize / 2) + squareSize * j - sizeOfPlant / 4);

				i += 1;

				if (i > data.getNbColumns() - 1) {
					i = 0;
					j += 1;
				}
			}
		}
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param x        the float x-coordinate of the cell.
	 * @param y        the float y-coordinate of the cell.
	 */
	@Override
	public void drawOnlyOneCell(Graphics2D graphics, int x, int y, String s) {
		super.drawOnlyOneCell(graphics, x, y, s);
	}

	@Override
	public void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String color) {
		super.drawRectangle(graphics, x, y, width, height, color);
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param moving   the moving element.
	 */
	@Override
	public void moveAndDrawElement(Graphics2D graphics, SimpleGameData data, MovingElement moving) {
		super.moveAndDrawElement(graphics, data, moving);
	}

	int sizeOfPlant = Plant.getSizeOfPlant() - 10;

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 *
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param x        the float x-coordinate of the cell.
	 * @param y        the float y-coordinate of the cell.
	 */

	@Override
	public void drawOnlyOneCell(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}
}

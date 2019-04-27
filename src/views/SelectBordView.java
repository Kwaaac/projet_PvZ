package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import models.Chrono;
import models.Coordinates;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;

public class SelectBordView extends SimpleGameView {
	private final Plant[] selectedPlants;
	private static int squareSize;
	private final Chrono[] caseChrono = { new Chrono(), new Chrono(), new Chrono(), new Chrono(), new Chrono(),
			new Chrono(), new Chrono(), new Chrono(), new Chrono() };

	public SelectBordView(int xOrigin, int yOrigin, int length, int width, int squareSize, Plant[] selectedPlants) {
		super(xOrigin, yOrigin, length, width);
		this.selectedPlants = selectedPlants;
		SelectBordView.squareSize = squareSize;
	}

	public static SelectBordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data,
			Plant[] selectedPlants) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new SelectBordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize,
				selectedPlants);
	}

	public void startCooldown(int numCase) {
		Chrono askedChrono = caseChrono[numCase];
		if(askedChrono.isReset()) {
			askedChrono.start();
		}
		
	}
	
	public boolean isThisChronoReset(float coord, int origin) {
		int numCase = this.indexFromReaCoord(coord, origin);
		Chrono askedChrono = caseChrono[numCase];
		return askedChrono.isReset();
	}
	
	public void checkCooldown() {
		for(int i = 0 ; i < selectedPlants.length; i++) {
			Chrono askedChrono = caseChrono[i];
			Plant askedPlant = selectedPlants[i];
			if(askedChrono.asReachTimer(askedPlant.getCooldown())) {
				askedChrono.stop();
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
		return indexFromReaCoord(y, super.getYOrigin());
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
		return indexFromReaCoord(x, super.getXOrigin());
	}
	
	public Plant[] getSelectedPlants() {
		return selectedPlants;
	}
	
	protected float xFromI(int i) {
		return realCoordFromIndex(i, super.getXOrigin());
	}

	protected float yFromJ(int j) {
		return realCoordFromIndex(j, super.getYOrigin());
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

		graphics.setColor(Color.GRAY);
		graphics.fill(
				new Rectangle2D.Float(super.getXOrigin(), super.getYOrigin(), super.getWidth(), super.getLength()));

		graphics.setColor(Color.WHITE.darker());
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.draw(new Line2D.Float(super.getXOrigin(), super.getYOrigin() + i * squareSize,
					super.getXOrigin() + super.getWidth(), super.getYOrigin() + i * squareSize));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(new Line2D.Float(super.getXOrigin() + i * squareSize, super.getYOrigin(),
					super.getXOrigin() + i * squareSize, super.getYOrigin() + super.getLength()));
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
				graphics.setColor(Color.GREEN.darker());
				graphics.fill(drawCell(i, j));
				graphics.setColor(data.getCellColor(i, j));
			}
		}

		int sizeOfPlant = Plant.getSizeOfPlant();
		int i = 0;

		for (Plant p : selectedPlants) {
			p.draw(this, graphics, (int) (squareSize / 2) - sizeOfPlant / 4,
					(int) (squareSize / 2) + 100 + squareSize * i - sizeOfPlant / 4);

			i += 1;
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

	/*-----------------------------Plants------------------------------*/
	/*-------------------------------DAY-------------------------------*/
	@Override
	public void drawPeashooter(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawCherryBomb(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawWallNut(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawPotatoMine(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 10, y + sizeOfPlant / 2, sizeOfPlant - 15, sizeOfPlant - 15));
	}

	@Override
	public void drawSnowPea(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawSunFlower(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));

		graphics.setColor(Color.decode("#AF6907"));
		graphics.fill(new Ellipse2D.Float(x - 7, y + sizeOfPlant / 2 + 8, sizeOfPlant - 15, sizeOfPlant - 15));
	}

	@Override
	public void drawChomper(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 25, y + sizeOfPlant / 2 - 10, sizeOfPlant + 20, sizeOfPlant + 20));
	}

	@Override
	public void drawRepeater(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	/*-------------------------------NIGHT------------------------------*/
	@Override
	public void drawPuffShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawSunShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawFumeShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawGraveBuster(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawHypnoShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawScaredyShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawIceShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawDoomShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 15, y + sizeOfPlant / 2, sizeOfPlant, sizeOfPlant));
	}

}

package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;

public class BordView extends SimpleGameView {
	private static int squareSize;

	public BordView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		super(xOrigin, yOrigin, length, width);
		BordView.squareSize = squareSize;
	}

	public static BordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new BordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize);
	}

	public int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validité des
		return (int) ((coord - origin) / squareSize); // coordonnées!
	}

	public float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	public static int getSquareSize() {
		return squareSize;
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
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public static int caseYFromY(float y) {
		return (int) ((y - 100) / squareSize);
	}

	public static int caseXFromX(float x) {
		return (int) ((x - 450) / squareSize);
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

	protected float xFromI(int i) {
		return realCoordFromIndex(i, super.getXOrigin());
	}

	protected float yFromJ(int j) {
		return realCoordFromIndex(j, super.getYOrigin());
	}

	protected RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j) + 2, yFromJ(i) + 2, squareSize - 4, squareSize - 4);
	}

	public Plant[] getSelectedPlants() {
		return getSelectedPlants();
	}

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 */

	
	
	public void draw(Graphics2D graphics, SimpleGameData data) {
		graphics.setColor(Color.decode("#A9A9A9"));
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.setStroke(new BasicStroke(4));
			graphics.draw(new Line2D.Float(super.getXOrigin(), super.getYOrigin() + i * squareSize,
					super.getXOrigin() + super.getWidth(), super.getYOrigin() + i * squareSize));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(new Line2D.Float(super.getXOrigin() + i * squareSize, super.getYOrigin(),
					super.getXOrigin() + i * squareSize, super.getYOrigin() + super.getLength()));
		}

		for (int i = 0; i < data.getNbLines(); i++) {
			for (int j = 0; j < data.getNbColumns(); j++) {
					graphics.setColor(Color.GREEN.darker());
					graphics.fill(drawCell(i, j));
			}
		}

		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(super.getXOrigin() + super.getWidth(), super.getYOrigin(),
				super.getXOrigin() + super.getWidth(), super.getLength()));
		
		graphics.fill(new Rectangle2D.Float(super.getXOrigin(), 0, super.getXOrigin() + super.getWidth(), 148));

		ArrayList<Plant> myPlants = data.getMyPlants();

		for (Plant p : myPlants) {
			p.draw(this, graphics, (int) p.getX(), (int) p.getY());
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

	public void drawString(Graphics2D graphics, SimpleGameData data, int i, int j, String string) {
		graphics.setColor(Color.BLACK);
		graphics.drawString(string, j, j);
		
	}
	public void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String string) {
		graphics.setColor(Color.decode(string));
		graphics.fill(new Rectangle2D.Float(x,y,width,height));
	}

	

}

package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.RectangularShape;

import models.MovingElement;
import models.SimpleGameData;

public class BordView extends SimpleGameView{
	
	

	public BordView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		super(xOrigin, yOrigin, length, width, squareSize);
	}

	public static BordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new BordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize);
	}
	
	protected int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validitÃ© des coordonnées!								
		return super.indexFromReaCoord(coord, origin);
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
		return super.lineFromY(y);
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
		return super.columnFromX(x);
	}

	public float realCoordFromIndex(int index, int origin) {
		return super.realCoordFromIndex(index, origin);
	}

	protected float xFromI(int i) {
		return super.xFromI(i);
	}

	protected float yFromJ(int j) {
		return super.yFromJ(j);
	}

	protected RectangularShape drawCell(int i, int j) {
		return super.drawCell(i, j);
	}

	protected RectangularShape drawSelectedCell(int i, int j) {
		return super.drawSelectedCell(i, j);
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
		// example
//		System.out.println("Bord:" + super.getXOrigin() + " : " + super.getWidth());
		graphics.setColor(Color.WHITE.darker());
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.draw(
					new Line2D.Float(super.getXOrigin(), super.getYOrigin() + i * super.getSquareSize(), super.getXOrigin() + super.getWidth(), super.getYOrigin() + i * super.getSquareSize()));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(
					new Line2D.Float(super.getXOrigin() + i * super.getSquareSize(), super.getYOrigin(), super.getXOrigin() + i * super.getSquareSize(), super.getYOrigin() + super.getLength()));
		}

		for (int i = 0; i < data.getNbLines(); i++) {
			for (int j = 0; j < data.getNbColumns(); j++) {
				if(! data.hasPlant(i, j)) {
				graphics.setColor(Color.GREEN.darker());
				graphics.fill(drawCell(i, j));
				graphics.setColor(data.getCellColor(i, j));
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
	public void drawOnlyOneCell(Graphics2D graphics, SimpleGameData data, int x, int y, String s) {
		super.drawOnlyOneCell(graphics, data, x, y, s);
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

}

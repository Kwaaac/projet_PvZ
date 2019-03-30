package Views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import Models.Coordinates;
import Models.MovingElement;
import Models.SimpleGameData;
import Models.Plants.CherryBomb;
import Models.Plants.Peashooter;
import Models.Plants.Plant;
import Models.Plants.WallNut;
import Models.Zombies.ConeheadZombie;
import Models.Zombies.FlagZombie;

public class SimpleGameView implements GameView {
	private final int xOrigin;
	private final int yOrigin;
	private final int width;
	private final int length;
	private static int squareSize;

	private SimpleGameView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.length = length;
		this.width = width;
		SimpleGameView.squareSize = squareSize;
	}

	public static int getSquareSize() {
		return squareSize;
	}
	
	public static SimpleGameView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new SimpleGameView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize);
	}
	
	private int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validité des
																// coordonnées!
		return (int) ((coord - origin) / squareSize);
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

	public float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	private float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	private float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	private RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j) + 2, yFromJ(i) + 2, squareSize - 4, squareSize - 4);
	}

	private RectangularShape drawSelectedCell(int i, int j) {
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
		// example
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, width, length));

		graphics.setColor(Color.WHITE.darker());
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.draw(
					new Line2D.Float(xOrigin, yOrigin + i * squareSize, xOrigin + width, yOrigin + i * squareSize));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(
					new Line2D.Float(xOrigin + i * squareSize, yOrigin, xOrigin + i * squareSize, yOrigin + length));
		}

		Coordinates c = data.getSelected();
		if (c != null) {
			graphics.setColor(Color.BLACK);
			graphics.fill(drawSelectedCell(c.getI(), c.getJ()));
		}

		for (int i = 0; i < data.getNbLines(); i++) {
			for (int j = 0; j < data.getNbColumns(); j++) {
				graphics.setColor(Color.GREEN.darker());
				graphics.fill(drawCell(i, j));
				graphics.setColor(data.getCellColor(i, j));
			}
		}
		
		int sizeOfPlant = Plant.getSizeOfPlant();
		graphics.setColor(Color.decode("#90D322"));
		Peashooter p1 = new Peashooter((int)(squareSize/2)-sizeOfPlant/2,(int)(squareSize/2)+100-sizeOfPlant/2);
		graphics.draw(p1.draw());
		graphics.fill(p1.draw());
		
		graphics.setColor(Color.decode("#CB5050"));
		CherryBomb p2 = new CherryBomb((int)(squareSize/2)-sizeOfPlant/2,(int)(squareSize/2)+100+squareSize-sizeOfPlant/2);
		graphics.draw(p2.draw());
		graphics.fill(p2.draw());
		
		graphics.setColor(Color.decode("#ECB428"));
		WallNut p3 = new WallNut((int)(squareSize/2)-sizeOfPlant/2,(int)(squareSize/2)+100+squareSize*2-sizeOfPlant/2);
		graphics.draw(p3.draw());
		graphics.fill(p3.draw());
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
		int sizeOfPlant = Plant.getSizeOfPlant();
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
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
		graphics.setColor(graphics.getBackground());
		graphics.fill(moving.draw());
		moving.move();
		if (moving instanceof FlagZombie) {
			graphics.setColor(Color.RED);
			graphics.fill(moving.draw());
		}
		if (moving instanceof ConeheadZombie) {
			graphics.setColor(Color.RED.darker());
			graphics.fill(moving.draw());
		}
		graphics.setColor(Color.BLACK);
		graphics.fill(moving.draw());
	}
}

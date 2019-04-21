package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import fr.umlv.zen5.ApplicationContext;
import models.Coordinates;
import models.MovingElement;
import models.SimpleGameData;
import plants.CherryBomb;
import plants.Peashooter;
import plants.Plant;
import plants.WallNut;

public class SelectBordView extends SimpleGameView {
	private final Plant[] selectedPlants;

	public SelectBordView(int xOrigin, int yOrigin, int length, int width, int squareSize, Plant[] selectedPlants) {
		super(xOrigin, yOrigin, length, width, squareSize);
		this.selectedPlants = selectedPlants;
	}

	public static SelectBordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data,
			Plant[] selectedPlants) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new SelectBordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize,
				selectedPlants);
	}

	public int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validité des
															// coordonn�es!
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
	
	public Plant[] getSelectedPlants() {
		return selectedPlants;
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
		graphics.fill(
				new Rectangle2D.Float(super.getXOrigin(), super.getYOrigin(), super.getWidth(), super.getLength()));

		graphics.setColor(Color.GRAY.brighter());
		graphics.fill(new Rectangle2D.Float(1890, 98, super.getWidth(), 904));

		graphics.setColor(Color.WHITE.darker());
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.draw(new Line2D.Float(super.getXOrigin(), super.getYOrigin() + i * super.getSquareSize(),
					super.getXOrigin() + super.getWidth(), super.getYOrigin() + i * super.getSquareSize()));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(new Line2D.Float(super.getXOrigin() + i * super.getSquareSize(), super.getYOrigin(),
					super.getXOrigin() + i * super.getSquareSize(), super.getYOrigin() + super.getLength()));
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
				if (!data.hasPlant(i, j)) {
					graphics.setColor(Color.GREEN.darker());
					graphics.fill(drawCell(i, j));
					graphics.setColor(data.getCellColor(i, j));
				}
			}
		}

		int sizeOfPlant = Plant.getSizeOfPlant();
		int squareSize = super.getSquareSize();
		int i = 0;
		
		for(Plant p: selectedPlants) {
			p.draw(this, graphics, data, (int) (squareSize / 2) - sizeOfPlant / 2, (int) (squareSize / 2) + 100 + squareSize * i - sizeOfPlant / 2);
			
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

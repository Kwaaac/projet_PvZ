package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.MovingElement;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public class BordView extends SimpleGameView {
	private static int squareSize;
	private static int xOrigin;
	private static int yOrigin;
	private static int length;
	private static int width;

	public BordView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		BordView.xOrigin = xOrigin;
		BordView.yOrigin = yOrigin;
		BordView.length = length;
		BordView.width = width;
		BordView.squareSize = squareSize;
	}
	
	public BordView(int xOrigin, int yOrigin, int width, int length) {
		BordView.xOrigin = xOrigin;
		BordView.yOrigin = yOrigin;
		BordView.width = width;
		BordView.length = length;
	}

	public static BordView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new BordView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize);
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
		return indexFromReaCoord(y, yOrigin);
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
		return (int) ((y - yOrigin) / squareSize);
	}

	public static int caseXFromX(float x) {
		return (int) ((x - xOrigin) / squareSize);
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

	protected float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	protected float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	public RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j), yFromJ(i), squareSize, squareSize);
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

		// used to create a checkerboard with the cells
		int checkerboard = 1;

		// Draw cells
		for (int i = 0; i < data.getNbLines(); i++) {

			if (i % 2 == 0) { checkerboard = 1; } else { checkerboard = 0; }

			ArrayList<Cell> cells = data.getLineCell(i, 0);
			for (int j = 0; j < cells.size(); j++) {

				if (checkerboard % 2 == 0) {
					graphics.setColor(cells.get(j).getColor());
				} else {
					graphics.setColor(cells.get(j).getColorDarker());
				}

				graphics.fill(drawCell(i, j));
				checkerboard += 1;
			}
		}

		graphics.setColor(Color.decode("#cbd9ef"));
		graphics.fill(new Rectangle2D.Float(xOrigin + width, 150,
				xOrigin + width, 150 + length));

		graphics.fill(new Rectangle2D.Float(xOrigin, 0, xOrigin + width, 150));

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

	public void drawString(Graphics graphics, int i, int j, String string, String color, int fontSize) {
		graphics.setColor(Color.decode(color));
		graphics.setFont(new Font("Afterglow", Font.PLAIN, fontSize));
		graphics.drawString(string, i, j);
	}

	public void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String string) {
		graphics.setColor(Color.decode(string));
		graphics.fill(new Rectangle2D.Float(x, y, width, height));
	}
	
	public void drawEllipse(Graphics2D graphics, int x, int y, int width, int height, String string) {
		graphics.setColor(Color.decode(string));
		graphics.fill(new Ellipse2D.Float(x, y, width, height));
	}
	
	
	
	
	public void drawAll(ApplicationContext context, SimpleGameData dataBord, BordView view, ArrayList<Zombie> myZombies, 
			ArrayList<Projectile> myBullet, ArrayList<LawnMower> myLawnMower, boolean debug, boolean debuglock, SimpleGameData dataSelect, int money, SelectBordView plantSelectionView) {
		
		view.drawRectangle(context, 0, 0, xOrigin+width, yOrigin+length+100, "#cbd9ef");
		
		view.draw(context, dataBord);
		debuglock = dataBord.movingZombiesAndBullets(context, view, myZombies, myBullet, myLawnMower, debug, debuglock);
		
		
		plantSelectionView.draw(context, dataSelect);
		view.drawRectangle(context, 250, 10, 160, 60, "#A77540");
		view.drawRectangle(context, 255, 15, 150, 50, "#CF9456");
		view.drawString(context, 260, 55, String.valueOf(money), "#FFFF00", 50); //SUN YOU HAVE
		view.drawEllipse(context, 350, 15, 45, 45, "#FEFF33");
		view.drawRectangle(context, 10, 10, 165, 55, "#A77540");
		view.drawRectangle(context, 15, 15, 155, 45, "#CF9456");
		view.drawString(context, 20, 55, "MENU", "#FFFF00", 50);//MENU BUTTON
	}
	
	
}

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
	private static int height;
	private static int width;

	public BordView(int xOrigin, int yOrigin, int height, int width, int squareSize) {
		BordView.xOrigin = xOrigin;
		BordView.yOrigin = yOrigin;
		BordView.height = height;
		BordView.width = width;
		BordView.squareSize = squareSize;
	}
	
	public BordView(int xOrigin, int yOrigin, int width, int height) {
		BordView.xOrigin = xOrigin;
		BordView.yOrigin = yOrigin;
		BordView.width = width;
		BordView.height = height;
	}

	public static BordView initGameGraphics(int xOrigin, int yOrigin, int height, SimpleGameData data) {
		int squareSize = (int) (width-xOrigin)/data.getNbColumns();
		return new BordView(xOrigin, yOrigin, height, data.getNbColumns() * squareSize, squareSize);
	}
	
	public int getXOrigin() {
		return xOrigin;
	}

	public int getYOrigin() {
		return yOrigin;
	}

	public static int getWidth() {
		return width;
	}
	
	public static void setWidth(int x) {
		BordView.width = x;
	}

	public static int getHeight() {
		return height;
	}
	
	public static void setHeight(int x) {
		BordView.height = x;
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
		
		Cell[][] matrix = data.getMatrix();
		
		int m = matrix.length;
		
		int p = matrix[0].length;
		
		// used to create a checkerboard with the cells
		int checkerboard = 1;

		// Draw cells
		for (int i = 0; i < m; i++) {

			if (i % 2 == 0) { checkerboard = 1; } else { checkerboard = 0; }

			for (int j = 0; j < p; j++) {

				matrix[i][j].drawBoardCell(graphics, yFromJ(i), xFromI(j), checkerboard%2);

				checkerboard += 1;
			}
		}

		graphics.setColor(Color.decode("#cbd9ef"));
		graphics.fill(new Rectangle2D.Float(xOrigin + width, 150,
				xOrigin + width, 150 + height));

		graphics.fill(new Rectangle2D.Float(xOrigin, 0, xOrigin + width, 150));

		ArrayList<Plant> myPlants = data.getMyPlants();

		for (Plant plant : myPlants) {
			plant.draw(this, graphics);
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
	
	public void drawMenu(ApplicationContext context, BordView view, int width, int height) {
		view.drawRectangle(context, 0, 0, width, height, "#61DB5F");
		view.drawString(context, (width/2)-125, (height/6)-50, "MENU", "#000000", 85);
		
		view.drawRectangle(context, 0, 200, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 205, width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6), "Play", "000000", 50);
		
		view.drawRectangle(context, 0, 225+(height/6), width, (height/6), "#000000");
		view.drawRectangle(context, 5, 230+(height/6), width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6)*2, "Resume", "000000", 50);
		
		view.drawRectangle(context, 0, 250+(height/6)*2, width, (height/6), "#000000");
		view.drawRectangle(context, 5, 255+(height/6)*2, width-10, (height/6)-10, "#22D398");
		view.drawString(context, (width / 2) - 100, 150+(height / 6)*3, "Editor", "000000", 50);
	}
	
	public void drawMapSelection(ApplicationContext context, BordView view, int width, int height) {
		view.drawRectangle(context, 0, 0, width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 10, width - 10, (height / 5) - 15, "#61DB5F");
		view.drawString(context, (width / 2) - 100, 1 * (height / 10), "DAY", "000000", 50);

		view.drawRectangle(context, 0, (height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, (height / 5) + 5, width - 10, (height / 5) -10, "#5F79DB");
		view.drawString(context, (width / 2) - 100, 3 * (height / 10), "NIGHT", "000000", 50);

		view.drawRectangle(context, 0, 2*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 2*(height / 5) + 5, width - 10, (height / 5) -10, "#5FC1DB");
		view.drawString(context, (width / 2) - 100, 5 * (height / 10), "POOL", "000000", 50);
		
		view.drawRectangle(context, 0, 3*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 3*(height / 5) + 5, (width/2)-5, (height / 5) -10, "#5F79DB");
		view.drawRectangle(context, width/2, 3*(height / 5) + 5, (width/2) - 5, (height / 5) - 10, "#5FC1DB");
		view.drawString(context, (width / 2) - 155, 7 * (height / 10), "NIGHTPOOL", "000000", 50);
		
		view.drawRectangle(context, 0, 4*(height / 5), width, (height / 5), "#000000");
		view.drawRectangle(context, 5, 4*(height / 5) +5, width - 10, (height / 5) - 10, "#BC4C29");
		view.drawString(context, (width / 2) - 100, 9 * (height / 10), "ROOF", "000000", 50);
	}
	
	public void drawPlantSelection(ApplicationContext context, BordView view, SelectBordView viewContent, SelectBordView plantSelectionView, SimpleGameData dataBord,
			SimpleGameData dataSelect, int width, int height) {
		view.drawRectangle(context, 0, 0, width, height, "#ffffff"); // background
		viewContent.draw(context, dataBord);
		plantSelectionView.draw(context, dataSelect);
		view.drawRectangle(context, width - 65, 15, 50, 50, "#DE0000"); // quit
	}
	
	
}

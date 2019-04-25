package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.ConeheadZombie;
import models.zombies.FlagZombie;
import models.zombies.NormalZombie;
import models.zombies.Zombie;

public abstract class SimpleGameView implements GameView {
	private final int xOrigin;
	private final int yOrigin;
	private final int width;
	private final int length;
	private static int squareSize;

	protected SimpleGameView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.length = length;
		this.width = width;
		SimpleGameView.squareSize = squareSize;
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

	public static int getSquareSize() {
		return squareSize;
	}

	public int indexFromReaCoord(float coord, int origin) { // attention, il manque des test de validité des
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

	protected float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	protected float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	protected RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j) + 2, yFromJ(i) + 2, squareSize - 4, squareSize - 4);
	}

	protected RectangularShape drawSelectedCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j), yFromJ(i), squareSize, squareSize);
	}

	int sizeOfPlant = Plant.getSizeOfPlant();

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
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}
	
	@Override
	public void drawOnlyOneCell(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	/*-----------------------------Plants------------------------------*/
	
	@Override
	public void drawPeashooter(Graphics2D graphics, int x, int y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawCherryBomb(Graphics2D graphics, int x, int y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawWallNut(Graphics2D graphics, int x, int y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawPotatoMine(Graphics2D graphics, int x, int y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x + 10, y + 10, sizeOfPlant - 20, sizeOfPlant - 20));
	}
	
	
	/*-----------------------------Zombies------------------------------*/
	
	int sizeOfZombie = Zombie.getSizeOfZombie();
	
	@Override
	public void drawNormalZombie(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	@Override
	public void drawConeheadZombie(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	@Override
	public void drawFlagZombie(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	/*-----------------------------Bullets------------------------------*/
	
	int sizeOfProjectile = Projectile.getSizeOfProjectile();
	
	@Override
	public void drawBullet(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfProjectile, sizeOfProjectile));
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
		moving.move();

		moving.draw(this, graphics, moving.getX(), moving.getY());
	}

	public void drawSelectedPlant(Graphics2D context, SimpleGameData data, BordView view, int p) {
	}
	
	
}

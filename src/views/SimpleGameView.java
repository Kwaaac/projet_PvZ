package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public abstract class SimpleGameView implements GameView {
	private final int xOrigin;
	private final int yOrigin;
	private final int width;
	private final int length;

	protected SimpleGameView(int xOrigin, int yOrigin, int length, int width) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.length = length;
		this.width = width;
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
	/*-------------------------------DAY-------------------------------*/
	@Override
	public void drawPeashooter(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawCherryBomb(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawWallNut(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawPotatoMine(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x + 10, y + 10, sizeOfPlant - 20, sizeOfPlant - 20));
	}

	@Override
	public void drawSnowPea(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawSunFlower(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode("#AF6907"));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));
	}


	@Override
	public void drawChomper(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x - 10, y - 10, sizeOfPlant + 20, sizeOfPlant + 20));
	}

	@Override
	public void drawRepeater(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	/*-------------------------------NIGHT------------------------------*/
	@Override
	public void drawPuffShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawSunShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawFumeShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawGraveBuster(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawHypnoShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawScaredyShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawIceShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	@Override
	public void drawDoomShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
	}

	/*-----------------------------Zombies------------------------------*/

	int sizeOfZombie = Zombie.getSizeOfZombie();

	@Override
	public void drawNormalZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawConeheadZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawFlagZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawBucketheadZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	/*-----------------------------Bullets------------------------------*/

	int sizeOfProjectile = Projectile.getSizeOfProjectile();

	@Override
	public void drawBullet(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
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

package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.zombies.Zombie;

public abstract class SimpleGameView implements GameView {

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

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 *
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param x        the int x-coordinate of the screen.
	 * @param y        the int y-coordinate of the screen.
	 * @param width    the width of the shape.
	 * @param height   the height of the shape.
	 * @param s        the color in hexa of the shape.
	 */
	
	@Override
	public void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, width, height));
	}

	@Override
	public void drawEllipse(Graphics2D graphics, int x, int y, int width, int height, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, width, height));
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 *
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param i        the int x-coordinate of the screen.
	 * @param j        the int y-coordinate of the screen.
	 * @param string   the string you want to write.
	 * @param color    the color in hexa of the string.
	 * @param fontSize the font size of the string.
	 */
	
	@Override
	public void drawString(Graphics graphics, int i, int j, String string, String color, int fontSize) {
		graphics.setColor(Color.decode(color));
		graphics.setFont(new Font("Afterglow", Font.PLAIN, fontSize));
		graphics.drawString(string, i, j);
	}
	
	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 *
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param i        the int x-coordinate of the screen.
	 * @param j        the int y-coordinate of the screen.
	 * @param cost     the string you want to write.
	 */
	
	public void drawCost(Graphics graphics, int i, int j, String cost) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Afterglow", Font.PLAIN, 20));
		graphics.drawString(cost, i+5+sizeOfPlant/2, j+SelectBordView.getSquareSize()-5);
	}
	
	@Override
	public void drawSun(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, 85, 85));
	}
	
	@Override
	public void drawSmallSun(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, 85, 85));
	}

	/*-----------------------------Zombies------------------------------*/

	int sizeOfZombie = Zombie.getSizeOfZombie();

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 *
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param x        the float x-coordinate of the screen.
	 * @param y        the float y-coordinate of the screen.
	 * @param color    the color in hexa of the zombie.
	 */
	
	@Override
	public void drawBalloonZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawBaseballZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawBungeeZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawCatapultBaseballZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawCatapultZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawDiggerZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawDrZomboss(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawDuckyTubeZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawFootballZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawGaltingPeaZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawGargantuar(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawGigaFootballZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawGigagargantuar(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawImp(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawJalapenoZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawLadderZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}


	@Override
	public void drawPeashooterZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawPogoZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawScreenDoorZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	@Override
	public void drawTrashCanZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawWallNutZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawZombieBobsledTeam(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawZombieYeti(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawTallNutZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawSquashZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	/*-----------------------------Miscellaneous------------------------------*/

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
		moving.draw(this, graphics);
	}

	public void drawSelectedPlant(Graphics2D context, SimpleGameData data, BordView view, int p) {
	}

	

}

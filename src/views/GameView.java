package views;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import models.MovingElement;
import models.SimpleGameData;

/**
 * The GameView class is in charge of the graphical view of a clicky game.
 */
public interface GameView {
	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int lineFromY(float y);

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x a float x-coordinate
	 * @return the index of the corresponding column.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int columnFromX(float x);

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 */
	public void draw(Graphics2D graphics, SimpleGameData data);

	/**
	 * Draws the game board from its data, using an existing
	 * {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 */
	public default void draw(ApplicationContext context, SimpleGameData data) {
		context.renderFrame(graphics -> draw(graphics, data));
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
	public void drawOnlyOneCell(Graphics2D graphics, int x, int y, String s);

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 * @param x       the float x-coordinate of the cell.
	 * @param y       the float y-coordinate of the cell.
	 * @param yellow
	 */
	public default void drawOnlyOneCell(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	public void drawOnlyOneCell(Graphics2D graphics, float x, float y, String s);
	
	public default void drawOnlyOneCell(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	/**
	 * Draws only only the specified moving element in the game board from its data,
	 * using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param moving   the moving element.
	 */
	public void moveAndDrawElement(Graphics2D graphics, SimpleGameData data, MovingElement moving);

	/**
	 * Draws only the specified moving element in the game board from its data,
	 * using an existing {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 * @param moving  the moving element.
	 */
	public default void moveAndDrawElement(ApplicationContext context, SimpleGameData data, MovingElement moving) {
		context.renderFrame(graphics -> moveAndDrawElement(graphics, data, moving));
	}

	/*-----------------------------Plants------------------------------*/

	void drawPeashooter(Graphics2D graphics, int x, int y, String s);

	public default void drawPeashooter(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	void drawCherryBomb(Graphics2D graphics, int x, int y, String s);

	public default void drawCherryBomb(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	void drawWallNut(Graphics2D graphics, int x, int y, String s);

	public default void drawWallNut(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	void drawPotatoMine(Graphics2D graphics, int x, int y, String s);

	public default void drawPotatoMine(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	/*-----------------------------Zombies------------------------------*/

	void drawNormalZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawNormalZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	void drawConeheadZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawConeheadZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	void drawFlagZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawFlagZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	
	/*-----------------------------Bullets------------------------------*/

	void drawBullet(Graphics2D graphics, float x, float y, String s);

	public default void drawBullet(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
}

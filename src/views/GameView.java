package views;

import java.awt.Graphics;
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

	void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String color);

	public default void drawRectangle(ApplicationContext context, int x, int y, int width, int height, String color) {
		context.renderFrame(graphics -> drawRectangle(graphics, x, y, width, height, color));
	}

	void drawEllipse(Graphics2D graphics, int x, int y, int width, int height, String color);

	public default void drawEllipse(ApplicationContext context, int x, int y, int width, int height, String color) {
		context.renderFrame(graphics -> drawEllipse(graphics, x, y, width, height, color));
	}

	void drawString(Graphics graphics, int x, int y, String s, String color, int font);

	public default void drawString(ApplicationContext context, int x, int y, String s, String color, int fontSize) {
		context.renderFrame(graphics -> drawString(graphics, x, y, s, color, fontSize));
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

	void drawSun(Graphics2D graphics, float x, float y, String s);

	public default void drawSun(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawSun(graphics, x, y, s));
	}

	void drawSmallSun(Graphics2D graphics, float x, float y, String s);

	public default void drawSmallSun(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawSun(graphics, x, y, s));
	}

	/*-----------------------------Zombies------------------------------*/



	void drawBalloonZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBalloonZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawBalloonZombie(graphics, x, y, s));
	}

	void drawBaseballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBaseballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawBaseballZombie(graphics, x, y, s));
	}

	void drawBungeeZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBungeeZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawBungeeZombie(graphics, x, y, s));
	}

	void drawCatapultBaseballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawCatapultBaseballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawCatapultBaseballZombie(graphics, x, y, s));
	}

	void drawCatapultZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawCatapultZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawCatapultZombie(graphics, x, y, s));
	}

	
	void drawDiggerZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDiggerZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawDiggerZombie(graphics, x, y, s));
	}


	void drawDrZomboss(Graphics2D graphics, float x, float y, String s);

	public default void drawDrZomboss(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawDrZomboss(graphics, x, y, s));
	}

	void drawDuckyTubeZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDuckyTubeZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawDuckyTubeZombie(graphics, x, y, s));
	}

	void drawFootballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawFootballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawFootballZombie(graphics, x, y, s));
	}

	void drawGaltingPeaZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawGaltingPeaZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawGaltingPeaZombie(graphics, x, y, s));
	}

	void drawGargantuar(Graphics2D graphics, float x, float y, String s);

	public default void drawGargantuar(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawGargantuar(graphics, x, y, s));
	}

	void drawGigaFootballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawGigaFootballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawGigaFootballZombie(graphics, x, y, s));
	}

	void drawGigagargantuar(Graphics2D graphics, float x, float y, String s);

	public default void drawGigagargantuar(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawGigagargantuar(graphics, x, y, s));
	}

	void drawImp(Graphics2D graphics, float x, float y, String s);

	public default void drawImp(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawImp(graphics, x, y, s));
	}

	void drawJalapenoZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawJalapenoZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawJalapenoZombie(graphics, x, y, s));
	}

	void drawLadderZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawLadderZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawLadderZombie(graphics, x, y, s));
	}


	void drawPeashooterZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawPeashooterZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawPeashooterZombie(graphics, x, y, s));
	}

	void drawPogoZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawPogoZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawPogoZombie(graphics, x, y, s));
	}

	void drawScreenDoorZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawScreenDoorZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawScreenDoorZombie(graphics, x, y, s));
	}


	void drawSquashZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawSquashZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawSquashZombie(graphics, x, y, s));
	}

	void drawTallNutZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawTallNutZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawTallNutZombie(graphics, x, y, s));
	}

	void drawTrashCanZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawTrashCanZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawTrashCanZombie(graphics, x, y, s));
	}

	void drawWallNutZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawWallNutZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawWallNutZombie(graphics, x, y, s));
	}

	void drawZombieBobsledTeam(Graphics2D graphics, float x, float y, String s);

	public default void drawZombieBobsledTeam(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawZombieBobsledTeam(graphics, x, y, s));
	}

	void drawZombieYeti(Graphics2D graphics, float x, float y, String s);

	public default void drawZombieYeti(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawZombieYeti(graphics, x, y, s));
	}
}

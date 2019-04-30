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
	
	
	void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String color);
	public default void drawRectangle(ApplicationContext context, int x, int y, int width, int height, String color) {
		context.renderFrame(graphics -> drawRectangle(graphics, x, y, width, height, color));
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
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	

	
	void drawString(Graphics2D graphics, int x, int y, String s);
	public default void drawString(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	/*-----------------------------Plants------------------------------*/
	/*------------------------------DAY--------------------------------*/

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
	
	void drawSnowPea(Graphics2D graphics, int x, int y, String color);
	
	public default void drawSnowPea(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawSunFlower(Graphics2D graphics, int x, int y, String color);
	
	public default void drawSunFlower(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawChomper(Graphics2D graphics, int x, int y, String color);
	
	public default void drawChomper(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawRepeater(Graphics2D graphics, int x, int y, String color);
	
	public default void drawRepeater(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	/*-------------------------------NIGHT------------------------------*/
	void drawPuffShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawPuffShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawSunShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawSunShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawFumeShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawFumeShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawGraveBuster(Graphics2D graphics, int x, int y, String color);
	
	public default void drawGraveBuster(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawHypnoShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawHypnoShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawScaredyShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawScaredyShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawIceShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawIceShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDoomShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawDoomShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	/*-------------------------------POOL-------------------------------*/
	void drawCattails(Graphics2D graphics, int x, int y, String color);
	
	public default void drawCattails(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawSeaShroom(Graphics2D graphics, int x, int y, String color);
	
	public default void drawSeaShroom(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawTangleKelp(Graphics2D graphics, int x, int y, String color);
	
	public default void drawTangleKelp(ApplicationContext context, int x, int y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawLilyPad(Graphics2D graphics, int x, int y, String color);
	
	public default void drawLilyPad(ApplicationContext context, int x, int y, String s) {
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
	
	void drawBucketheadZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBucketheadZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawBackupDancerZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBackupDancerZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawBalloonZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBalloonZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawBaseballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBaseballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawBungeeZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawBungeeZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawCatapultBaseballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawCatapultBaseballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawCatapultZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawCatapultZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDancingZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDancingZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDiggerZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDiggerZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDolphinRiderZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDolphinRiderZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDrZomboss(Graphics2D graphics, float x, float y, String s);

	public default void drawDrZomboss(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawDuckyTubeZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawDuckyTubeZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawFootballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawFootballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawGaltingPeaZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawGaltingPeaZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawGargantuar(Graphics2D graphics, float x, float y, String s);

	public default void drawGargantuar(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawGigaFootballZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawGigaFootballZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawGigagargantuar(Graphics2D graphics, float x, float y, String s);

	public default void drawGigagargantuar(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawImp(Graphics2D graphics, float x, float y, String s);

	public default void drawImp(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawJackintheBoxZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawJackintheBoxZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawJalapenoZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawJalapenoZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawLadderZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawLadderZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawNewspaperZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawNewspaperZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawPeashooterZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawPeashooterZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawPogoZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawPogoZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawPoleVaultingZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawPoleVaultingZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawScreenDoorZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawScreenDoorZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawSnorkelZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawSnorkelZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawSquashZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawSquashZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawTallNutZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawTallNutZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawTrashCanZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawTrashCanZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawWallNutZombie(Graphics2D graphics, float x, float y, String s);

	public default void drawWallNutZombie(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawZombieBobsledTeam(Graphics2D graphics, float x, float y, String s);

	public default void drawZombieBobsledTeam(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	void drawZombieYeti(Graphics2D graphics, float x, float y, String s);

	public default void drawZombieYeti(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	
	/*-----------------------------Bullets------------------------------*/

	void drawBullet(Graphics2D graphics, float x, float y, String s);
	
	public default void drawBullet(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}
	
	/*-----------------------------Miscellaneous------------------------------*/
	
	void drawLawnMower(Graphics2D graphics, float x, float y, String color);
	
	public default void drawLawnMower(ApplicationContext context, float x, float y, String s) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, x, y, s));
	}

	

	

	

	
}

package views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
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
	
	@Override
	public void drawRectangle(Graphics2D graphics, int x, int y, int width, int height, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Rectangle2D.Float(x, y, width, height));
	}
	
	@Override
	public void drawString(Graphics2D graphics, int x, int y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.drawString(s, x, y);
	}
	
	@Override
	public void drawSun(Graphics2D graphics, float x, float y, String s) {
		graphics.setColor(Color.decode(s));
		graphics.fill(new Ellipse2D.Float(x, y, 85, 85));
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
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode("#AF6907"));
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
	/*-------------------------------POOL-------------------------------*/
	
	@Override
	public void drawCattails(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode("#DB5FBD"));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));
	}

	@Override
	public void drawSeaShroom(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode("#16D9B6"));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));
	}

	@Override
	public void drawTangleKelp(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Rectangle2D.Float(x, y, sizeOfPlant, sizeOfPlant));
		
		graphics.setColor(Color.decode("#000000"));
		graphics.fill(new Ellipse2D.Float(x + 8, y + 8, sizeOfPlant - 16, sizeOfPlant - 16));
	}

	@Override
	public void drawLilyPad(Graphics2D graphics, int x, int y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x - 15, y - 15, sizeOfPlant + 30, sizeOfPlant + 30));
		
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
	
	@Override
	public void drawBackupDancerZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

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
	public void drawDancingZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawDiggerZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	@Override
	public void drawDolphinRiderZombie(Graphics2D graphics, float x, float y, String color) {
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
	public void drawJackintheBoxZombie(Graphics2D graphics, float x, float y, String color) {
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
	public void drawNewspaperZombie(Graphics2D graphics, float x, float y, String color) {
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
	public void drawPoleVaultingZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}
	
	@Override
	public void drawScreenDoorZombie(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
	}

	@Override
	public void drawSnorkelZombie(Graphics2D graphics, float x, float y, String color) {
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

	/*-----------------------------Projectiles------------------------------*/

	int sizeOfProjectile = Projectile.getSizeOfProjectile();

	@Override
	public void drawPea(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfProjectile, sizeOfProjectile));
	}
	
	@Override
	public void drawFrozenPea(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfProjectile, sizeOfProjectile));
	}
	
	/*-----------------------------Miscellaneous------------------------------*/
	int[] SizeOfLawnMower = LawnMower.getSizeOfLawnMower();
	
	
	@Override
	public void drawLawnMower(Graphics2D graphics, float x, float y, String color) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new RoundRectangle2D.Float(x, y+(SizeOfLawnMower[1]/3), SizeOfLawnMower[0], SizeOfLawnMower[1], 10, 10));
		graphics.setColor(Color.BLACK);
		graphics.fill(new Ellipse2D.Float(x+SizeOfLawnMower[0]/4, y+SizeOfLawnMower[1]/4+(SizeOfLawnMower[1]/3), SizeOfLawnMower[0]/2, SizeOfLawnMower[1]/2));
		graphics.fill(new Rectangle2D.Float(x+10,y+3+(SizeOfLawnMower[1]/3),3,SizeOfLawnMower[1]-5));
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

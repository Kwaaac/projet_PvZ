
/**
 * EACH ZOMBIE HAS :
 * A NAME
 * A COLOR
 * COORDINATES
 * A SPEED
 * A SPAWNING CONDITION
 * A METHODE FOR CREATE A NEW ONE
 * A DRAW FOR THE BORD
 * SOMETIMES AN ACTION
 */

package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import models.Chrono;
import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.MovingElement;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;

public abstract class Zombie extends Entities implements MovingElement, IZombie, Serializable, Comparable<Zombie> {
	private double actSpeed;
	private double speedRecord;
	private boolean testMode;

	private final static int sizeOfZombie = 75;

	protected int shootBarMax;
	protected long shootBar;
	protected long shootTime;
	private final int threat;

	private Chrono slowedTime = new Chrono();
	private int slowedLimit;
	private boolean slowed = false;
	protected boolean switchSpeed = false;

	private Chrono stunnedTime = new Chrono();
	private long stunnedLimit;
	private boolean stunned = false;

	private boolean fly;

	protected boolean fertilizer;

	// Changed in the file properties
	protected final static HashMap<String, Double> mSpeed = new HashMap<String, Double>() {
		{
			put("reallyFast", 0.0);
			put("fast", 0.0);
			put("medium", 0.0);
			put("slow", 0.0);
			put("verySlow", 0.0);
			put("ultraSlow", 0.0);
		}

	};

	public Zombie(int x, int y, int damage, int life, int threat, String s, boolean fertilizer, boolean fly) {
		super(x, y, damage, life, false);

		if (mSpeed.get(s) == null) {
			throw new IllegalStateException("The selected speed does not exist");
		}
		speedRecord = mSpeed.get(s);

		actSpeed = speedRecord;
		shootBarMax = (int) (speedRecord * -7500);
		shootTime = System.currentTimeMillis();

		this.threat = threat;
		this.fertilizer = fertilizer;
		this.fly = fly;

		slowedTime.steady();
		stunnedTime.steady();

	}

	public Zombie(int x, int y, int damage, int life, int threat, String s, boolean fertilizer) {
		this(x, y, damage, life, threat, s, fertilizer, false);
	}

	private final static ArrayList<Zombie> common = new ArrayList<Zombie>(
			Arrays.asList(new NormalZombie(), new FlagZombie(), new BucketheadZombie(), new ConeheadZombie(),
					new NewspaperZombie(), new PoleVaultingZombie(), new BalloonZombie(), new DancingZombie(),
					new JackintheBoxZombie(), new Zomboni()));

	// zombies that are allowed to swim
	private final static ArrayList<Zombie> pool = new ArrayList<Zombie>(
			Arrays.asList(new DolphinRiderZombie(), new SnorkelZombie(), new DuckyTubeZombie()));

	/**
	 * give the zombie a case and add the zombie on the zombieList of the cell
	 * 
	 * @param data Data of the main Bord
	 */
	@Override
	public void setCase(SimpleGameData data) {
		int cX = BordView.caseXFromX(x);
		int cY = BordView.caseYFromY(y);

		Coordinates caseZ = new Coordinates(cX, cY);

		if (!caseZ.equals(caseXY)) {

			Cell actCell = data.getCell(cY, cX);
			if (actCell != null) {

				if (!(cX == data.getNbColumns() - 1) || cX < 0) {
					Cell cell = data.getCell(caseXY.getJ(), caseXY.getI());
					if (cell != null) {
						cell.removeZombie(this);
					}
				}

				actCell.addZombie(this);

				caseXY = caseZ;
			}
		}
	}

	@Override
	/**
	 * Allow the zombie to move Allow the zombie to recover from it's statue effect
	 */
	public void move() {
		if (slowedTime.asReachTimer(slowedLimit)) { // slowing effect stop
			slowedTime.steady();
			slowed = false;
		}

		if (stunnedTime.asReachTimerMs(stunnedLimit)) {// Stunnung effect stop
			stunnedTime.steady();
			stunned = false;
		}

		setX((float) (getX() + actSpeed));
	}

	/**
	 * 
	 * 
	 * @param speed the new speed from
	 */
	protected void setBasicSpeed(String speed) {
		if (mSpeed.get(speed) == null) {
			throw new IllegalStateException("The selected speed does not exist");
		}
		speedRecord = mSpeed.get(speed);
	}

	/**
	 * Set the actual speed
	 * 
	 * @param speed The new speed
	 */
	public void setSpeed(double speed) {
		actSpeed = speed;
	}

	/**
	 * Change the zombie's speed according to it's status
	 */
	@Override
	public void go() {
		double speedCheck = speedRecord;

		if (testMode) {
			speedCheck -= 2;
		}

		if (!isBad() || switchSpeed) { // Zombie hypnotized
			speedCheck *= -1;
		}

		if (slowed) {
			speedCheck /= 2;
		}

		if (stunned) {
			speedCheck = 0;
		}
		setSpeed(speedCheck);
	}

	/**
	 * 
	 * @return size of zombie
	 */
	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	/**
	 * stop the zombie
	 */
	public void stop() {
		actSpeed = 0;
	}

	/**
	 * TestMode ON
	 */
	public void SpeedBoostON() {
		testMode = true;
	}

	/**
	 * TestMode Off
	 */
	public void SpeedBoostOFF() {
		testMode = false;
	}

	/**
	 * Increase the shoorbar
	 */
	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	/**
	 * Reset the shootBar
	 */
	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	/**
	 * @return True if the zombie can eat, false otherwise
	 */
	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	/**
	 * @return the hitbox of the plant
	 */
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfZombie);
	}

	/**
	 * if a zombie eat a brain, the player loose
	 * 
	 * @param xOrigin    origin of the board
	 * @param squareSize square size of the board
	 * @return true if the zombie is eating a brain, false otherwise
	 */
	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize;
	}

	/**
	 * if a zombie go out of the board
	 */
	public boolean isOutside(int xOrigin, int sqrSize, int nbrSqr) {
		return x > xOrigin + sqrSize * nbrSqr;
	}

	public void chopped(boolean sharp) {
	}

	/**
	 * Used to draw if a zombie as any fertilizer or when it is slowed
	 * 
	 * Draw for a normal zombie size, if the zombie has a different way to be drawed
	 * please refer theses two condition directly into their draw method.
	 */
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {

		if (slowed) {
			graphics.setColor(new Color(99, 197, 255, 100));
			graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		}

		if (fertilizer) {
			graphics.setColor(new Color(38, 198, 35, 70));
			graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		}

	}

	/**
	 * cette m�thode a pour but de r�partir les d�gats aux diff�rentes
	 * entit�es du jeu, une fois les d�gat correctement attribuer et la vie des
	 * entit�es mise a jour elle aide par la suite a les redistribuer dans les
	 * diff�rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entit�es qui subiront les d�gats de l'entit�e
	 *                 objet utilisant la m�thode et qui attaqueront cette meme
	 *                 entit�es tous ensemble
	 */

	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data) {
		List<Projectile> Le;
		int thisY = view.lineFromY(this.getY());
		int thisX = view.columnFromX(this.getX());

		Cell actualCell = data.getCell(thisY, thisX);

		if (actualCell != null) {
			Le = actualCell.getProjectilesInCell();

			// Check the Cell behind him
			Cell prevCell = data.getCell(thisY, thisX + 1);
			if (prevCell != null) {
				Le.addAll(prevCell.getProjectilesInCell());
			}

			for (Projectile e : Le) {

				if (this.hit(e) && !(e.isInConflict()) && this.isBad() && !(fly && !e.isFlying())) {
					e.action(data);
					chopped(e.isSharp());
					this.slowed(e.isSlowing());
					e.setConflictMode(true);
					takeDmg(e.getDamage());
					e.takeDmg(1);

					if (e.isDead()) {
						DPe.addInDP(e);
					}
					/*
					 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
					 * attaquant ne sois mort on empeche des echange de d�gats(on en a besoin pour
					 * pas qu'une plante morte soit capable de tu� apr�s sa mort)
					 */
					else if (this.isDead()) {
						if (fertilizer) {
							data.addFertilizer();
						}
						e.setConflictMode(false);
						DPe.addInDP(this);
						break;
					}
				}

			}
		}
	}

	/**
	 * Conflict of the plants versus the zombies
	 * 
	 * @param deadPoolE the deadpool to kill the entity
	 * @param view      view of the board
	 * @param data      data of the board
	 * @param str
	 */
	public void conflictPvZ(DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		Plant p;
		if (data.getCell(view.lineFromY(y), view.columnFromX(x)) != null
				&& data.getCell(view.lineFromY(y), view.columnFromX(x)).isPlantedPlant()) {

			p = data.getCell(view.lineFromY(y), view.columnFromX(x)).getPlantToAttack();
			if (this.hit(p) && !fly) {
				this.stop();
				if (this.readyToshot()) {

					this.mortalKombat(p);
					this.resetAS();
				}
			}
			if (p.isDead()) {
				str.append(p + " meurt\n");
				deadPoolE.add(p);
				data.getCell(view.lineFromY(p.getY()), view.columnFromX(p.getX())).removePlant(p);
			}
		}
	}

	/**
	 * Conflict of the good zombies versus the bad zombies
	 * 
	 * @param deadPoolE the deadpool to kill the entity
	 * @param view      view of the board
	 * @param data      data of the board
	 * @param str
	 */
	public void conflictZvZ(DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		ArrayList<Zombie> zombies;
		int thisY = view.lineFromY(y);
		int thisX = view.columnFromX(x);

		Cell actualCell = data.getCell(thisY, thisX);

		if (isBad()) {

			if (actualCell != null && actualCell.isThereBadZombies()) {

				zombies = actualCell.getZombiesToAttack(this);

				// Check the Cell behind him
				Cell prevCell = data.getCell(thisY, thisX + 1);
				if (prevCell != null) {
					zombies.addAll(prevCell.getZombiesToAttack(this));
				}

				for (Zombie z : zombies) {
					if (this.hit(z)) {
						this.stop();
						z.stop();

						// Bad attack Good
						if (!isStunned()) {
							if (readyToshot()) {
								z.takeDmg(damage);
								resetAS();
							}
						}

						// Good attack Bad
						if (z.readyToshot()) {
							takeDmg(z.damage);
							z.resetAS();
						}

					}
					if (z.isDead()) {
						str.append(z + " meurt\n");
						deadPoolE.add(z);
						data.getCell(view.lineFromY(z.getY()), view.columnFromX(z.getX())).removeZombie(z);
					}

				}
			}
		} else {
			if (!isBad() && isOutside(view.getXOrigin(), BordView.getSquareSize(), data.getNbColumns())) {
				str.append(this + " meurt\n");
				deadPoolE.add(this);
				Cell cell = data.getCell(view.lineFromY(y), view.columnFromX(x));
				if (cell != null) {
					cell.removeZombie(this);
				}
			}
		}
	}

	/**
	 * Conflict of the lawnMower versus the zombies
	 * 
	 * @param deadPoolE the deadpool to kill the entity
	 * @param view      view of the board
	 * @param data      data of the board
	 * @param str
	 */
	public void conflictLvZ(DeadPool deadPoolE, List<LawnMower> myLawnMower, BordView view, SimpleGameData data,
			StringBuilder str) {
		for (LawnMower l : myLawnMower) {
			if (this.hit(l)) {
				if (!(l.isMoving())) {
					l.go();
				}
				if (fertilizer) {
					data.addFertilizer();
				}
				life = 0;
				str.append(this + " meurt tu� par une tondeuse\n");
				l.setLife(100000);
			}
		}
	}

	/**
	 * Handle the conflict beetween the entity
	 * 
	 * @param myZombies   List of all the zombies
	 * @param myBullet    List of all the projectiles
	 * @param list        List of all the plants
	 * @param myLawnMower List of all the lawnMower
	 * @param deadPoolE   deadpool
	 * @param view        view of the main board
	 * @param data        data of the main board
	 * @param str
	 */
	public static void ZCheckConflict(List<Zombie> myZombies, List<Projectile> myBullet, List<Plant> list,
			List<LawnMower> myLawnMower, DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {

		LawnMower.hasToDie(myLawnMower, deadPoolE, data, view);
		Plant.hasToDieAll(deadPoolE, list, data); // gere les mort si il n'y a aucun zombie sur le plateau
		Projectile.hasToDie(deadPoolE, myBullet, data);

		for (Zombie z : myZombies) {
			z.go();
			z.conflictBvZ(deadPoolE, view, data);
			if (z.action(view, data, myZombies) && !z.isStunned()) {
				z.incAS();
				z.conflictPvZ(deadPoolE, view, data, str);
			}
			z.conflictZvZ(deadPoolE, view, data, str);
			z.conflictLvZ(deadPoolE, myLawnMower, view, data, str);
			if (z.isDead()) {
				str.append(z + " meurt\n");
				if (z.isGifted()) {
					data.addFertilizer();
				}
				deadPoolE.add(z);

			}

		}

	}

	/**
	 * 
	 * @return true if the zombie is slowed, false otherwise
	 */
	public boolean isSlowed() {
		return slowed;
	}

	/**
	 * Apply the slowing effect on the zombie
	 * 
	 * @param slowingTime The slowing time (seconds)
	 */
	public void slowed(int slowingTime) {
		if (slowingTime > 0) {
			slowedLimit = slowingTime;
			slowed = true;
			slowedTime.start();
		}
	}

	/**
	 * 
	 * @return true if the zombie is stunned, false otherwise
	 */
	public boolean isStunned() {
		return stunned;
	}

	/**
	 * Apply the stun effect on the zombie
	 * 
	 * @param stunningTime The stunning time (milliSeconds)
	 */
	public void stunned(long stunningTime) {
		stunnedLimit = stunningTime;
		stunned = true;
		stunnedTime.start();
	}

	/*
	 * For zombies that don't have actions
	 * 
	 */
	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		return true;
	}

	/**
	 * get the zombie according to the map
	 * 
	 * @param map actual map
	 * @return the available zombies
	 */
	public static ArrayList<Zombie> getZombieList(String map) {
		if (map == "Pool" || map == "NightPool") {
			ArrayList<Zombie> res = new ArrayList<Zombie>();
			res.addAll(common);
			res.addAll(pool);
			return res;
		}
		return common;
	}

	/**
	 * @return the threat of the zombie
	 */
	@Override
	public int getThreat() {
		return threat;
	}

	/**
	 * @return the probablity of spawning of the zombie
	 */
	@Override
	public Integer getProb(int difficulty) {
		return (int) (((100 / threat) * (difficulty)) * 0.55 + 0.10 * threat);
	}

	/**
	 * @return if a zombie can spawn according to the actual diffuculty of the game
	 */
	@Override
	public boolean canSpawn(int difficulty) {
		return threat <= difficulty;
	}

	/**
	 * @return if the zombie is common, meaning it's not a zombie from a map
	 */
	@Override
	public boolean isCommon() {
		return true;
	}

	/**
	 * Will change the zombie's side and update it's position in the cell list
	 */
	@Override
	public void reverseTeam(SimpleGameData data) {
		Cell actualCell = data.getCell(getCaseJ(), getCaseI());

		if (actualCell != null) {
			actualCell.removeZombie(this);
			super.reverseTeam(data);
			actualCell.addZombie(this);
		}
	}

	/**
	 * add a fertilizer the to zombie
	 */
	@Override
	public void giftZombie() {
		this.fertilizer = true;
	}

	/**
	 * 
	 * @return if the zombie has a fertilizer
	 */
	private boolean isGifted() {
		return fertilizer;
	}

	/**
	 * Used to apply the effect of the MagnetShroom
	 * 
	 * @return true and apply the effect if the zombie is magnetizable, false
	 *         otherwise and does nothing
	 */
	@Override
	public boolean magnetizable() {
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Zombie)) {
			return false;
		}
		Zombie z = (Zombie) o;
		return super.equals(z) && actSpeed == z.actSpeed && shootTime == z.shootTime && threat == z.threat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), actSpeed, shootBarMax, shootTime, threat);
	}

	/**
	 * @set the speed properties of zombies from the file "Properties.txt"
	 */
	public static void setZombieMoveSpeed_reallyFast(Double x) {
		mSpeed.put("reallyFast", x);
	}

	public static void setZombieMoveSpeed_fast(Double x) {
		mSpeed.put("fast", x);
	}

	public static void setZombieMoveSpeed_medium(Double x) {
		mSpeed.put("medium", x);
	}

	public static void setZombieMoveSpeed_slow(Double x) {
		mSpeed.put("slow", x);
	}

	public static void setZombieMoveSpeed_verySlow(Double x) {
		mSpeed.put("verySlow", x);
	}

	public static void setZombieMoveSpeed_ultraSlow(Double x) {
		mSpeed.put("ultraSlow", x);
	}

	/**
	 * @return True is the zombie fly, false otherwise
	 */
	public boolean isFlying() {
		return fly;
	}

	/**
	 * Switch the fly state of zombie
	 */
	public void switchFly() {
		fly = !fly;
	}

	/**
	 * compare the zombies according to theirs x
	 */
	@Override
	public int compareTo(Zombie z) {
		if (x < z.x) {
			return 1;
		} else if (x > z.x) {
			return -1;
		} else {
			return 0;
		}
	}

}

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
import models.nextzombie.DuckyTubeZombie;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;

public abstract class Zombie extends Entities implements MovingElement, IZombie, Serializable {
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

	private Chrono stunnedTime = new Chrono();
	private long stunnedLimit;
	private boolean stunned = false;
	
	private boolean fly = false;

	protected boolean fertilizer;

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

	public Zombie(int x, int y, int damage, int life, int threat, String s, boolean fertilizer) {
		super(x, y, damage, life, false);

		speedRecord = mSpeed.get(s); // Normal Speed

		actSpeed = speedRecord;
		shootBarMax = (int) (speedRecord * -7500);
		shootTime = System.currentTimeMillis();
		this.threat = threat;
		this.fertilizer = fertilizer;
		slowedTime.steady();
		stunnedTime.steady();
	}

	private final static ArrayList<Zombie> common = new ArrayList<Zombie>(
			Arrays.asList(new NormalZombie(), new FlagZombie(), new BucketheadZombie(), new ConeheadZombie(),
					new NewspaperZombie(), new PoleVaultingZombie()));
	private final static ArrayList<Zombie> pool = new ArrayList<Zombie>(
			Arrays.asList(new DolphinRiderZombie(), new DuckyTubeZombie()));

	public float getX() {
		return super.getX();
	}

	@Override
	/**
	 * give the zombie a case and add the zombie on the zombieList of the cell
	 * 
	 * @param data Data of the main Bord
	 */
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

	public float getY() {
		return super.getY();
	}

	@Override
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

	public double getSpeed() {
		return actSpeed;
	}

	protected void setBasicSpeed(String speed) {
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
	 * Change the zombie's speed according to it's affliction
	 */
	@Override
	public void go() {
		double speedCheck = speedRecord;

		if (testMode) {
			speedCheck -= 2;
		}

		if (!isBad()) { // Zombie hypnotized
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

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public void stop() {
		actSpeed = 0;
	}

	public void SpeedBoostON() {
		testMode = true;
	}

	public void SpeedBoostOFF() {
		testMode = false;
	}

	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfZombie);
	}

	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize;
	}

	public boolean soonEatingBrain(int xOrigin, int squareSize) {
		return (xOrigin - squareSize < x && x < xOrigin);
	}

	public int whereIsHeEatingBrain(int xOrigin, int squareSize, float y, int Yorigin, BordView view) {
		if (this.soonEatingBrain(xOrigin, squareSize)) {
			return view.indexFromReaCoord(y, Yorigin);
		}
		return -1;
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
	 * cette m�thode a pour but de r�partir les d�gats aux diff�rentes entit�es du
	 * jeu, une fois les d�gat correctement attribuer et la vie des entit�es mise a
	 * jour elle aide par la suite a les redistribuer dans les diff�rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entit�es qui subiront les d�gats de l'entit�e objet
	 *                 utilisant la m�thode et qui attaqueront cette meme entit�es
	 *                 tous ensemble
	 */

	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data) {
		ArrayList<Projectile> Le;
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
				if (this.hit(e) && !(e.isInConflict()) && this.isBad()) {
					this.slowed(e.isSlowing());
					e.setConflictMode(true);
					this.mortalKombat(e);
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

	public void conflictPvZ(DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		Plant p;
		if (data.getCell(view.lineFromY(y), view.columnFromX(x)) != null
				&& data.getCell(view.lineFromY(y), view.columnFromX(x)).isPlantedPlant()) {

			p = data.getCell(view.lineFromY(y), view.columnFromX(x)).getPlantToAttack();
			if (this.hit(p)) {
				this.stop();
				if (this.readyToshot()) {

					this.mortalKombat(p);
					this.resetAS();
				}
			}
			if (p.isDead()) {
				str.append(p + "meurt\n");
				deadPoolE.add(p);
				data.getCell(view.lineFromY(p.getY()), view.columnFromX(p.getX())).removePlant(p);
			}
		}
	}

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
						str.append(z + "meurt\n");
						deadPoolE.add(z);
						data.getCell(view.lineFromY(z.getY()), view.columnFromX(z.getX())).removeZombie(z);
					}

				}
			}
		}
	}

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

	public static ArrayList<Zombie> getZombieList(String map) {
		if (map == "pool") {
			ArrayList<Zombie> res = new ArrayList<Zombie>();
			res.addAll(common);
			res.addAll(pool);
			return res;
		}
		return common;
	}

	@Override
	public int getThreat() {
		return threat;
	}

	@Override
	public Integer getProb(int difficulty) {
		return (int) (((100 / threat) * (difficulty)) * 0.55 + 0.10 * threat);
	}

	@Override
	public boolean canSpawn(int difficulty) {
		return threat <= difficulty;
	}

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

	@Override
	public void giftZombie() {
		this.fertilizer = true;
	}

	private Boolean isGifted() {
		return (Boolean) fertilizer;
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

	public static void setZombieMoveSpeed_reallyFast(Double x) {
		mSpeed.put("reallyFast",x);
	}

	public static void setZombieMoveSpeed_fast(Double x) {
		mSpeed.put("fast",x);
	}

	public static void setZombieMoveSpeed_medium(Double x) {
		mSpeed.put("medium",x);
	}

	public static void setZombieMoveSpeed_slow(Double x) {
		mSpeed.put("slow",x);
	}

	public static void setZombieMoveSpeed_verySlow(Double x) {
		mSpeed.put("verySlow",x);
	}

	public static void setZombieMoveSpeed_ultraSlow(Double x) {
		mSpeed.put("ultraSlow",x);
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

}

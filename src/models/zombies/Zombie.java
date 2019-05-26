package models.zombies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public abstract class Zombie extends Entities implements MovingElement, IZombie, Serializable {
	private double speed;
	private final static int sizeOfZombie = 75;

	protected int shootBarMax;
	protected long shootBar;
	protected long shootTime;
	private final int threat;

	protected Chrono slowedTime = new Chrono();
	protected int slowedLimit;
	protected boolean afflicted = false;

	protected final HashMap<String, Double> mSpeed = new HashMap<String, Double>() {
		{
			put("reallyFast", -1.5);// 9+
			put("fast", -1.38);// 7
			put("medium", -1.05);// 5.5
			put("slow", -0.93);// 4.7
			put("verySlow", -0.5);// 2.5
			put("ultraSlow", -0.3);// 1.5
		}

	};

	public Zombie(int x, int y, int damage, int life, int threat, String s) {
		super(x, y, damage, life, false);
		speed = mSpeed.get(s);
		shootBarMax = (int) (speed * -7500);
		shootTime = System.currentTimeMillis();
		this.threat = threat;
		slowedTime.steady();
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
					data.getCell(caseXY.getJ(), caseXY.getI()).removeZombie(this);
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
		setX((float) (getX() + getSpeed()));
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double d) {
		speed = d;
	}

	public void go(float x) {
		if (!afflicted) { // zombie without affliction
			setSpeed(x);

		} else { // slowed
			setSpeed(x / 2);
			shootBarMax = (int) (getSpeed() * -7500);

			if (slowedTime.asReachTimer(6)) { // slowing effect stop
				slowedTime.steady();
				afflicted = false; // a upgrade avec le shockEffect
			}
		}

	}

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public void stop() {
		this.setSpeed(0);
	}

	public void SpeedBoostON() {
		setSpeed(getSpeed() - 2);
	}

	public void SpeedBoostOFF() {
		setSpeed(getSpeed() + 2);
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
	 * cette mï¿½thode a pour but de rï¿½partir les dï¿½gats aux diffï¿½rentes
	 * entitï¿½es du jeu, une fois les dï¿½gat correctement attribuer et la vie des
	 * entitï¿½es mise a jour elle aide par la suite a les redistribuer dans les
	 * diffï¿½rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entitï¿½es qui subiront les dï¿½gats de l'entitï¿½e
	 *                 objet utilisant la mï¿½thode et qui attaqueront cette meme
	 *                 entitï¿½es tous ensemble
	 */

	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data) {
		ArrayList<Projectile> Le;
		if (data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())) != null) {
			Le = data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).getProjectilesInCell();
			for (Projectile e : Le) {
				e.action();
				if (this.hit(e) && !(e.isInConflict()) && this.isBad()) {
					this.slowed(e.isSlowing());
					e.setConflictMode(true);
					this.mortalKombat(e);
					if (e.isDead()) {
						DPe.addInDP(e);
					}
					/*
					 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
					 * attaquant ne sois mort on empeche des echange de dï¿½gats(on en a besoin pour
					 * pas qu'une plante morte soit capable de tuï¿½ aprï¿½s sa mort)
					 */
					else if (this.isDead()) {
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
		if (data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())) != null
				&& data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).isThereZombies()) {
			zombies = data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).getZombiesInCell();
			for (Zombie z : zombies) {
				if (z.isGood() && this.hit(z)) {
					this.stop();
					z.stop();
					if (this.readyToshot()) {
						(z).mortalKombat(this);
						this.resetAS();
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

	public void conflictLvZ(DeadPool deadPoolE, ArrayList<LawnMower> myLawnMower, BordView view, SimpleGameData data,
			StringBuilder str) {
		if (x < view.getXOrigin()) {
			for (LawnMower l : myLawnMower) {
				if (this.hit(l)) {
					if (!(l.isMoving())) {
						l.go();
					}
					life = 0;
					str.append(this + " meurt tué par une tondeuse\n");
					l.setLife(100000);
				}
			}
		}
	}

	public static void ZCheckConflict(ArrayList<Zombie> myZombies, ArrayList<Projectile> myBullet,
			ArrayList<Plant> myPlants, ArrayList<LawnMower> myLawnMower, DeadPool deadPoolE, BordView view,
			SimpleGameData data, StringBuilder str) {
		LawnMower.hasToDie(myLawnMower, deadPoolE, data, view);
		Plant.hasToDie(deadPoolE, myPlants, data); // gere les mort si il n'y a aucun zombie sur le plateau
		Projectile.hasToDie(deadPoolE, myBullet, data);
		for (Zombie z : myZombies) {
			z.go();
			z.incAS();
			z.conflictBvZ(deadPoolE, view, data);
			if (z.action(data)) {
				z.conflictPvZ(deadPoolE, view, data, str);
				z.conflictZvZ(deadPoolE, view, data, str);
			}
			z.conflictLvZ(deadPoolE, myLawnMower, view, data, str);
			if (z.isDead()) {
				deadPoolE.add(z);
				str.append(z + " meurt\n");
			}

		}

	}

	public void slowed(int slowing) {
		if (slowing > 0) {
			slowedLimit = slowing;
			afflicted = true;
			slowedTime.start();
		}
	}

	/*
	 * For zombies that don't have actions
	 * 
	 */
	@Override
	public boolean action(SimpleGameData dataBord) {
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Zombie)) {
			return false;
		}
		Zombie z = (Zombie) o;
		return super.equals(z) && speed == z.speed && shootTime == z.shootTime && threat == z.threat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), speed, shootBarMax, shootTime, threat);
	}
}

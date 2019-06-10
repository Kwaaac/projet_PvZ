
/**
 * EACH PLANT HAS :
 * A NAME
 * A COLOR
 * COORDINATES
 * A PLANTING CONDITION
 * A METHODE FOR CREATE A NEW ONE
 * A DRAW FOR THE SELECTION BAR
 * A DRAW FOR THE BORD
 * AN ACTION
 * SOMETIMES A SUPER ACTION
 */

package models.plants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.day.*;
import models.plants.night.*;
import models.plants.pool.*;

public abstract class Plant extends Entities implements IPlant, Serializable {
	private final static int sizeOfPlant = 75; //definition de la taille de reference d'une plante
	protected final int shootBarMax; // 
	protected long shootBar; // 
	protected long shootTime; // 
	protected final Integer cost; //cout d'une plante en soleil
	protected final Long cooldown; //temps de recharge de l'action d'une plante
	private Coordinates plantSelect; //coordonnees matricielles d'une plante
	private boolean fertilized; //activation ou non d'une super action de plante
	

	// Arrayliste de l'ensemble des plantes de jour
	private final static ArrayList<Plant> day = new ArrayList<>(Arrays.asList(new CherryBomb(), new Chomper(),
			new Peashooter(), new Repeater(), new PotatoMine(), new Squash(), new SnowPea(), new SunFlower(),
			new WallNut(), new Pot(), new Jalapeno(), new Threepeater(), new SplitPea(), new GaltingPea(),
			new TwinSunFlower(), new CabbageShooter(), new Cactus()));
	
	// Arrayliste de l'ensemble des plantes de nuit
	private final static ArrayList<Plant> night = new ArrayList<>(Arrays.asList(new MagnetShroom(), new DoomShroom(),
			new FumeShroom(), new GraveBuster(), new HypnoShroom(), new IceShroom(), new PuffShroom(),
			new ScaredyShroom(), new SunShroom(), new Plantern(), new Blover()));
	
	// Arrayliste de l'ensemble des plantes de piscine
	private final static ArrayList<Plant> pool = new ArrayList<>(
			Arrays.asList(new Cattails(), new LilyPad(), new SeaShroom(), new TangleKelp()));

	// Dictionnaire de definition des differents cooldown de recharge d'action d'une plante
	private final HashMap<String, Long> mCooldown = new HashMap<String, Long>() {
		{
			put("free", (long) 0);
			put("fast", (long) 5);
			put("medium", (long) 15);
			put("slow", (long) 20);
			put("verySlow", (long) 35);
			put("bigTime", (long) 60);
		}

	};

	public Plant(int x, int y, int damage, int life, int shootBarMax, int cost, String cooldown) {
		super(x, y, damage, life, true);

		this.shootBarMax = shootBarMax;
		this.cost = cost;
		this.cooldown = mCooldown.get(cooldown);

		// Prevent the plant to shoot instantly
		shootTime = 0;
	}

	public static ArrayList<Plant> getPlantList(String s) { //getter des arraylist de plantes
		if (s == "night") {
			return night;
		}
		if (s == "pool") {
			return pool;
		}
		return day;
	}

	public int getLife() { //renvoi la vie d'une plante
		return super.life;
	}

	public void setCase(SimpleGameData data) {

	}

	/**
	 * @return Coordinates for it's placement in the PlantSelection
	 */
	public Coordinates getPlaceSelect() {
		return this.plantSelect;
	}

	public int getCost() { //renvoi le cout d'une plante
		return cost;
	}

	public static int getSizeOfPlant() { //renvoi la taille d'une plante
		return sizeOfPlant;
	}

	public long getSpeedShooting() {
		return shootBar;
	}

	public long getCooldown() { //renvoi le temps d'attente entre chaque action d'une plante
		return cooldown;
	}

	@Override
	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	@Override
	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	public long getTimer() {
		return shootBar;
	}

	@Override
	public boolean readyToshot() { //permet de lancer l'action d'une plante si le timer est plein
		return shootBar >= shootBarMax;
	}

	@Override
	public Coordinates hitBox() { //renvoi la hitbox d'une plante 
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}

	@Override
	public int getTypeOfPlant() {
		return 1;
	}

	/**
	 * For every plants, it can only be placed on grassCell and with any ground
	 * Plant and we asumme that they are main plants (Override the plant to change
	 * it)
	 * 
	 * @param the cell where the plant will be planted
	 * @return True if the cell is a correct location for the plant, false otherwise
	 */
	@Override
	public boolean plantingCondition(Cell cell) {

		if (cell.isGrass()) {
			return cell.addPlant(this);

		} else if (cell.isGroundPlantPlanted()) {
			return cell.addPlant(this);
		}

		return false;
	}
	
	public boolean feedPlant() {
		if(fertilized) {
			return false;
		}
		fertilized = true;
		return true;
	}
	
	public Boolean isFertilized() {
		return fertilized;
	}
	
	public void unFeed() {
		fertilized = false;
	}

	public static void hasToDieAll(DeadPool DPe, List<Plant> list, SimpleGameData data) {
		for (Plant p : list) {
			p.hasToDie(DPe, data);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Plant)) {
			return false;
		}
		Plant p = (Plant) o;
		return super.equals(p) && shootBarMax == p.shootBarMax && shootTime == p.shootTime && cost == p.cost
				&& cooldown == p.cooldown;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), shootBarMax, shootTime, cost, cooldown);
	}

	public void hasToDie(DeadPool DPe, SimpleGameData data) {
	}
}

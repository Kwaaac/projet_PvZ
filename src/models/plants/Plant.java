
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

import java.awt.image.Kernel;
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
	private final static int sizeOfPlant = 75; 
	
	// system of a loading bar
	
	protected final int shootBarMax;
	protected long shootBar; 
	protected long shootTime; 
	
	protected final Integer cost; 
	protected final Long cooldown;
	private Coordinates plantSelect;
	private boolean fertilized;

	// Arraylist of all the day plants
	private final static ArrayList<Plant> day = new ArrayList<>(Arrays.asList(new CherryBomb(), new Chomper(),
			new Peashooter(), new Repeater(), new PotatoMine(), new Squash(), new SnowPea(), new SunFlower(),
			new WallNut(), new Pot(), new Jalapeno(), new Threepeater(), new SplitPea(), new GaltingPea(),
			new TwinSunFlower(), new CabbageShooter(), new KernelPult(), new Cactus()));
	
	// Arraylist of all the night plants
	private final static ArrayList<Plant> night = new ArrayList<>(Arrays.asList(new MagnetShroom(), new DoomShroom(),
			new FumeShroom(), new GraveBuster(), new HypnoShroom(), new IceShroom(), new PuffShroom(),
			new ScaredyShroom(), new SunShroom(), new Plantern(), new Blover()));
	
	// Arraylist of all the pool plants
	private final static ArrayList<Plant> pool = new ArrayList<>(
			Arrays.asList(new Cattails(), new LilyPad(), new SeaShroom(), new TangleKelp()));

	// cooldown (seconds)
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
		if(mCooldown.get(cooldown) == null) {
			throw new IllegalStateException("The selected cooldown of the " + this + " does not exist");
		}
		this.cooldown = mCooldown.get(cooldown);

		// Prevent the plant to shoot instantly
		shootTime = 0;
	}

	public static ArrayList<Plant> getPlantList(String s) {
		if (s == "night") {
			return night;
		}
		if (s == "pool") {
			return pool;
		}
		return day;
	}

	/**
	 * @return plant's life
	 */
	public int getLife() { 
		return super.life;
	}

	/**
	 * @return Coordinates for it's placement in the PlantSelection
	 */
	public Coordinates getPlaceSelect() {
		return this.plantSelect;
	}

	/**
	 * 
	 * @return plant's cost
	 */
	public int getCost() { 
		return cost;
	}

	/**
	 * 
	 * @return size of plants
	 */
	public static int getSizeOfPlant() { 
		return sizeOfPlant;
	}

	/**
	 * 
	 * @return plant's cd
	 */
	public long getCooldown() {
		return cooldown;
	}

	
	/**
	 * Increase the shoorbar
	 */
	@Override
	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	/**
	 * Reset the shootBar
	 */
	@Override
	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	/**
	 * @return True if the plant can shoot, false otherwise
	 */
	@Override
	public boolean readyToshot() { 
		return shootBar >= shootBarMax;
	}

	/**
	 * @return the hitbox of the plant
	 */
	@Override
	public Coordinates hitBox() { 
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}

	/**
	 * Return the type of the plant, by default, it's a main plant
	 */
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
	
	/**
	 * Fertilise the plant
	 * 
	 * @return true if the plant has been fertilized
	 */
	public boolean feedPlant() {
		if(fertilized) {
			return false;
		}
		fertilized = true;
		return true;
	}
	
	/**
	 * 
	 * @return true is the plant is fertilized, false otherwise
	 */
	public Boolean isFertilized() {
		return fertilized;
	}
	
	/**
	 * unfertilize the plant
	 */
	public void unFeed() {
		fertilized = false;
	}

	/**
	 * Kill all the plant that must die
	 * 
	 * @param DPe deapool of entities
	 * @param list List of plants
	 * @param data databoard of the game
	 */
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

	/**
	 * Kill the plant
	 * 
	 * @param list List of plants
	 * @param data databoard of the game
	 */
	public void hasToDie(DeadPool DPe, SimpleGameData data) {
	}
}

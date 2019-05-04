package models.plants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.SimpleGameData;
import models.plants.day.CherryBomb;
import models.plants.day.Chomper;
import models.plants.day.Peashooter;
import models.plants.day.PotatoMine;
import models.plants.day.Repeater;
import models.plants.day.SnowPea;
import models.plants.day.SunFlower;
import models.plants.day.WallNut;
import models.plants.night.DoomShroom;
import models.plants.night.FumeShroom;
import models.plants.night.GraveBuster;
import models.plants.night.HypnoShroom;
import models.plants.night.IceShroom;
import models.plants.night.PuffShroom;
import models.plants.night.ScaredyShroom;
import models.plants.night.SunShroom;
import models.plants.pool.Cattails;
import models.plants.pool.LilyPad;
import models.plants.pool.SeaShroom;
import models.plants.pool.TangleKelp;
import models.zombies.Zombie;

public abstract class Plant extends Entities implements IPlant {
	private final String type = "Plant";
	private final static int sizeOfPlant = 75;
	protected final int shootBarMax;
	protected long shootBar;
	protected long shootTime;
	protected final int cost;
	protected final Long cooldown;
	private Coordinates plantSelect;
	
	
	
	private final static ArrayList<Plant> day = new ArrayList<>(Arrays.asList(new CherryBomb(), new Chomper(), new Peashooter(), new PotatoMine(), new Repeater(), new SnowPea(), new SunFlower(), new WallNut()));
	private final static ArrayList<Plant> night = new ArrayList<>(Arrays.asList(new DoomShroom(), new FumeShroom(), new GraveBuster(), new HypnoShroom(), new IceShroom(), new PuffShroom(), new ScaredyShroom(), new SunShroom()));
	private final static ArrayList<Plant> pool = new ArrayList<>(Arrays.asList(new Cattails(), new LilyPad(), new SeaShroom(), new TangleKelp()));
	
	private final HashMap<String, Long> mCooldown = new HashMap<String, Long>();
	
//	static {
//		mCooldown.put("free", (long) 0);
//		mCooldown.put("fast", (long) 5);
//		mCooldown.put("medium", (long) 15);
//		mCooldown.put("slow", (long) 20);
//		mCooldown.put("verySlow", (long) 35);
//		mCooldown.put("bigTime", (long) 60);
//		
//	}

	
	public Plant(int x, int y, int damage, int life, int shootBarMax, int cost, String cooldown) {
		super(x, y, damage, life);
		
		mCooldown.put("free", (long) 0);
		mCooldown.put("fast", (long) 5);
		mCooldown.put("medium", (long) 15);
		mCooldown.put("slow", (long) 20);
		mCooldown.put("verySlow", (long) 35);
		mCooldown.put("bigTime", (long) 60);
		
		this.shootBarMax = shootBarMax;
		this.cost = cost;
		this.cooldown = mCooldown.get(cooldown);
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
	 * @return Coordinates for it's placement in the PlantSelection
	 */
	public Coordinates getPlaceSelect() {
		return this.plantSelect;
	}

	public int getCost() {
		return cost;
	}

	public String toString() {
		return "Type: " + type;
	}

	public static int getSizeOfPlant() {
		return sizeOfPlant;
	}

	public long getSpeedShooting() {
		return shootBar;
	}

	public long getCooldown() {
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
	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	@Override
	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfPlant);
	}

	public static void hasToDie(DeadPool DPe, ArrayList<Plant> Mp, ArrayList<Zombie> myZombies, SimpleGameData data) {
		if (myZombies.size() == 0) {
			for (Plant p : Mp) {
				if (p.isDead()) {
					data.getCell(p.getCaseJ(), p.getCaseI()).removePlant();
					DPe.add(p);
				}
			}

		}
	}
	
	@Override
	public boolean canBePlantedOnWater() {
		return false;
	}
	
	@Override 
	public boolean isLilyPad() {
		return false;
	}

}

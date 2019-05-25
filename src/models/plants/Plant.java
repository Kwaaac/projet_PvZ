package models.plants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.SimpleGameData;
import models.cells.Cell;
import models.cells.TileCell;
import models.cells.WaterCell;
import models.plants.day.*;
import models.plants.night.*;
import models.plants.pool.*;
import models.zombies.Zombie;
import views.BordView;

public abstract class Plant extends Entities implements IPlant, Serializable {
	private final static int sizeOfPlant = 75;
	protected final int shootBarMax;
	protected long shootBar;
	protected long shootTime;
	protected final Integer cost;
	protected final Long cooldown;
	private Coordinates plantSelect;

	private final static ArrayList<Plant> day = new ArrayList<>(
			Arrays.asList(new CherryBomb(), new Chomper(), new Peashooter(), new Repeater(), new PotatoMine(), new Squash(),
					new SnowPea(), new SunFlower(), new WallNut(), new Pot()));
	private final static ArrayList<Plant> night = new ArrayList<>(
			Arrays.asList(new DoomShroom(), new FumeShroom(), new GraveBuster(), new HypnoShroom(), new IceShroom(),
					new PuffShroom(), new ScaredyShroom(), new SunShroom()));
	private final static ArrayList<Plant> pool = new ArrayList<>(
			Arrays.asList(new Cattails(), new LilyPad(), new SeaShroom(), new TangleKelp()));

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

	public static ArrayList<Plant> getPlantList(String s) {
		if (s == "night") {
			return night;
		}
		if (s == "pool") {
			return pool;
		}
		return day;
	}

	public void setCase(SimpleGameData data) {
		int cX = BordView.caseXFromX(x);
		int cY = BordView.caseYFromY(y);

		Coordinates caseZ = new Coordinates(cX, cY);

		if (!caseZ.equals(caseXY)) {

			Cell actCell = data.getCell(cY, cX);
			if (actCell != null) {

				if (!(cX == data.getNbColumns() - 1)) {
					data.getCell(caseXY.getJ(), caseXY.getI()).removePlant(this);
				}

				actCell.addPlant(this);

				caseXY = caseZ;
			}
		}
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
					data.getCell(p.getCaseJ(), p.getCaseI()).removePlant(p);
					DPe.add(p);
				}
			}

		}
	}

	@Override
	public int getTypeOfPlant() {
		return 1;
	}

	/**
	 * For every plants, it can not be placed on water or roof and with any ground Plant and we asumme
	 * that they are main plants (Override the plant to change it)
	 * 
	 * @param the cell where the plant will be planted
	 * @return True if the cell is a correct location for the plant, false otherwise
	 */
	@Override
	public boolean plantingCondition(Cell cell) {

		
		if (!(cell.equals(new WaterCell()) || cell.equals(new TileCell()))) {
			return cell.addPlant(this);
			
		} else if (cell.isGroundPlantPlanted()) {

			Plant gp = cell.getGroundPlant();
			
			if (gp.equals(new LilyPad()) || gp.equals(new Pot())) {
				return cell.addPlant(this);
			}
				
		}

		return false;
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
}

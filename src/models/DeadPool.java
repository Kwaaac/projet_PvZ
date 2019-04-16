package models;

import java.util.ArrayList;

import plants.Plant;
import plants.Projectile;
import zombies.Zombie;

public class DeadPool {
	private ArrayList<IEntite> deadPool;

	public DeadPool() {
		this.deadPool = new ArrayList<IEntite>();
	}

	public ArrayList<IEntite> getDeadPool() {
		return deadPool;
	}

	public void add(IEntite i) {
		if (!(deadPool.contains(i))) {
			deadPool.add(i);
		}
	}

	public boolean empty() {
		if (deadPool.size() <= 0) {
			return true;
		}
		return false;
	}
	
	public void addInDP(IEntite entites) {
		this.add(entites);
	}

	@Override
	public String toString() {
		StringBuilder bs = new StringBuilder();
		bs.append(" dead pool contient :\n\t");
		for (IEntite i : deadPool) {
			bs.append(i).append(", ");
		}
		return bs.toString();
	}
	
	public void clearEntity(ArrayList<?> Le) {
		for(IEntite DPe: deadPool) {
			Le.remove(DPe);
		}
	}
	
	public void deletingEverything(ArrayList<Zombie> MyZombies, ArrayList<Plant> MyPlants, ArrayList<Projectile> MyBullet) {
		this.clearEntity(MyBullet);
		this.clearEntity(MyZombies);
		this.clearEntity(MyPlants);
		deadPool.clear();
	}

}

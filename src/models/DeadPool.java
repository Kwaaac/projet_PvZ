package models;

import java.util.ArrayList;

import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import models.zombies.Zombie;

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
	
	public void clearEntity(ArrayList<?> Le, SimpleGameData dataBord) {

		for(IEntite DPe: deadPool) {
			Le.remove(DPe);
			if(dataBord.getCell(DPe.getCaseJ(), DPe.getCaseI()) != null) {
				dataBord.getCell(DPe.getCaseJ(), DPe.getCaseI()).removeEntity(DPe);
				}

		}

	}

	
	public void deletingEverything(ArrayList<Zombie> MyZombies, SimpleGameData dataBord, ArrayList<Projectile> MyBullet,ArrayList<LawnMower> MyLawnMower) {
		this.clearEntity(MyBullet, dataBord);
		this.clearEntity(dataBord.getMyPlants(), dataBord);
		this.clearEntity(MyZombies, dataBord);
		this.clearEntity(MyLawnMower, dataBord);
		deadPool.clear();
	}

}

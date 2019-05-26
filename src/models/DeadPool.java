package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import models.zombies.Zombie;

public class DeadPool implements Serializable{
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
	
	public void clearEntityBullet(SimpleGameData dataBord) {

		for(IEntite DPe: deadPool) {
			dataBord.removeB(DPe);
			
			int caseJ = DPe.getCaseJ();
			int caseI = DPe.getCaseI();
			
			if(dataBord.getCell(caseJ, caseI) != null) {
				dataBord.getCell(caseJ, caseI).removeZombie(DPe);
				dataBord.getCell(caseJ, caseI).removeProjectile(DPe);
			}
		}

	}
	
	public void clearEntityPlants(SimpleGameData dataBord) {

		for(IEntite DPe: deadPool) {
			dataBord.removeP(DPe);
			
			int caseJ = DPe.getCaseJ();
			int caseI = DPe.getCaseI();
			
			if(dataBord.getCell(caseJ, caseI) != null) {
				dataBord.getCell(caseJ, caseI).removeZombie(DPe);
				dataBord.getCell(caseJ, caseI).removeProjectile(DPe);
			}
		}

	}
	
	public void clearEntityZombies(SimpleGameData dataBord) {

		for(IEntite DPe: deadPool) {
			dataBord.removeZ(DPe);
			
			int caseJ = DPe.getCaseJ();
			int caseI = DPe.getCaseI();
			
			if(dataBord.getCell(caseJ, caseI) != null) {
				dataBord.getCell(caseJ, caseI).removeZombie(DPe);
				dataBord.getCell(caseJ, caseI).removeProjectile(DPe);
			}
		}

	}
	
	public void clearEntityLawnMower(SimpleGameData dataBord) {

		for(IEntite DPe: deadPool) {
			dataBord.removeLM(DPe);
			
			int caseJ = DPe.getCaseJ();
			int caseI = DPe.getCaseI();
			
			if(dataBord.getCell(caseJ, caseI) != null) {
				dataBord.getCell(caseJ, caseI).removeZombie(DPe);
				dataBord.getCell(caseJ, caseI).removeProjectile(DPe);
			}
		}

	}
	


	
	public void deletingEverything(SimpleGameData dataBord) {
		this.clearEntityBullet(dataBord);
		this.clearEntityPlants(dataBord);
		this.clearEntityZombies(dataBord);
		this.clearEntityLawnMower(dataBord);
		deadPool.clear();
	}

}

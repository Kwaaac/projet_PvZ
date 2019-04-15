package models;

import java.util.ArrayList;
import java.util.Collections;

public class DeadPool {
	private ArrayList<IEntite> DeadPool;

	public DeadPool() {
		this.DeadPool = new ArrayList<IEntite>();
	}

	public ArrayList<IEntite> getDeadPool() {
		return DeadPool;
	}

	public void add(IEntite i) {
		if (!(DeadPool.contains(i))) {
			DeadPool.add(i);
		}
	}

	public boolean empty() {
		if (DeadPool.size() <= 0) {
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
		for (IEntite i : DeadPool) {
			bs.append(i).append(", ");
		}
		return bs.toString();
	}

}

package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DeadPool {
	private ArrayList<Integer> DeadPool;

	public DeadPool() {
		this.DeadPool = new ArrayList<Integer>();
	}
	
	
	
	public ArrayList<Integer> getDeadPool() {
		return DeadPool;
	}


	public void add(int i) {
		if(!(DeadPool.contains(i))) {
			DeadPool.add(i);
		}
	}
	
	public boolean empty() {
		if(DeadPool.size() <= 0) {
			return true;
		}
		return false;
	}
	
	public void reverseSort() {
		Collections.sort(DeadPool);
		Collections.reverse(DeadPool);
	}
	
	@Override
	public String toString() {
		StringBuilder bs = new StringBuilder();
		bs.append(" dead pool contient :\n\t");
		for(int i : DeadPool) {
			bs.append(i).append(", ");
		}
		return bs.toString();
	}
	
	
	
	
	
	
}

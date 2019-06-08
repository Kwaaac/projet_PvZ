package models;

import java.util.ArrayList;

import models.zombies.NormalZombie;
import models.zombies.Zombie;
import views.BordView;

public class Tombstone {
	private final int x;
	private final int y;
	private Zombie burriedDead;
	private Tombstone(int x, int y, Zombie burriedDead) {
		this.x = x;
		this.y = y;
		this.burriedDead = burriedDead;

	}


	public void wakeUp(SimpleGameData data, BordView view, ArrayList<Zombie> allZombies) {
		burriedDead = allZombies.get(data.RandomPosGenerator(allZombies.size()-1));
		int sqrS = BordView.getSquareSize();
		int x = view.getYOrigin() + this.x * sqrS + (sqrS / 2)
				- (Zombie.getSizeOfZombie() / 2);
		data.addZ(burriedDead.createNewZombie((int) view.yFromJ(y+2),x, false));
	}
	
	
	public static Tombstone createTombstone(int x, int y) {
		return new Tombstone(x,y,null);
		
	}

}

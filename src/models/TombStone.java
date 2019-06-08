package models;

import java.util.ArrayList;

import models.zombies.NormalZombie;
import models.zombies.Zombie;
import views.BordView;

public class TombStone {
	private final int x;
	private final int y;
	private Zombie burriedDead;

	private TombStone(int x, int y, Zombie burriedDead) {
		this.x = x;
		this.y = y;
		this.burriedDead = burriedDead;

	}

	/**
	 * Spawn a zombie on the tombstone. This method also check is there is still is
	 * a tombstone on the cell
	 * 
	 * @param data       data from the board
	 * @param view       view of the board
	 * @param allZombies list of the zombie available
	 * 
	 */
	public void wakeUp(SimpleGameData data, BordView view, ArrayList<Zombie> allZombies) {

		burriedDead = allZombies.get(data.RandomPosGenerator(allZombies.size() - 1));
		int sqrS = BordView.getSquareSize();
		int x = view.getYOrigin() + this.x * sqrS + (sqrS / 2) - (Zombie.getSizeOfZombie() / 2);
		data.addZ(burriedDead.createNewZombie((int) view.yFromJ(y + 2), x, false));

	}

	public static TombStone createTombstone(int x, int y) {
		return new TombStone(x, y, null);

	}

}

package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.Chrono;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class DancingZombie extends Zombie {

	private final String name = "DancingZombie";
	private final String color = "#000000";
	private Chrono dance = new Chrono();
	private boolean lock = false;

	public DancingZombie(int x, int y) {
		super(x, y, 100, 340, 1, "ultraSlow",false);
		dance.start();
	}

		public DancingZombie(int x, int y, boolean gifted) {
			super(x, y, 100, 340, 1, "ultraSlow", gifted);
			dance.start();
	}

	public DancingZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new DancingZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));

		super.draw(view, graphics);
	}

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		int scareSize = BordView.getSquareSize();
		ArrayList<Zombie> zombieInQueu = new ArrayList<Zombie>();

		if (dance.asReachTimer(10) && lock == false) {
			if (dataBord.isCorrectBordLocation(view, (float) super.getX() + scareSize, (float) super.getY())) {
				zombieInQueu.add(new BackupDancerZombie((int) super.getX() + scareSize, (int) super.getY()));// avant
			}

			if (dataBord.isCorrectBordLocation(view, (float) super.getX(), (float) super.getY() + scareSize)) {
				zombieInQueu.add(new BackupDancerZombie((int) super.getX(), (int) super.getY() + scareSize));// haut
			}

			if (dataBord.isCorrectBordLocation(view, (float) super.getX() + scareSize, (float) super.getY())) {
				zombieInQueu.add(new BackupDancerZombie((int) super.getX() - scareSize, (int) super.getY()));// arriere
			}

			if (dataBord.isCorrectBordLocation(view, (float) super.getX(), (float) super.getY() - scareSize)) {
				zombieInQueu.add(new BackupDancerZombie((int) super.getX(), (int) super.getY() - scareSize));// bas
			}
			lock = true;
			dataBord.setZombieInQueu(zombieInQueu);
			this.resetAS();
			return false;
		}

		this.incAS();
		
		return true;
	}
}

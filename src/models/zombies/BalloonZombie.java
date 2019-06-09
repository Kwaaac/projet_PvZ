package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class BalloonZombie extends Zombie {

	private final String type = "Balloon Zombie";
	private final String color = "#ffffff";
	private boolean balloon = true;

	public BalloonZombie(int x, int y) {
		super(x, y, 100, 210, 1, "slow", false, true);
	}

	public BalloonZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 210, 1, "slow", gifted, true);
	}

	public BalloonZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return type;
	}

	@Override
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new BalloonZombie(x, y, gift);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		int decalage = balloon ? 25 : 0;
		
		
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y - decalage, sizeOfZombie, sizeOfZombie));
		if (balloon) {
			graphics.setColor(Color.decode("#8e8b8c"));
			graphics.fill(new Rectangle2D.Float(x + 40, y + 5 - decalage, 4, 30));
			graphics.setColor(Color.decode("#e50d38"));
			graphics.fill(new Ellipse2D.Float(x + 20, y - 40 - decalage, 40, 60));

		}

		super.draw(view, graphics);

		if (isSlowed()) {
			super.draw(view, graphics);
		}
	}

	@Override
	public void chopped(boolean sharp) {
		if(sharp) {
			balloon = false;
			switchFly();
		}
	}
}

package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import models.Chrono;
import models.SimpleGameData;
import models.cells.Cell;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Fume extends Projectile {
	private String name = "Fume";
	private String color = "#7714AD";
	private Chrono ttl = new Chrono();

	public Fume(float x, float y) {
		super(x, y, 0, 10000000, 0);
		ttl.start();
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String getColor() {
		return null;
	}

	@Override
	public void action(SimpleGameData data) {
		

		if (ttl.asReachTimerMs(1500)) {
			Cell cell = data.getCell(getCaseJ(), getCaseI());
			
			for (Zombie z : cell.getBadZombiesInCell()) {
				z.takeDmg(20);
			}
			setLife(0);
			
		}
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(new Color(119, 20, 173, 60));

		float newX = view.xFromI(view.columnFromX(x));
		float newY = view.yFromJ(view.lineFromY(y));
		int sqrS = BordView.getSquareSize();

		graphics.fill(new Rectangle2D.Float(newX, newY, sqrS, sqrS));
	}

	@Override
	public int isSlowing() {
		return 0;
	}
}

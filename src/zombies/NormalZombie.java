package zombies;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class NormalZombie extends Zombie {

	private final String name = "Normal Zombie";
	private final Color color = Color.BLACK;

	public NormalZombie(int x, int y) {
		super(x, y, 100, 200, -0.73);
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Ellipse2D.Float draw() {
		return new Ellipse2D.Float(getX(), getY(), getSizeOfZombie(), getSizeOfZombie());
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	public void go() {
		super.setSpeed((float) -0.73);
	}

}

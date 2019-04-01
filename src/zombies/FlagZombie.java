package zombies;

import java.awt.geom.Ellipse2D;

public class FlagZombie extends Zombie{

	private final String name = "Flag Zombie";
	private static double[] speedState = {0.0, -2.9, -1.9};
	
	public FlagZombie(int x, int y) {
		super(x, y, 100, 200, speedState[1]);
	}
	
	@Override
	public Ellipse2D.Float draw(){
		return new Ellipse2D.Float(getX(), getY(), getSizeOfZombie(), getSizeOfZombie());
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
}

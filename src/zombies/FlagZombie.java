package zombies;

import java.awt.geom.Ellipse2D;

public class FlagZombie extends Zombie{
	private final String name = "Flag Zombie";
	
	public FlagZombie(int x, int y) {
		super(x, y, 100, 200, -0.75);
	}
	
	@Override
	public Ellipse2D.Float draw(){
		return new Ellipse2D.Float(super.x, super.y, getSizeOfZombie(), getSizeOfZombie());
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	public void go() {
		super.setSpeed((float) -0.75);
	}
}

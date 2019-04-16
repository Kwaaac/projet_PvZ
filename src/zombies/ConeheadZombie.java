package zombies;

import java.awt.geom.Ellipse2D;

public class ConeheadZombie extends Zombie {

	private final String name = "Conehead Zombie";
	
	public ConeheadZombie(int x, int y) {
		super(x, y, 100, 560, -0.7);
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
		super.setSpeed((float) -0.7);
	}
}

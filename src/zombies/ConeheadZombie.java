package zombies;

import java.awt.geom.Ellipse2D;

public class ConeheadZombie extends Zombie {

	private final String name = "Conehead Zombie";
	
	public ConeheadZombie(int x, int y) {
		super(x, y, 100, 560, -1.7);
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

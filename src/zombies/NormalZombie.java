package zombies;

import java.awt.geom.Ellipse2D;

public class NormalZombie extends Zombie{

	private final String name = "Normal Zombie";
	
	public NormalZombie(int x, int y) {
		super(x, y, 100, 200, -2.7);
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

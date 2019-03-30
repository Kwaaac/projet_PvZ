package Models.Zombies;

import java.awt.geom.Ellipse2D;

public class FlagZombie extends Zombie{

	private final String name = "Flag Zombie";
	
	public FlagZombie(int x, int y, int damage, int life, int speed) {
		super(x, y, 100, 200, -2);
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

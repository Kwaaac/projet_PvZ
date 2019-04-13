package plants;

import java.awt.geom.Rectangle2D;

import zombies.Zombie;

public class Mine extends Plant{

	private final String name = "CheeryBomb";
	
	public Mine(int x, int y) {
		super(x, y, 1800, 0, 999999);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}
	
	@Override
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}
	
	public void conflictAll(Zombie... z) {}
	
}

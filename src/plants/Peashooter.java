package plants;

import java.awt.geom.Rectangle2D;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	
	public Peashooter(int x, int y) {
		super(x, y, 20, 300);
	}


	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	
	@Override
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(getX(), getY(), getSizeOfPlant(), getSizeOfPlant());
	}
	
}

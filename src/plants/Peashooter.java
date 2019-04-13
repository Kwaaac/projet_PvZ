package plants;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

public class Peashooter extends Plant{
	private final String name = "Peashooter";
	
	public Peashooter(int x, int y) {
		super(x, y, 20, 300, 200);
	}


	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	
	public Rectangle2D.Float draw(){
		return new Rectangle2D.Float(super.x, super.y, getSizeOfPlant(), getSizeOfPlant());
	}
}

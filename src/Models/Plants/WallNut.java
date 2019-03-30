package Models.Plants;

import java.awt.geom.Rectangle2D;

public class WallNut extends Plant{
	private final String name = "WallNut";
	
	
	public WallNut(int x, int y) {
		super(x, y, 0, 3600);
		// TODO Auto-generated constructor stub
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

package plants;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Float;

import models.Coordinates;


public class Bullet extends Projectile {

	private final String name = "Bullet";
	
	public Bullet(int x, int y) {
		super(x, y, 900 , 1 , 4.7);
	}

	@Override
	public Ellipse2D.Float draw(){
		return new Ellipse2D.Float(super.x, super.y, super.getSizeOfProjectile(), super.getSizeOfProjectile());
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

	@Override
	public void incAS() {}

	@Override
	public void resetAS() {}

}

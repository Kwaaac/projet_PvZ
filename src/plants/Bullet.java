package plants;

import java.awt.geom.Ellipse2D;


public class Bullet extends Projectile {

	private final String name = "Bullet";
	
	public Bullet(float x, float y) {
		super(x, y, 45, 1 , 7.7);
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

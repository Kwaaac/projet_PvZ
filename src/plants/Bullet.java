package plants;

import java.awt.geom.Ellipse2D;


public class Bullet extends Projectile {

	private final String name = "Bullet";
	
	public Bullet(int x, int y) {
		super(x, y, 900 , 1 , 2.7);
	}

	@Override
	public Ellipse2D.Float draw(){
		return new Ellipse2D.Float(getX(), getY(), 20, 20);
	}
	
	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}

}

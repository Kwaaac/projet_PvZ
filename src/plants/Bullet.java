package plants;

import java.awt.geom.Ellipse2D;

import models.Entities;
import models.MovingElement;

public abstract class Bullet extends Entities implements MovingElement{

	private final String name = "bullet";
	
	
	
	public Bullet(int x, int y, int damage, int life) {
		super(x, y, damage, 1);
		// TODO Auto-generated constructor stub
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

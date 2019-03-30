package Models;

import java.awt.geom.Ellipse2D;

public class HorizontallyMovingElement implements MovingElement{
	private int x;
	private int y;
	private final int speed;

	public HorizontallyMovingElement(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public int getX() {
		return x;
	}
	
	@Override
	public void move() {
		x += speed;
	}
	
	@Override
	public Ellipse2D.Float draw(){
		return new Ellipse2D.Float(x, y, 10, 10);
	}
}

package models;

import java.awt.geom.Ellipse2D;

public class HorizontallyMovingElement implements MovingElement{
	private int x;
	private int y;
	private final double speed;

	public HorizontallyMovingElement(int x, int y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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

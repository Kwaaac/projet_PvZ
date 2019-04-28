package models;

import java.awt.Graphics2D;

import views.BordView;
import views.SimpleGameView;

public class Soleil implements MovingElement {
	private float x;
	private float y;
	private Coordinates caseXY;

	private final int size = 85;

	private String color = "#FFFF00";

	private final int sunny = 25;

	private final double speed;

	public Soleil(float x, float y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
	}
	
	public boolean isClicked(float clicX, float clicY) {
		if(x <= clicX && clicX <= x + size) {
			return y <= clicY && clicY <= y + size;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Soleil moyen: " + sunny;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void move() {
		setY((float) (getY() + speed));
	}

	public Coordinates getCase() {
		return caseXY;
	}

	public int getSunny() {
		return sunny;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawSun(graphics, x, y, color);
	}

	public void setCase(float x, float y) {
		caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
	}

}

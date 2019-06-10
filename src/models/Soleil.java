package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import views.BordView;
import views.SimpleGameView;

public class Soleil implements MovingElement, Serializable {
	private float x;
	private float y;
	private Coordinates caseXY;

	private final int yTarget;

	private final int size;

	private String color = "#FFFF00";

	private final int sunny;

	private double speed;

	public Soleil(float x, float y, double speed, int sunny, int size) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.size = size;
		this.sunny = sunny;
		this.caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
		this.yTarget = (150 + SimpleGameData.RandomPosGenerator(1, 6) * BordView.getSquareSize()) - 125;
	}

	/**
	 * @return true si we have clicked on a sun
	 */
	public boolean isClicked(float clicX, float clicY) {
		if (x <= clicX && clicX <= x + size) {
			return y <= clicY && clicY <= y + size;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Soleil: " + sunny;
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

	public void setCase() {
		if (y >= yTarget) {
			this.speed = 0;
		}
		caseXY = new Coordinates(BordView.caseXFromX(x), BordView.caseYFromY(y));
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
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, size, size));
	}

}

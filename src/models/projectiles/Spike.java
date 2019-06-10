package models.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import models.Coordinates;
import models.SimpleGameData;
import models.cells.Cell;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Spike extends Projectile {
	private final String name = "Spike";
	private final String color = "#ad0000";
	int sizeofP;
	private final boolean seekerHead;
	private final Zombie target;
	
	
	private float plantX = -1;
	private float plantY = -1;
	
	private float targetX = -1;
	private float targetY = -1;
	
	public Spike(float x, float y, int damage, int life) {
		super(x, y, damage, life, 20.0, true);
		sizeofP = getSizeOfProjectile();
		seekerHead = false;
		target = null;
	}

	public Spike(float x, float y, int damage, int life, Zombie target, float plantX, float plantY) {
		super(x, y, damage, life, 20.0, true);
		sizeofP = getSizeOfProjectile();
		seekerHead = target != null ? true : false;
		this.target = target;
		
		this.plantX = plantX;
		this.plantY = plantY;
		
		targetX = target.getX();
		targetY = target.getY() + 25;
	}

	@Override
	public void move() {
		if (seekerHead) {
			
			if(targetX < this.x && speed>0) {
				speed *= -1;
			}
			int x = (int) (super.x + speed);
			setX(x);
			
			float a = (targetY - plantY) / (targetX - plantX);
			float b = plantY - a * plantX;
			
			setY(a*x+b);
			
			
			
		} else {
			super.move();
		}
	}

	@Override
	public void setCase(SimpleGameData data) {
		if (seekerHead) {
			// Inscrit dans une cellule si la target est dans la cellule
			if (!target.isDead()) {
				Coordinates caseTarget = target.getCase();
				int cX = BordView.caseXFromX(x);
				int cY = BordView.caseYFromY(y);

				Coordinates caseP = new Coordinates(cX, cY);

				if (caseP.equals(caseTarget)) {
					Cell actCell = data.getCell(cY, cX);
					actCell.addProjectile(this);
				}
			} else {
				setLife(0);
			}
		} else {
			super.setCase(data);
		}
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeofP, sizeofP - 20));
	}

	@Override
	public int isSlowing() {
		return 0;
	}

	@Override
	public boolean isFlying() {
		return true;
	}

	@Override
	public boolean isSharp() {
		return true;
	}

}

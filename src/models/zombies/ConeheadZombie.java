package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class ConeheadZombie extends Zombie {

	private final String name = "Conehead Zombie";
	private final String color = "#000000";

	public ConeheadZombie(int x, int y) {
		super(x, y, 100, 560, 2, "slow",false);
	}
	

	public ConeheadZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 560, 2, "slow", gifted);
	}

	public ConeheadZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new ConeheadZombie(x, y, gift);
	}
	
	int sizeOfZombie = super.getSizeOfZombie();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		if (life > 300) {
			graphics.setColor(Color.decode("#CB5050"));
			graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, 180, 2));

			graphics.setColor(Color.decode(color));
			graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 0, -180, 2));
		} else {

			graphics.setColor(Color.decode(color));
			graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		}
		
		super.draw(view, graphics);
	}
	
}

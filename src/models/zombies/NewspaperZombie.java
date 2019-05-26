package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class NewspaperZombie extends Zombie {

	private final String name = "NewspaperZombie";
	private String color = "#FFFFFF";
	
	public NewspaperZombie(int x, int y) {
		super(x, y, 100, 340, 1, "slow");
	}

	public NewspaperZombie() {
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
	
	public void go() {
		super.go((float) -0.93);
	}
	
	@Override
	public Zombie createNewZombie(int x, int y) {
		return new NewspaperZombie(x, y);
	}
	
	int sizeOfZombie = super.getSizeOfZombie();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 90, 180, 2));

		graphics.setColor(Color.decode("#000000"));
		graphics.fill(new Arc2D.Float(x, y, sizeOfZombie, sizeOfZombie, 90, -180, 2));
	}
	
	@Override
	public boolean action(SimpleGameData dataBord) {
		if (this.life <= 200) {
			setSpeed(-1.70);
			shootBarMax = 4000;
			color = "#000000";
		}
		return true;
	}

	@Override
	public void action(BordView view, SimpleGameData dataBord, ArrayList<Zombie> myZombies) {
		
	}
	
	
}

package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import views.BordView;
import views.SimpleGameView;

public class DuckyTubeZombie extends Zombie {

	private final String type = "Ducky Tube Zombie";
	private final String color = "#000000";
	
	public DuckyTubeZombie(int x, int y) {
		super(x, y, 100, 200, 1 , "slow" , false);
	}
	
	public DuckyTubeZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 200, 1 , "slow" , gifted);
	}

	public DuckyTubeZombie() {
		this(50, 50);
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return type;
	}
	
	@Override
	public Zombie createNewZombie(int x, int y,boolean gift) {
		return new DuckyTubeZombie(x, y, gift);
	}
	
	int sizeOfZombie = super.getSizeOfZombie();
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		graphics.setColor(Color.decode(color));
		graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
		view.drawRectangle(graphics, (int) (x-10), (int) y + sizeOfZombie/2, sizeOfZombie + 20, 10, "#f4dd0e");
		if(isSlowed()) {
			super.draw(view, graphics);
		}
	}
	
	@Override
	public boolean isCommon() {
		return false;
	}
}

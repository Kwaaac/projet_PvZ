package models.projectiles;

import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;
import views.SimpleGameView;

public class LawnMower extends Projectile {
	private final String name = "Tondeuse";
	private final static String color = "#B44A4A";
	private static final int[] SizeOfLawnMower = {110,95};
	
	public LawnMower(float x, float y) {
		super(x, y, 100000, 100000 , 20.0);
	}
	
	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return super.toString() + "--" + name; 
	}


	public static int[] getSizeOfLawnMower() {
		return SizeOfLawnMower;
	}

	public static LawnMower createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPeashooter(context, x,  y, color);
		
		return new LawnMower(x, y);
	}
	
	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, float x, float y) {
		view.drawLawnMower(graphics, x, y, color);
	}

	

}

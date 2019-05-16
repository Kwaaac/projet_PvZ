package models.plants.day;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import models.zombies.Zombie;
import views.BordView;
import views.SimpleGameView;

public class Pot extends Plant {
	private final String name = "Pot";
	private final String color = "#C98C79";

	public Pot(int x, int y) {
		super(x, y, 0, 300, 0, 0, "free");
	}

	public Pot() {
		this(-10, -10);
	}

	int sizeOfPlant = super.getSizeOfPlant();

	@Override
	public String toString() {
		return super.toString() + "--" + name;
	}

	@Override
	public void action(ArrayList<Projectile> myBullet, BordView view, ArrayList<Zombie> myZombies,
			SimpleGameData dataBord) {
	}

	@Override
	public Plant createAndDrawNewPlant(SimpleGameView view, ApplicationContext context, int x, int y) {
		view.drawPot(context, x, y, color);

		return new Pot(x, y);

	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics, int x, int y) {
		view.drawPot(graphics, x, y, color);
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pot)) {
			return false;
		}
		Pot p = (Pot) o;
		return name.equals(p.name) && color.equals(p.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}

	@Override
	public int getTypeOfPlant() {
		return 0;
	}
}

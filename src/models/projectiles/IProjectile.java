package models.projectiles;

import java.awt.Graphics2D;

import views.SimpleGameView;

public interface IProjectile {

	void draw(SimpleGameView view, Graphics2D graphics, float x, float y);

	/**
	 * @return How many seconds the pea will freeze a Zombie
	 */

	int isSlowing();

	String getColor();

	void action();
}

package models.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import models.DeadPool;
import models.SimpleGameData;
import models.cells.Cell;
import models.plants.Plant;
import models.projectiles.LawnMower;
import models.projectiles.Projectile;
import views.BordView;
import views.SimpleGameView;

public class DiggerZombie extends Zombie {

	private final String name = "DiggerZombie";
	private final String color = "#000000";
	private boolean outOfEarth = false;

	public DiggerZombie(int x, int y) {
		super(x, y, 100, 200, 1, "reallyFast", false);
	}

	public DiggerZombie(int x, int y, boolean gifted) {
		super(x, y, 100, 200, 1, "reallyFast", gifted);
	}

	public DiggerZombie() {
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
	public Zombie createNewZombie(int x, int y, boolean gift) {
		return new DiggerZombie(x, y, gift);
	}

	@Override
	public void draw(SimpleGameView view, Graphics2D graphics) {
		if (!outOfEarth) {
			view.drawRectangle(graphics, (int) (x - 10), (int) (sizeOfZombie + y), sizeOfZombie + 20, 10, "#963e00");
		} else {
			graphics.setColor(Color.decode(color));
			graphics.fill(new Ellipse2D.Float(x, y, sizeOfZombie, sizeOfZombie));
			view.drawRectangle(graphics, (int) (x - 10), (int) (y), sizeOfZombie + 20, 10, "#963e00");
		}
		super.draw(view, graphics);
	}

	int sizeOfZombie = super.getSizeOfZombie();

	@Override
	public boolean action(BordView view, SimpleGameData dataBord, List<Zombie> myZombies) {
		if (x <= view.getXOrigin() + 10) {
			outOfEarth = true;
			setBasicSpeed("slow");
			super.switchSpeed = true;
		}
		return true;
	}

	@Override
	public boolean isCommon() {
		return false;
	}

	@Override
	public void conflictBvZ(DeadPool DPe, BordView view, SimpleGameData data) {
		if (outOfEarth) {
			List<Projectile> Le;
			if (data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).isPlantedPlant()) {
				Le = data.getCell(view.lineFromY(this.getY()), view.columnFromX(this.getX())).getProjectilesInCell();
				for (Projectile e : Le) {
					e.action(data);
					if (this.outOfEarth == true && this.hit(e) && !(e.isInConflict()) && this.isBad()) {
						this.slowed(e.isSlowing());
						e.setConflictMode(true);
						this.mortalKombat(e);
						if (e.isDead()) {
							DPe.addInDP(e);
						}
						/*
						 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
						 * attaquant ne sois mort on empeche des echange de d�gats(on en a besoin pour
						 * pas qu'une plante morte soit capable de tu� apr�s sa mort)
						 */
						else if (this.isDead()) {
							e.setConflictMode(false);
							DPe.addInDP(this);
							break;
						}
					}

				}
			}
		}
	}
	
	@Override
	public void conflictPvZ(DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		Plant p;
		if (data.getCell(view.lineFromY(y), view.columnFromX(x)) != null
				&& data.getCell(view.lineFromY(y), view.columnFromX(x)).isPlantedPlant()) {

			p = data.getCell(view.lineFromY(y), view.columnFromX(x)).getPlantToAttack();
			if (this.hit(p)) {
				this.stop();
				if (this.readyToshot()) {

					this.mortalKombat(p);
					this.resetAS();
				}
			}
			if (p.isDead()) {
				str.append(p + " meurt\n");
				deadPoolE.add(p);
				data.getCell(view.lineFromY(p.getY()), view.columnFromX(p.getX())).removePlant(p);
			}
		}
	}
	
	@Override
	public void conflictLvZ(DeadPool deadPoolE, List<LawnMower> myLawnMower, BordView view, SimpleGameData data,
			StringBuilder str) {
		if(outOfEarth) {
			for (LawnMower l : myLawnMower) {
				if (this.hit(l)) {
					if (!(l.isMoving())) {
						l.go();
					}
					if (fertilizer) {
						data.addFertilizer();
					}
					life = 0;
					str.append(this + " meurt tu� par une tondeuse\n");
					l.setLife(100000);
				}
			}
		}
	}
	
	@Override
	public void conflictZvZ(DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		if(outOfEarth) {
			ArrayList<Zombie> zombies;
			int thisY = view.lineFromY(y);
			int thisX = view.columnFromX(x);

			Cell actualCell = data.getCell(thisY, thisX);

			if (isBad()) {

				if (actualCell != null && actualCell.isThereBadZombies()) {

					zombies = actualCell.getZombiesToAttack(this);

					// Check the Cell behind him
					Cell prevCell = data.getCell(thisY, thisX + 1);
					if (prevCell != null) {
						zombies.addAll(prevCell.getZombiesToAttack(this));
					}

					for (Zombie z : zombies) {
						if (this.hit(z)) {
							this.stop();
							z.stop();

							// Bad attack Good
							if (!isStunned()) {
								if (readyToshot()) {
									z.takeDmg(damage);
									resetAS();
								}
							}

							// Good attack Bad
							if (z.readyToshot()) {
								takeDmg(z.getDamage());
								z.resetAS();
							}

						}
						if (z.isDead()) {
							str.append(z + " meurt\n");
							deadPoolE.add(z);
							data.getCell(view.lineFromY(z.getY()), view.columnFromX(z.getX())).removeZombie(z);
						}

					}
				}
			} else {
				if (!isBad() && isOutside(view.getXOrigin(), BordView.getSquareSize(), data.getNbColumns())) {
					str.append(this + " meurt\n");
					deadPoolE.add(this);
					Cell cell = data.getCell(view.lineFromY(y), view.columnFromX(x));
					if (cell != null) {
						cell.removeZombie(this);
					}
				}
			}
		}
	}
	@Override
	public boolean magnetizable() {
		if(!outOfEarth) {
			outOfEarth = true;
		}
		return false;
	}
}

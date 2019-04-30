package models.zombies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import models.Cell;
import models.Coordinates;
import models.DeadPool;
import models.Entities;
import models.IEntite;
import models.MovingElement;
import models.SimpleGameData;
import models.plants.Plant;
import models.projectiles.Projectile;
import views.BordView;

public abstract class Zombie extends Entities implements MovingElement, IZombie {
	private final String name;
	private double speed;
	private final static int sizeOfZombie = 75;

	protected final int shootBarMax;
	protected long shootBar;
	protected long shootTime;

	// Liste de nom pour les zombies, tous différents pour que chaque zombie soit unique (200 pseudos provenant du TP 10 de java)
	protected static ArrayList<String> zombieNames = new ArrayList<>(Arrays.asList("PortCharlotte472", "Birdseye722",
			"Freeville753", "Kamas397", "PrincetonJunction132", "Edroy498", "Marshallberg573", "Anderson828",
			"NewRome174", "Caneyville505", "PointIsabel867", "Exell248", "Jacksonburg582", "PleasantPrairie521",
			"Keene960", "Marienville401", "Greenleafton109", "Gobler450", "Shickley652", "Hineston722",
			"SaintPetersburg498", "EastProvidence325", "Sorum711", "Netcong446", "Richtex482", "Hernandez859",
			"Kodiak857", "Lajitas791", "Moffit265", "Pawnee966", "NorthSaltLake643", "Idabel501", "Clementon338",
			"Macksburg438", "Whitefish491", "LongIsland276", "HarlemSprings125", "Powderhorn245", "Melby343",
			"Brookings203", "SanAugustine398", "MillerPlace573", "Bailey840", "Stonybrook200", "Yscloskey301",
			"Minter630", "Hewins376", "Cecilville429", "ShawsheenVillage468", "Omak205", "OracleJunction654",
			"CapitolHeights105", "Newsoms820", "Frenchman601", "Felda445", "HartfordCity480", "Emmalane526",
			"LakeBridgeport886", "Oacoma699", "Post355", "Priddy478", "ShawsheenVillage458", "Piketon525", "Mosby288",
			"Rapids550", "London532", "Simsbury529", "BloomingPrairie242", "TurtleLake870", "WhiteCastle636",
			"Dustin705", "Makanda578", "Fayette361", "HickoryWithe453", "Daphne309", "Playita383", "Millerton665",
			"Emmons226", "AngleInlet584", "WestWinfield815", "Redgranite973", "Wellsford773", "ElOjo913",
			"Idyllwild303", "VimyRidge703", "Mazie999", "Gatliff953", "Jennersville945", "Leonore563",
			"BedfordCenter565", "Maxville505", "Roscoe515", "Portville508", "Western600", "Palestine909",
			"Clintwood716", "ElectricMills702", "JubileeSprings716", "AuGres181", "Joffre705", "Corydon152",
			"Soperton775", "Monterey349", "Combes945", "Fenn336", "Shelbina268", "FederalDam131", "BuckGrove822",
			"SanJacinto480", "Hermantown851", "DesLacs240", "NewWoodstock532", "Minersville456", "Repaupo711",
			"Loyalhanna155", "Boutte799", "Sixes960", "Kellerville984", "SierraBlanca714", "Verdigre680",
			"NewWilmington713", "Seelyville235", "Buenos702", "McBain430", "EurekaSprings588", "Wheatland444",
			"LaGrande373", "NewCastle982", "Okolona536", "Wurtland531", "Marmet624", "CapeNeddick428", "Dobson197",
			"Wakita402", "Bostic998", "PanoramaVillage673", "Finlay362", "CrowsBluff100", "PalmerLake688", "Elkader520",
			"Regent690", "Berthoud710", "Bray567", "Atlantis551", "BuckGrove253", "HooversonHeights773",
			"LongboatKey474", "SpringChurch761", "Porterfield"));

	public Zombie(int x, int y, int damage, int life, double speed) {
		super(x, y, damage, life);
		this.name = zombieNames.remove(0);
		this.speed = -1.7;
		this.shootBarMax = (int) (speed * -7500);
		shootTime = System.currentTimeMillis();
		

	}

	public float getX() {
		return super.getX();
	}

	@Override
	public void setCase(SimpleGameData data) {
		int cX = BordView.caseXFromX(x);
		int cY = BordView.caseYFromY(y);

		Coordinates caseZ = new Coordinates(cX, cY);

		if (!caseZ.equals(caseXY)) {

			Cell actCell = data.getCell(cY, cX);
			if (actCell != null) {

				if (!(cX == data.getNbColumns() - 1)) {
					data.getCell(caseXY.getJ(), caseXY.getI()).removeEntity(this);
				}

				actCell.addEntity(this);

				caseXY = caseZ;
			}
		}

	}

	public float getY() {
		return super.getY();
	}

	@Override
	public void move() {
		setX((float) (getX() + speed));
	}

	@Override
	public String toString() {
		return name;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(float x) {
		speed = x;
	}

	public static int getSizeOfZombie() {
		return sizeOfZombie;
	}

	public void stop() {
		this.speed = 0;
	}

	public void go() {
		speed = -1.7;
	}

	public void SpeedBoostON() {
		speed -= 2;
	}

	public void SpeedBoostOFF() {
		speed += 2;
	}

	public void incAS() {
		shootBar = System.currentTimeMillis() - shootTime;
	}

	public void resetAS() {
		shootTime = System.currentTimeMillis();

		this.shootBar = 1;
	}

	public boolean readyToshot() {
		return shootBar >= shootBarMax;
	}

	public void conflictAll(Plant p) {
	}

	public Coordinates hitBox() {
		return new Coordinates((int) x, (int) x + sizeOfZombie);
	}

	public boolean isEatingBrain(int xOrigin, int squareSize) {
		return x < xOrigin - squareSize / 2;
	}

	/**
	 * cette mï¿½thode a pour but de rï¿½partir les dï¿½gats aux diffï¿½rentes
	 * entitï¿½es du jeu, une fois les dï¿½gat correctement attribuer et la vie des
	 * entitï¿½es mise a jour elle aide par la suite a les redistribuer dans les
	 * diffï¿½rente deadpools
	 * 
	 * @param view     vue sur la quelle ce joue le conflict (si on en met plusieur
	 *                 je saurai la faire marcher sur plusieur vues)
	 * @param DPz      deadPool foPlant p = (Plant) this;
	 *                 if(p.draw().getBounds2D().intersects(((Zombie)
	 *                 e).draw().getBounds2D())){ return true; }r Zombies
	 * @param DPp      deadPool for Plant
	 * @param DPb      deadPool for Projectile
	 * @param entities suite d'entitï¿½es qui subiront les dï¿½gats de l'entitï¿½e
	 *                 objet utilisant la mï¿½thode et qui attaqueront cette meme
	 *                 entitï¿½es tous ensemble
	 */

	public void conflictBvZ(DeadPool DPe, ArrayList<Projectile> Le, SimpleGameData data) {
		for (IEntite e : Le) {
			if (this.hit(e) && !(e.isInConflict())) {
				e.setConflictMode(true);
				this.mortalKombat(e);
				if (e.isDead()) {
					DPe.addInDP(e);
				}
				/*
				 * si ils sont plusieur a le taper et que sa vie tombe a zero avant que les
				 * attaquant ne sois mort on empeche des echange de dï¿½gats(on en a besoin pour
				 * pas qu'une plante morte soit capable de tuï¿½ aprï¿½s sa mort)
				 */
				else if (this.isDead()) {
					e.setConflictMode(false);
					// On remet le nom du zombie mort dans la list de nom
					zombieNames.add(name);
					DPe.addInDP(this);
					break;
				}
			}

		}
	}

	public void conflictPvZ(DeadPool deadPoolE, ArrayList<Plant> myPlants, BordView view, SimpleGameData data,
			StringBuilder str) {
		for (Plant p : myPlants) {

			if (this.hit(p)) {
				this.stop();
				if (this.readyToshot()) {
					(p).mortalKombat(this);

					this.resetAS();
				}

			}
			if (p.isDead()) {
				str.append(p + "meurt\n");
				deadPoolE.add(p);
				data.getCell(view.lineFromY(p.getY()), view.columnFromX(p.getX())).removePlant();
			} 
		}
	}

	public static void ZCheckConflict(ArrayList<Zombie> myZombies, ArrayList<Projectile> myBullet,
			ArrayList<Plant> myPlants, DeadPool deadPoolE, BordView view, SimpleGameData data, StringBuilder str) {
		for (Zombie z : myZombies) {
			z.go();
			z.incAS();
			z.conflictBvZ(deadPoolE, myBullet, data);
			z.conflictPvZ(deadPoolE, myPlants, view, data, str);
			if (z.isEatingBrain(view.getXOrigin(), BordView.getSquareSize()) || z.isDead()) {
				deadPoolE.add(z);
				str.append(z + " meurt\n");
			}
		}
	}

	public abstract Integer getProb(int difficulty);

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Zombie)){ return false; }
		Zombie z = (Zombie) o;
		return name.equals(z.name);
	}
}

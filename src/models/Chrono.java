package models;

import java.io.Serializable;
import java.util.Objects;

import models.plants.day.CherryBomb;

public class Chrono implements Serializable{
	private long tempsDepart = 0;
	private long tempsFin = 0;
	private long pauseDepart = 0;
	private long pauseFin = 0;
	private long duree = 0;

	/**
	 * get the chrono ready for launch
	 */

	public void steady() {
		tempsDepart = 0;
		tempsFin = 0;
		pauseDepart = 0;
		pauseFin = 0;
		duree = 0;
	}

	/**
	 * lance le chronometre / peut �tre utilise pour le reset
	 */
	public void start() {
		tempsDepart = System.currentTimeMillis();
		tempsFin = 0;
		pauseDepart = 0;
		pauseFin = 0;
		duree = 0;
	}

	/**
	 * met en pause le chronom�tre
	 */
	public void pause() {
		if (tempsDepart == 0) {
			return;
		}
		pauseDepart = System.currentTimeMillis();
		duree = (tempsFin - tempsDepart) - (pauseFin - pauseDepart);
	}

	/**
	 * relance le chronom�tre
	 */
	public void resume() {
		if (tempsDepart == 0) {
			return;
		}
		if (pauseDepart == 0) {
			return;
		}
		pauseFin = System.currentTimeMillis();
		tempsDepart = tempsDepart + pauseFin - pauseDepart;
		tempsFin = 0;
		pauseDepart = 0;
		pauseFin = 0;
		duree = 0;
	}

	/**
	 * arr�te le chronom�tre
	 */
	public void stop() {
		if (tempsDepart == 0) {
			return;
		}
		tempsFin = System.currentTimeMillis();
		duree = (tempsFin - tempsDepart) - (pauseFin - pauseDepart);
		tempsDepart = 0;
		tempsFin = 0;
		pauseDepart = 0;
		pauseFin = 0;
	}

	/**
	 * 
	 * @return renvoie la dur�e du chrono en seconde
	 */
	public long getDureeSec() {
		return duree / 1000;
	}

	/**
	 * 
	 * @returnrenvoie la dur�e du chrono en milliseconde
	 */
	public long getDureeMs() {
		return duree;
	}

	/**
	 * 
	 * @return renvoie la dur�e du chrono au format texte
	 */
	public String getDureeTxt() {
		return timeToHMS(getDureeSec());
	}
	
	/**
	 * If the chrono is reset then, it start
	 */
	public void startChronoIfReset() {
		if (this.isReset()) {
			this.start();
		}
	}

	/**
	 * 
	 * @param tempsS temps a atteindre et arrete le chrono
	 * @return revoie si le chronometre a atteind le temps donnee en parametre
	 */
	public boolean asReachTimerAndStop(long tempsS) {
		this.pause();

		if (this.getDureeSec() >= tempsS) {
			this.stop();
			return true;
		}
		this.resume();
		return false;
	}

	/**
	 * 
	 * @param tempsS temps (S) a atteindre et relance le chrono
	 * @return revoie si le chronometre a atteind le temps donnee en parametre
	 */
	public boolean asReachTimer(long tempsS) {
		this.pause();

		if (this.getDureeSec() >= tempsS) {
			this.start();
			return true;
		}
		this.resume();
		return false;
	}
	
	/**
	 * 
	 * @param tempsS temps (Ms) a atteindre et relance le chrono
	 * @return revoie si le chronometre a atteind le temps donnee en parametre
	 */
	public boolean asReachTimerMs(long tempsMs) {
		this.pause();

		if (this.getDureeMs() >= tempsMs) {
			this.start();
			return true;
		}
		this.resume();
		return false;
	}

	/**
	 * Observe sir le chrono est en stand-by
	 * 
	 * @return Renvoie true si le chrono est en stand-by, false si il est lanc�
	 */

	public boolean isReset() {
		return (tempsDepart == 0 && tempsFin == 0 && pauseDepart == 0 && pauseFin == 0);
	}

	/**
	 * 
	 * @param tempsS temps en seconde
	 * @return temps au format texte : "?? h ?? min ?? s"
	 */
	public static String timeToHMS(long tempsS) {

		int h = (int) (tempsS / 3600);
		int m = (int) ((tempsS % 3600) / 60);
		int s = (int) (tempsS % 60);

		String r = "";

		if (h > 0) {
			r += h + " h ";
		}
		if (m > 0) {
			r += m + " min ";
		}
		if (s > 0) {
			r += s + " s";
		}
		if (h <= 0 && m <= 0 && s <= 0) {
			r = "0 s";
		}

		return r;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chrono)) {
			return false;
		}
		Chrono c = (Chrono) o;
		return tempsDepart == c.tempsDepart && tempsFin == c.tempsFin && pauseDepart == c.pauseDepart
				&& pauseFin == c.pauseFin && duree == c.duree;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tempsDepart, tempsFin, pauseDepart, pauseFin, duree);
	}
}

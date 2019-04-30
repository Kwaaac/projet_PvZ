package models;

public class Chrono {
	private long tempsDepart = 0;
	private long tempsFin = 0;
	private long pauseDepart = 0;
	private long pauseFin = 0;
	private long duree = 0;

	/**
	 * lance le chronometre / peut être utilise pour le reset
	 */
	public void start() {
		tempsDepart = System.currentTimeMillis();
		tempsFin = 0;
		pauseDepart = 0;
		pauseFin = 0;
		duree = 0;
	}

	/**
	 * met en pause le chronomètre
	 */
	public void pause() {
		if (tempsDepart == 0) {
			return;
		}
		pauseDepart = System.currentTimeMillis();
		duree = (tempsFin - tempsDepart) - (pauseFin - pauseDepart);
	}

	/**
	 * relance le chronomètre
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
	 * arrête le chronomètre
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
	 * @return renvoie la durée du chrono en seconde
	 */
	public long getDureeSec() {
		return duree / 1000;
	}

	/**
	 * 
	 * @returnrenvoie la durée du chrono en milliseconde
	 */
	public long getDureeMs() {
		return duree;
	}

	/**
	 * 
	 * @return renvoie la durée du chrono au format texte
	 */
	public String getDureeTxt() {
		return timeToHMS(getDureeSec());
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
	 * @param tempsS temps a atteindre et relance le chrono
	 * @return revoie si le chronometre a atteind le temps donnee en parametre
	 */
	public boolean asReachTimer(long tempsS) {
		this.pause();
		if (this.getDureeSec() >= tempsS ) {
			this.start();
			return true;
		}
		this.resume();
		return false;
	}

	/**
	 * Observe sir le chrono est en stand-by
	 * 
	 * @return Renvoie true si le chrono est en stand-by, false si il est lancé
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
}

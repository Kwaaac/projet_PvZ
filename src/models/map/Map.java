package models.map;

import java.io.Serializable;
import java.util.ArrayList;

import models.SimpleGameData;
import views.BordView;

public abstract class Map implements Serializable {

	public static SimpleGameData dataBord() {
		ArrayList<Integer> mapProperties = null; // déclaration d'une arraylist qui contient les propriétées importantes d'une map
		
		String x = SimpleGameData.getMap(); // détection de la map sélectionnée au préalable
		switch (x) {
		case "Day":
			mapProperties = Day.getMapProperties();
			break;
		case "Night":
			mapProperties = Night.getMapProperties();
			break;
		case "Pool":
			mapProperties = Pool.getMapProperties();
			break;
			
		case "NightPool":
			mapProperties = Pool.getMapProperties();
			break;
			
		case "Roof":
			mapProperties = Pool.getMapProperties();
			break;
		}

		return new SimpleGameData(mapProperties.get(0), mapProperties.get(1)); //renvoi du nombre de lignes et colonnes
	}

	public static BordView view(SimpleGameData dataBord) {
		ArrayList<Integer> mapProperties = null;
		String x = SimpleGameData.getMap();
		switch (x) {
		case "Day":
			mapProperties = Day.getMapProperties();
			break;
			
		case "Night":
			mapProperties = Night.getMapProperties();
			break;
			
		case "Pool":
			mapProperties = Pool.getMapProperties();
			break;
			
		case "NightPool":
			mapProperties = Pool.getMapProperties();
			break;
			
		case "Roof":
			mapProperties = Pool.getMapProperties();
			break;
		}
		
		return BordView.initGameGraphics(mapProperties.get(2), mapProperties.get(3), mapProperties.get(4), dataBord); // xOrigine - yOrigine - largeur - ligne - colonne
	}

}

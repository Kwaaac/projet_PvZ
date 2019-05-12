package models.map;

import java.util.ArrayList;

import models.SimpleGameData;
import views.BordView;

public abstract class Map {
	private final int column = 8;

	public int getColumn() {
		return column;
	}

	public static SimpleGameData dataBord() {
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

		return new SimpleGameData(mapProperties.get(0), mapProperties.get(1));
	}

	public static BordView view() {
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
		return BordView.initGameGraphics(mapProperties.get(2), mapProperties.get(3), mapProperties.get(4), dataBord());
	}

}

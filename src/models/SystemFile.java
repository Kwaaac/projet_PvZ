package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import models.plants.Plant;
import models.zombies.Zombie;

public class SystemFile {
	private final static Path file = Paths.get("system.txt");
	private final static Path properties = Paths.get("properties.txt");
	
	
	
	public void save() throws IOException {
//		ArrayList<Plant> myPlants = ;
//		ArrayList<Zombie> myZombies = ;
//		ArrayList<Coordinates> placedPlant = ;
//		ArrayList<Soleil> mySun = ;
//		ArrayList<Zombie> zombieInQueu = ;
//		String map = SimpleGameData.getMap();
//		int difficulty = ;
//		int actualMoney = ;
//		Charset charset = StandardCharsets.UTF_8;
//		
//		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)){
//			writer.write(myPlants+"\n");
//			writer.write(myZombies+"\n");
//			writer.write(placedPlant+"\n");
//			writer.write(mySun+"\n");
//			writer.write(zombieInQueu+"\n");
//			writer.write(map+"\n");
//			writer.write(difficulty+"\n");
//			writer.write(actualMoney+"\n");
//		}
	}
	
	public void load() throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		try (BufferedReader reader = Files.newBufferedReader(file, charset)){
			
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static Path getPath() {
		return file;
	}
	
	
	
	public void GamePropertiesWrites(double speed, int plantSelectionNumber, int zombieSpawnRate, int timer, int sunNumber, int sunCostMultiplicator, 
			int bossNumber) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		
		
		try (BufferedWriter writer = Files.newBufferedWriter(properties, charset)){
			writer.write(speed+"\n");
			writer.write(plantSelectionNumber+"\n");
			writer.write(zombieSpawnRate+"\n");
			writer.write(timer+"\n");
			writer.write(sunNumber+"\n");
			writer.write(sunCostMultiplicator+"\n");
			writer.write(bossNumber+"\n");
		}
	}
	
//	public void GamePropertiesReads() throws IOException {
//		Charset charset = StandardCharsets.UTF_8;
//		
//		
//		try (BufferedReader reader = Files.newBufferedReader(file, charset)){
//			String line;
//			int c = 0;
//			while ((line = reader.readLine()) != null) {
//				switch (c) {
//					case 0:
//					Zombie.setSpeed(Integer.valueOf(line));
//					break;
//					case 1:
//					SimpleGameData.setSelectionNumberOfPlant(Integer.valueOf(line));
//					break;
//					case 2:
//					SimpleGameData.setZombieSpawnRate(Integer.valueOf(line));
//					break;
//					case 3:
//					SimpleGameData.setGameTimer(Integer.valueOf(line));
//					break;
//					case 4:
//					SimpleGameData.setSunNumber(Integer.valueOf(line));
//					break;
//					case 5:
//					SimpleGameData.setSunCostMultiplicator(Integer.valueOf(line));
//					break;
//					case 6:
//					Zombie.setBossNumber(Integer.valueOf(line));
//				}
//			}
//		}
//	}
}

package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import models.cells.Cell;
import models.plants.Plant;
import models.zombies.Zombie;
import views.SelectBordView;



@SuppressWarnings("serial")
public class SystemFile implements Serializable{

	/**
	 * Serialisation of :
	 * 		the game datas
	 * 		the selection view which contains plant selection
	 * 		the matrix of the game
	 */
	
	public static void save(SimpleGameData data, SelectBordView selectView) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("DataBord.ser"))) {
            oos.writeObject(data);
        }
        catch (IOException e) {
            e.printStackTrace();
            
        }
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SelectView.ser"))) {
            oos.writeObject(selectView.getSelectedPlants());
        }
        catch (IOException e) {
            e.printStackTrace();
            
        }
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("matrix.ser"))) {
            oos.writeObject(data.getMatrix());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		
		Charset charset = StandardCharsets.UTF_8;
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("map.txt"), charset)){
			writer.write(SimpleGameData.getMap());
		}
	}
	
	/**
	 * @return a game data or a plant selection view
	 * 
	 * @param s permit to chose what do we read
	 * 
	 */
	
	public static Object read(String s) throws ClassNotFoundException, IOException {
		
		switch (s) {
		case "data":
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("DataBord.ser"))) {
				
				SimpleGameData x = (SimpleGameData) ois.readObject();
				
				try (BufferedReader reader = Files.newBufferedReader(Paths.get("map.txt"), StandardCharsets.UTF_8)){
					String line;
					
					while ((line = reader.readLine()) != null) {
						x.setMap(line.toString());
					}
				}
				try (ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("matrix.ser"))) {
					 Cell[][] matrix = (Cell[][]) ois2.readObject();
					 
					 x.setMatrix(matrix);
				}
				catch (IOException e) {
		            e.printStackTrace();
		        }
				
				return x;
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			break;
			
		case "view":
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SelectView.ser"))) {
				
				@SuppressWarnings("unchecked")
				ArrayList<Plant> x = (ArrayList<Plant>) ois.readObject();
				return x;
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			break;
		}	
		return null;
	}
	
	/**
	 * Read of "Properties.txt" file to get information of the some game data :
	 * 		- the number of plant you can play
	 * 		- the time you have to wait until the zombies spawn
	 * 		- the time until the difficulty is swiching
	 * 		- the sun money you got at the begining
	 * 		- the number of fertilizer you got at the begining
	 * 		- the chance of a fertilized zombie spawning
	 * 		- the chance of a tombe spawn
	 * 		- all the different move speed that a zombie can have
	 */
	public static void readProperties(SimpleGameData dataBord) throws IOException {
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("Properties.txt"), StandardCharsets.UTF_8)){
			
			String content;
			int line = 0;
			
			while ((content = reader.readLine()) != null) {
				String[] x = content.split("#");
				switch(line) {
				case 2:
					SimpleGameData.setSelectionCaseNumber(Integer.valueOf(x[0]));
					break;
				case 4:
					dataBord.setTimeLimit(Long.valueOf(x[0]));
					break;
				case 6:
					dataBord.setTimeLimitDifficulty(Long.valueOf(x[0]));
					break;
				case 8:
					dataBord.setActualMoney(Integer.valueOf(x[0]));
					break;
				case 10:
					dataBord.setActualFertilizer(Integer.valueOf(x[0]));
					break;
				case 12:
					dataBord.setFertilizerChance(Integer.valueOf(x[0]));
					break;
				case 14:
					dataBord.setChanceTombeStone(Integer.valueOf(x[0]));
					break;
				case 16:
					Zombie.setZombieMoveSpeed_reallyFast(Double.valueOf(x[0]));
					break;
				case 17:
					Zombie.setZombieMoveSpeed_fast(Double.valueOf(x[0]));
					break;
				case 18:
					Zombie.setZombieMoveSpeed_medium(Double.valueOf(x[0]));
					break;
				case 19:
					Zombie.setZombieMoveSpeed_slow(Double.valueOf(x[0]));
					break;
				case 20:
					Zombie.setZombieMoveSpeed_verySlow(Double.valueOf(x[0]));
					break;
				case 21:
					Zombie.setZombieMoveSpeed_ultraSlow(Double.valueOf(x[0]));
					break;
				}
				line+=1;
			}
		}
	}
	
	/**
	 * Read of "Properties.txt" file to get information of the number of plant you can play.
	 * This information is aleardy use in the other methode but this one permite to get this info when ever you want.
	 */
	public static void readPrincipalMenuProperties() throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("Properties.txt"), StandardCharsets.UTF_8)){
					
			String content;
			int line = 0;
			
			while ((content = reader.readLine()) != null) {
				String[] x = content.split("#");
				switch(line) {
				case 2:
					SimpleGameData.setSelectionCaseNumber(Integer.valueOf(x[0]));
					break;
				}
				line+=1;
			}
		}
	
	}
	
}

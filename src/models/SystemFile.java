package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import models.cells.Cell;
import models.plants.Plant;
import views.SelectBordView;



public class SystemFile implements Serializable{

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
	
	
	public static void readProperties() throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("Properties.txt"), StandardCharsets.UTF_8)){
			
			String content;
			int line = 0;
			
			while ((content = reader.readLine()) != null) {
				switch(line) {
				case 0:
					
					break;
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				}
			}
		}
	}
	
	
	
	
}

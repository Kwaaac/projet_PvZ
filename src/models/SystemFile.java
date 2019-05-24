package models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SystemFile implements Serializable{

	public static void save(SimpleGameData data) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.ser"))) {
            oos.writeObject(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void read() throws ClassNotFoundException {
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream("test.ser"))) {
        	Object x = oos.readObject();
        	System.out.println(x);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}

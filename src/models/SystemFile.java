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
	
	public static Object read() throws ClassNotFoundException {
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream("test.ser"))) {
			System.out.println("ok read");
			return oos.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
}

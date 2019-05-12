package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemFile {
	private final static Path file = Paths.get("system.txt");
	
	
	
	public void save() throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)){
			writer.write("Bonjour\n");
		}
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
}

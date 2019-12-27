package application.model.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVReader {
	
	public static List<Map<String, List<String>>> readCSV(String path) throws IOException{
		
		String line;
		List<Map<String, List<String>>> records = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));

		// The first line matches field's names
		line =  reader.readLine();
		
		String[] champs = line.split(",");
		
		while((line = reader.readLine()) != null) {
			String[] values = line.split(",",champs.length); // On définit une limite au cas ou ou il y aurais des , a la fin des champs
			Map<String, List<String>> dico = new HashMap<>();
			
			for (int i = 0; i < champs.length; i++) {
				dico.put(champs[i], Arrays.asList(values[i].split(";")));
			}
			records.add(dico);
		}
		
		reader.close();
		
				
		return records;
	}

}

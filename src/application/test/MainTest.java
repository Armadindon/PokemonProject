package application.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import application.model.appmodel.TeamBuilder;
import application.model.utils.CSVReader;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Test de lecture du fichier save.cer");
		ObjectInputStream ois =null;
		try {
			final FileInputStream file = new FileInputStream("save.ser");
			 ois = new ObjectInputStream(file);
			TeamBuilder t = (TeamBuilder) ois.readObject();
			System.out.println(t.getTeamSize());
			System.out.println(t.getPokeList());
			System.out.println(t.getPokeList().get(0).getAllPossiblesMoves());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ois !=null) {
					ois.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

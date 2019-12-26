package application.test;

import java.io.IOException;

import application.model.utils.CSVReader;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Lecture du fichier pokemon.csv");
		System.out.println(CSVReader.readCSV("scripts/pokemons.csv"));

	}

}

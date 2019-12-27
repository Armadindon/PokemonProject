package application.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import application.model.utils.CSVReader;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Lecture des fichiers CSV :");
		
		System.out.println("Lecture de pokemon.csv...");
		List<Map<String, List<String>>> dataPokemon = CSVReader.readCSV("scripts/pokemons.csv");
		System.out.println("Lecture de moves.csv...");
		List<Map<String, List<String>>> dataMoves = CSVReader.readCSV("scripts/moves.csv");
		
		ArrayList<Move> existingMoves = new ArrayList<>();
		for(Map<String, List<String>> data : dataMoves) {
			Move mv = Move.generateFromMap(data);
			if(mv != null) existingMoves.add(mv);
		}
		
		System.out.println(existingMoves);
		
		ArrayList<Pokemon> Pokedex = new ArrayList<>();
		for(Map<String, List<String>> data : dataPokemon) {
			Pokemon pk = Pokemon.generateFromMap(data, existingMoves);
			if(pk != null) Pokedex.add(pk);
		}
		
		System.out.println(Pokedex);
		
	}

}

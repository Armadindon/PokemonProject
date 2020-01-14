package application.model.appmodel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import application.model.pokemon.Stats;
import application.model.utils.CSVReader;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TeamBuilder implements Serializable {

	private static TeamBuilder teamBuilderSingleton = null;

	private final ArrayList<Move> existingMoves;
	private final ArrayList<Pokemon> pokeList;

	private TeamBuilder(ArrayList<Move> existingMoves, ArrayList<Pokemon> pokeList) {
		this.existingMoves = existingMoves;
		this.pokeList = pokeList;
	}

	public static synchronized TeamBuilder getInstance() throws IOException {

		if (teamBuilderSingleton != null) {
			return teamBuilderSingleton;
		}

		List<Map<String, List<String>>> dataPokemon = CSVReader.readCSV("scripts/pokemons.csv");
		List<Map<String, List<String>>> dataMoves = CSVReader.readCSV("scripts/moves.csv");

		ArrayList<Move> existingMoves = new ArrayList<>();
		for (Map<String, List<String>> data : dataMoves) {
			Move mv = Move.generateFromMap(data);
			if (mv != null)
				existingMoves.add(mv);
		}
		ArrayList<Pokemon> pokeList = new ArrayList<>();
		for (Map<String, List<String>> data : dataPokemon) {
			Pokemon pk = Pokemon.generateFromMap(data, existingMoves);
			if (pk != null)
				pokeList.add(pk);
		}

		return new TeamBuilder(existingMoves, pokeList);
	}

	public ArrayList<Pokemon> createRandomTeam(int numberOfPokemon) {
		ArrayList<Pokemon> randomTeam = new ArrayList<>();

		Pokemon randomPokemon;
		int indexPokemon;
		int indexMove;

		for (int i = 0; i < numberOfPokemon; i++) {
			indexPokemon = new Random().nextInt(pokeList.size());
			randomPokemon = pokeList.get(indexPokemon);

			int pkmnMovesSizes = randomPokemon.getAllPossiblesMoves().size();
			if (pkmnMovesSizes > 0) {
				for (int j = 0; j < 4; j++) {

					// Change the random number if the attack is already learned

					do {
						indexMove = new Random().nextInt(pkmnMovesSizes);
						if (randomPokemon.getAllPossiblesMoves().size() == randomPokemon.getlearnedMoves().size()) {
							break;
						}
					} while (randomPokemon.getlearnedMoves()
							.contains(randomPokemon.getAllPossiblesMoves().get(indexMove)));

					randomPokemon.addMoveToLearnedMoves(randomPokemon.getAllPossiblesMoves().get(indexMove));
				}
			}
			randomTeam.add(randomPokemon);
		}

		return randomTeam;
	}

	public ArrayList<Pokemon> getPokeList() {
		return pokeList;
	}

}

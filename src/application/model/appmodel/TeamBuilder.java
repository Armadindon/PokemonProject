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

	private Pokemon pokemon;
	private final ArrayList<Pokemon> team = new ArrayList<>(6); // max 6 pokemons
	private final ArrayList<Move> existingMoves;
	private final ArrayList<Pokemon> pokeList;

	private TeamBuilder(ArrayList<Move> existingMoves, ArrayList<Pokemon> pokeList) {
		this.pokemon = pokeList.get(0);
		this.existingMoves = existingMoves;
		this.pokeList = pokeList;
	}

	public static TeamBuilder getInstance() throws IOException {

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

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public ArrayList<Pokemon> getTeam() {
		ArrayList<Pokemon> copyTeam = new ArrayList<>();
		team.forEach(p -> {
			try {
				copyTeam.add((Pokemon) p.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		});
		return copyTeam;
	}

	public ArrayList<Pokemon> getPokeList() {
		return pokeList;
	}

	public void addPokemonToTeam(Pokemon pokemon) {
		if (pokemon == null)
			throw new IllegalArgumentException("Pokemon cannot be null");

		team.add(pokemon);
	}

	public void removePokemon(int pokemonIndex) {
		team.remove(pokemonIndex);
	}

	public int getTeamSize() {
		return team.size();
	}
	
	public boolean canAddPokemon() {

		if (pokemon.getlearnedMoves().size() == 0) {
			return false;
		}

		return true;

	}
	
	public boolean addMovePokedex(Move move) {
		return pokemon.addMoveToLearnedMoves(move); 
	}

	public void removeMovePokedex(int moveIndex) {
		pokemon.removeMoveFromLearnedMoves(moveIndex);
	}
}

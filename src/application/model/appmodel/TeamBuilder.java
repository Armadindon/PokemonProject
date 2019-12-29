package application.model.appmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class TeamBuilder {
	
	private Pokemon pokemon;
	private ArrayList<Pokemon> team = new ArrayList<>(6); // Only 6 pokemons
	ArrayList<Move> existingMoves;
	ArrayList<Pokemon> pokeList;
	
	private TeamBuilder(ArrayList<Move> existingMoves, ArrayList<Pokemon> pokeList) {
		this.pokemon = pokeList.get(0);
		this.existingMoves = existingMoves;
		this.pokeList = pokeList;
	}
	

	public static TeamBuilder createTeamBuilder() throws IOException {
		
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

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public ArrayList<Pokemon> getTeam() {
		return team;
	}
	
	public ArrayList<Pokemon> getPokeList(){
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

	public void updateName(String name, Label labelChangeName) {
		if (name.length() > 15) {
			labelChangeName.setText("Name too long");

		} else if (name.contains(":")) {
			// TODO Mettre une vrai vérification sur les ponctuations
			labelChangeName.setText("Wrong name");

		} else {
			labelChangeName.setText("Name changed !");
			pokemon.setName(name);
		}

		FadeTransition ft = new FadeTransition(new Duration(3_000), labelChangeName);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		ft.play();

	}
	
	public boolean canAddPokemon(Label labelError) {
		
		if(pokemon.getlearnedMoves().size()==0) {
			labelError.setText("Your Pokémon need at least 1 Move");
			FadeTransition ft = new FadeTransition(new Duration(5_000), labelError);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			ft.play();
			return false;
		}
		
		return true;
		
	}

	/**
	 * Show the pokemon's infos on the pokedex
	 * 
	 * @param pokemon
	 * @param name
	 * @param type1
	 * @param type2
	 * @param height
	 * @param weigth
	 * @param hp
	 * @param atk
	 * @param atkSpe
	 * @param def
	 * @param defSpe
	 * @param speed
	 * @param img
	 * @param description
	 */
	public void modelPokedexUpdate(Pokemon pokemon, Label name, Label type1, Label type2, Label height, Label weight,
			Label hp, Label atk, Label atkSpe, Label def, Label defSpe, Label speed, ImageView img,
			TextArea description, ArrayList<VBox> teamDisplay, Button confirmButton) {

		name.setText(pokemon.getName());
		type1.setText(pokemon.getType1().name());
		if (pokemon.getType2() == null) {
			type2.setText("");
		} else {
			type2.setText(pokemon.getType2().name());
		}

		height.setText(Integer.toString(pokemon.getHeight()) + " m");
		weight.setText(Integer.toString(pokemon.getWeight()) + " kg");

		Stats pokeStats = pokemon.getBaseStats();

		hp.setText(Integer.toString(pokeStats.getHp()));
		atk.setText(Integer.toString(pokeStats.getAttack()));
		atkSpe.setText(Integer.toString(pokeStats.getSpecialAttack()));
		def.setText(Integer.toString(pokeStats.getDefense()));
		defSpe.setText(Integer.toString(pokeStats.getSpecialDefense()));
		speed.setText(Integer.toString(pokeStats.getSpeed()));

		img.setImage((Image) pokemon.getFrontSprite());

		if (0 != team.size()) {
			confirmButton.setDisable(false);

			int i;
			for (i = 0; i < team.size(); i++) {
				((Label) teamDisplay.get(i).getChildren().get(0)).setText(team.get(i).getName());
				teamDisplay.get(i).setVisible(true);

			}

			for (i = team.size(); i < 6; i++) {
				teamDisplay.get(i).setVisible(false);
			}
		} else {
			confirmButton.setDisable(true);
			teamDisplay.get(0).setVisible(false);
		}

	}

	public void modelMovesUpdate(Move move, Label name, Label type, Label accuracy, Label pp, Label effect,
			TextArea description, ArrayList<VBox> moves, ArrayList<Button> delButtons, Button confirmButton) {
		
		ArrayList<Move> learnedMoves = pokemon.getlearnedMoves();
		name.setText(move.getName());
		type.setText(move.getType().name());
		accuracy.setText(Integer.toString(move.getAccuracy()));
		pp.setText(Integer.toString(move.getPp()));
		effect.setText(move.getEffectToString());
		description.setText(move.getDescription());

		if (0 != learnedMoves.size()) {
			confirmButton.setDisable(false);

			int i;
			for (i = 0; i < learnedMoves.size(); i++) {
				((Label) moves.get(i).getChildren().get(0)).setText(learnedMoves.get(i).getName());
				((Label) moves.get(i).getChildren().get(1)).setText(Integer.toString((learnedMoves.get(i).getPp())));
				moves.get(i).setVisible(true);
				delButtons.get(i).setVisible(true);
			}

			for (i = learnedMoves.size(); i < 4; i++) {
				moves.get(i).setVisible(false);
				delButtons.get(i).setVisible(false);
			}

		} else {
			// TODO retirer le com pour desactiver le boutton, retire pour des tests
			// confirmButton.setDisable(true);
			moves.get(0).setVisible(false);
			delButtons.get(0).setVisible(false);
		}
	}
	
	public void addMovePokedex(Move move, Label labelError) {
		if(!pokemon.addMoveToLearnedMoves(move)){
			labelError.setText("Oops you cannot add this move");
			
			FadeTransition ft = new FadeTransition(new Duration(3_000), labelError);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			ft.play();
		}
	}
	
	public void removeMovePokedex(int moveIndex) {
		pokemon.removeMoveFromLearnedMoves(moveIndex);
	}
}

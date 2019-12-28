package application.model.appmodel;

import java.util.ArrayList;

import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import application.model.pokemon.Stats;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Pokedex {

	private Pokemon pokemon;
	private ArrayList<Pokemon> team = new ArrayList<>(6); // Only 6 pokemons

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public ArrayList<Pokemon> getTeam() {
		return team;
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
		labelChangeName.setText("Name changed !");
		FadeTransition ft = new FadeTransition(new Duration(4_000), labelChangeName);
		
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
			TextArea description, ArrayList<VBox> moves, Button confirmButton) {

		name.setText(move.getName());
		type.setText(move.getType().name());
		accuracy.setText(Integer.toString(move.getAccuracy()));
		pp.setText(Integer.toString(move.getPp()));
		effect.setText(move.getEffectToString());
		description.setText(move.getDescription());

		if (0 != pokemon.getlearnedMoves().size()) {
			confirmButton.setDisable(false);

			int i;
			for (i = 0; i < team.size(); i++) {
				((Label) moves.get(i).getChildren().get(0)).setText(team.get(i).getName());
				moves.get(i).setVisible(true);
			}

			for (i = team.size(); i < 6; i++) {
				moves.get(i).setVisible(false);
			}

		} else {
			//TODO retirer le com, pour des tests
			// confirmButton.setDisable(true);
			moves.get(0).setVisible(false);
		}
	}

}

package application.model.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import application.model.appmodel.TeamBuilder;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Player {
	private final ArrayList<Pokemon> team;
	private Pokemon selectedPokemon;
	private int alive;
	private final boolean bot;
	
	public Player(ArrayList<Pokemon> team, boolean bot) {
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); //Le premier Pokémon est celui lancé en premier
		alive = team.size();
		this.bot = bot;
	}
	
	public static Player createRandomPlayer() throws IOException {
		return new Player(TeamBuilder.createTeamBuilder().createRandomTeam(), true);
	}
	
	public void switchPokemon(Pokemon p) {
		if(team.contains(p)) {
			selectedPokemon = p;
		}else {
			throw new IllegalArgumentException("The pokemon is not in the team");
		}
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public void moveDisplayUpdate(AnchorPane movePane) {
		ArrayList<Move> pokeMoves = selectedPokemon.getlearnedMoves();
		for(int i = 0; i < pokeMoves.size(); i++) {
			// From the Anchor pane of moves, get the i vbox containing 2 labels. 0: move's Name, 1: move's PP
			Move pokeMove = pokeMoves.get(i);
			
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(0))).setText(pokeMove.getName());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(1))).setText(pokeMove.getPP() + "/" + pokeMove.getMaxPP());
		}
	}
	
	public void teamDisplayUpdate(AnchorPane switchPane) {
		int pokeIndex = team.indexOf(selectedPokemon);
		
		for(int i = 0; i < team.size(); i++) {
			Pokemon pokemon = team.get(i);
			
			HBox pokemonDisplay = ((HBox) switchPane.getChildren().get(i));
			((ImageView) pokemonDisplay.getChildren().get(0)).setImage(pokemon.getFrontSprite());
			
			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(0))).setText(pokemon.getName());
			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(1))).setText(pokemon.getBaseStats().getHp() + "/" + "hpmax");
		
			if(i == pokeIndex) {
				pokemonDisplay.setDisable(true);
			}
		}
	}
	
	// boolean à remplacer quand le boolean bot sera implémenter
	public void mainScreenUpdate(ImageView imageViewAllyPokemon, Boolean bot) {
		if(bot) { 
			imageViewAllyPokemon.setImage(selectedPokemon.getFrontSprite());
			return;
		}
		imageViewAllyPokemon.setImage(selectedPokemon.getBackSprite());
	}
}

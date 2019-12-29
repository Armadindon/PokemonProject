package application.model.fight;

import java.util.ArrayList;
import java.util.Objects;

import application.model.pokemon.Pokemon;

public class Player {
	private final ArrayList<Pokemon> team;
	private Pokemon selectedPokemon;
	private int alive;
	private final boolean bot;
	
	public Player(ArrayList<Pokemon> team, Pokemon SelectedPokemon,boolean bot) {
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); //Le premier Pokémon est celui lancé en premier
		alive = team.size();
		this.bot = bot;
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

}

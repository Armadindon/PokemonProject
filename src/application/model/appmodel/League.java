package application.model.appmodel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import application.model.fight.Player;

/**
 * Class representing a League constitued of several Trainers, per default, in the pokemon games, a league have a number of members and a league master
 * @author Armadindon, Kwaaac
 *
 */
public class League implements Serializable{

	private final List<Player> league;
	private int wichPlayer;
	
	/**
	 * Default Contstructor for league
	 * @param league - List containing differents players composing the league
	 * 
	 */
	public League(List<Player> league) {
		this.league = Objects.requireNonNull(league);
		this.wichPlayer = 0;
	}
	
	/**
	 * Permit to have next player of the league
	 * @return The Next Player to fight against the main player
	 */
	public Player getFightingTeam() {
		if(isOver()) {
			throw new IllegalStateException("The league is over");
		}
		
		return league.get(wichPlayer);
	}
	
	/**
	 * Permit to indicate that we want the next Player when we will use getFightingTeam()
	 */
	public void nextFightingTeam() {
		wichPlayer++;
	}
	
	/**
	 * Check if the league is over (if they are no trainers left)
	 * @return true if the league is over, or false if not
	 */
	public boolean isOver() {
		return wichPlayer > league.size() - 1;
	}
	
	/**
	 * Static method for generate a pseudo randmo opponent with a random team.
	 * 
	 * @param nbPokemon - numbers of Pokemon per person
	 * @return A single opponent 
	 * @throws IOException
	 */
	public static League generateRandomOpponent(int nbPokemon) throws IOException {
		return new League(new ArrayList<Player>(Arrays.asList(new Player(TeamBuilder.getInstance().createRandomTeam(nbPokemon), true))));
	}
	
	/**
	 * Static Method for generate a pseudo random league with random Teams.
	 * @param members - number of members of the league
	 * @param pokemonPerPerson - numbers of Pokemon per person (The League Master always have 6 pokemon)
	 * @param tb - The teambuilder object containing all the possible pokemon and moves
	 * @return a new random League based on the parameters given
	 * @throws IOException 
	 */
	public static League generateRandomLeague(int members, int pokemonPerPerson) throws IOException {
		
		ArrayList<Player> league = new ArrayList<>();
		
		for (int i = 0; i < members-1 ; i++) {
			
			league.add(new Player(TeamBuilder.getInstance().createRandomTeam(pokemonPerPerson), true));
			
		}
		
		//For the League Master
		
		league.add(new Player(TeamBuilder.getInstance().createRandomTeam(6), true));
		
		return new League(league);
	}

}

package application.model.appmodel;

import java.io.Serializable;
import java.util.ArrayList;

import application.model.fight.Player;

public class League implements Serializable{

	private final ArrayList<Player> league;
	private int wichTeam;

	public League(ArrayList<Player> league) {
		this.league = league;
		this.wichTeam = 0;
	}
	
	public Player getFightingTeam() {
		return league.get(wichTeam);
	}
	
	public void nextFightingTeam() {
		wichTeam++;
	}
	
	public static League generateRandomLeague(int members, int pokemonPerPerson,TeamBuilder tb) {
		
		ArrayList<Player> league = new ArrayList<>();
		
		for (int i = 0; i < members-1 ; i++) {
			
			league.add(new Player(tb.createRandomTeam(pokemonPerPerson), true));
			
		}
		
		//For the League Master
		
		league.add(new Player(tb.createRandomTeam(6), true));
		
		return new League(league);
	}

}

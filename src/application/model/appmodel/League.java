package application.model.appmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class League implements Serializable{

	private final ArrayList<TeamBuilder> league;
	private int wichTeam;

	public League(ArrayList<TeamBuilder> league) {
		this.league = league;
		this.wichTeam = 0;
	}
	
	public TeamBuilder getFightingTeam() {
		return league.get(wichTeam);
	}
	
	public void nextFightingTeam() {
		wichTeam++;
	}

}

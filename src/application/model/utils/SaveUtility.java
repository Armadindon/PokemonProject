package application.model.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;

public class SaveUtility implements Serializable{
	private MenuSelect whichMenu;
	private TeamBuilder player;
	private League league;
	
	
	public SaveUtility(MenuSelect whichMenu, TeamBuilder player, Optional<League> league) {
		super();
		this.whichMenu = whichMenu;
		this.player = player;
		if(league.isPresent()) this.league = league.get();
		else this.league = null;
	}


	public MenuSelect getWhichMenu() {
		return whichMenu;
	}


	public TeamBuilder getPlayer() {
		return player;
	}


	public Optional<League> getLeague() {
		if(league == null) return Optional.empty();
		return Optional.of(league);
	}
	
	
}

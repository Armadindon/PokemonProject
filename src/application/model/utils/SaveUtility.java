package application.model.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;

/**
 * Utils Class who represent all the data to save
 * @author Armadindon
 */
public class SaveUtility implements Serializable {
	private MenuSelect whichMenu;
	private TeamBuilder player;
	private League league;
	private SpecialData data;

	/**
	 * Default constructor
	 * @param whichMenu
	 * @param player
	 * @param league
	 * @param data
	 */
	public SaveUtility(MenuSelect whichMenu, TeamBuilder player, Optional<League> league, Optional<SpecialData> data) {
		this.whichMenu = whichMenu;
		this.player = player;
		if (league.isPresent())
			this.league = league.get();
		else
			this.league = null;

		if (data != null) {
			if (data.isPresent())
				this.data = data.get();
			else
				this.data = null;
		}
	}

	public MenuSelect getWhichMenu() {
		return whichMenu;
	}

	public TeamBuilder getPlayer() {
		return player;
	}

	public Optional<League> getLeague() {
		if (league == null)
			return Optional.empty();
		return Optional.of(league);
	}

	public Optional<SpecialData> getSpecialData() {
		if (data == null)
			return Optional.empty();
		return Optional.of(data);
	}

}

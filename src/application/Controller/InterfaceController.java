package application.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.model.appmodel.League;
import application.model.appmodel.SpecialData;
import application.model.appmodel.TeamBuilder;

public interface InterfaceController {
	
	/**
	 * Used to update the interface displaying on the scene
	 */
	void displayUpdate();

	/**
	 * Initialise the controller. Used when you charge the controller via switching
	 * betweens scene and you want to transfert datas
	 * 
	 * @param teamBuilder Player's team
	 * @param league Opponent's team
	 * @throws IOException
	 */
	void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data) throws IOException;
}

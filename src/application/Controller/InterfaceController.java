package application.Controller;

import java.io.IOException;
import java.util.Optional;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.fight.Player;

/**
 * This interface is used to regroup every Controller
 * 
 * @author kwaaac
 *
 */
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
	 * @param league      Opponent's team
	 * @param data 		  Optional special datas for special events
	 * @throws IOException
	 */
	void initTeamBuilder(Player player, Optional<League> league, Optional<SpecialData> data)
			throws IOException;
}

package application.Controller;

import java.io.IOException;
import java.util.Optional;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This abstract controller allow to factorise code used by controllers
 * 
 * @author Kwaaac
 *
 */
public abstract class AbstractController implements InterfaceController {

	protected TeamBuilder teamBuilder;

	protected Optional<League> league = Optional.empty();

	protected Optional<SpecialData> data;

	/**
	 * Change the scene without transferring any datas
	 * 
	 * @param event    Event that trigger the method
	 * @param fxmlFile The next scene
	 * @throws IOException Can throw an error if the load fails
	 */
	protected void changeSceneWithoutData(Event event, String fxmlFile) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/application/view/" + fxmlFile));
		Scene moveScene = new Scene(root);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	/**
	 * Change the scene with transferring a teamBuilder and a League In case League
	 * is null, it must be an optional object
	 * 
	 * @param event       Event that trigger the method
	 * @param fxmlFile    The next scene
	 * @param teamBuilder Player team
	 * @param league      Opponents team
	 * @throws IOException Can throw an error if the load fails
	 */
	protected void changeSceneTeamBuilder(Event event, String fxmlFile, TeamBuilder teamBuilder,
			Optional<League> league, Optional<SpecialData> data) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/view/" + fxmlFile));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		InterfaceController controller = loader.getController();

		if (data == null) {
			data = Optional.empty();
		}

		controller.initTeamBuilder(teamBuilder, league, data);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();

	}

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		if (league.isPresent()) {
			this.league = league;
		} else
			this.league = Optional.empty();

		if (data.isPresent()) {
			this.data = data;
		} else
			this.data = Optional.empty();

		this.teamBuilder = teamBuilder;
	}

}

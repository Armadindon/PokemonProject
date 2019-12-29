package application;

import java.io.IOException;

import application.model.appmodel.TeamBuilder;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractTeamBuilderController extends AbstractController
		implements InterfaceTeamBuilderController {
	
	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder) throws IOException {
		return;
	}

	protected void changeSceneTeamBuilder(Event event, String fxmlFile, TeamBuilder teamBuilder) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlFile));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		InterfaceTeamBuilderController controller = loader.getController();

		controller.initTeamBuilder(teamBuilder);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();

	}

}

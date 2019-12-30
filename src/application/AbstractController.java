package application;

import java.io.IOException;

import application.model.appmodel.TeamBuilder;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractController implements InterfaceController {

	protected void changeSceneWithoutData(Event event, String fxmlFile) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
		Scene moveScene = new Scene(root);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	protected void changeSceneTeamBuilder(Event event, String fxmlFile, TeamBuilder teamBuilder) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlFile));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		InterfaceController controller = loader.getController();

		controller.initTeamBuilder(teamBuilder);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();

	}
	
	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder) throws IOException {
		return;
	}
	
}

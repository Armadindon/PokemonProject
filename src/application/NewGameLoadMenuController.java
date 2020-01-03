package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.appmodel.TeamBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class NewGameLoadMenuController extends AbstractController {

	private TeamBuilder teamBuilder;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnLoadGame;

	@FXML
	private Button btnNewGame;

	@FXML
	private Button btnQuit;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private AnchorPane root;

	@FXML
	void load(ActionEvent event) {
		// TODO Implement load
	}

	@FXML
	void newGame(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "BuildTeam.fxml", teamBuilder, Optional.empty());
	}

	@FXML
	void quit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void displayUpdate() {
		// No new display
	}

    @FXML
    void initialize() throws IOException {
		teamBuilder = TeamBuilder.createTeamBuilder();
	}
}

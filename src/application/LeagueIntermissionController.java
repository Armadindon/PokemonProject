package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import application.model.utils.MenuSelect;
import application.model.utils.SaveUtility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class LeagueIntermissionController extends AbstractController {

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league) throws IOException {
		super.initTeamBuilder(teamBuilder, league);

		if (this.league.isPresent()) {
			this.league.get().nextFightingTeam();
		}

		displayUpdate();
	}

	@FXML
	private AnchorPane root;

	@FXML
	private Label labelText;

	@FXML
	private Button btnKeepGoing;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnQuit;

	@FXML
	void nextLeagueFight(ActionEvent event) throws IOException {
		changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, league);

	}

	@FXML
	void goBackToMenu(ActionEvent event) throws IOException {
		changeSceneTeamBuilder(event, "ChooseGame.fxml", teamBuilder, league);
	}

	@FXML
	void quit(ActionEvent event) {
		Platform.exit();

	}

	@FXML
	void save(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));

		File f = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

		SaveUtility save;

		if (league != null)
			save = new SaveUtility(MenuSelect.INTERLEAGUE, teamBuilder, league);
		else
			save = new SaveUtility(MenuSelect.INTERLEAGUE, teamBuilder, Optional.empty());

		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(save);
		oos.flush();
		oos.close();
	}

	@Override
	public void displayUpdate() {
		if (league.isPresent() && league.get().isOver()) {
			labelText.setText("You won every fights !");
			btnKeepGoing.setPrefWidth(426);
			btnKeepGoing.setText("Choose an another game mode");

			// Change the action
			btnKeepGoing.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						goBackToMenu(event);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}

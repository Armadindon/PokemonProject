package application.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import application.model.appmodel.League;
import application.model.appmodel.SpecialData;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class LeagueIntermissionController extends AbstractController {
	
	private MediaPlayer mp;

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data) throws IOException {
		super.initTeamBuilder(teamBuilder, league, data);

		if (this.league.isPresent()) {
			this.league.get().nextFightingTeam();
		}
		
		String path = System.getProperty("user.dir") + "/Misc/Music/Pokemon_Red_&_Blue_OST/16 - Victory.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mp.setCycleCount(MediaPlayer.INDEFINITE);

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
		changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, league, data);

	}

	@FXML
	void goBackToMenu(ActionEvent event) throws IOException {
		mp.stop();
		changeSceneTeamBuilder(event, "ChooseGame.fxml", teamBuilder, league, data);
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

		Optional<SpecialData> saveData;

		if(data != null) {
			saveData = data;
		} else {
			saveData = Optional.empty();
		}

		if (league != null)
			save = new SaveUtility(MenuSelect.INTERLEAGUE, teamBuilder, league, saveData);
		else
			save = new SaveUtility(MenuSelect.INTERLEAGUE, teamBuilder, Optional.empty(), saveData);

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

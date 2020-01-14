package application.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import application.model.fight.Player;
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

/**
 * 
 * This class is designed to be used with the view file :
 * "LeagueIntermission.fxml"
 * 
 * This class is used to tell to player if he won, loose of can keep going on
 * the league
 * 
 * @author kwaaac
 */
public class LeagueIntermissionController extends AbstractController {

	private MediaPlayer mp;

	@Override
	public void initTeamBuilder(Player player, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		super.initTeamBuilder(player, league, data);

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

	/**
	 * Go the next fight in case the player is fighting a league
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void nextLeagueFight(ActionEvent event) throws IOException {
		mp.stop();
		changeSceneTeamBuilder(event, "Fight.fxml", player, league, data);

	}

	/**
	 * In case the player win or loose, he goes to the choose game menu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void goBackToMenu(ActionEvent event) throws IOException {
		mp.stop();
		changeSceneTeamBuilder(event, "ChooseGame.fxml", player, league, data);
	}

	/**
	 * Qui the game without saving
	 * 
	 * @param event
	 */
	@FXML
	void quit(ActionEvent event) {
		Platform.exit();

	}

	/**
	 * Save the current game
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void save(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));

		File f = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

		SaveUtility save;

		Optional<SpecialData> saveData;

		if (data != null) {
			saveData = data;
		} else {
			saveData = Optional.empty();
		}

		if (league != null)
			save = new SaveUtility(MenuSelect.INTERLEAGUE, player, league, saveData);
		else
			save = new SaveUtility(MenuSelect.INTERLEAGUE, player, Optional.empty(), saveData);

		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(save);
		oos.flush();
		oos.close();
	}

	@Override
	public void displayUpdate() {
		// If the player won or loose, there is a different interface
		if (super.data.get().equals(SpecialData.WIN) || super.data.get().equals(SpecialData.LOOSE)) {
			if (super.data.get().equals(SpecialData.LOOSE)) {
				labelText.setText("You loosed, And toc !");
			} else {
				labelText.setText("You won every fights !");
			}

			btnKeepGoing.setPrefWidth(426);
			btnKeepGoing.setText("Choose an another game mode");

			// Change the action with the interface
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

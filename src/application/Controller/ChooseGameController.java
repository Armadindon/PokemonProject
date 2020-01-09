package application.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import application.model.utils.MenuSelect;
import application.model.utils.SaveUtility;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This controller allow the player to choose a game mode between multiple
 * gamemodes, saves and quit. The view affected to this class is "ChooseGame.fxml"
 * 
 * @author Armadindon, kwaaac
 *
 */
public class ChooseGameController extends AbstractController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label labelSave;

	@FXML
	private Button btnHomeMade;

	@FXML
	private Button btnLigueDUT2;

	@FXML
	private Button btnLigueGame;

	@FXML
	private Button btnQuit;

	@FXML
	private Button btnRandomFight;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private AnchorPane root;

	/**
	 * Change the scene to the fight scene with a single random opponent
	 * 
	 * @param event ActionEvent by clicking on the button
	 * @throws IOException Can generate an error from changing scene
	 */
	@FXML
	void randomFight(ActionEvent event) throws IOException {
		// We generate a League with only one guy
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder,
				Optional.of(League.generateRandomOpponent(6)), Optional.empty());
	}

	/**
	 * Change the scene to the fight scene with the DUT2 League
	 * 
	 * @param event ActionEvent by clicking on the button
	 * @throws IOException Can generate an error from changing scene
	 */
	@FXML
	void LigueDUT2Game(ActionEvent event) throws IOException {
		// créer une ligue random si pas init

		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder,
				Optional.of(League.generateRandomLeague(9, 6)), Optional.empty());

	}

	/**
	 * Change the scene to the fight scene with a single opponent but with the home
	 * made modifications
	 * 
	 * @param event ActionEvent by clicking on the button
	 * @throws IOException
	 */
	@FXML
	void homeMadeGame(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder,
				Optional.of(League.generateRandomLeague(2, 6)), Optional.of(SpecialData.HOMEMADE));
	}

	/**
	 * Change the scene to the fight scene with the League
	 * 
	 * @param event ActionEvent by clicking on the button
	 * @throws IOException
	 */
	@FXML
	void ligueGame(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder,
				Optional.of(League.generateRandomLeague(5, 4)), Optional.empty());

	}

	/**
	 * When used this method open an file explorer to save a save file
	 * 
	 * @param event ActionEvent by clicking on the button
	 * @throws IOException
	 */
	@FXML
	void save(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));

		File f = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

		SaveUtility save = new SaveUtility(MenuSelect.MAINMENU, teamBuilder, league, data);

		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(save);
		oos.flush();
		oos.close();

		labelSave.setText("Team saved !");
		displayUpdate();
	}

	/**
	 * This method close the application
	 * 
	 * @param event
	 */
	@FXML
	void quit(ActionEvent event) {
		Platform.exit();
	}


	@Override
	public void displayUpdate() {
		// Update the display of the "save" notification
		FadeTransition ft = new FadeTransition(new Duration(3_000), labelSave);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		ft.play();
	}

}

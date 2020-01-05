package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.appmodel.League;
import application.model.appmodel.SpecialData;
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

	@FXML
	void randomFight(ActionEvent event) throws IOException {
		//We generate a League with only one guy
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, Optional.of(League.generateRandomLeague(2, 3, teamBuilder)), data);
	}

	@FXML
	void LigueDUT2Game(ActionEvent event) throws IOException {
		// créer une ligue random si pas init
		
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, Optional.of(League.generateRandomLeague(9, 6, teamBuilder)), data);

	}

	@FXML
	void homeMadeGame(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, league, Optional.of(SpecialData.HOMEMADE));
	}

	@FXML
	void ligueGame(ActionEvent event) throws IOException {
		// créer une ligue random si pas init

		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, Optional.of(League.generateRandomLeague(5, 4, teamBuilder)), data);

	}

	@FXML
	void save(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));
		
		File f = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());
		
	    SaveUtility save = new SaveUtility(MenuSelect.MAINMENU, teamBuilder, league, data);
	    
	    FileOutputStream file =  new FileOutputStream(f);
	    ObjectOutputStream oos = new ObjectOutputStream(file);
	    oos.writeObject(save);
	    oos.flush();
	    oos.close();
	    
		labelSave.setText("Team saved !");
		displayUpdate();
	}

	@FXML
	void quit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void displayUpdate() {
		FadeTransition ft = new FadeTransition(new Duration(3_000), labelSave);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		ft.play();
	}

}

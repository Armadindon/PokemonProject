package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.appmodel.TeamBuilder;
import application.model.utils.MenuSelect;
import application.model.utils.SaveUtility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

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
	void load(ActionEvent event) throws IOException, ClassNotFoundException {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));
		
		File f = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());
		
	    
	    FileInputStream file =  new FileInputStream(f);
	    ObjectInputStream ois = new ObjectInputStream(file);
	    
	    SaveUtility save = (SaveUtility) ois.readObject();
	    ois.close();
	    changeSceneTeamBuilder(event, save.getWhichMenu().getFile(), save.getPlayer(), save.getLeague());
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

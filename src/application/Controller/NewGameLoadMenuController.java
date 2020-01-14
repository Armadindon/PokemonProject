package application.Controller;

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
import application.model.fight.Player;
import application.model.pokemon.Pokemon;
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

/**
 *  This class is designed to be used with the view file :
 * "NewGameLoadMenu.fxml"
 * 
 * This class is used to allow the player to select a new game, load a game of quit the game
 * 
 * @author kwaaac
 *
 */
public class NewGameLoadMenuController extends AbstractController {

	private Player player;

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

	/**
	 * Load a game
	 * 
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	void load(ActionEvent event) throws IOException, ClassNotFoundException {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));
		
		File f = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());
		if(f == null) return;
	    
	    FileInputStream file =  new FileInputStream(f);
	    ObjectInputStream ois = new ObjectInputStream(file);
	    
	    SaveUtility save = (SaveUtility) ois.readObject();
	    ois.close();
	    changeSceneTeamBuilder(event, save.getWhichMenu().getFile(), save.getPlayer(), save.getLeague(), save.getSpecialData());
	}

	/**
	 * The handler start a new game by building the player's team
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void newGame(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "BuildTeam.fxml", player, Optional.empty(), Optional.empty());
	}

	/**
	 * The handler quit the game without saving
	 * 
	 * @param event
	 */
	@FXML
	void quit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void displayUpdate() {
		// No new display
	}

	/**
	 * Initialize the class, by getting the TeamBuilder instance
	 * 
	 * @throws IOException
	 */
    @FXML
    void initialize() throws IOException {
		player = new Player();
	}
}

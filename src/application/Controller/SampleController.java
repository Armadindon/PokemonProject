package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import application.model.pokemon.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * This class is designed to be used with the view file : "BuildTeam.fxml"
 * 
 * This allow the player to build a team while being on the pokedex futhermore,
 * this class can be used to handle the pokedex
 * 
 * @author kwaaac
 *
 */
public class SampleController extends AbstractController {

	private Pokemon selectedPokemon;

	private ArrayList<Pokemon> team;

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		super.initTeamBuilder(teamBuilder, league, data);

		selectedPokemon = super.teamBuilder.getPokemon();

		team = super.teamBuilder.getTeam();

		items = FXCollections.observableArrayList(super.teamBuilder.getPokeList());

		listPokemon.setItems(items);
		listPokemon.getSelectionModel().select(selectedPokemon.getId() - 1);

		displayUpdate();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox VBoxTeam1;

	@FXML
	private VBox VBoxTeam2;

	@FXML
	private VBox VBoxTeam3;

	@FXML
	private VBox VBoxTeam4;

	@FXML
	private VBox VBoxTeam5;

	@FXML
	private VBox VBoxTeam6;

	@FXML
	private Button btnAddPokemon;

	@FXML
	private Button btnReturn;

	@FXML
	private Button btnConfirmTeam;

	@FXML
	private Button btnDelTeam1;

	@FXML
	private Button btnDelTeam2;

	@FXML
	private Button btnDelTeam3;

	@FXML
	private Button btnDelTeam4;

	@FXML
	private Button btnDelTeam5;

	@FXML
	private Button btnDelTeam6;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private Label labelAttack;

	@FXML
	private Label labelAttackSpe;

	@FXML
	private Label labelDef;

	@FXML
	private Label labelDefSpe;

	@FXML
	private Label labelHP;

	@FXML
	private Label labelHeight;

	@FXML
	private Label labelPokemonName;

	@FXML
	private Label labelSpeed;

	@FXML
	private Label labelTeamName1;

	@FXML
	private Label labelTeamName2;

	@FXML
	private Label labelTeamName3;

	@FXML
	private Label labelTeamName4;

	@FXML
	private Label labelTeamName5;

	@FXML
	private Label labelTeamName6;

	@FXML
	private Label labelType1;

	@FXML
	private Label labelType2;

	@FXML
	private Label labelWeight;

	@FXML
	private ListView<Pokemon> listPokemon = new ListView<>();
	ObservableList<Pokemon> items = null;

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea textADescription;

	@FXML
	private TextField textFSearch;

	/**
	 * remove a pokemon from the starting list
	 * 
	 * @param event
	 */
	@FXML
	void deletePokemon(ActionEvent event) {
		Button button = (Button) event.getSource();
		teamBuilder.removePokemon(Integer.parseInt(button.getId().replace("btnDelTeam", "")) - 1);
		displayUpdate();
	}

	/**
	 * Update the pokemon when the user clicks on the ListView
	 * 
	 * @param event
	 * @throws CloneNotSupportedException
	 */
	@FXML
	void showPoke(MouseEvent event) throws CloneNotSupportedException {
		Pokemon pkmn;
		if ((pkmn = listPokemon.getSelectionModel().getSelectedItem()) != null) {
			selectedPokemon = listPokemon.getSelectionModel().getSelectedItem();
			displayUpdate();
		}
	}

	/**
	 * Go to the pokeMove class where the player can choose moves for the selected
	 * pokemon
	 * 
	 * @param event
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	@FXML
	void changeToPokeMove(ActionEvent event) throws IOException, CloneNotSupportedException {
		if (team.size() == 6) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Your team is full !");

			alert.show();

			return;
		}

		if (null == listPokemon.getSelectionModel().getSelectedItem()) {
			return;
		}

		super.teamBuilder.setPokemon((Pokemon) listPokemon.getSelectionModel().getSelectedItem().clone());

		if (data == null) {
			data = Optional.empty();
		}

		super.changeSceneTeamBuilder(event, "pokeMove.fxml", teamBuilder, Optional.empty(), data);
	}

	/**
	 * Change the scene to the main menu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void returnToMenu(ActionEvent event) throws IOException {
		super.changeSceneWithoutData(event, "NewGameLoadMenu.fxml");
	}

	/**
	 * Use the search bar to find a pokemon inside the listView
	 * 
	 * @param event
	 */
	@FXML
	void searchPokemon(ActionEvent event) {
		if (((TextField) event.getSource()).getText().contentEquals("")) {
			items = FXCollections.observableArrayList(teamBuilder.getPokeList());
			listPokemon.setItems(items);
			return;
		}
		items = FXCollections.observableArrayList((List<Pokemon>) teamBuilder.getPokeList().stream()
				.filter(p -> p.toString().contains(((TextField) event.getSource()).getText()))
				.collect(Collectors.toList()));
		listPokemon.setItems(items);

	}

	/**
	 * When the team is ready to user can continue to the next step
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void goToMenu(ActionEvent event) throws IOException {
		changeSceneTeamBuilder(event, "ChooseGame.fxml", teamBuilder, league, data);
	}

	
	@Override
	public void displayUpdate() {

		ArrayList<VBox> teamDisplay = new ArrayList<>(
				Arrays.asList(VBoxTeam1, VBoxTeam2, VBoxTeam3, VBoxTeam4, VBoxTeam5, VBoxTeam6));

		labelPokemonName.setText(selectedPokemon.getName());

		labelType1.setText(selectedPokemon.getType1().name());
		if (selectedPokemon.getType2() == null) {
			labelType2.setText("");
		} else {
			labelType2.setText(selectedPokemon.getType2().name());
		}

		labelHeight.setText(Integer.toString(selectedPokemon.getHeight()) + " m");
		labelWeight.setText(Integer.toString(selectedPokemon.getWeight()) + " kg");

		Stats pokeStats = selectedPokemon.getBaseStats();

		labelHP.setText(Integer.toString(pokeStats.getHp()));
		labelAttack.setText(Integer.toString(pokeStats.getAttack()));
		labelAttackSpe.setText(Integer.toString(pokeStats.getSpecialAttack()));
		labelDef.setText(Integer.toString(pokeStats.getDefense()));
		labelDefSpe.setText(Integer.toString(pokeStats.getSpecialDefense()));
		labelSpeed.setText(Integer.toString(pokeStats.getSpeed()));

		imgPokemon.setImage(new Image("file:" + selectedPokemon.getFrontSprite()));

		if (0 != team.size()) {
			btnConfirmTeam.setDisable(false);

			int i;
			for (i = 0; i < team.size(); i++) {
				((Label) teamDisplay.get(i).getChildren().get(0)).setText(team.get(i).getName());
				teamDisplay.get(i).setVisible(true);

			}

			for (i = team.size(); i < 6; i++) {
				teamDisplay.get(i).setVisible(false);
			}
		} else {
			btnConfirmTeam.setDisable(true);
			teamDisplay.get(0).setVisible(false);
		}

		textADescription.setText(selectedPokemon.getDescription());
	}

}

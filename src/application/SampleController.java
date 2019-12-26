package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.appmodel.Pokedex;
import application.model.pokemon.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SampleController {

	private Pokedex appModel = new Pokedex();
	private Label lbPkmName;

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

	Pokemon p1 = new Pokemon(1, "Bulbizar", 0, 200, 300, null, null, null, null, null, new Stats(2, 3, 3, 4, 5, 6),
			null, null, null, null);
	Pokemon p2 = new Pokemon(2, "Salamèche", 0, 800, 150, null, null, null, null, null, new Stats(6, 8, 4, 5, 6, 8),
			null, null, null, null);

	@FXML
	private ListView<Pokemon> listPokemon = new ListView<>();
	ObservableList<Pokemon> items = FXCollections.observableArrayList(p1, p2);

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea textADescription;

	@FXML
	private TextField textFSearch;

	@FXML
	void showPoke(MouseEvent event) {
		appModel.showPokemon(listPokemon.getSelectionModel().getSelectedItem(), labelPokemonName, labelType1,
				labelType2, labelHeight, labelWeight, labelHP, labelAttack, labelAttackSpe, labelDef, labelDefSpe,
				labelSpeed, imgPokemon, textADescription);
	}

	@FXML
	void initialize() {

		listPokemon.setItems(items);
		listPokemon.getSelectionModel().select(0);

		appModel.showPokemon(listPokemon.getSelectionModel().getSelectedItem(), labelPokemonName, labelType1,
				labelType2, labelHeight, labelWeight, labelHP, labelAttack, labelAttackSpe, labelDef, labelDefSpe,
				labelSpeed, imgPokemon, textADescription);
	}

}

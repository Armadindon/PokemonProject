package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.model.appmodel.Pokedex;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import application.model.utils.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PokeMoveController {

	private Pokedex pokedex;

	private Pokemon selectedPokemon;

	private ArrayList<Pokemon> team;

	public void initPokemonMove(Pokedex pokedex) throws IOException {
		this.pokedex = pokedex;
		selectedPokemon = pokedex.getPokemon();

		// changement des labels et infos de la page
		imgPokemon.setImage(selectedPokemon.getFrontSprite());

		ArrayList<Move> allPossibleMoves = selectedPokemon.getAllPossiblesMoves();
		System.out.println(allPossibleMoves);

		items = FXCollections.observableArrayList(allPossibleMoves);

		listMove.setItems(items);
		listMove.getSelectionModel().select(0);
		
		displayUpdate();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnCancel;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private Label labelAccuracy;

	@FXML
	private Label labelEffect;

	@FXML
	private Label labelError;

	@FXML
	private Label labelChangeName;
	
	@FXML
	private Label labelMoveName;

	@FXML
	private Label labelMoveName1;

	@FXML
	private Label labelMoveName2;

	@FXML
	private Label labelMoveName3;

	@FXML
	private Label labelMoveName4;

	@FXML
	private Label labelPP;

	@FXML
	private Label labelPP1;

	@FXML
	private Label labelPP2;

	@FXML
	private Label labelPP3;

	@FXML
	private Label labelPP4;

	@FXML
	private TextField textFPokemonName;

	@FXML
	private Label labelType;

	@FXML
	private VBox vboxMove1;

	@FXML
	private VBox vboxMove2;

	@FXML
	private VBox vboxMove3;

	@FXML
	private VBox vboxMove4;

	@FXML
	private ListView<Move> listMove = new ListView<>();
	ObservableList<Move> items = null;

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea textADescriptionMove;

	@FXML
	private TextField textFSearch;
	
	@FXML
    void changeName(ActionEvent event) {
		
		
    }

	@FXML
	void changeToPokedexCancel(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("interface.fxml"));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		SampleController controller = loader.getController();

		controller.initSampleController(pokedex);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	@FXML
	void changeToPokedexAddPokemon(ActionEvent event) throws IOException {
		pokedex.addPokemonToTeam(selectedPokemon);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("interface.fxml"));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		SampleController controller = loader.getController();

		controller.initSampleController(pokedex);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	@FXML
	void showMoves(MouseEvent event) {
		displayUpdate();
	}

	private void displayUpdate() {
		pokedex.modelMovesUpdate(listMove.getSelectionModel().getSelectedItem(), labelMoveName, labelType,
				labelAccuracy, labelPP, labelEffect, textADescriptionMove,
				new ArrayList<VBox>(Arrays.asList(vboxMove1, vboxMove2, vboxMove3, vboxMove4)), btnConfirm);
	}
	
	@FXML
	public void initialise() {
		textFPokemonName.setFont(Font.font("System", FontWeight.NORMAL, 42));
	}

}

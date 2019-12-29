package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
		textFPokemonName.setFont(Font.font("System", FontWeight.NORMAL, 24));
		textFPokemonName.setText(selectedPokemon.getName());
		imgPokemon.setImage(selectedPokemon.getFrontSprite());

		ArrayList<Move> allPossibleMoves = selectedPokemon.getAllPossiblesMoves();

		items = FXCollections.observableArrayList(allPossibleMoves);

		listMove.setItems(items);
		listMove.getSelectionModel().select(0);

		for (Pokemon p : pokedex.getTeam()) {
			for (Move m : p.getlearnedMoves()) {
				System.out.println(m);
			}
		}

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
	private Button btnChangeName;

	@FXML
	private Button btnMove0;

	@FXML
	private Button btnMove1;

	@FXML
	private Button btnMove2;

	@FXML
	private Button btnMove3;

	@FXML
	private Button btnAddMove;

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
	private Label labelMoveName0;

	@FXML
	private Label labelMoveName1;

	@FXML
	private Label labelMoveName2;

	@FXML
	private Label labelMoveName3;

	@FXML
	private Label labelPP;

	@FXML
	private Label labelPP0;

	@FXML
	private Label labelPP1;

	@FXML
	private Label labelPP2;

	@FXML
	private Label labelPP3;

	@FXML
	private TextField textFPokemonName = new TextField();

	@FXML
	private Label labelType;

	@FXML
	private VBox vboxMove0;

	@FXML
	private VBox vboxMove1;

	@FXML
	private VBox vboxMove2;

	@FXML
	private VBox vboxMove3;

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
		pokedex.updateName(textFPokemonName.getText(), labelChangeName);
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
		
		if(pokedex.getPokemon().getlearnedMoves().size()==0) {
			labelError.setText("Your Pokémon need at least 1 Move");
			return;
		}
		
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
				new ArrayList<VBox>(Arrays.asList(vboxMove0, vboxMove1, vboxMove2, vboxMove3)),
				new ArrayList<Button>(Arrays.asList(btnMove0, btnMove1, btnMove2, btnMove3)), btnConfirm);
	}

	@FXML
	private void addMove(ActionEvent event) {
		pokedex.addMovePokedex(listMove.getSelectionModel().getSelectedItem(), labelError);
		displayUpdate();
	}

	@FXML
	private void removeMove(ActionEvent event) {
		Button button = (Button) event.getSource();
		pokedex.removeMovePokedex(Integer.parseInt(button.getId().replace("btnMove", "")));
		displayUpdate();
	}
	
    @FXML
    void searchMove(ActionEvent event) {
    	System.out.println(((TextField) event.getSource()).getText());
    	
    	if(((TextField) event.getSource()).getText().contentEquals("")) {
    		items = FXCollections.observableArrayList(pokedex.getPokemon().getAllPossiblesMoves());
    		listMove.setItems(items);
    		return;
    	}
    	items = FXCollections.observableArrayList((List<Move>) pokedex.getPokemon().getAllPossiblesMoves().stream().filter(p -> p.toString().contains(((TextField) event.getSource()).getText())).collect(Collectors.toList()));
    	listMove.setItems(items);
    	
    }

}

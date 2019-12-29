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
import application.model.pokemon.*;
import application.model.utils.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SampleController {

	private Pokedex pokedex = new Pokedex();

	public void initSampleController(Pokedex pokedex) {
		this.pokedex = pokedex;
		listPokemon.getSelectionModel().select(pokedex.getPokemon().getId() - 1);
		displayUpdate();
	}

	private void displayUpdate() {
		
		pokedex.modelPokedexUpdate(listPokemon.getSelectionModel().getSelectedItem(), labelPokemonName, labelType1,
				labelType2, labelHeight, labelWeight, labelHP, labelAttack, labelAttackSpe, labelDef, labelDefSpe,
				labelSpeed, imgPokemon, textADescription,
				new ArrayList<VBox>(Arrays.asList(VBoxTeam1, VBoxTeam2, VBoxTeam3, VBoxTeam4, VBoxTeam5, VBoxTeam6)),
				btnConfirmTeam);
	}
	
	private ArrayList<Pokemon> pokeList = new ArrayList<>();

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

	@FXML
	private ListView<Pokemon> listPokemon = new ListView<>();
	ObservableList<Pokemon> items = null;

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea textADescription;

	@FXML
	private TextField textFSearch;

	@FXML
	void deletePokemon(ActionEvent event) {
		Button button = (Button) event.getSource();
		pokedex.removePokemon(Integer.parseInt(button.getId().replace("btnDelTeam", "")) - 1);
		displayUpdate();
	}

	@FXML
	void showPoke(MouseEvent event) {
		displayUpdate();
	}

	@FXML
	void changeToPokeMove(ActionEvent event) throws IOException {
		if (pokedex.getTeamSize() == 6) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Your team is full !");

			alert.show();

			return;
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("pokeMove.fxml"));

		Parent root = loader.load();
		Scene moveScene = new Scene(root);

		// Acces to the controller of pokemove

		PokeMoveController controller = loader.getController();
		
		pokedex.setPokemon(listPokemon.getSelectionModel().getSelectedItem());
		
		controller.initPokemonMove(pokedex);
		
		
		
		
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	@FXML
	void initialize() throws IOException {
		
		

		List<Map<String, List<String>>> dataPokemon = CSVReader.readCSV("scripts/pokemons.csv");
		List<Map<String, List<String>>> dataMoves = CSVReader.readCSV("scripts/moves.csv");

		ArrayList<Move> existingMoves = new ArrayList<>();
		for (Map<String, List<String>> data : dataMoves) {
			Move mv = Move.generateFromMap(data);
			if (mv != null)
				existingMoves.add(mv);
		}

		for (Map<String, List<String>> data : dataPokemon) {
			Pokemon pk = Pokemon.generateFromMap(data, existingMoves);
			if (pk != null)
				pokeList.add(pk);
		}
		

		items = FXCollections.observableArrayList(pokeList);

		listPokemon.setItems(items);
		listPokemon.getSelectionModel().select(0);

		displayUpdate();
	}
	
    @FXML
    void searchPokemon(ActionEvent event) {
    	System.out.println(((TextField) event.getSource()).getText());
    	
    	if(((TextField) event.getSource()).getText().contentEquals("")) {
    		items = FXCollections.observableArrayList(pokeList);
    		listPokemon.setItems(items);
    		return;
    	}
    	items = FXCollections.observableArrayList((List<Pokemon>) pokeList.stream().filter(p -> p.toString().contains(((TextField) event.getSource()).getText())).collect(Collectors.toList()));
    	listPokemon.setItems(items);
    	
    }

}

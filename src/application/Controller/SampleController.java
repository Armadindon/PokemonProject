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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SampleController extends AbstractController {

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data) throws IOException {
		super.initTeamBuilder(teamBuilder, league, data);

		items = FXCollections.observableArrayList(teamBuilder.getPokeList());

		listPokemon.setItems(items);
		listPokemon.getSelectionModel().select(teamBuilder.getPokemon().getId() - 1);

		displayUpdate();
	}

	@Override
	public void displayUpdate() {

		teamBuilder.modelPokedexUpdate(listPokemon.getSelectionModel().getSelectedItem(), labelPokemonName, labelType1,
				labelType2, labelHeight, labelWeight, labelHP, labelAttack, labelAttackSpe, labelDef, labelDefSpe,
				labelSpeed, imgPokemon, textADescription,
				new ArrayList<VBox>(Arrays.asList(VBoxTeam1, VBoxTeam2, VBoxTeam3, VBoxTeam4, VBoxTeam5, VBoxTeam6)),
				btnConfirmTeam);
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

	@FXML
	void deletePokemon(ActionEvent event) {
		Button button = (Button) event.getSource();
		teamBuilder.removePokemon(Integer.parseInt(button.getId().replace("btnDelTeam", "")) - 1);
		displayUpdate();
	}

	@FXML
	void showPoke(MouseEvent event) {
		displayUpdate();
	}

	@FXML
	void changeToPokeMove(ActionEvent event) throws IOException, CloneNotSupportedException {
		if (teamBuilder.getTeamSize() == 6) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Your team is full !");

			alert.show();

			return;
		}
		
		teamBuilder.setPokemon((Pokemon) listPokemon.getSelectionModel().getSelectedItem().clone());

		if(data == null) {
			data = Optional.empty();
		}
		
		super.changeSceneTeamBuilder(event, "pokeMove.fxml", teamBuilder, Optional.empty(), data);
	}
	
	@FXML
	void returnToMenu(ActionEvent event) throws IOException{
		super.changeSceneWithoutData(event, "NewGameLoadMenu.fxml");
	}

	@FXML
	void searchPokemon(ActionEvent event) {
		System.out.println(((TextField) event.getSource()).getText());

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
	
    @FXML
    void goToMenu(ActionEvent event) throws IOException {
    	changeSceneTeamBuilder(event,"ChooseGame.fxml" , teamBuilder, league, data);
    }


}

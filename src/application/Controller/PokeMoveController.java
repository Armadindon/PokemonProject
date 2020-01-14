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
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * This class is designed to be used with the view file : "PokeMove.fxml"
 * 
 * This class allow the player to add moves to a pokemon or the view moves that
 * a pokemon can learn
 * 
 * @author kwaaac
 *
 */
public class PokeMoveController extends AbstractController {

	private Pokemon selectedPokemon;

	private Move selectedMove;

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		super.initTeamBuilder(teamBuilder, league, data);

		selectedPokemon = teamBuilder.getPokemon();

		// Labels and view infos
		textFPokemonName.setFont(Font.font("System", FontWeight.NORMAL, 24));
		textFPokemonName.setText(selectedPokemon.getName());
		imgPokemon.setImage(new Image("file:" + selectedPokemon.getFrontSprite()));

		ArrayList<Move> allPossibleMoves = selectedPokemon.getAllPossiblesMoves();

		items = FXCollections.observableArrayList(allPossibleMoves);

		listMove.setItems(items);
		listMove.getSelectionModel().select(0);
		selectedMove = listMove.getSelectionModel().getSelectedItem();

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

	/**
	 * This method allow to change the name of the pokemon, it trigger a message to
	 * the user
	 * 
	 * @param event
	 */
	@FXML
	void changeName(ActionEvent event) {
		String name = textFPokemonName.getText();

		if (name.length() > 15) {
			labelChangeName.setText("Name too long");

		} else if (name.contains(":")) { // TODO Ajouter du regex pour eviter tout caractère non voulue
			labelChangeName.setText("Unauthorized character");
		} else {
			labelChangeName.setText("Name Changed");
			selectedPokemon.setName(name);
		}

		labelChangeName.setVisible(true);

		FadeTransition ft = new FadeTransition(new Duration(3_000), labelChangeName);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		ft.play();
	}

	/**
	 * Cancel and don't add the pokemon to the team, go back to the pokedex
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void changeToPokedexCancel(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "BuildTeam.fxml", teamBuilder, Optional.empty(), data);
	}

	/**
	 * Go back to the pokedex while adding the pokemon to the team
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void changeToPokedexAddPokemon(ActionEvent event) throws IOException {

		if (teamBuilder.canAddPokemon()) {

			System.out.println(selectedPokemon.getName());
			teamBuilder.addPokemonToTeam(selectedPokemon);

			super.changeSceneTeamBuilder(event, "BuildTeam.fxml", teamBuilder, Optional.empty(), data);

		}

	}

	/**
	 * Update the move each time the the user click on the listView
	 * 
	 * @param event
	 */
	@FXML
	void showMoves(MouseEvent event) {
		Move move;
		if (null != (move = listMove.getSelectionModel().getSelectedItem())) {
			selectedMove = move;
			displayUpdate();
		}
	}
	
	/**
	 * Update the whole interface, depending on the selected moves
	 * 
	 */
	@Override
	public void displayUpdate() {

		if (null == listMove.getSelectionModel().getSelectedItem()) {
			return;
		}

		Move selectedMove = listMove.getSelectionModel().getSelectedItem();

		ArrayList<VBox> vboxList = new ArrayList<>(Arrays.asList(vboxMove0, vboxMove1, vboxMove2, vboxMove3));
		ArrayList<Button> btnList = new ArrayList<>(Arrays.asList(btnMove0, btnMove1, btnMove2, btnMove3));

		ArrayList<Move> learnedMoves = super.teamBuilder.getPokemon().getlearnedMoves();
		labelMoveName.setText(selectedMove.getName());
		labelType.setText(selectedMove.getType().name());
		labelAccuracy.setText(Integer.toString(selectedMove.getAccuracy()));
		labelPP.setText(Integer.toString(selectedMove.getMaxPP()));
		labelEffect.setText(selectedMove.getEffectToString());
		textADescriptionMove.setText(selectedMove.getDescription());

		if (0 != learnedMoves.size()) {
			btnConfirm.setDisable(false);
			System.out.println(learnedMoves);
			int i;
			for (i = 0; i < learnedMoves.size(); i++) {
				((Label) vboxList.get(i).getChildren().get(0)).setText(learnedMoves.get(i).getName());
				((Label) vboxList.get(i).getChildren().get(1))
						.setText(Integer.toString((learnedMoves.get(i).getMaxPP())));
				vboxList.get(i).setVisible(true);
				btnList.get(i).setVisible(true);
			}

			for (i = learnedMoves.size(); i < 4; i++) {
				vboxList.get(i).setVisible(false);
				btnList.get(i).setVisible(false);
			}

		} else {
			btnConfirm.setDisable(true);
			vboxList.get(0).setVisible(false);
			btnList.get(0).setVisible(false);
		}
	}

	/**
	 * Add a chosen move to the available slots 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void addMove(ActionEvent event) throws IOException {
		if (!super.teamBuilder.addMovePokedex(selectedMove)) {
			labelError.setText("This move has failed to be added");

			FadeTransition ft = new FadeTransition(new Duration(3_000), labelError);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			ft.play();
		}

		displayUpdate();
	}

	/**
	 * Remove the move associated with the button
	 * 
	 * @param event
	 */
	@FXML
	private void removeMove(ActionEvent event) {
		Button button = (Button) event.getSource();
		teamBuilder.removeMovePokedex(Integer.parseInt(button.getId().replace("btnMove", "")));
		displayUpdate();
	}

	/**
	 * Use the search bar to find corresponding moves
	 * 
	 * @param event
	 */
	@FXML
	void searchMove(ActionEvent event) {
		System.out.println(((TextField) event.getSource()).getText());

		if (((TextField) event.getSource()).getText().contentEquals("")) {
			items = FXCollections.observableArrayList(teamBuilder.getPokemon().getAllPossiblesMoves());
			listMove.setItems(items);
			return;
		}
		items = FXCollections.observableArrayList((List<Move>) selectedPokemon.getAllPossiblesMoves().stream()
				.filter(p -> p.toString().contains(((TextField) event.getSource()).getText()))
				.collect(Collectors.toList()));
		listMove.setItems(items);

	}

}

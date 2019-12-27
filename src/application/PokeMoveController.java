package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.pokemon.Pokemon;
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
import javafx.stage.Stage;


public class PokeMoveController {
	
	private Pokemon selectedPokemon;
	
	public void initPokemonMove(Pokemon pokemon) {
		selectedPokemon = pokemon;
		
		// changement des labels et infos de la page
		
		labelPokemonName.setText(pokemon.getName());
		imgPokemon.setImage(pokemon.getFrontSprite());
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddPokemon;
    
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
    private Label labelPokemonName;

    @FXML
    private Label labelType;

    @FXML
    private ListView<?> listPokemon;

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea textADescriptionMove;

    @FXML
    private TextField textFSearch;

    
    @FXML
    void changeToPokedexCancel(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
		Scene moveScene = new Scene(root);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(moveScene);
		window.show();
    }

    @FXML
    void showMoves(MouseEvent event) {
    }

}

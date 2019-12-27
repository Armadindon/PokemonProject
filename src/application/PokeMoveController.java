package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class PokeMoveController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddPokemon;

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
    void showMoves(MouseEvent event) {
    }

}

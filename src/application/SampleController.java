package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.pokemon.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class SampleController {

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
    private Label labelDefense;

    @FXML
    private Label labelDefenseSpe;

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
    private ListView<String> listPokemon;

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea textADescription;

    @FXML
    private TextField textFPokemon;


}

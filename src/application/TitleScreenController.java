package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TitleScreenController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView imgArmadindon;// return at x: 730

	@FXML
	private ImageView imgKwaaac; // return at x: 169

	@FXML
	private ImageView imgPokemon;

	@FXML
	private Label labelArmadindon; // return at x: 616

	@FXML
	private Label labelKwaac; // return at x: 67

	@FXML
	private Label labelPressAnyKey;

	@FXML
	private Label labelTitle;

	@FXML
	private AnchorPane root;

	@FXML
	void changeToGame(MouseEvent event) throws IOException {
	
		Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
		Scene moveScene = new Scene(root);

		
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	@FXML
	void initialize() {
		FadeTransition ft = new FadeTransition(new Duration(10_000), root);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);

		ft.play();

	}

}

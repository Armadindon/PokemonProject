package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TitleScreenController {
	
	private MediaPlayer mp;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView imgArmadindon;

	@FXML
	private ImageView imgKwaaac;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private Label labelArmadindon;

	@FXML
	private Label labelKwaaac;

	@FXML
	private Label labelPressAnyKey;

	@FXML
	private Label labelTitle;

	@FXML
	private AnchorPane root;

	@FXML
	private AnchorPane anchorPFade;

	@FXML
	void changeToGame(MouseEvent event) throws IOException {
		goToGame(event);
	}

	@FXML
	void changeToGameK(KeyEvent event) throws IOException {
		goToGame(event);
	}

	private void goToGame(Event event) throws IOException {
		
		Parent scene = FXMLLoader.load(getClass().getResource("interface.fxml"));
		Scene moveScene = new Scene(scene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(moveScene);
		window.show();
	}

	private void pokeFade() {
		FadeTransition ft1 = new FadeTransition(new Duration(1_000), anchorPFade);
		ft1.setFromValue(0.0);
		ft1.setToValue(1.0);
		ft1.setAutoReverse(true);
		ft1.play();

		FadeTransition ft2 = new FadeTransition(new Duration(1_000), labelPressAnyKey);
		ft2.setFromValue(0.0);
		ft2.setToValue(1.0);
		ft2.setCycleCount(10_000);
		ft2.setAutoReverse(true);
		ft2.play();

		TranslateTransition tImgKwaaac = new TranslateTransition(new Duration(2_000), imgKwaaac);
		tImgKwaaac.setByX(169);
		tImgKwaaac.play();

		TranslateTransition tLabelKwaaac = new TranslateTransition(new Duration(2_000), labelKwaaac);
		tLabelKwaaac.setByX(100);
		tLabelKwaaac.play();

		TranslateTransition tImgArmadindon = new TranslateTransition(new Duration(2_000), imgArmadindon);
		tImgArmadindon.setByX(-169);
		tImgArmadindon.play();

		TranslateTransition tLabelArmadindon = new TranslateTransition(new Duration(2_000), labelArmadindon);
		tLabelArmadindon.setByX(-100);
		tLabelArmadindon.play();

	}

	@FXML
	void initialize() {
		
		String path = System.getProperty("user.dir") + "/src/application/Misc/Music/Pokemon_Red_&_Blue_OST/01 - Opening.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mp.setCycleCount(MediaPlayer.INDEFINITE);

		// Duration time while the sreen display void

		FadeTransition ft = new FadeTransition(new Duration(4_000), anchorPFade);
		ft.setFromValue(0.0);
		ft.setToValue(0.0);
		ft.setOnFinished(e -> pokeFade());

		ft.play();
	}

}

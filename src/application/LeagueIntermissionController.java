package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LeagueIntermissionController extends AbstractController {

	private TeamBuilder teamBuilder;

	private Optional<League> league = Optional.empty();

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league) throws IOException {
		if (league.isPresent()) {
			this.league = league;
		}else league = Optional.empty();

		this.teamBuilder = teamBuilder;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	private Label labelSave;

	@FXML
	private Button btnHomeMade;

	@FXML
	private Button btnLigueDUT2;

	@FXML
	private Button btnLigueGame;

	@FXML
	private Button btnQuit;

	@FXML
	private Button btnRandomFight;

	@FXML
	private ImageView imgPokemon;

	@FXML
	private AnchorPane root;

	@FXML
	void randomFight(ActionEvent event) throws IOException {
		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, Optional.empty());
	}

	@FXML
	void LigueDUT2Game(ActionEvent event) throws IOException {
		// créer une ligue random si pas init

		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, league);

	}

	@FXML
	void homeMadeGame(ActionEvent event) throws IOException {

		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, Optional.empty());
	}

	@FXML
	void ligueGame(ActionEvent event) throws IOException {
		// créer une ligue random si pas init

		super.changeSceneTeamBuilder(event, "Fight.fxml", teamBuilder, league);

	}
	
	@FXML
	void save(ActionEvent event) {
		

		
		labelSave.setText("Team saved !");
		displayUpdate();
	}

	@FXML
	void quit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void displayUpdate() {
		FadeTransition ft = new FadeTransition(new Duration(3_000), labelSave);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);

		ft.play();
	}

}

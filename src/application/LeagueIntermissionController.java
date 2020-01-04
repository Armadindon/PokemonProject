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
import javafx.scene.text.Font;
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
    private AnchorPane root;

    @FXML
    private Button btnKeepGoing;

    @FXML
    private Font x1;

    @FXML
    private Button btnQuit;

    @FXML
    void nextLeagueFight(ActionEvent event) {

    }

    @FXML
    void quit(ActionEvent event) {

    }

    @FXML
    void randomFight(ActionEvent event) {

    }

	@Override
	public void displayUpdate() {
		// TODO Auto-generated method stub
		
	}

}

package application.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import application.model.appmodel.League;
import application.model.appmodel.SpecialData;
import application.model.appmodel.TeamBuilder;
import application.model.fight.Action;
import application.model.fight.Player;
import application.model.moves.AttackResult;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import application.model.utils.MenuSelect;
import application.model.utils.SaveUtility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FightController extends AbstractController {
	
	private MediaPlayer mp;

	private League currentLeague;

	private SpecialData currentData;

	private Player playerUser;

	private Player playerFoe;

	// Homemade mod properties

	private int swapMaxTurn = 1;

	private int swapTimer = 0;

	private int swapNbPkmn = 3;

	@Override
	public void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		super.initTeamBuilder(teamBuilder, league, data);

		tabPaneMenu.getSelectionModel().select(1);

		if (league.isPresent()) {
			this.playerFoe = league.get().getFightingTeam();
			this.currentLeague = league.get();
		} else {
			playerFoe = new Player(teamBuilder.createRandomTeam(6), true);
			this.currentLeague = null;
		}

		if (super.data.isPresent()) {
			this.currentData = data.get();
		} else {
			this.currentData = null;
		}

		playerUser = new Player(teamBuilder, false);
		
		// Music
		String path = System.getProperty("user.dir") + "/Misc/Music/Pokemon_Red_&_Blue_OST/15 - Battle.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mp.setCycleCount(MediaPlayer.INDEFINITE);

		displayUpdate();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane root;

	@FXML
	private Color x4;

	@FXML
	private TabPane tabPaneMenu;

	@FXML
	private Label labelMatchNotification;

	@FXML
	private HBox hBoxMenuButtons;

	@FXML
	private Font x1;

	@FXML
	private Button btnMenu;

	@FXML
	private Color x2;

	@FXML
	private AnchorPane movePane;

	@FXML
	private Font x31;

	@FXML
	private AnchorPane switchPane;

	@FXML
	private Button cancelButtonSwitch;

	@FXML
	private ImageView imageViewAllyPokemon;

	@FXML
	private ImageView ImageViewFoePokemon;

	@FXML
	private Label labelFoePokeName;

	@FXML
	private Insets x5;

	@FXML
	private Label labelFoePokeLvl;

	@FXML
	private ProgressBar progressBarFoePokeHP;

	@FXML
	private Label labelFoePokeStatus;

	@FXML
	private Label labelFoePokeHP;

	@FXML
	private Font x11;

	@FXML
	private Label labelAllyPokeName;

	@FXML
	private Label labelAllyPokeLvl;

	@FXML
	private ProgressBar progressBarAllyPokeHP;

	@FXML
	private Label labelAllyPokeStatus;

	@FXML
	private Label labelAllyPokeHP;

	@FXML
	private Font x111;

	@FXML
	private AnchorPane anchorPaneMenu;

	private List<String> msgs = null;

	// HP Display

	private static final String RED_BAR = "red-bar";
	private static final String YELLOW_BAR = "yellow-bar";
	private static final String ORANGE_BAR = "orange-bar";
	private static final String GREEN_BAR = "green-bar";
	private static final String[] pgColorStyles = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };

	@FXML
	void run(ActionEvent event) throws IOException {
		super.changeSceneWithoutData(event, "NewGameLoadMenu.fxml");
	}

	@FXML
	void mainMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(1);
	}

	@FXML
	void mainMenuClick(MouseEvent event) {
		if (msgs.size() >= 1) {
			labelMatchNotification.setText(msgs.get(0));
			msgs = msgs.subList(1, msgs.size());
		} else {
			try {
				if (playerUser.getAlive() <= 0) { // lose
					changeSceneTeamBuilder(event, "ChooseGame.fxml", teamBuilder, Optional.empty(), Optional.empty());
				} else if (playerFoe.getAlive() == 0) { // win
					changeSceneTeamBuilder(event, "LeagueIntermission.fxml", teamBuilder, Optional.of(currentLeague),
							data);
				} else {
					msgs = null;
					tabPaneMenu.getSelectionModel().select(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void backPackPage(ActionEvent event) {
	}

	@FXML
	void fightMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(2);
		moveDisplayUpdate(playerUser);
	}

	@FXML
	void switchMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(3);

		teamDisplayUpdate(playerUser);
	}

	/*
	 * Pour les deux méthodes ci dessous, il faudrait changer l'affichage pour
	 * afficher un message en fonction de player.turn()
	 */
	@FXML
	void switchPokemon(MouseEvent event) {
		boolean wasAlive = playerUser.getSelectedPokemon().isAlive();
		int numPokemon = Integer.parseInt((((HBox) event.getSource()).getId()).replace("hboxSwitchPokemon", ""));
		playerUser.setNextAction(Action.SWITCH, numPokemon, playerFoe);
		if (playerFoe.isBot())
			playerFoe.generateNextAction(playerUser);
		tabPaneMenu.getSelectionModel().select(1);
		if (wasAlive)
			doTurns();
		else

			switchPokemons();
	}

	/*
	 * Permet de faire son tour seulement si on switch (utile dans le cas ou on
	 * switch car le pokémon est mort)
	 */
	private void switchPokemons() {
		if (playerUser.getNextAction() == Action.SWITCH) {
			playerUser.turn(playerFoe);
		}
		if (playerFoe.getNextAction() == Action.SWITCH) {
			playerUser.turn(playerUser);
		}
		displayUpdate();
	}

	@FXML
	void useMove(MouseEvent event) {
		int numMove = Integer.parseInt((((VBox) event.getSource()).getId()).replace("vBoxMove", ""));
		playerUser.setNextAction(Action.MOVE, numMove, playerFoe);
		if (playerFoe.isBot())
			playerFoe.generateNextAction(playerUser);
		tabPaneMenu.getSelectionModel().select(1);
		doTurns();
	}

	@FXML
	void openMenu(ActionEvent event) {
		anchorPaneMenu.setVisible(true);
	}

	@FXML
	void pokedex(ActionEvent event) {

	}

	@FXML
	void save(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));

		File f = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());

		SaveUtility save;

		Optional<SpecialData> saveData;

		if (currentData != null) {
			saveData = Optional.of(currentData);
		} else {
			saveData = Optional.empty();
		}
		if (currentLeague != null)
			save = new SaveUtility(MenuSelect.FIGHT, teamBuilder, Optional.of(currentLeague), saveData);
		else
			save = new SaveUtility(MenuSelect.FIGHT, teamBuilder, Optional.empty(), saveData);

		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(save);
		oos.flush();
		oos.close();

	}

	@FXML
	void load(ActionEvent event) throws IOException, ClassNotFoundException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pokemon Save Files (*.pkmn)", "*.pkmn"));
		fileChooser.setInitialDirectory(new File("Saves"));

		File f = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());
		if (f == null)
			return;

		FileInputStream file = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(file);

		SaveUtility save = (SaveUtility) ois.readObject();
		ois.close();
		changeSceneTeamBuilder(event, save.getWhichMenu().getFile(), save.getPlayer(), save.getLeague(), data);
	}

	@FXML
	void backToMenu(ActionEvent event) throws IOException {
		super.changeSceneWithoutData(event, "TitleScreen.fxml");
	}

	@FXML
	void back(ActionEvent event) {
		anchorPaneMenu.setVisible(false);
	}

	// Il faudrait gérer ca dans cette méthode ici (on évite de déléguer au modèle
	// l'affichage)
	@Override
	public void displayUpdate() {
		// generate the moves of the player in the interface
		moveDisplayUpdate(playerUser);
		teamDisplayUpdate(playerUser);
		mainScreenUpdate(playerUser, imageViewAllyPokemon, labelAllyPokeName, labelAllyPokeLvl, labelAllyPokeHP,
				labelAllyPokeStatus, progressBarAllyPokeHP);
		mainScreenUpdate(playerFoe, ImageViewFoePokemon, labelFoePokeName, labelFoePokeLvl, labelFoePokeHP,
				labelFoePokeStatus, progressBarFoePokeHP);
	}

	public void moveDisplayUpdate(Player p) {
		ArrayList<Move> pokeMoves = p.getSelectedPokemon().getlearnedMoves();
		for (int i = 0; i < pokeMoves.size(); i++) {
			// From the Anchor pane of moves, get the i vbox containing 2 labels. 0: move's
			// Name, 1: move's PP
			Move pokeMove = pokeMoves.get(i);

			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(0))).setText(pokeMove.getName());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(1))).setText(pokeMove.getType().name());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(2)))
					.setText(pokeMove.getPP() + "/" + pokeMove.getMaxPP());
			if (pokeMove.getPP() <= 0)
				movePane.getChildren().get(i).setDisable(true);
		}
	}

	public void teamDisplayUpdate(Player p) {

		ArrayList<Pokemon> team = p.getTeam();

		int pokeIndex = team.indexOf(p.getSelectedPokemon());

		for (int i = 0; i < team.size(); i++) {
			Pokemon pokemon = team.get(i);

			HBox pokemonDisplay = ((HBox) switchPane.getChildren().get(i));
			((ImageView) pokemonDisplay.getChildren().get(0)).setImage(new Image("file:" + pokemon.getFrontSprite()));
			pokemonDisplay.setDisable(false);

			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(0))).setText(pokemon.getName());
			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(1)))
					.setText(pokemon.getCurrentStats().getHp() + "/" + pokemon.getBaseStats().getHp());

			if (i == pokeIndex || !team.get(i).isAlive()) {
				pokemonDisplay.setDisable(true);
			}
		}
	}

	private void updateProgressBarColor(ProgressBar pgHP, String colorStyle) {
		pgHP.getStyleClass().removeAll(pgColorStyles);
		pgHP.getStyleClass().add(colorStyle);
	}

	public void mainScreenUpdate(Player p, ImageView imgV, Label pokeName, Label pokeLvl, Label pokeHP,
			Label PokeStatus, ProgressBar pgHP) {
		Pokemon selectedPokemon = p.getSelectedPokemon();

		if (p.isBot()) {

			imgV.setImage(new Image("file:" + selectedPokemon.getFrontSprite()));
		} else {
			imgV.setImage(new Image("file:" + selectedPokemon.getBackSprite()));
		}

		int maxHP = selectedPokemon.getBaseStats().getHp();
		int currentHP = selectedPokemon.getCurrentStats().getHp();

		pokeName.setText(selectedPokemon.getName());
		pokeLvl.setText("lvl" + selectedPokemon.getLevel());
		pokeHP.setText(currentHP + " / " + maxHP);

		if (selectedPokemon.getStatus() != null)
			PokeStatus.setText(selectedPokemon.getStatus().name());
		else
			PokeStatus.setText("None");

		Double progress = (double) currentHP / maxHP;

		pgHP.setProgress(progress);
		if (progress < 0.2) {
			updateProgressBarColor(pgHP, RED_BAR);
		} else if (progress < 0.4) {
			updateProgressBarColor(pgHP, ORANGE_BAR);
		} else if (progress < 0.6) {
			updateProgressBarColor(pgHP, YELLOW_BAR);
		} else {
			updateProgressBarColor(pgHP, GREEN_BAR);
		}
	}

	private String[] turns(Player... players) {

		String[] messages;
		
		if (currentData != null && currentData == SpecialData.HOMEMADE) {
			messages = new String[players.length + 1];
		} else {
			messages = new String[players.length];
		}

		AttackResult att;

		for (int i = 0; i < players.length; i++) {
			switch (players[i].getNextAction()) {
			case MOVE:
				messages[i] = players[i].getSelectedPokemon().getName() + " utilise "
						+ players[i].getSelectedPokemon().getlearnedMoves().get(players[i].getWhichAction()).getName()
						+ "\n";
				break;

			case SWITCH:
				messages[i] = "Reviens " + players[i].getSelectedPokemon().getName() + ", vasy "
						+ players[i].getTeam().get(players[i].getWhichAction()).getName() + "\n";
				break;

			default:
				break;
			}
			att = players[i].turn(players[i].getWhichPlayer());

			if (att != null) {
				switch (att) {
				case SUCCEED:
					messages[i] += "Le coup a touché !\n";
					break;

				case NOTEFFECTIVE:
					messages[i] += "Le coup n'était pas très efficace ...\n";
					break;

				case EFFECTIVE:
					messages[i] += "C'est super efficace !\n";
					break;

				case MISSED:
					messages[i] += "L'attaque a ratée !\n";
					break;

				case BOOSTED:
					messages[i] += "Il s'est Boost !\n";
					break;

				case ASLEEP:
					messages[i] += "Il est endormi, il n'a pas pu attaquer \n";
					break;

				case FROZEN:
					messages[i] += "Il est Glacé, il n'a pas pu attaquer \n";
					break;

				case PARALYZED:
					messages[i] += "Il est paralysé, il n'a pas pu attaquer \n";
					break;
				}
			}

			if (!players[i].getWhichPlayer().getSelectedPokemon().isAlive()) {
				messages[i] += "Le pokémon adverse est KO !\n";
				players[i].getWhichPlayer().mainPokemonKilled();
				if (players[i].getWhichPlayer().getAlive() < 0) {
					if (players[i].getWhichPlayer().isBot()) {
						messages[i] += "Vous avez Gagné - Combat suivant !\n";
					} else {
						messages[i] += "Vous avez Perdu - A l'acceuil !\n";
					}
				} else if (!players[i].getWhichPlayer().isBot() && players[i].getWhichPlayer().getAlive() != 0) {
					tabPaneMenu.getSelectionModel().select(3);
					cancelButtonSwitch.setDisable(true);
					return null;
				}else if(!players[i].getWhichPlayer().isBot() && players[i].getWhichPlayer().getAlive() <= 0) {
				}
			}

			if (!players[i].getSelectedPokemon().isAlive()) {
				messages[i] += "Le pokémon est KO a cause de son status !\n";
				players[i].mainPokemonKilled();
				if (players[i].getAlive() < 0) {
					if (players[i].isBot()) {
						messages[i] += "Vous avez Gagné - Combat suivant !\n";
					} else {
						messages[i] += "Vous avez Perdu - A l'acceuil !\n";
					}
				} else if (!players[i].isBot() && players[i].getAlive() <= 0) {
					tabPaneMenu.getSelectionModel().select(3);
					cancelButtonSwitch.setDisable(true);
					return null;
				}
			}

		}

		displayUpdate();

		return messages;
	}

	/*
	 * Permet d'aiguiller la priorité
	 */
	public void doTurns() {
		String[] messages = new String[3];

		if (playerFoe.getNextAction().getPriority() > playerUser.getNextAction().getPriority()) {
			// Il faut afficher le message en fonction du retour
			messages = turns(playerFoe, playerUser);
		} else if (playerFoe.getNextAction().getPriority() < playerUser.getNextAction().getPriority()) {
			// Il faut afficher le message en fonction du retour
			messages = turns(playerUser, playerFoe);
		} else {
			// si les deux on la même priorité
			if (playerUser.getNextAction() == Action.MOVE && playerFoe.getNextAction() == Action.MOVE) {
				// C'est la vitesse des pokémons qui choisit la priorité
				if (playerUser.getSelectedPokemon().getCurrentStats().getSpeed() >= playerFoe.getSelectedPokemon()
						.getCurrentStats().getSpeed()) {
					// en cas d'égalité on donne l'avantage au joueur
					messages = turns(playerUser, playerFoe);
				} else {
					messages = turns(playerFoe, playerUser);
				}
			} else {
				// l'ordre n'a pas d'importance
				messages = turns(playerUser, playerFoe);
			}
		}

		if (currentData != null && currentData == SpecialData.HOMEMADE && swapTimer < swapMaxTurn) {
			homadeSwitch(playerUser, playerFoe);
			messages[2] = "Une force Cessyenne est survenu...";
			swapTimer = 0;
		} else {
			swapTimer++;
		}
		

		displayUpdate();

		if (messages != null) {
			msgs = Arrays.asList(messages);
			tabPaneMenu.getSelectionModel().select(0);
			mainMenuClick(null);
		}

	}

	private int getPokemonAliveIndex(ArrayList<Pokemon> team) {
		int res;
		do {
			res = new Random().nextInt(team.size());
		} while (!team.get(res).isAlive());

		return res;
	}

	private void homadeSwitch(Player... players) {
		Collections.shuffle(Arrays.asList(players));

		int intOne;
		int intTwo;

		for (int i = 0; i + 1 < players.length; i = i + 2) {
			// If one of the two players are KO, they don't swap
			if (players[i].getAlive() == 0 || players[i + 1].getAlive() == 0) {
				continue;
			}

			intOne = getPokemonAliveIndex(players[i].getTeam());
			intTwo = getPokemonAliveIndex(players[i + 1].getTeam());

			players[i].swapTeam(players[i + 1], intOne, intTwo);
		}
	}
}

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

import application.Controller.Utils.SpecialData;
import application.model.appmodel.League;
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

/**
 * This controller manage the fights interface and the fight process
 * 
 * @author Armadindon, Kwaaac
 *
 */
public class FightController extends AbstractController {

	private MediaPlayer mp;

	private League currentLeague;

	private SpecialData currentData;

	private Player playerUser;

	private Player playerFoe;

	// Menus constants

	private final static int TEXTMENU = 0;

	private final static int MAINMENU = 1;

	private final static int FIGHTMENU = 2;

	private final static int SWITCHMENU = 3;

	private final static int BACKPACKMENU = 4;

	// Homemade mod properties

	private int swapMaxTurn = 1;

	private int swapTimer = 0;

	private int swapNbPkmn = 3;

	@Override
	public void initTeamBuilder(Player player, Optional<League> league, Optional<SpecialData> data)
			throws IOException {
		super.initTeamBuilder(player, league, data);

		openMainMenu();

		if (league.isPresent()) {
			this.playerFoe = league.get().getFightingTeam();
			this.currentLeague = league.get();
		} else {
			playerFoe = new Player(TeamBuilder.getInstance().createRandomTeam(6), true);
			this.currentLeague = null;
		}

		if (super.data.isPresent()) {
			this.currentData = data.get();
		} else {
			this.currentData = null;
		}

		playerUser = super.player;

		// Music
		String path = System.getProperty("user.dir") + "/Misc/Music/Pokemon_Red_&_Blue_OST/15 - Battle.mp3";
		Media media = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		mp.setCycleCount(MediaPlayer.INDEFINITE);

		mp.stop(); // Retirer pour eviter de vous rendre fou lors de la correction

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

	/**
	 * This method make the player run the fight
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void run(ActionEvent event) throws IOException {
		mp.stop();
		super.changeSceneWithoutData(event, "NewGameLoadMenu.fxml");
	}

	/**
	 * Use this method when opening a menu within the code and not an event Open the
	 * text menu
	 */
	private void openTextMenu() {
		tabPaneMenu.getSelectionModel().select(TEXTMENU);
	}

	/**
	 * Use this method when opening a menu within the code and not an event Open the
	 * main menu
	 */
	private void openMainMenu() {
		tabPaneMenu.getSelectionModel().select(MAINMENU);
	}

	/**
	 * Use this method when opening a menu within the code and not an event Open the
	 * fight menu
	 */
	private void openFightMenu() {
		tabPaneMenu.getSelectionModel().select(FIGHTMENU);
		moveDisplayUpdate(playerUser);
	}

	/**
	 * Use this method when opening a menu within the code and not an event Open the
	 * switch menu
	 */
	private void openSwitchMenu() {
		tabPaneMenu.getSelectionModel().select(SWITCHMENU);
		teamDisplayUpdate(playerUser);
	}

	/**
	 * Use this method when opening a menu within the code and not an event Open the
	 * backpack menu
	 */
	private void openBackPackMenu() {
		tabPaneMenu.getSelectionModel().select(BACKPACKMENU);
	}

	/**
	 * Event button, opening the main Menu
	 * 
	 * @param event
	 */
	@FXML
	void mainMenu(ActionEvent event) {
		openMainMenu();
	}

	/**
	 * Event button, opening the backPack menu
	 * 
	 * @param event
	 */
	@FXML
	void backPackPage(ActionEvent event) {
		openBackPackMenu();
	}

	/**
	 * Event button, opening the fighMenu
	 * 
	 * @param event
	 */
	@FXML
	void fightMenu(ActionEvent event) {
		openFightMenu();
	}

	/**
	 * Event button, opening the switch menu
	 * 
	 * @param event
	 */
	@FXML
	void switchMenu(ActionEvent event) {
		openSwitchMenu();
	}

	/**
	 * Handle the text events after the turn. If there is no text anymore, it check
	 * the win/loose condition
	 * 
	 * @param event
	 */
	@FXML
	void mainMenuClick(MouseEvent event) {
		if (msgs.size() >= 1) {
			labelMatchNotification.setText(msgs.get(0));
			msgs = msgs.subList(1, msgs.size());
		} else {
			System.out.println("Vivants j1 " + playerUser.getAlive() + "Vivants j2 " + playerFoe.getAlive());
			try {
				if (playerUser.getAlive() <= 0) { // lose
					mp.stop();
					System.out.println("Le joueur a perdu, on le redirige");
					changeSceneTeamBuilder(event, "LeagueIntermission.fxml", player, Optional.empty(),
							Optional.of(SpecialData.LOOSE));
				} else if (playerFoe.getAlive() <= 0) { // win
					mp.stop();
					System.out.println("Le joueur a gagné, on le redirige");

					currentLeague.nextFightingTeam();

					if (currentLeague.isOver()) {
						data = Optional.of(SpecialData.WIN);
					} else {
						data = Optional.of(SpecialData.INTERMISSION);
					}

					changeSceneTeamBuilder(event, "LeagueIntermission.fxml", player, Optional.of(currentLeague),
							data);
				} else {
					msgs = null;
					openMainMenu();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This methods allow to switch a pokemon during a fight. If the player has to
	 * change a pokemon because of it's death it won't count as a turn and the
	 * player will still play
	 * 
	 * @param event
	 */
	@FXML
	void switchPokemon(MouseEvent event) {
		boolean wasAlive = playerUser.getSelectedPokemon().isAlive();
		int numPokemon = Integer.parseInt((((HBox) event.getSource()).getId()).replace("hboxSwitchPokemon", ""));
		playerUser.setNextAction(Action.SWITCH, numPokemon, playerFoe);
		if (playerFoe.isBot())
			playerFoe.generateNextAction(playerUser);
		openMainMenu();
		if (wasAlive)
			doTurns();
		else
			switchPokemons();
	}

	/**
	 * If the switch occurred because of the death of the pokemon then the turn is
	 * not used
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

	/**
	 * This method allow a player to use a move depending on wich case he clicked on
	 * 
	 * @param event
	 */
	@FXML
	void useMove(MouseEvent event) {
		int numMove = Integer.parseInt((((VBox) event.getSource()).getId()).replace("vBoxMove", ""));
		playerUser.setNextAction(Action.MOVE, numMove, playerFoe);
		if (playerFoe.isBot())
			playerFoe.generateNextAction(playerUser);
		openMainMenu();
		doTurns();
	}

	/**
	 * Open the menu, where you can save, load, quit and more if added
	 * 
	 * 
	 * @param event
	 */
	@FXML
	void openMenu(ActionEvent event) {
		anchorPaneMenu.setVisible(true);
	}

	/**
	 * Leave the menu
	 * 
	 * @param event
	 */
	@FXML
	void back(ActionEvent event) {
		anchorPaneMenu.setVisible(false);
	}

	/**
	 * This event handler will be used to check the pokedex during a fight. Not
	 * implemented yet
	 * 
	 * @param event
	 */
	@FXML
	void pokedex(ActionEvent event) {

	}

	/**
	 * This method will save the game. As it is during a figth, the fight will me
	 * saved
	 * 
	 * @param event
	 * @throws IOException
	 */
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
			save = new SaveUtility(MenuSelect.FIGHT, player, Optional.of(currentLeague), saveData);
		else
			save = new SaveUtility(MenuSelect.FIGHT, player, Optional.empty(), saveData);

		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(save);
		oos.flush();
		oos.close();

	}

	/**
	 * This method will load a save
	 * 
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Leave the fight an go back to the titleScreen
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void backToMenu(ActionEvent event) throws IOException {
		super.changeSceneWithoutData(event, "TitleScreen.fxml");
	}

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

	/**
	 * Update the moves interface depending on the player given
	 * 
	 * @param p The player you want to update the interface
	 */
	public void moveDisplayUpdate(Player p) {
		ArrayList<Move> pokeMoves = p.getSelectedPokemon().getlearnedMoves();

		int i;
		for (i = 0; i < pokeMoves.size(); i++) {
			// From the Anchor pane of moves, get the i vbox containing 2 labels. 0: move's
			// Name, 1: move's PP
			Move pokeMove = pokeMoves.get(i);
			((VBox) movePane.getChildren().get(i)).setDisable(false);
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(0))).setText(pokeMove.getName());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(1))).setText(pokeMove.getType().name());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(2)))
					.setText(pokeMove.getPP() + "/" + pokeMove.getMaxPP());
			if (pokeMove.getPP() <= 0)
				movePane.getChildren().get(i).setDisable(true);
		}

		for (i = pokeMoves.size(); i < 4; i++) {
			((VBox) movePane.getChildren().get(i)).setDisable(true);
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(0))).setText("");
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(1))).setText("");
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(2))).setText("");
		}

	}

	/**
	 * Update the team interface depending on the player given
	 * 
	 * @param p The player you want to update the interface
	 */
	public void teamDisplayUpdate(Player p) {

		List<Pokemon> team = p.getTeam();

		int pokeIndex = team.indexOf(p.getSelectedPokemon());

		int i;
		for (i = 0; i < team.size(); i++) {
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

		for (i = team.size(); i < 6; i++) {
			((HBox) switchPane.getChildren().get(i)).setDisable(true);
		}

	}

	/**
	 * Update the HP bar
	 * 
	 * @param pgHP       ProgressBar of HP
	 * @param colorStyle String of the color you want to change
	 */
	private void updateProgressBarColor(ProgressBar pgHP, String colorStyle) {
		pgHP.getStyleClass().removeAll(pgColorStyles);
		pgHP.getStyleClass().add(colorStyle);
	}

	/**
	 * Update the main interface of the two pokemon fighting
	 * 
	 * @param p          the Player you want his interface updated
	 * @param imgV       The image of the pokemon
	 * @param pokeName   The label for the pokemon's name
	 * @param pokeLvl    The label for the pokemon's lvl
	 * @param pokeHP     The label for the pokemon's hp
	 * @param PokeStatus The label for the pokemon's status
	 * @param pgHP       The progress for the pokemon's hp
	 */
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

	/**
	 * this method will manage the turns of each player
	 * 
	 * @param players Every players that are in the fight
	 * @return An array of String designed to update the text info of the game
	 */
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
					openSwitchMenu();
					cancelButtonSwitch.setDisable(true);
					return null;
				} else if (!players[i].getWhichPlayer().isBot() && players[i].getWhichPlayer().getAlive() <= 0) {
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
					openSwitchMenu();
					cancelButtonSwitch.setDisable(true);
					return null;
				}
			}

		}

		displayUpdate();

		return messages;
	}

	/**
	 * Handle the prority of turns
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
			openTextMenu();
			mainMenuClick(null);
		}

	}

	/**
	 * Get the index of an alive pokemon in the list given
	 * 
	 * @param list Team of a player
	 * @return The index of a random alive pokemon
	 */
	private int getPokemonAliveIndex(List<Pokemon> list) {
		int res;
		do {
			res = new Random().nextInt(list.size());
		} while (!list.get(res).isAlive());

		return res;
	}

	/**
	 * Takes every player in pairs and switch theirs pokemon between them
	 * 
	 * 
	 * @param players every Players in the fight 
	 */
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

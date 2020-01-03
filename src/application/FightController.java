package application;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.model.fight.Action;
import application.model.fight.Fight;
import application.model.fight.Player;
import application.model.moves.AttackResult;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FightController extends AbstractController {

	private Player playerUser;

	private Player playerFoe;

	//private Fight fightModel; // Pas sur si on garde, mon idée serait de gérer les combats dans fights et de
								// faire appel aux méthode de rafraichissement de l'interface présente dans le
								// controlleur donc ça puisse l'info dans les players (comme ça on pense à si on
								// veut jouer à plusieurs joueurs et au dévellopement futur possible du jeu, hop
								// comme l'affichage se fait en fonction du joueur c'est cool étou)
								//
								//Jsuis Ok

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView ImageViewFoePokemon;

    @FXML
    private HBox hBoxMenuButtons;

    @FXML
    private ImageView imageViewAllyPokemon;

    @FXML
    private Label labelAllyPokeHP;

    @FXML
    private Label labelAllyPokeLvl;

    @FXML
    private Label labelAllyPokeName;

    @FXML
    private Label labelFoePokeHP;

    @FXML
    private Label labelFoePokeLvl;

    @FXML
    private Label labelFoePokeName;

    @FXML
    private AnchorPane movePane;

    @FXML
    private ProgressBar progressBarAllyPokeHP;

    @FXML
    private ProgressBar progressBarFoePokeHP;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane switchPane;
    
    @FXML
    private AnchorPane anchorPaneMenu;

    @FXML
    private TabPane tabPaneMenu;

    @FXML
    private TextArea textAreaMatchNotification;

	@FXML
	private VBox vBoxMove0;

	@FXML
	private VBox vBoxMove1;

	@FXML
	private VBox vBoxMove2;

	@FXML
	private VBox vBoxMove3;
	
	@FXML
    private Button cancelButtonSwitch;
	
	
	private List<String> msgs = null;

	@FXML
	void run(ActionEvent event) {
		// oof
		Platform.exit();
	}
	
	@FXML
	void mainMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(1);
	}
	

    @FXML
    void mainMenuClick(MouseEvent event) {
    	if(msgs.size()>=1) {
        	textAreaMatchNotification.setText(msgs.get(0));
    		msgs = msgs.subList(1, msgs.size());
    	}
    	else {
    		msgs = null;
    		tabPaneMenu.getSelectionModel().select(1);
    	}
    }

	@FXML
	void backPackPage(ActionEvent event) {
	}

	@FXML
	void fightMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(2);
		playerUser.moveDisplayUpdate(movePane);
	}

	@FXML
	void switchMenu(ActionEvent event) {
		tabPaneMenu.getSelectionModel().select(3);
		
		playerUser.teamDisplayUpdate(switchPane);
	}

	
	/*
	 * Pour les deux méthodes ci dessous, il faudrait changer l'affichage pour afficher un message en fonction de player.turn()
	 */
	@FXML
	void switchPokemon(MouseEvent event) {
		boolean wasAlive = playerUser.getSelectedPokemon().isAlive();
		int numPokemon = Integer.parseInt((((HBox) event.getSource()).getId()).replace("hboxSwitchPokemon", ""));
		playerUser.setNextAction(Action.SWITCH, numPokemon,playerFoe);
		if(playerFoe.isBot()) playerFoe.generateNextAction(playerUser);
		tabPaneMenu.getSelectionModel().select(1);
		if(wasAlive) doTurns();
		else switchPokemons();
	}
	
	/*
	 * Permet de faire son tour seulement si on switch (utile dans le cas ou on switch car le pokémon est mort)
	 */
	private void switchPokemons() {
		if(playerUser.getNextAction() == Action.SWITCH) {
			playerUser.turn(playerFoe);
		}
		if(playerFoe.getNextAction() == Action.SWITCH) {
			playerUser.turn(playerUser);
		}
		displayUpdate();
	}

	@FXML
	void useMove(MouseEvent event) {
		int numMove = Integer.parseInt((((VBox) event.getSource()).getId()).replace("vBoxMove", ""));
		playerUser.setNextAction(Action.MOVE, numMove,playerFoe);
		if(playerFoe.isBot()) playerFoe.generateNextAction(playerUser);
		tabPaneMenu.getSelectionModel().select(1);
		doTurns();
	}
	
	@FXML
	void openMenu(ActionEvent event) {
		anchorPaneMenu.setVisible(true);
	}
	
	@FXML
	void save(ActionEvent event) {
		
	}
	
	@FXML
	void load(ActionEvent event) {
		
	}
	
	@FXML
	void backToMenu(ActionEvent event) throws IOException {
		super.changeSceneWithoutData(event, "TitleScreen.fxml");
	}
	
	@FXML
	void back(ActionEvent event) {
		anchorPaneMenu.setVisible(false);
	}
	
	//Il faudrait gérer ca dans cette méthode ici (on évite de déléguer au modèle l'affichage)
	@Override
	public void displayUpdate() {
		// generate the moves of the player in the interface
		playerUser.moveDisplayUpdate(movePane);
		playerUser.teamDisplayUpdate(switchPane);
		playerUser.mainScreenUpdate(imageViewAllyPokemon, labelAllyPokeName, labelAllyPokeLvl, labelAllyPokeHP, progressBarAllyPokeHP, false);
		playerFoe.mainScreenUpdate(ImageViewFoePokemon, labelFoePokeName, labelFoePokeLvl, labelFoePokeHP, progressBarFoePokeHP, true);
	}

	@FXML
	void initialize() throws IOException {

		// Pour selectionner un tab (text:0, menu principal: 1, fight:2, switch: 3,
		// backpack: 4 (mais rien c'est fait pour lui encore)
		tabPaneMenu.getSelectionModel().select(1);
		
		playerUser = Player.createRandomPlayer(false);
		
		playerFoe = Player.createRandomPlayer(true);
		
		// A voir si on actuallise pas l'affichage dans le fight et à partir de là on actualise avec les players
		
		displayUpdate();
		
	}
	
	
	private String[] turns(Player... players) {
		
		String[] messages = new String[players.length];
		AttackResult att;
		
		for(int i =0;i<players.length;i++) {
			switch(players[i].getNextAction()) {
				case MOVE:
					messages[i] = players[i].getSelectedPokemon().getName()+" utilise "+players[i].getSelectedPokemon().getlearnedMoves().get(players[i].getWhichAction()).getName()+"\n";
					break;
					
				case SWITCH:
					messages[i] = "Reviens "+players[i].getSelectedPokemon().getName()+", vasy "+players[i].getTeam().get(players[i].getWhichAction()).getName()+"\n";
					break;
		
				default:
					break;
			}
			att = players[i].turn(players[i].getWhichPlayer());
			
			if(att != null) {
				switch(att) {
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
				}
			}
			
			
			if(!players[i].getWhichPlayer().getSelectedPokemon().isAlive()) {
				messages[i]+= "Le pokémon adverse est KO !";
				if(players[i].getWhichPlayer().isBot()) players[i].getWhichPlayer().forceSwitch();
				else {
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
		String[] messages = new String[2];
		
		
		if(playerFoe.getNextAction().getPriority() > playerUser.getNextAction().getPriority()) {
			//Il faut afficher le message en fonction du retour
			messages = turns(playerFoe,playerUser);
		}else if(playerFoe.getNextAction().getPriority() < playerUser.getNextAction().getPriority()) {
			//Il faut afficher le message en fonction du retour
			messages = turns(playerUser,playerFoe);
		}else {
			//si les deux on la même priorité
			if(playerUser.getNextAction() == Action.MOVE && playerFoe.getNextAction() == Action.MOVE) {
				//C'est la vitesse des pokémons qui choisit la priorité
				if(playerUser.getSelectedPokemon().getCurrentStats().getSpeed() >= playerFoe.getSelectedPokemon().getCurrentStats().getSpeed()) {
					//en cas d'égalité on donne l'avantage au joueur
					messages = turns(playerUser,playerFoe);
				}else {
					messages = turns(playerFoe,playerUser);
				}
			}else {
				//l'ordre n'a pas d'importance
				messages = turns(playerUser,playerFoe);
			}
		}
		displayUpdate();
		
		if(messages != null) {
			msgs = Arrays.asList(messages);
			tabPaneMenu.getSelectionModel().select(0);
			mainMenuClick(null);
		}

		
		
	}
}

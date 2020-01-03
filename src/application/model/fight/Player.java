package application.model.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import application.model.appmodel.TeamBuilder;
import application.model.items.Item;
import application.model.moves.AttackResult;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Player {
	private final ArrayList<Pokemon> team;
	private Pokemon selectedPokemon;
	private int alive;
	private final boolean bot;
	private Action nextAction;
	private int whichAction;
	private Player whichPlayer;
	private ArrayList<Item> backPack = new ArrayList<>();

	// HP Display

	private static final String RED_BAR = "red-bar";
	private static final String YELLOW_BAR = "yellow-bar";
	private static final String ORANGE_BAR = "orange-bar";
	private static final String GREEN_BAR = "green-bar";
	private static final String[] pgColorStyles = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };

	public Player(ArrayList<Pokemon> team, boolean bot) {
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); // Le premier Pokémon est celui lancé en premier
		alive = team.size();
		this.bot = bot;
	}

	public static Player createRandomPlayer(boolean b) throws IOException {
		return new Player(TeamBuilder.createTeamBuilder().createRandomTeam(), b);
	}

	public void switchPokemon(Pokemon p) {
		System.out.println("Le prochain pokémon : "+p.getName());
		if (p.isAlive()) {
			selectedPokemon = p;
		} else {
			throw new IllegalArgumentException("The pokemon is KO");
		}
	}

	public boolean isBot() {
		return bot;
	}

	public void moveDisplayUpdate(AnchorPane movePane) {
		ArrayList<Move> pokeMoves = selectedPokemon.getlearnedMoves();
		for (int i = 0; i < pokeMoves.size(); i++) {
			// From the Anchor pane of moves, get the i vbox containing 2 labels. 0: move's
			// Name, 1: move's PP
			Move pokeMove = pokeMoves.get(i);

			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(0))).setText(pokeMove.getName());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(1))).setText(pokeMove.getType().name());
			(((Label) ((VBox) movePane.getChildren().get(i)).getChildren().get(2)))
					.setText(pokeMove.getPP() + "/" + pokeMove.getMaxPP());
		}
	}

	public void teamDisplayUpdate(AnchorPane switchPane) {
		int pokeIndex = team.indexOf(selectedPokemon);

		for (int i = 0; i < team.size(); i++) {
			Pokemon pokemon = team.get(i);

			HBox pokemonDisplay = ((HBox) switchPane.getChildren().get(i));
			((ImageView) pokemonDisplay.getChildren().get(0)).setImage(pokemon.getFrontSprite());
			pokemonDisplay.setDisable(false);

			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(0))).setText(pokemon.getName());
			(((Label) ((VBox) pokemonDisplay.getChildren().get(1)).getChildren().get(1)))
					.setText(pokemon.getBaseStats().getHp() + "/" + "hpmax");

			if (i == pokeIndex || !team.get(i).isAlive()) {
				pokemonDisplay.setDisable(true);
			}
		}
	}

	private void updateProgressBarColor(ProgressBar pgHP, String colorStyle) {
		pgHP.getStyleClass().removeAll(pgColorStyles);
		pgHP.getStyleClass().add(colorStyle);
	}

	// boolean à enlever des paramètres quand le boolean bot sera implémenter
	public void mainScreenUpdate(ImageView imageViewPokemon, Label pokeName, Label pokeLvl, Label pokeHP,
			ProgressBar pgHP, Boolean bot) {
		if (bot) {
			imageViewPokemon.setImage(selectedPokemon.getFrontSprite());
		} else {
			imageViewPokemon.setImage(selectedPokemon.getBackSprite());
		}

		int maxHP = selectedPokemon.getBaseStats().getHp();
		int currentHP = selectedPokemon.getCurrentStats().getHp();

		pokeName.setText(selectedPokemon.getName());
		pokeLvl.setText("lvl" + selectedPokemon.getLevel());
		pokeHP.setText(currentHP + " / " + maxHP);

		Double progress = (double) currentHP/maxHP;

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
	
	public void setNextAction(Action nextAction, int which,Player target) {
		this.nextAction = nextAction;
		this.whichAction = which;
		whichPlayer = target;
	}
	
	public Pokemon getSelectedPokemon() {
		return selectedPokemon;
	}
	
	public Action getNextAction() {
		return nextAction;
	}
	
	/*
	 * On dit :
	 * 80% de chance que le bot attaque avec un attaque aléatoire
	 * 20 % il switch sur un pokémon aléatoire
	 */
	public void generateNextAction(Player p) {
		double choice = Math.random();
		Random randomChoice = new Random();
		if(choice < 0.80) {
			System.out.println("Le bot attaquera !");
			setNextAction(Action.MOVE, randomChoice.nextInt(selectedPokemon.getlearnedMoves().size()),p);
		}else {
			System.out.println("Le bot changera de pokémon ! "+team.size());
			int next;
			do {
				next =  randomChoice.nextInt(team.size());
			} while (!team.get(next).isAlive() && !team.get(next).equals(selectedPokemon));
			setNextAction(Action.SWITCH,next ,p);
		}
		
	}
	
	public void forceSwitch() {
		Random randomChoice = new Random();
		int next;
		do {
			next =  randomChoice.nextInt(team.size());
		} while (!team.get(next).isAlive());
		setNextAction(Action.SWITCH,next ,whichPlayer);
	}
	
	
	/*
	 * Méthode principale : Effectue le tour du joueur
	 */
	public AttackResult turn(Player p) {
		switch(nextAction) {
			case MOVE:
				return selectedPokemon.getlearnedMoves().get(whichAction).use(selectedPokemon, p.selectedPokemon);
			case SWITCH:
				switchPokemon(team.get(whichAction));
			default:
				break;
		}
			
		return null;
	}
	
	public int getWhichAction() {
		return whichAction;
	}
	
	public ArrayList<Pokemon> getTeam() {
		return team;
	}
	
	/*
	 * Check différents paramètres:
	 * 	-Le pokémon actuel est il vivant ?
	 */
	public boolean isEverythingOk() {
		if(!selectedPokemon.isAlive()) {
			alive -=1;
			return false;
		};
		return true;
	}
	
	public Player getWhichPlayer() {
		return whichPlayer;
	}
}

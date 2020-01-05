package application.model.fight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import application.model.appmodel.TeamBuilder;
import application.model.items.Item;
import application.model.moves.AttackResult;
import application.model.pokemon.Pokemon;

public class Player implements Serializable {
	private final ArrayList<Pokemon> team;
	private Pokemon selectedPokemon;
	private int alive;
	private final boolean bot;
	private Action nextAction;
	private int whichAction;
	private Player whichPlayer;
	private ArrayList<Item> backPack = new ArrayList<>();

	public Player(ArrayList<Pokemon> team, boolean bot) {
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); // Le premier Pokémon est celui lancé en premier
		alive = team.size();
		this.bot = bot;
	}

	public Player(TeamBuilder teamBuilder, boolean bot) {
		this(teamBuilder.getTeam(), bot);
	}

	public void switchPokemon(Pokemon p) {
		if (p.isAlive()) {
			selectedPokemon = p;
		} else {
			throw new IllegalArgumentException("The pokemon is KO");
		}
	}

	public boolean isBot() {
		return bot;
	}

	public void setNextAction(Action nextAction, int which, Player target) {
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
	 * On dit : 80% de chance que le bot attaque avec un attaque aléatoire 20 % il
	 * switch sur un pokémon aléatoire
	 */
	public void generateNextAction(Player p) {
		double choice = Math.random();
		Random randomChoice = new Random();
		if (choice < 0.80) {
			System.out.println("Le bot attaquera !");
			setNextAction(Action.MOVE, randomChoice.nextInt(selectedPokemon.getlearnedMoves().size()), p);
		} else {
			System.out.println("Le bot changera de pokémon ! " + team.size());
			int next;
			do {
				next = randomChoice.nextInt(team.size());
			} while (!team.get(next).isAlive() && !team.get(next).equals(selectedPokemon));
			setNextAction(Action.SWITCH, next, p);
		}

	}

	public void forceSwitch() {
		Random randomChoice = new Random();
		int next;

		do {
			next = randomChoice.nextInt(team.size());
		} while (!team.get(next).isAlive());

		// setNextAction(nextAction, next, whichPlayer);

		switchPokemon(team.get(next));
	}

	/**
	 * Remove the pokemon at the given index to replace it with the new pokemon
	 * 
	 * @param newPkmn       The new pokemon you want to swap
	 * @param pokemonNumber The old pokemon you want to swap
	 */
	private void swapPokemon(Pokemon newPkmn, int pokemonNumber) {
		team.remove(pokemonNumber);
		team.add(pokemonNumber, newPkmn);
	}

	/**
	 * Swap 2 pokemons between the playerOne you use this method on and the
	 * playerTwo you put in parameter
	 * 
	 * 
	 * @param playerTwo the other player you want to switch with
	 * @param intOne    Index of the pokemon of the playerOne
	 * @param intTwo    Index of the pokemon of the playerTwo
	 */
	public void swapTeam(Player playerTwo, int intOne, int intTwo) {
		Pokemon pkmnOne = team.get(intOne);
		Pokemon pkmnTwo = playerTwo.getTeam().get(intTwo);

		this.swapPokemon(pkmnTwo, intOne);
		playerTwo.swapPokemon(pkmnOne, intTwo);
	}

	/*
	 * Méthode principale : Effectue le tour du joueur
	 * 
	 */
	public AttackResult turn(Player p) {
		switch (nextAction) {
		case MOVE:

			if (selectedPokemon.getStatus() != null
					&& !selectedPokemon.getStatus().getEachTurn().use(selectedPokemon)) {
				switch (selectedPokemon.getStatus()) {
				case FREEZE:
					return AttackResult.FROZEN;
				case PARALYSIS:
					return AttackResult.PARALYZED;
				case SLEEP:
					return AttackResult.ASLEEP;
				default:
					return null;

				}
			}
			return selectedPokemon.getlearnedMoves().get(whichAction).use(selectedPokemon, p.selectedPokemon);
		case SWITCH:
			selectedPokemon.getCurrentStats().resetBoosts();
			switchPokemon(team.get(whichAction));
			if (team.get(whichAction).getStatus() != null) {
				team.get(whichAction).getStatus().getWhenReceived().use(team.get(whichAction));
			}
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

	public void mainPokemonKilled() {
		alive--;
		if (bot && alive >= 1) {
			forceSwitch();
		}
	}

	public int getAlive() {
		return alive;
	}

	public Player getWhichPlayer() {
		return whichPlayer;
	}
}

package application.model.fight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import application.model.appmodel.TeamBuilder;
import application.model.items.Item;
import application.model.moves.AttackResult;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;

/**
 * Class representing a trainer, this class is mostly used inf the fights
 * 
 * @author Armadindon, Kwaaac
 *
 */
public class Player implements Serializable {
	private final List<Pokemon> team;
	private Pokemon selectedPokemon;
	private final boolean bot;
	private Action nextAction;
	private int whichAction;
	private Player whichPlayer;
	private ArrayList<Item> backPack = new ArrayList<>();

	/**
	 * Default Constructor
	 * 
	 * @param team - List containing max 6 pokemons
	 * @param bot  - if the player is a bot
	 */
	public Player(List<Pokemon> team, boolean bot) {
		if ((team.size() > 6 || team.size() == 0))
			throw new IllegalArgumentException("The team need at least 1 pokemon and max 6 pokemon");
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); // Le premier Pokémon est celui lancé en premier
		this.bot = bot;
	}

	/**
	 * User Player constructor
	 * 
	 * Create an empty player in the case that the user build a new team and with it
	 * a new player
	 * 
	 */
	public Player() {
		this.team = new ArrayList<Pokemon>();
		this.selectedPokemon = null;
		this.bot = false;
	}

	/**
	 * Switch the Pokemon current Pokemon
	 * 
	 * @param p - The Pokemon that we want to throw
	 */
	public void switchPokemon(Pokemon p) {
		if (p.isAlive()) {
			selectedPokemon = p;
		} else {
			throw new IllegalArgumentException("The pokemon is KO");
		}
	}

	/**
	 * Tell if the player is a bot
	 * 
	 * @return true if the player is a bot, false else
	 */
	public boolean isBot() {
		return bot;
	}

	/**
	 * Permit to tell the player the next action he will do
	 * 
	 * @param nextAction - Action that will be executed
	 * @param which      - On which parameter (Which Pokemon ? Which Item ? Which
	 *                   Move ? )
	 * @param target     - Which player will receive the action
	 */
	public void setNextAction(Action nextAction, int which, Player target) {
		this.nextAction = nextAction;
		this.whichAction = which;
		whichPlayer = target;
	}

	/**
	 * Tell which Pokemon is actually sended by the Trainer
	 * 
	 * @return
	 */
	public Pokemon getSelectedPokemon() {
		return selectedPokemon;
	}

	/**
	 * Tell which action wil be executed next
	 * 
	 * @return the action (can be null if no action are planned
	 */
	public Action getNextAction() {
		return nextAction;
	}

	/**
	 * Generate the next action of the given player
	 * 
	 * @param p - The player to generate the action
	 */
	public void generateNextAction(Player p) {
		double choice = Math.random();
		Random randomChoice = new Random();
		if (choice < 0.80 || getAlive() == 1) {
			System.out.println("Le bot attaquera !");
			setNextAction(Action.MOVE, randomChoice.nextInt(selectedPokemon.getlearnedMoves().size()), p);
		} else {
			System.out.println("Le bot changera de pokémon ! " + team.size());
			int next;
			do {
				next = randomChoice.nextInt(team.size());
			} while (!team.get(next).isAlive() && team.get(next).equals(selectedPokemon));
			setNextAction(Action.SWITCH, next, p);
		}
	}

	/**
	 * Force the player to switch pokemon with a random pokemon
	 */
	public void forceSwitch() {
		Random randomChoice = new Random();
		int next;

		do {
			next = randomChoice.nextInt(team.size());
		} while (!team.get(next).isAlive() || team.get(next).equals(selectedPokemon));

		System.out.println("next pokemon is alive ?: " + team.get(next).isAlive());
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
		Pokemon removedP = team.remove(pokemonNumber);
		if (removedP.equals(selectedPokemon)) {
			selectedPokemon = newPkmn;
		}
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

	/**
	 * Main method - the player will make his turn
	 * 
	 * @param p - The player impacted by the event
	 * @return The result of the attack, can be null if the action is not supported
	 *         yet
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
			Pokemon switchPokemon;

			selectedPokemon.getCurrentStats().resetBoosts();
			if (!team.get(whichAction).isAlive()) {
				switchPokemon = selectedPokemon;
			} else {
				switchPokemon = team.get(whichAction);
			}
			switchPokemon(switchPokemon);

			if (switchPokemon.getStatus() != null) {
				switchPokemon.getStatus().getWhenReceived().use(switchPokemon);
			}
		default:
			break;
		}

		return null;
	}

	/**
	 * Get the index of the next action executed
	 * 
	 * @return the index of the next action to execute
	 */
	public int getWhichAction() {
		return whichAction;
	}

	/**
	 * Get the current team
	 * 
	 * @return the team
	 */
	public List<Pokemon> getTeam() {
		return team;
	}

	/**
	 * action to execute when the current Pokemon is dead
	 */
	public void mainPokemonKilled() {
		if (bot && getAlive() >= 1) {
			forceSwitch();
		}
	}

	/**
	 * Give the number of alive pokemon in the team
	 * 
	 * @return the number of alive pokemons
	 */
	public int getAlive() {
		return ((List<Pokemon>) team.stream().filter(p -> p.isAlive()).collect(Collectors.toList())).size();
	}

	/**
	 * Give the ennemy player
	 * 
	 * @return the ennemy player
	 */
	public Player getWhichPlayer() {
		return whichPlayer;
	}

	/**
	 * Get the first pokemon of the team or the pokemon that is fighting
	 * 
	 * @return Pokemon - the main pokemon of the player
	 */
	public Pokemon getPokemon() {
		return selectedPokemon;
	}

	/**
	 * Remove a pokemon from the player's team with the index of the pokemon
	 * 
	 * @param pokemonIndex int - index of the pokemon
	 */
	public void removePokemon(int pokemonIndex) {
		team.remove(pokemonIndex);
	}

	/**
	 * Set a selectedPokemon of the player
	 * 
	 * @param pokemon Pokemon to put
	 */
	public void setPokemon(Pokemon pokemon) {
		this.selectedPokemon = pokemon;
	}

	/**
	 * Can this pokemon join the team based on his available moves
	 * 
	 * @return True if it can, false otherwise
	 */
	public boolean canAddPokemon() {

		if (selectedPokemon.getlearnedMoves().size() == 0) {
			return false;
		}

		return true;

	}

	/**
	 * Add the pokemon in parameter to the player's team
	 * 
	 * @param pokemon The pokemon that will be added to the team
	 * @throws IllegalArgumentException if the given pokemon is null
	 */
	public void addPokemonToTeam(Pokemon pokemon) {
		if (pokemon == null)
			throw new IllegalArgumentException("Pokemon cannot be null");

		team.add(pokemon);
	}

	/**
	 * Add a move to the selected Pokemon
	 * 
	 * @param move the move that will be added
	 * @return True if the move managed to be added, False otherwise
	 */
	public boolean addMovePokedex(Move move) {
		return selectedPokemon.addMoveToLearnedMoves(move);
	}

	/**
	 * Remove the move at the specified index
	 * 
	 * @param moveIndex index of the move
	 */
	public void removeMovePokedex(int moveIndex) {
		selectedPokemon.removeMoveFromLearnedMoves(moveIndex);
	}

	/**
	 * Get the player ready for the battle by putting his first pokemon ready to
	 * battle. If the pokemon is K.O, then the following one will be chosen until a
	 * pokemon is picked.
	 * 
	 * @throws IllegalStateException If the whole team is K.O
	 * 
	 */
	public void getReadyForBattle() {
		for (int i = 0; i < team.size(); i++) {
			if (team.get(i).isAlive()) {
				selectedPokemon = team.get(i);
				return;
			}
		}

		throw new IllegalStateException(
				"It seems like you try to fight with a team with no pokemon available for battle");
	}

	/**
	 * Heal the player's whole team (HP, stats, status, PPs)
	 * 
	 */
	public void healTeam() {
		for (Pokemon pokemon : team) {
			pokemon.healPokemon();
		}

	}
}

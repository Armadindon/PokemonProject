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
import application.model.pokemon.Pokemon;


/**
 * Class representing a trainer, this class is mostly used inf the fights
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
	 * @param team - List containing max 6 pokemons
	 * @param bot - if the player is a bot
	 */
	public Player(List<Pokemon> team, boolean bot) {
		if(team.size() > 6 || team.size() == 0) throw new IllegalArgumentException("The team need at least 1 pokemon and max 6 pokemon");
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); // Le premier Pokémon est celui lancé en premier
		this.bot = bot;
	}
	
	/**
	 * Second Constructor with a teambuilder instead of a List
	 * @param teamBuilder
	 * @param bot
	 */
	public Player(TeamBuilder teamBuilder, boolean bot) {
		this(Objects.requireNonNull(teamBuilder).getTeam(), bot);
	}
	
	/**
	 * Switch the Pokemon current Pokemon
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
	 * @return true if the player is a bot, false else
	 */
	public boolean isBot() {
		return bot;
	}
	
	/**
	 * Permit to tell the player the next action he will do
	 * @param nextAction - Action that will be executed
	 * @param which - On which parameter (Which Pokemon ? Which Item ? Which Move ? )
	 * @param target - Which player will receive the action
	 */
	public void setNextAction(Action nextAction, int which, Player target) {
		this.nextAction = nextAction;
		this.whichAction = which;
		whichPlayer = target;
	}
	
	/**
	 * Tell which Pokemon is actually sended by the Trainer
	 * @return
	 */
	public Pokemon getSelectedPokemon() {
		return selectedPokemon;
	}
	
	/**
	 * Tell which action wil be executed next
	 * @return the action (can be null if no action are planned
	 */
	public Action getNextAction() {
		return nextAction;
	}

	/**
	 * Generate the next action of the given player
	 * @param p - The player to generate the action
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
		Pokemon removedP = team.remove(pokemonNumber);
		if(removedP.equals(selectedPokemon)) {
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

	public List<Pokemon> getTeam() {
		return team;
	}

	public void mainPokemonKilled() {
		if (bot && getAlive() >= 1) {
			forceSwitch();
		}
	}

	public int getAlive() {
		return ((List<Pokemon>) team.stream().filter(p->p.isAlive()).collect(Collectors.toList())).size();
	}

	public Player getWhichPlayer() {
		return whichPlayer;
	}
}

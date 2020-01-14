package application.model.pokemon;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import application.model.items.Item;
import application.model.moves.Move;

/**
 * Class representing a Pokemon, some functionalities are prepared but not
 * implemented (example : items or level)
 * 
 * @author Armadindon
 *
 */
public class Pokemon implements Serializable, Cloneable {
	private final int id;
	private String name;
	private final int baseExperience;
	private final int height;
	private final int weight;
	private final Item carriedItem;
	private final String frontSprite;
	private final String backSprite;
	private final ArrayList<Move> allPossiblesMoves;
	private ArrayList<Move> learnedMoves;
	private Stats baseStats;
	private Stats currentStats;
	private final Type type1;
	private final Type type2;
	private Status status;
	private final int level = 1; // on considère que tout les pokémons sont niveau 1 pour l'instant
	private boolean alive = true;
	private final String description;

	/**
	 * Default Constructor used by our factories
	 * 
	 * @param id
	 * @param name
	 * @param baseExperience
	 * @param height
	 * @param weight
	 * @param carriedItem
	 * @param frontSprite
	 * @param backSprite
	 * @param allPossiblesMoves
	 * @param learnedMoves
	 * @param baseStats
	 * @param currentStats
	 * @param type1
	 * @param type2
	 * @param status
	 * @param description
	 */
	private Pokemon(int id, String name, int baseExperience, int height, int weight, Item carriedItem,
			String frontSprite, String backSprite, ArrayList<Move> allPossiblesMoves, ArrayList<Move> learnedMoves,
			Stats baseStats, Stats currentStats, Type type1, Type type2, Status status, String description) {
		this.id = id;
		this.name = name;
		this.baseExperience = baseExperience;
		this.height = height;
		this.weight = weight;
		this.carriedItem = carriedItem;
		this.frontSprite = frontSprite;
		this.backSprite = backSprite;
		this.allPossiblesMoves = allPossiblesMoves;
		this.learnedMoves = learnedMoves;
		this.baseStats = baseStats;
		this.currentStats = currentStats;
		this.type1 = type1;
		this.type2 = type2;
		this.status = status;
		this.description = description;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Pokemon clonedPokemon = (Pokemon) super.clone();

		clonedPokemon.baseStats = (Stats) baseStats.clone();
		clonedPokemon.currentStats = (Stats) currentStats.clone();

		ArrayList<Move> clonedMoves = new ArrayList<>();

		for (Move m : learnedMoves) {
			clonedMoves.add((Move) m.clone());
		}

		clonedPokemon.learnedMoves = clonedMoves;

		return clonedPokemon;
	}

	/**
	 * Generate a Pokemon from data issued of a CSV file (created by us), you need
	 * to generate all the moves first for passing it as an argument
	 * 
	 * @param data          - data get from CSVReader
	 * @param existingMoves - All Moves objects existing
	 * @return a Pokemon from the data
	 */
	public static Pokemon generateFromMap(Map<String, List<String>> data, ArrayList<Move> existingMoves) {
		int id = Integer.parseInt(data.get("id").get(0));
		String name = data.get("name").get(0);
		int baseExperience = Integer.parseInt(data.get("base_experience").get(0));
		int height = Integer.parseInt(data.get("height").get(0));
		int weight = Integer.parseInt(data.get("weight").get(0));
		Item carriedItem = null;
		String frontSprite = File.separatorChar + "scripts" + File.separatorChar
				+ data.get("spriteFront").get(0).replace("/", File.separator);

		String backSprite;
		if (data.get("spriteBack").get(0).equals("NULL")) {
			backSprite = null;
		} else {
			backSprite = File.separatorChar + "scripts" + File.separatorChar
					+ data.get("spriteBack").get(0).replace("/", File.separator);
		}

		ArrayList<Move> allPossiblesMoves = new ArrayList<>();

		for (String moveId : data.get("learnableMove")) {
			Optional<Move> optMove = existingMoves.stream().filter(move -> move.getId() == Integer.parseInt(moveId))
					.findAny();
			if (optMove.isPresent())
				allPossiblesMoves.add(optMove.get());
		}

		ArrayList<Move> learnedMoves = new ArrayList<>(4); // On apprend que 4 moves
		Stats baseStats = new Stats(Integer.parseInt(data.get("speed").get(0)),
				Integer.parseInt(data.get("attack").get(0)), Integer.parseInt(data.get("spAttack").get(0)),
				Integer.parseInt(data.get("defense").get(0)), Integer.parseInt(data.get("spDefense").get(0)),
				Integer.parseInt(data.get("hp").get(0)));
		Stats currentStats = new Stats(Integer.parseInt(data.get("speed").get(0)),
				Integer.parseInt(data.get("attack").get(0)), Integer.parseInt(data.get("spAttack").get(0)),
				Integer.parseInt(data.get("defense").get(0)), Integer.parseInt(data.get("spDefense").get(0)),
				Integer.parseInt(data.get("hp").get(0)));
		Type type1 = Type.valueOf(data.get("type1").get(0).toUpperCase());
		Type type2 = null;
		if (!data.get("type2").get(0).equals("NULL")) {
			type2 = Type.valueOf(data.get("type2").get(0).toUpperCase());
		}

		Status status = null;

		String description = data.get("description").get(0).replace("\"", "");

		return new Pokemon(id, name, baseExperience, height, weight, carriedItem, frontSprite, backSprite,
				allPossiblesMoves, learnedMoves, baseStats, currentStats, type1, type2, status, description);
	}

	/**
	 * Get the Id of the pokemon
	 * 
	 * @return The id of the Pokemon
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the name of the pokemon
	 * 
	 * @return The name of the Pokemon
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the name of the pokemon
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the height of the pokemon
	 * 
	 * @return The height of the Pokemon
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * get the weight of the pokemon
	 * 
	 * @return The weight of the Pokemon
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * get the base stats of the pokemon
	 * 
	 * @return The base stats of the Pokemon
	 */
	public Stats getBaseStats() {
		return baseStats;
	}

	/**
	 * get the level of the pokemon
	 * 
	 * @return The level of the Pokemon
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * get the current Stats of the pokemon
	 * 
	 * @return The current Stats of the Pokemon
	 */
	public Stats getCurrentStats() {
		return currentStats;
	}

	/**
	 * get the relative path of the pokemon front sprite
	 * 
	 * @return The relative path to the front sprite
	 */
	public String getFrontSprite() {
		return System.getProperty("user.dir") + frontSprite;
	}

	/**
	 * get the relative path of the pokemon back sprite, if the bach sprite is null,
	 * it return the front sprite
	 * 
	 * @return The relative path to the sprite
	 */
	public String getBackSprite() {
		if (backSprite == null)
			return System.getProperty("user.dir") + frontSprite;
		else
			return System.getProperty("user.dir") + backSprite;
	}

	/**
	 * get the main type of a pokemon
	 * 
	 * @return The main type of the pokemon
	 */
	public Type getType1() {
		return type1;
	}

	/**
	 * get the second type of a pokemon, can be null
	 * 
	 * @return The second type of the pokemon, can be null
	 */
	public Type getType2() {
		return type2;
	}

	/**
	 * Get the moves that the pokemon can learn
	 * 
	 * @return a list of moves
	 */
	public ArrayList<Move> getAllPossiblesMoves() {
		return allPossiblesMoves;
	}

	/**
	 * Get the moves that the pokemon learned
	 * 
	 * @return a list of moves
	 */
	public ArrayList<Move> getlearnedMoves() {
		return learnedMoves;
	}

	/**
	 * Set the status of a pokemon, and execute their effects
	 */
	public void setStatus(Status status) {
		if (this.status != null && status == null)
			this.status.getWhenCured().use(this);
		if (status != null)
			status.getWhenReceived().use(this);
		this.status = status;
	}

	@Override
	public String toString() {
		return id + " - " + name;
	}

	// TODO: Mettre à jour le equals et hashcode quant le eq&hash des items et stats
	// seront fait
	@Override
	public int hashCode() {
		return Objects.hash(allPossiblesMoves, backSprite, baseExperience, baseStats, carriedItem, currentStats,
				frontSprite, height, id, learnedMoves, name, status, type1, type2, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pokemon))
			return false;
		Pokemon other = (Pokemon) obj;
		return Objects.equals(allPossiblesMoves, other.allPossiblesMoves)
				&& Objects.equals(backSprite, other.backSprite) && baseExperience == other.baseExperience
				&& Objects.equals(baseStats, other.baseStats) && Objects.equals(carriedItem, other.carriedItem)
				&& Objects.equals(currentStats, other.currentStats) && Objects.equals(frontSprite, other.frontSprite)
				&& height == other.height && id == other.id && Objects.equals(learnedMoves, other.learnedMoves)
				&& Objects.equals(name, other.name) && status == other.status && type1 == other.type1
				&& type2 == other.type2 && weight == other.weight;
	}

	/**
	 * Make the pokemon learning a move
	 * 
	 * @param move - move to learn
	 * @return true if he learned, false else
	 */
	public boolean addMoveToLearnedMoves(Move move) {
		if (learnedMoves.size() < 4) {
			if (learnedMoves.contains(move)) {
				return false;
			}
			learnedMoves.add(move);

		}
		return false;
	}

	/**
	 * Forget about the move with the given index
	 * 
	 * @param moveIndex - index of the move to forget
	 */
	public void removeMoveFromLearnedMoves(int moveIndex) {
		learnedMoves.remove(moveIndex);
	}

	/**
	 * Hurt the pokemon with given damages
	 * 
	 * @param damage - damage to give to the pokemon
	 */
	public void hurt(int damage) {
		currentStats.add(damage);
		if (currentStats.getHp() < 0)
			;
		alive = false;
	}

	/**
	 * Tell if the pokemon is alive
	 * 
	 * @return true if the pokemon is alive, false else
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * permit to add hp to a pokemon
	 * 
	 * @param hp to give
	 */
	public void addHp(int hp) {
		currentStats.add(hp);
		if (currentStats.getHp() == 0)
			alive = false;
		if (currentStats.getHp() > baseStats.getHp())
			currentStats.setHp(baseStats.getHp());
	}

	/**
	 * Get the currents status of a pokemon
	 * 
	 * @return the status of the pokemon
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Get the description about a pokemon
	 * 
	 * @return the description of the pokemon
	 */
	public String getDescription() {
		return description;
	}
	
	private void resetStat() {
		currentStats.setHp(baseStats.getHp());
		currentStats.resetBoosts();
	}

	/**
	 * Restaure the current stat of the pokemon and restaures all it's PP
	 * 
	 */
	public void healPokemon() {
		resetStat();
		status = null;
		for (Move move : learnedMoves) {
			// It's over 9_000
			move.restaurePP(9_001);
		}
	}

}

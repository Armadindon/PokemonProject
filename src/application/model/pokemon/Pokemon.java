package application.model.pokemon;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import application.model.items.Item;
import application.model.moves.Move;
import javafx.scene.image.Image;

public class Pokemon {
	private final int id;
	private String name;
	private final int baseExperience;
	private final int height;
	private final int weight;
	private final Item carriedItem;
	private final Image frontSprite;
	private final Image backSprite;
	private final ArrayList<Move> allPossiblesMoves;
	private ArrayList<Move> learnedMoves;
	private Stats baseStats;
	private Stats currentStats;
	private final Type type1;
	private final Type type2;
	private Status status;
	private final int level = 1; // on considère que tout les pokémons sont niveau 1 pour l'instant 
	private boolean alive = true;

	
	//Constructeur Temporaire
	public Pokemon(int id, String name, int baseExperience, int height, int weight, Item carriedItem, Image frontSprite,
			Image backSprite, ArrayList<Move> allPossiblesMoves, ArrayList<Move> learnedMoves, Stats baseStats,
			Stats currentStats, Type type1, Type type2, Status status) {
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
	}
	
	public static Pokemon generateFromMap(Map<String, List<String>> data, ArrayList<Move> existingMoves) {
		int id = Integer.parseInt(data.get("id").get(0));
		String name  = data.get("name").get(0);
		int baseExperience = Integer.parseInt(data.get("base_experience").get(0));
		int height = Integer.parseInt(data.get("height").get(0));
		int weight = Integer.parseInt(data.get("weight").get(0));
		Item carriedItem = null;
		Image frontSprite = new Image("file:" + System.getProperty("user.dir") + File.separatorChar + "scripts" + File.separatorChar + data.get("spriteFront").get(0).replace("/", File.separator));
		Image backSprite = new Image("file:" + System.getProperty("user.dir") + File.separatorChar + "scripts" + File.separatorChar + data.get("spriteBack").get(0).replace("/", File.separator));
		
		ArrayList<Move> allPossiblesMoves = new ArrayList<>();
		
		for (String moveId : data.get("learnableMove")) {
			Optional<Move> optMove = existingMoves.stream().filter(move -> move.getId() == Integer.parseInt(moveId)).findAny();
			if(optMove.isPresent()) allPossiblesMoves.add(optMove.get());
		}
		
		ArrayList<Move> learnedMoves = new ArrayList<>(4); // On apprend que 4 moves
		Stats baseStats = new Stats(Integer.parseInt(data.get("speed").get(0)), Integer.parseInt(data.get("attack").get(0)), Integer.parseInt(data.get("spAttack").get(0)), Integer.parseInt(data.get("defense").get(0)), Integer.parseInt(data.get("spDefense").get(0)), Integer.parseInt(data.get("hp").get(0)));
		Stats currentStats = new Stats(Integer.parseInt(data.get("speed").get(0)), Integer.parseInt(data.get("attack").get(0)), Integer.parseInt(data.get("spAttack").get(0)), Integer.parseInt(data.get("defense").get(0)), Integer.parseInt(data.get("spDefense").get(0)), Integer.parseInt(data.get("hp").get(0)));
		Type type1 = Type.valueOf(data.get("type1").get(0).toUpperCase());
		Type type2 = null;
		if(!data.get("type2").get(0).equals("NULL")) {
			type2 = Type.valueOf(data.get("type2").get(0).toUpperCase());
		}
		
		Status status = null;
		
		
		return new Pokemon(id, name, baseExperience, height, weight, carriedItem, frontSprite, backSprite, allPossiblesMoves, learnedMoves, baseStats, currentStats, type1, type2, status);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	public Stats getBaseStats() {
		return baseStats;
	}

	public Image getFrontSprite() {
		return frontSprite;
	}
	
	public Type getType1() {
		return type1;
	}
	
	public Type getType2() {
		return type2;
	}
	
	public ArrayList<Move> getAllPossiblesMoves(){
		return allPossiblesMoves;
	}
	
	public ArrayList<Move> getlearnedMoves(){
		return learnedMoves;
	}

	@Override
	public String toString() {
		return id + " - " + name;
	}

	
	// TODO: Mettre à jour le equals et hashcode quant le eq&hash des items et stats seront fait
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
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean addMoveToLearnedMoves(Move move) {
		if(learnedMoves.size() < 4) {
			if(learnedMoves.contains(move)) {
				return false;
			}
			learnedMoves.add(move);
			
		}
		return false;
	}
	
	public void removeMoveFromLearnedMoves(int moveIndex) {
		learnedMoves.remove(moveIndex);
	}
	
	public int getLevel() {
		return level;
	}
	
	public Stats getCurrentStats() {
		return currentStats;
	}
	
	public void hurt(int damage) {
		currentStats.add(damage);
		if(currentStats.getHp()<0);
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
}

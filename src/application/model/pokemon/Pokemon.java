package application.model.pokemon;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.model.items.Item;
import application.model.moves.Move;

public class Pokemon {
	private final int id;
	private final String name;
	private final int baseExperience;
	private final int height;
	private final int weight;
	private final Item carriedItem;
	private final Path frontSprite;
	private final Path backSprite;
	private final ArrayList<Move> allPossiblesMoves;
	private ArrayList<Move> learnedMoves;
	private Stats baseStats;
	private Stats currentStats;
	private final Type type1;
	private final Type type2;
	private Status status;
	
	//Constructeur Temporaire
	public Pokemon(int id, String name, int baseExperience, int height, int weight, Item carriedItem, Path frontSprite,
			Path backSprite, ArrayList<Move> allPossiblesMoves, ArrayList<Move> learnedMoves, Stats baseStats,
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
	
	
	public static Pokemon generateFromMap(Map<String, List<String>> data) {
		int id = Integer.parseInt(data.get("id").get(0));
		String name  = data.get("name").get(0);
		int baseExperience = Integer.parseInt(data.get("base_experience").get(0));
		int height = Integer.parseInt(data.get("height").get(0));
		int weight = Integer.parseInt(data.get("weight").get(0));
		Item carriedItem = null;
		Path frontSprite = Paths.get(data.get("spriteFront").get(0));
		Path backSprite = Paths.get(data.get("spriteBack").get(0));
		
		//Rajouter les Moves
		
		return null;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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

	@Override
	public String toString() {
		return id + " - " + name;
	}
	
}

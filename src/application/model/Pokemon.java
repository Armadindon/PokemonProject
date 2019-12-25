package application.model;

import java.io.File;
import java.util.ArrayList;

public class Pokemon {
	private final int id;
	private final String name;
	private final int baseExperience;
	private final int height;
	private final int weight;
	private final Item carriedItem;
	private final File frontSprite;
	private final File backSprite;
	private final ArrayList<Move> allPossiblesMoves;
	private ArrayList<Move> learnedMoves;
	private Stats baseStats;
	private Stats currentStats;
	private final Type type1;
	private final Type type2;
	private Status status;
	
	//Constructeur Temporaire
	public Pokemon(int id, String name, int baseExperience, int height, int weight, Item carriedItem, File frontSprite,
			File backSprite, ArrayList<Move> allPossiblesMoves, ArrayList<Move> learnedMoves, Stats baseStats,
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
	
	
	

}

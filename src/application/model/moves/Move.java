package application.model.moves;

import java.util.HashMap;

import application.model.pokemon.Type;

public class Move {
	/*
	 * "id", "name", "move_category", "accuracy", "effect_ailment", "effect_chance",
	 * "damage_class", "type", "power", "pp", "priority", "target", "stat_changes",
	 * "description"])
	 */

	private final int id;
	private final String name;
	private final String moveCategory;
	private final int accuracy;
	private final MoveSideEffect effect;
	private final int effectChance;
	private final String damageClass;
	private final Type type;
	private final int power;
	private final int pp;
	private final int priority;
	private final Target target;
	private final HashMap<String, Integer> statChange;
	private final String description;

	public Move(int id, String name, String moveCategory, int accuracy, MoveSideEffect effect, int effectChance,
			String damageClass, Type type, int power, int pp, int priority, Target target, String statChange,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.moveCategory = moveCategory;
		this.accuracy = accuracy;
		this.effect = effect;
		this.effectChance = effectChance;
		this.damageClass = damageClass;
		this.type = type;
		this.power = power;
		this.pp = pp;
		this.priority = priority;
		this.target = target;
		this.statChange = parseStatChange(statChange);
		this.description = description;
	}

	/**
	 * Change the string from the csvfile to a HashMap key: name of the stat value:
	 * changes applied on the stat
	 * 
	 * @param statChange ex: attack : 2, defense : -2
	 * @return the hashmap with all the stats changes or an empty hashmap if there
	 *         are no changes
	 */
	private HashMap<String, Integer> parseStatChange(String statChange) {
		HashMap<String, Integer> res = new HashMap<>();

		if (statChange.equalsIgnoreCase("No stat change")) {
			return res;
		}

		String[] arrayStats = statChange.split(",");

		for (int i = 0; i < arrayStats.length; i++) {
			String[] stat = arrayStats[i].split(":");
			res.put(stat[0], Integer.parseInt(stat[1]));
		}

		return res;
	}

}

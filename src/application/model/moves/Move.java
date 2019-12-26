package application.model.moves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final AttackType damageClass;
	private final Type type;
	private final int power;
	private final int pp;
	private final int priority;
	private final Target target;
	private final HashMap<String, Integer> statChange;
	private final String description;

	public Move(int id, String name, String moveCategory, int accuracy, MoveSideEffect effect, int effectChance,
			AttackType damageClass, Type type, int power, int pp, int priority, Target target, String statChange,
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
	
	public static Move generateFromMap(Map<String, List<String>> data) { // on ne gère juste les atatques pour l'instant (classe damage)
		
		if(data.get("move_category").get(0).equals("damage")) {
			int id = Integer.parseInt(data.get("id").get(0));
			String name = data.get("name").get(0);
			String moveCategory = data.get("move_category").get(0);
			int accuracy = Integer.parseInt(data.get("accuracy").get(0));
			MoveSideEffect effect = null;
			int effectChance = 0;
			AttackType damageClass = AttackType.getType(data.get("damage_class").get(0));
			Type type = Type.getTypeFromString(data.get("type").get(0));
			int power = Integer.parseInt(data.get("power").get(0));
			int pp = Integer.parseInt(data.get("pp").get(0));
			int priority = Integer.parseInt(data.get("priority").get(0));
			Target target = Target.getTargetFromString(data.get("target").get(0).replace("-", "")); // on remplace les "-" car ils ne sont pas autorisés dans les noms de constantes (Caractère spécial)
			String statChange = data.get("stat_changes").get(0);
			String description = data.get("description").get(0);
			return new Move(id, name, moveCategory, accuracy, effect, effectChance, damageClass, type, power, pp, priority, target, statChange, description);
		}
		return null;
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

package application.model.moves;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.pokemon.Pokemon;
import application.model.pokemon.Status;
import application.model.pokemon.Type;

public class Move implements Serializable{
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
	private final int maxPp;
	private int pp;
	private final int priority;
	private final Target target;
	private final HashMap<String, Integer> statChange;
	private final String description;

	public Move(int id, String name, String moveCategory, int accuracy, MoveSideEffect effect, int effectChance,
			AttackType damageClass, Type type, int power, int maxPp, int priority, Target target, String statChange,
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
		this.maxPp = maxPp;
		this.pp = maxPp;
		this.priority = priority;
		this.target = target;
		this.statChange = parseStatChange(statChange);
		this.description = description;
	}

	public static Move generateFromMap(Map<String, List<String>> data) { // on ne gere juste les atatques pour l'instant

		String moveCategory = data.get("move_category").get(0);
		if (moveCategory.equals("damage") || moveCategory.equals("damage+ailment") || moveCategory.equals("ailment")) {
			int id = Integer.parseInt(data.get("id").get(0));
			String name = data.get("name").get(0);
			int accuracy = 100;// si la prochaine instruction n'a pas de données
			if (!data.get("accuracy").get(0).equals(""))
				accuracy = Integer.parseInt(data.get("accuracy").get(0)); // peut renvoyer un string vide

			MoveSideEffect effect;
			int effectChance;

			if (moveCategory.equals("damage+ailment") || moveCategory.equals("ailment")) {
				try {
					Status status = Status.valueOf(data.get("effect_ailment").get(0).toUpperCase());
					effect = (p1, p2) -> {
						p1.setStatus(status);
					};
					effectChance = Integer.parseInt(data.get("effect_chance").get(0));
				} catch (Exception e) {
					System.err.println(e);
					return null; // Si le effect_ailment n'est pas connu ou pas encore programmé on ne rajoute
									// pas le move (exemple : Trap, Confusion, etc.)
				}

			} else {
				effect = null;
				effectChance = 0;
			}

			AttackType damageClass = AttackType.valueOf(data.get("damage_class").get(0).toUpperCase());
			Type type = Type.valueOf(data.get("type").get(0).toUpperCase());
			int power = Integer.parseInt(data.get("power").get(0));
			int maxPp = Integer.parseInt(data.get("pp").get(0));
			int pp = maxPp;
			int priority = Integer.parseInt(data.get("priority").get(0));

			// on remplace les "-" car ils ne sont pas autoris�s dans les noms de constantes
			// (Caract�re sp�cial)
			Target target = Target.valueOf(data.get("target").get(0).replace("-", "").toUpperCase());
			String statChange = data.get("stat_changes").get(0);
			String description = data.get("description").get(0);
			return new Move(id, name, moveCategory, accuracy, effect, effectChance, damageClass, type, power, maxPp,
					priority, target, statChange, description);
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public String getEffectToString() {
		return "none";
	}

	public int getMaxPP() {
		return maxPp;
	}
	
	public int getPP() {
		return pp;
	}

	public String getDescription() {
		return description;
	}

	public Type getType() {
		return type;
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

	public AttackResult use(Pokemon p, Pokemon p2) {
		if (p == null)
			throw new IllegalArgumentException("The pokemon can't be null");
		if (p2 == null)
			return AttackResult.MISSED;
		if (Math.random() * 100 <= accuracy) {
			int attack = (damageClass == AttackType.PHYSICAL) ? p.getCurrentStats().getAttack()
					: p.getCurrentStats().getSpecialAttack();
			int defense = (damageClass == AttackType.PHYSICAL) ? p2.getCurrentStats().getDefense()
					: p2.getCurrentStats().getSpecialDefense();
			double stab = (type == p.getType1() || type == p.getType2()) ? 1.5 : 1; // Le STAB est selon si le pokémon
																					// est du même type que l'attaque,
																					// sela donnes des dégats bonus
			
			double totalResistance = p2.getType1().resistanceAgain(type);
			totalResistance *= (p2.getType2() != null) ? p2.getType2().resistanceAgain(type) : 1;
			double randomMultiplicator = Math.random() * (1 - 0.85)+0.85;
			
			System.out.println("Attaque : "+attack+", defense : "+defense+", stab : "+stab+", resistance : "+totalResistance+ " randomMultiplicator : "+randomMultiplicator);

			int totalDamage = (int) ((((p.getLevel() * 0.4 + 2) * power * attack) / (defense * 50) + 2) * stab
					* totalResistance * randomMultiplicator);

			System.out.println(totalDamage + " ont été infligé");

			if (Math.random() * 100 <= effectChance) {
				System.out.println("L'effet a eu lieu en prime");
				effect.effect(p, p2);
			}
			
			p2.addHp(-totalDamage);
			pp --;

			if (totalResistance == 1)
				return AttackResult.SUCCEED;
			if (totalResistance > 1)
				return AttackResult.EFFECTIVE;
			if (totalResistance < 1)
				return AttackResult.NOTEFFECTIVE;
		}
		return AttackResult.MISSED;
	}
	
	
	@Override
	public String toString() {
		return id + " - " + name;
	}
	
}

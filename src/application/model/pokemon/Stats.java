package application.model.pokemon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the differents statistics of a pokemon and their boosts
 * @author Armadindon
 *
 */
public class Stats implements Serializable, Cloneable {

	private int speed;
	private int attack;
	private int specialAttack;
	private int defense;
	private int specialDefense;
	private int hp;

	private HashMap<String, Integer> boosts = new HashMap<>();

	/**
	 * Default constructor
	 * @param speed
	 * @param attack
	 * @param specialAttack
	 * @param defense
	 * @param specialDefense
	 * @param hp
	 */
	public Stats(int speed, int attack, int specialAttack, int defense, int specialDefense, int hp) {
		resetBoosts();
		this.speed = speed;
		this.attack = attack;
		this.specialAttack = specialAttack;
		this.defense = defense;
		this.specialDefense = specialDefense;
		this.hp = hp;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Stats stats = (Stats) super.clone();
		stats.resetBoosts();
		return stats;
	}

	/**
	 * Resets the boosts of the stats and initialize if its not the case
	 */
	public void resetBoosts() {
		String[] possibleBoost = { "attack", "special-attack", "defense", "special-defense", "speed", "accuracy", "evasion" };
		for (String string : possibleBoost) {
			boosts.put(string, 0);
		}
	}

	/**
	 * Boost the stats
	 * @param aBoosts - boost to add
	 */
	public void addBoosts(Map<String, Integer> aBoosts) {

		for (String lib : aBoosts.keySet()) {
			boosts.put(lib, boosts.get(lib) + aBoosts.get(lib));
			if (boosts.get(lib) > 6)
				boosts.put(lib, 6);
			if (boosts.get(lib) < -6)
				boosts.put(lib, -6);
		}
	}

	/**
	 * Permit to convert a level of boost to coefficient (levels go from -6 to 6)
	 * @param boost - boost to convert
	 * @return the converted coefficient to apply
	 */
	public double convertToDouble(int boost) {
		double[] values = { 1 / 3, 3 / 8, 3 / 7, 1 / 2, 3 / 5, 3 / 4, 1, 4 / 3, 5 / 3, 2, 7 / 3, 8 / 3, 3 };
		return values[boost + 6];
	}

	/**
	 * Add hp in stats
	 * @param hp - hp to add
	 */
	public void add(int hp) {
		if (this.hp + hp < 0)
			this.hp = 0;
		else
			this.hp += hp;
	}

	/**
	 * Return the current speed of the pokemon with boosts
	 * @return the speed with the boost added 
	 */
	public int getSpeed() {
		return (int) (speed * convertToDouble(boosts.get("speed")));
	}

	/**
	 * Return the current attack of the pokemon with boosts
	 * @return the attack with the boost added 
	 */
	public int getAttack() {
		return (int) (attack * convertToDouble(boosts.get("attack")));
	}

	/**
	 * Return the current special attack of the pokemon with boosts
	 * @return the special attack with the boost added 
	 */
	public int getSpecialAttack() {
		return (int) (specialAttack * convertToDouble(boosts.get("special-attack")));
	}

	/**
	 * Return the current defense of the pokemon with boosts
	 * @return the defense with the boost added 
	 */
	public int getDefense() {
		return (int) (defense * convertToDouble(boosts.get("defense")));
	}

	/**
	 * Return the current special defense of the pokemon with boosts
	 * @return the special defense with the boost added 
	 */
	public int getSpecialDefense() {
		return (int) (specialDefense * convertToDouble(boosts.get("special-defense")));
	}

	/**
	 * get the current hp of a pokemon
	 * @return the hp of pokemon
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * set the hp of a pokemon
	 * @param hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

}

package application.model.pokemon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Stats implements Serializable{
	
	private int speed;
	private int attack;
	private int specialAttack;
	private int defense;
	private int specialDefense;
	private int hp;
	
	private HashMap<String, Integer> boosts = new HashMap<>();
	
	public Stats(int speed, int attack, int specialAttack, int defense, int specialDefense, int hp) {
		resetBoosts();
		this.speed = speed;
		this.attack = attack;
		this.specialAttack = specialAttack;
		this.defense = defense;
		this.specialDefense = specialDefense;
		this.hp = hp;
	}
	
	public void resetBoosts() {
		String[] possibleBoost = {"attack","special-attack","defense","special-defense","speed","accuracy"};
		for (String string : possibleBoost) {
			boosts.put(string, 0);
		}
	}
	
	public void addBoosts(Map<String, Integer> aBoosts) {
		for (String lib : aBoosts.keySet()) {
			boosts.put(lib, boosts.get(lib)+aBoosts.get(lib));
			if(boosts.get(lib)>6) boosts.put(lib,6);
			if(boosts.get(lib)<-6) boosts.put(lib,-6);
		}
	}
	
	public double convertToDouble(int boost) {
		double[] values = {1/3,3/8,3/7,1/2,3/5,3/4,1,4/3,5/3,2,7/3,8/3,3};
		return values[boost+6];
	}
	
	public void add(int hp) {
		if(this.hp + hp <0) this.hp = 0;
		else this.hp += hp;
	}

	public int getSpeed() {
		return (int) (speed*convertToDouble(boosts.get("speed")));
	}

	public int getAttack() {
		return (int) (attack*convertToDouble(boosts.get("attack")));
	}

	public int getSpecialAttack() {
		return (int) (specialAttack*convertToDouble(boosts.get("special-attack")));
	}

	public int getDefense() {
		return (int) (defense*convertToDouble(boosts.get("defense")));
	}

	public int getSpecialDefense() {
		return (int) (specialDefense *convertToDouble( boosts.get("special-defense")));
	}

	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}

}

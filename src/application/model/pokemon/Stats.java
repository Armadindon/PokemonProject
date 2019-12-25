package application.model.pokemon;

public class Stats {
	
	private int speed;
	private int attack;
	private int specialAttack;
	private int defense;
	private int specialDefense;
	private int hp;
	
	public Stats(int speed, int attack, int specialAttack, int defense, int specialDefense, int hp) {
		super();
		this.speed = speed;
		this.attack = attack;
		this.specialAttack = specialAttack;
		this.defense = defense;
		this.specialDefense = specialDefense;
		this.hp = hp;
	}

	public int getSpeed() {
		return speed;
	}

	public int getAttack() {
		return attack;
	}

	public int getSpecialAttack() {
		return specialAttack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpecialDefense() {
		return specialDefense;
	}

	public int getHp() {
		return hp;
	}
	
	
	
	
	

}

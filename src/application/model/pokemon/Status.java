package application.model.pokemon;

import java.io.Serializable;

public enum Status implements Serializable{
	
	PARALYSIS(p -> {
		
		p.getCurrentStats()
	},p->{
		double choice = Math.random();
		if(choice<0.25) return false;
	}),
	FREEZE(),
	BURN(),
	POISON(),
	SLEEP();
	
	private StatusEffect effect;
	
	private Status(StatusEffect atSet,StatusEffect effect,StatusEffect healed) {
		this.effect = effect;
	}
	
	public boolean effect(Pokemon p) {
		return effect.use(p);
	}

}

package application.model.pokemon;

import java.util.HashMap;
import java.io.Serializable;
import java.lang.Math;

public enum Status implements Serializable{
	
	PARALYSIS(
			p->{
				HashMap<String, Integer> boost = new HashMap<>();
				boost.put("speed",-2);
				p.getCurrentStats().addBoosts(boost);
				return true;
			},p->{
				double random = Math.random();
				if(random < 0.25) return false;
				return true;
			},p->{
				HashMap<String, Integer> boost = new HashMap<>();
				boost.put("speed",2);
				p.getCurrentStats().addBoosts(boost);
				return true;
			}),
	FREEZE(p->{return true;},p->{
		double random = Math.random();
		if(random < 0.1){
			p.setStatus(null);
			return true;
		}	
		return false;},p->{return true;}),
	BURN(p->{
		HashMap<String, Integer> boost = new HashMap<>();
		boost.put("attack",-2);
		p.getCurrentStats().addBoosts(boost);
		return true;
	},p->{
		p.addHp((int) -p.getBaseStats().getHp()/16);
		return true;
	},p->{
		HashMap<String, Integer> boost = new HashMap<>();
		boost.put("attack",2);
		p.getCurrentStats().addBoosts(boost);
		return true;
	}),
	POISON(p->{return true;},
			p->{
				p.addHp((int) (-p.getBaseStats().getHp()/8));
				return true;
			},p->{return true;}),
	SLEEP(p->{return true;},p->{
		double random = Math.random();
		if(random < 0.1){
			p.setStatus(null);
			return true;
		}	
		return false;},p->{return true;});
	
	private StatusEffect whenReceived;
	private StatusEffect eachTurn;
	private StatusEffect whenCured;
	
	private Status(StatusEffect whenReceived, StatusEffect eachTurn, StatusEffect whenCured) {
		this.whenReceived = whenReceived;
		this.eachTurn = eachTurn;
		this.whenCured = whenCured;
	}
	
	public StatusEffect getWhenReceived() {
		return whenReceived;
	}
	
	public StatusEffect getEachTurn() {
		return eachTurn;
	}
	
	public StatusEffect getWhenCured() {
		return whenCured;
	}
	
}

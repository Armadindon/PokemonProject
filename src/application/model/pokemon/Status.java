package application.model.pokemon;

import java.io.Serializable;

public enum Status implements Serializable{
	
	PARALYSIS(p->{return true;},p->{return true;},p->{return true;}),
	FREEZE(p->{return false;},p->{return false;},p->{return false;}),
	BURN(p->{return false;},p->{return false;},p->{return false;}),
	POISON(p->{return false;},p->{return false;},p->{return false;}),
	SLEEP(p->{return false;},p->{return false;},p->{return false;});
	
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

package application.model.fight;

import java.io.Serializable;

public enum Action implements Serializable{
	
	MOVE(0),
	SWITCH(1),
	ITEM(1);
	
	private int priority;
	
	private Action(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}

}

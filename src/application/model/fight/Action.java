package application.model.fight;

import java.io.Serializable;

/**
 * Represent the different actions that a player can do, each action have a priority (Example : A Move occur after a Switch)
 * @author Armadindon
 *
 */
public enum Action implements Serializable{
	
	MOVE(0),
	SWITCH(1),
	ITEM(1);
	
	private int priority;
	
	private Action(int priority) {
		this.priority = priority;
	}
	
	/**
	 * Permit to have the priority of the selected action
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}

}

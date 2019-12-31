package application.model.fight;

public enum Action {
	
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

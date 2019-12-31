package application.model.fight;


public class Fight {
	
	private final Player firstPlayer;
	private final Player secondPlayer;
	
	
	public Fight(Player firstPlayer, Player secondPlayer) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
	}
	
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	
	public Player getSecondPlayer() {
		return firstPlayer;
	}
}

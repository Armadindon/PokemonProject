package application.model.utils;

import java.io.Serializable;

public enum MenuSelect implements Serializable{
	
	FIGHT("Fight.fxml"),
	INTERLEAGUE("LeagueIntermission.fxml"),
	MAINMENU("ChooseGame.fxml");
	
	private String file;
	
	private MenuSelect(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}

}

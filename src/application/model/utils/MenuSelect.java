package application.model.utils;

import java.io.Serializable;

/**
 * Represent the different page where we go when we load a Save
 * @author Armadindon
 */
public enum MenuSelect implements Serializable{
	
	FIGHT("Fight.fxml"),
	INTERLEAGUE("LeagueIntermission.fxml"),
	MAINMENU("ChooseGame.fxml");
	
	private String file;
	
	private MenuSelect(String file) {
		this.file = file;
	}
	
	/**
	 * Get the name of the fxml file to load
	 * @return name of the fxml file
	 */
	public String getFile() {
		return file;
	}

}

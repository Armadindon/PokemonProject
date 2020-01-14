package application.Controller.Utils;

import java.io.Serializable;

/**
 * Allow to give additional infos when changing infos between Controllers
 * @author Kwaaac
 *
 */
public enum SpecialData implements Serializable{
	HOMEMADE,
	POKEDEXVIEW,
	WIN,
	LOOSE,
	INTERMISSION;
}

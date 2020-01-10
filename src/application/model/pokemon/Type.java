package application.model.pokemon;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represent all the different types that a pokemon can have, and their resistances to other types
 * @author Armadindon
 *
 */
public enum Type implements Serializable{
	
	STEEL(0.5,2,0.5,1,1,0.5,2,0.5,0.5,0.5,0.5,0,0.5,0.5,2,1,1,0.5),
	FIGHTING(1,1,1,1,1,2,1,1,0.5,1,1,1,2,0.5,1,1,0.5,2),
	DRAGON(1,1,2,0.5,0.5,2,0.5,2,1,1,0.5,1,1,1,1,1,1,1),
	WATER(0.5,1,1,0.5,2,1,0.5,0.5,1,1,2,1,1,1,1,1,1,1),
	ELECTRIC(0.5,1,1,1,0.5,1,1,1,1,1,1,1,1,1,2,1,1,0.5),
	FAIRY(2,0.5,0,1,1,1,1,1,0.5,1,1,2,1,1,1,1,0.5,1),
	FIRE(0.5,1,1,2,1,0.5,0.5,0.5,0.5,1,0.5,1,1,2,2,1,1,1),
	ICE(2,2,1,1,1,1,2,0.5,1,1,1,1,1,2,1,1,1,1),
	BUG(1,0.5,1,1,1,1,2,1,1,1,0.5,1,1,2,0.5,1,1,2),
	NORMAL(1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1),
	GRASS(1,1,1,0.5,0.5,1,2,2,2,1,0.5,2,1,1,0.5,1,1,2),
	POISON(1,0.5,1,1,1,0.5,1,1,0.5,1,0.5,0.5,2,1,2,1,1,1),
	PSYCHIC(1,0.5,1,1,1,1,1,1,2,1,1,1,0.5,1,1,2,2,1),
	ROCK(2,2,1,2,1,1,0.5,1,1,0.5,2,0.5,1,1,2,1,1,0.5),
	GROUND(1,1,1,2,0,1,1,2,1,1,2,0.5,1,0.5,1,1,1,1),
	GHOST(1,0,1,1,1,1,1,1,0.5,0,1,0.5,1,1,1,2,2,1),
	DARK(1,2,1,1,1,2,1,1,2,1,1,1,0,1,1,0.5,0.5,1),
	FLYING(1,0.5,1,1,2,1,1,2,0.5,1,0.5,1,1,2,0,1,1,1);
	
	private static final class StaticList{ //Pour contourner l'erreur "Cannot refer to the static enum field within an initializer"
		//on fait une liste pour conserver l'ordre
		private static final String[] typeList = {"STEEL","FIGHTING","DRAGON","WATER","ELECTRIC","FAIRY","FIRE","ICE","BUG","NORMAL","GRASS","POISON","PSYCHIC","ROCK","GROUND","GHOST","DARK","FLYING"};
	}
	private final HashMap<String, Double> multiplicator;

	private Type(double... powerAgainst) {
		multiplicator = new HashMap<>();
		
		for (int i = 0; i < StaticList.typeList.length; i++) {
			multiplicator.put(StaticList.typeList[i],powerAgainst[i]);
		}
	}
	
	/**
	 * Return the coefficient of resistance to another type
	 * @param t - the type to test
	 * @return the coefficient of resistance
	 */
	public double resistanceAgain(Type t) {
		return multiplicator.get(t.name());
	}
	

}

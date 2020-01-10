package application.model.pokemon;

/**
 * Functional Interface representing the effect of a status on a pokemon
 * @author Armadindon
 *
 */
@FunctionalInterface
public interface StatusEffect {
	
	/**
	 * do the effect on the given pokemon
	 * @param p - pokemon targeted by the effect
	 * @return true if the pokemon can attack, false else
	 */
	public boolean use(Pokemon p);

}

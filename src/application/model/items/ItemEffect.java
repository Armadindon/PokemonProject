package application.model.items;

import application.model.pokemon.Pokemon;

/**
 * Functional Interface for representing an effect of an item on a Pokemon
 * @author perrin
 *
 */
@FunctionalInterface
public interface ItemEffect {
	
	/**
	 * The effect that will be executed on a pokemon
	 * @param p - pokemon who will receive the effect
	 */
	public void effect(Pokemon p);

}

package application.model.moves;

import java.io.Serializable;

import application.model.pokemon.Pokemon;

/**
 * Represent the second effect of a move 
 * @author Armadindon
 *
 */
@FunctionalInterface
public interface MoveSideEffect extends Serializable{
	
	/**
	 * The side effect of a move, apply on one of the two pokemon
	 * @param p1 - Pokemon using the move
	 * @param p2 - Pokemon targeted
	 */
	public void effect(Pokemon p1, Pokemon p2);

}

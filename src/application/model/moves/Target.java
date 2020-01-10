package application.model.moves;

import java.io.Serializable;

/**
 * Reprensent the target of a move
 * @author Armadindon
 *
 */
public enum Target implements Serializable{
	
	USER,
	SELECTEDPOKEMON,
	ALLOPPONENTS,
	RANDOMOPPONENT,
	ALLOTHERPOKEMON,
	SPECIFICMOVE,
	SELECTEDPOKEMONMEFIRST,
	ALLPOKEMON,
	ALLY,
	USERANDALLIES;
	

}

package application.model.moves;

import application.model.pokemon.Pokemon;

@FunctionalInterface
public interface MoveSideEffect {
	
	public void effect(Pokemon p1, Pokemon p2);

}

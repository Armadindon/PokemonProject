package application.model.moves;

import java.io.Serializable;

import application.model.pokemon.Pokemon;

@FunctionalInterface
public interface MoveSideEffect extends Serializable{
	
	public void effect(Pokemon p1, Pokemon p2);

}

package application.model.items;

import application.model.pokemon.Pokemon;

@FunctionalInterface
public interface ItemEffect {
	
	public void effect(Pokemon p);

}

package application.model.items;

import java.io.File;

import application.model.pokemon.Pokemon;

public interface Item {
	
	public void applyEffect(Pokemon p);
	public int getId();
	public String getName();
	public File getSprite();

}

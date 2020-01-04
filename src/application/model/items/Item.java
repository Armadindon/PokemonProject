package application.model.items;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.model.pokemon.Pokemon;

public interface Item {
	
	public void applyEffect(Pokemon p);
	public int getId();
	public String getName();
	public String getSprite();
	public static ArrayList<Item> fromMap(Map<String, List<String>> data){
		ArrayList<Item> possibleItems = new ArrayList<>();
		return possibleItems;
	}
}

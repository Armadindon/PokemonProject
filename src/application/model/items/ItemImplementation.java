package application.model.items;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.model.pokemon.Pokemon;

public abstract class ItemImplementation implements Item {
	
	private final int id;
	private final String name;
	private final String sprite;
	private ItemEffect effect;

	public ItemImplementation(int id, String name, String sprite, ItemEffect effect) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.effect = effect;
	}

	@Override
	public void applyEffect(Pokemon p) {
		effect.effect(p);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSprite() {
		return sprite;
	}
	



}

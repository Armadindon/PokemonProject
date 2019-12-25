package application.model.items;

import java.io.File;

import application.model.pokemon.Pokemon;

public abstract class ItemImplementation implements Item {
	
	private final int id;
	private final int name;
	private final File sprite;
	private ItemEffect effect;

	public ItemImplementation(int id, int name, File sprite, ItemEffect effect) {
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
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public File getSprite() {
		return null;
	}

}

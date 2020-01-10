package application.model.items;

import application.model.pokemon.Pokemon;

/**
 * Class for factorizing code about items
 * @author Armadindon
 */
public abstract class ItemImplementation implements Item {
	
	private final int id;
	private final String name;
	private final String sprite;
	private ItemEffect effect;
	
	/**
	 * Default constructor
	 * @param id
	 * @param name
	 * @param sprite
	 * @param effect
	 */
	public ItemImplementation(int id, String name, String sprite, ItemEffect effect) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.effect = effect;
	}
	
	/**
	 * Apply the effect on a pokemon
	 * @param p
	 */
	@Override
	public void applyEffect(Pokemon p) {
		effect.effect(p);
	}

	/**
	 * Get the id of the Item
	 * @return the id
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Get the name of the Item
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Get the the sprite path of the item
	 * @return the path of the sprite
	 */
	@Override
	public String getSprite() {
		return sprite;
	}
	
}

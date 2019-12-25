package application.model.items;

import java.io.File;

public class HealPotion extends ItemImplementation {
	
	private final int healAmount;
	

	public HealPotion(int id, int name, File sprite, ItemEffect effect, int healAmount) {
		super(id, name, sprite, effect);
		this.healAmount = healAmount;
	}

}

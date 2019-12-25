package application.model.items;

import java.io.File;

import application.model.pokemon.Status;

public class statusPotion extends ItemImplementation {
	
	private final Status curedStatus;

	public statusPotion(int id, int name, File sprite, ItemEffect effect,Status curedStatus) {
		super(id, name, sprite, effect);
		this.curedStatus = curedStatus;
	}
	



}

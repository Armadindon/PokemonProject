package application.model.items;

import java.io.File;

import application.model.pokemon.Status;

public class statusPotion extends ItemImplementation {
	
	private final Status curedStatus;

	public statusPotion(int id, String name, String sprite, Status curedStatus) {
		super(id, name, sprite, p-> {
			if(p.getStatus() == curedStatus) p.setStatus(null);
		});
		this.curedStatus = curedStatus;
	}
	



}

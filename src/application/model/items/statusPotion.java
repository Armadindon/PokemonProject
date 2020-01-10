package application.model.items;

import application.model.pokemon.Status;

/**
 * Item for healing status
 * @author Armadindon
 *
 */
public class statusPotion extends ItemImplementation {
	
	private final Status curedStatus;

	/**
	 * Default constructor
	 * @param id
	 * @param name
	 * @param sprite
	 * @param curedStatus - Status healed by the potion
	 */
	public statusPotion(int id, String name, String sprite, Status curedStatus) {
		super(id, name, sprite, p-> {
			if(p.getStatus() == curedStatus) p.setStatus(null);
		});
		this.curedStatus = curedStatus;
	}
	



}

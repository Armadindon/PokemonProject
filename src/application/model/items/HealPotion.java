package application.model.items;

/**
 * Represent a HealPotion
 * @author Armadindon
 *
 */
public class HealPotion extends ItemImplementation {
		
	
	/**
	 * Default Constructor
	 * @param id
	 * @param name
	 * @param sprite
	 * @param effect
	 * @param healAmount
	 */
	public HealPotion(int id, String name, String sprite, ItemEffect effect, int healAmount) {
		super(id, name, sprite, p->{
			if(p.isAlive()) p.addHp(healAmount);
		});
	}

}

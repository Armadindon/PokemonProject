package application.model.items;

import java.io.File;

public class HealPotion extends ItemImplementation {
	
	private final int healAmount;
	

	public HealPotion(int id, String name, String sprite, ItemEffect effect, int healAmount) {
		super(id, name, sprite, p->{
			if(p.isAlive()) p.addHp(healAmount);
		});
		this.healAmount = healAmount;
	}

}

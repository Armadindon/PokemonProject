package application.modele;

import java.util.EnumSet;
import java.util.HashMap;

public enum Type {
	
	Steel(0.5,2,0.5,1,1,0.5,2,0.5,0.5,0.5,0.5,0,0.5,0.5,2,1,1,0.5),
	Fighting(1,1,1,1,1,2,1,1,0.5,1,1,1,2,0.5,1,1,0.5,2),
	Dragon(1,1,2,0.5,0.5,2,0.5,2,1,1,0.5,1,1,1,1,1,1,1),
	Water(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),//TODO : Faire le tableau a partir de la
	Electric(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Fairy(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Fire(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Ice(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Bug(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Normal(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Grass(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Poison(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Psychic(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Rock(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Ground(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Ghost(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Dark(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
	Flying(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
	
	private HashMap<Type, Double> multiplicator;

	private Type(double... powerAgainst) {
		multiplicator = new HashMap<>();
		
		Object[] types = EnumSet.allOf(Type.class).toArray();
		for(int i = 0; i < types.length; i++) {
			multiplicator.put((Type) types[i] , powerAgainst[i]);
		}
	}
	


}

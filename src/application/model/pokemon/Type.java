package application.model.pokemon;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;

public enum Type {
	
	STEEL(0.5,2,0.5,1,1,0.5,2,0.5,0.5,0.5,0.5,0,0.5,0.5,2,1,1,0.5),
	FIGHTING(1,1,1,1,1,2,1,1,0.5,1,1,1,2,0.5,1,1,0.5,2),
	DRAGON(1,1,2,0.5,0.5,2,0.5,2,1,1,0.5,1,1,1,1,1,1,1),
	WATER(0.5,1,1,0.5,2,1,0.5,0.5,1,1,2,1,1,1,1,1,1,1),
	ELECTRIC(0.5,1,1,1,0.5,1,1,1,1,1,1,1,1,1,2,1,1,0.5),
	FAIRY(2,0.5,0,1,1,1,1,1,0.5,1,1,2,1,1,1,1,0.5,1),
	FIRE(0.5,1,1,2,1,0.5,0.5,0.5,0.5,1,0.5,1,1,2,2,1,1,1),
	ICE(2,2,1,1,1,1,2,0.5,1,1,1,1,1,2,1,1,1,1),
	BUG(1,0.5,1,1,1,1,2,1,1,1,0.5,1,1,2,0.5,1,1,2),
	NORMAL(1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1),
	GRASS(1,1,1,0.5,0.5,1,2,2,2,1,0.5,2,1,1,0.5,1,1,2),
	POISON(1,0.5,1,1,1,0.5,1,1,0.5,1,0.5,0.5,2,1,2,1,1,1),
	PSYCHIC(1,0.5,1,1,1,1,1,1,2,1,1,1,0.5,1,1,2,2,1),
	ROCK(2,2,1,2,1,1,0.5,1,1,0.5,2,0.5,1,1,2,1,1,0.5),
	GROUND(1,1,1,2,0,1,1,2,1,1,2,0.5,1,0.5,1,1,1,1),
	GHOST(1,0,1,1,1,1,1,1,0.5,0,1,0.5,1,1,1,2,2,1),
	DARK(1,2,1,1,1,2,1,1,2,1,1,1,0,1,1,0.5,0.5,1),
	FLYING(1,0.5,1,1,2,1,1,2,0.5,1,0.5,1,1,2,0,1,1,1);
	
	private EnumMap<Type, Double> multiplicator;

	private Type(double... powerAgainst) {
		multiplicator = new EnumMap<>(Type.class);
		
		Object[] types = EnumSet.allOf(Type.class).toArray();
		for(int i = 0; i < types.length; i++) {
			multiplicator.put((Type) types[i] , powerAgainst[i]);
		}
	}
	
	public static Type getTypeFromString(String typeString) {
		for(Type type : Type.class.getEnumConstants()){
			if(type.name().equals(typeString.toUpperCase())) return type;
		}
		return null;
	}
	


}

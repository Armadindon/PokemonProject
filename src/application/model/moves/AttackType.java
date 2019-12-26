package application.model.moves;

import java.util.EnumSet;

public enum AttackType {
	
	PHYSICAL,
	SPECIAL;
	
	
	public static AttackType getType(String typeString) {
		
		for(AttackType type : AttackType.class.getEnumConstants()) {
			if(type.name().equals(typeString.toUpperCase())) return type;
		}
		
		return null;
	}

}

package application.model.moves;

public enum Target {
	
	USER,
	SELECTEDPOKEMON,
	ALLOPONENTS;
	
	
	public static Target getTargetFromString(String targetString) {
		for(Target t : Target.class.getEnumConstants()) {
			if(t.name().equals(targetString.toUpperCase())) return t;
		}
		return null;
	}

}

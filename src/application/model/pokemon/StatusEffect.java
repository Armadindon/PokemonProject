package application.model.pokemon;

@FunctionalInterface
public interface StatusEffect {
	
	public boolean use(Pokemon p);

}

package application.model.appmodel;

import application.model.pokemon.Pokemon;
import application.model.pokemon.Stats;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pokedex {

	/**
	 * Show the pokemon's infos on the pokedex
	 * 
	 * @param pokemon
	 * @param name
	 * @param type1
	 * @param type2
	 * @param height
	 * @param weigth
	 * @param hp
	 * @param atk
	 * @param atkSpe
	 * @param def
	 * @param defSpe
	 * @param speed
	 * @param img
	 * @param description
	 */
	public void showPokemon(Pokemon pokemon, Label name, Label type1, Label type2, Label height, Label weight, Label hp,
			Label atk, Label atkSpe, Label def, Label defSpe, Label speed, ImageView img, TextArea description) {


		name.setText(pokemon.getName());
		type1.setText("EAU");
		type2.setText("PLANTE");
		height.setText(pokemon.getHeight() + "");
		weight.setText(pokemon.getWeight() + "");

		Stats pokeStats = pokemon.getBaseStats();

		hp.setText(pokeStats.getHp() + "");
		atk.setText(pokeStats.getAttack() + "");
		atkSpe.setText(pokeStats.getSpecialAttack() + "");
		def.setText(pokeStats.getDefense() + "");
		defSpe.setText(pokeStats.getSpecialDefense() + "");
		speed.setText(pokeStats.getSpeed() + "");
		
		img.setImage((Image) pokemon.getFrontSprite());

	}

}

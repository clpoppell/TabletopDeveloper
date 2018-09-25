package tabletop_5e_character_design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class Weapon{
	private final static int GROUP= "Group: ".length();
	private final static int TYPE= "Type: ".length();
	private final static int RANGE= "Range: ".length();
	private final static int PRICE= "Price: ".length();
	private final static int DAMAGE= "Damage: ".length();
	private final static int DAMAGE_TYPE= "DamageType: ".length();
	private final static int WEIGHT= "Weight: ".length();
	private final static int TAGS= "Tags: ".length();
	
	private final static String NOTFOUND= "NOT FOUND";
	
	@Getter String name;
	@Getter String price;
	@Getter double weight;
	@Getter String group;
	@Getter String type;
	@Getter String range;
	@Getter String damage;
	@Getter String damageType;
	@Getter List<String> tags;
	
	/**
	 * Creates a new Weapon using the provided array and name. If strings in {@code weaponFeatures}
	 * are too short or the array's size is too small, default weapon is created.
	 * @param name name of Weapon
	 * @param weaponFeatures array of specifically formatted strings
	 */
	public Weapon(String name, String[] weaponFeatures){
		try{
			this.name= name;
			price= weaponFeatures[4].substring(PRICE);
			group= weaponFeatures[1].substring(GROUP);
			type= weaponFeatures[2].substring(TYPE);
			range= weaponFeatures[3].substring(RANGE);
			damage= weaponFeatures[5].substring(DAMAGE);
			damageType= weaponFeatures[6].substring(DAMAGE_TYPE);
			weight= Double.parseDouble(weaponFeatures[7].substring(WEIGHT));
			tags= Arrays.asList(weaponFeatures[8].substring(TAGS).split(", "));
		}
		catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NumberFormatException e){
			this.name= name;
			price= NOTFOUND;
			group= NOTFOUND;
			type= NOTFOUND;
			range= NOTFOUND;
			damage= "0d0";
			damageType= NOTFOUND;
			weight= 0.0;
			tags= new ArrayList<>();
			tags.add(NOTFOUND);
		}
	}
	
	/**
	 * Returns true if {@code type} is "Ranged" or if {@code tags} includes "Versatile".
	 * @return {@code true} if this should use a {@link PlayerCharacter#dexMod}
	 * 		   {@code false} otherwise
	 */
	public boolean usesDex(){ return "Ranged".equalsIgnoreCase(type) || tags.contains("Versatile"); }
}
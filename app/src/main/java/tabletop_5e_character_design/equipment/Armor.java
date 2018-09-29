package tabletop_5e_character_design.equipment;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Armor{
	private final static int TYPE= "Type: ".length();
	private final static int RANGE= "Range: ".length();
	private final static int PRICE= "Price: ".length();
	private final static int AC_FORMULA= "".length();
	private final static int STEALTH_DISADVANTAGE= "Damage: ".length();
	private final static int STR_REQUIRED= "DamageType: ".length();
	private final static int WEIGHT= "Weight: ".length();
	private final static int TAGS= "Tags: ".length();
	
	private final static String NOTFOUND= "NOT FOUND";
	
	private String name;
	private String price;
	private String type;
	private String acFormula;
	private boolean stealthDisadvantage;
	private int strRequired;
	private double weight;
	
	/**
	 * Creates a new Armor using the provided array and name. If strings in {@code armorFeatures}
	 * are too short or the array's size is too small, default armor is created.
	 * @param name name of Armor
	 * @param armorFeatures array of specifically formatted strings
	 */
	public Armor(String name, String[] armorFeatures){
		try{
			this.name= name;
			type= armorFeatures[1].substring(TYPE);
			price= armorFeatures[2].substring(PRICE);
			acFormula= armorFeatures[3].substring(AC_FORMULA);
			strRequired= Integer.parseInt(armorFeatures[4].substring(STR_REQUIRED));
			stealthDisadvantage= "y".equalsIgnoreCase(armorFeatures[5].substring(STEALTH_DISADVANTAGE));
			weight= Double.parseDouble(armorFeatures[6].substring(WEIGHT));
		}
		catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NumberFormatException e){
			this.name= name;
			type= NOTFOUND;
			price= NOTFOUND;
			acFormula= NOTFOUND;
			strRequired= 0;
			stealthDisadvantage= true;
			weight= 0.0;
		}
	}
}

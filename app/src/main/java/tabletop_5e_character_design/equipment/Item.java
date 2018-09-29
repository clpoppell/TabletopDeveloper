package tabletop_5e_character_design.equipment;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Item{
	private final static int PRICE= "Price: ".length();
	private final static int WEIGHT= "Weight: ".length();
	
	private final static String NOTFOUND= "NOT FOUND";
	
	private String name;
	private String price;
	private double weight;
	
	/**
	 * Creates a new Item using the provided array and name. If strings in {@code ItemFeatures}
	 * are too short or the array's size is too small, default item is created.
	 * @param name name of Item
	 * @param itemFeatures array of specifically formatted strings
	 */
	public Item(String name, String[] itemFeatures){
		try{
			this.name= name;
			price= itemFeatures[1].substring(PRICE);
			weight= Double.parseDouble(itemFeatures[2].substring(WEIGHT).trim());
		}
		catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NumberFormatException e){
			this.name= name;
			price=NOTFOUND;
			weight=0.0;
		}
	}
}

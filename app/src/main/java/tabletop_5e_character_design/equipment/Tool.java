package tabletop_5e_character_design.equipment;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Tool{
	private final static int TYPE= "Type: ".length();
	private final static int PRICE= "Price: ".length();
	private final static int WEIGHT= "Weight: ".length();
	
	private final static String NOTFOUND= "NOT FOUND";
	
	private String name;
	private String type;
	private String price;
	private double weight;
	
	public Tool(String name, String[] toolFeatures){
		try{
			this.name= name;
			type= toolFeatures[1].substring(TYPE);
			price= toolFeatures[2].substring(PRICE);
			weight= Double.parseDouble(toolFeatures[3].substring(WEIGHT).trim());
		}
		catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException | NumberFormatException e){
			this.name= name;
			type= NOTFOUND;
			price= NOTFOUND;
			weight= 0.0;
		}
	}
}

package tabletop_5e_character_design;

public class ArmorEquipment extends Equipment{
	public final String type;
	public final String acFormula;
	public final int strRequired;
	public boolean stealthDisadvantage;
	
	public ArmorEquipment(String name, String type, String price, String acFormula, int strRequired, boolean stealthDisadvantage, double weight){
		super(name, price, weight);
		
		this.type= type;
		this.acFormula= acFormula;
		this.strRequired= strRequired;
		this.stealthDisadvantage= stealthDisadvantage;
	}
}

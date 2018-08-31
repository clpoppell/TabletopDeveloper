package tabletop_5e_character_design;

public class CharacterAttack{
	final String attackName;
	final int attackRange;
	final String attackArea;
	final String attackDamage;
	final String attackDamageType;
	final Boolean attackDC;
	final String statUsed;
	final String attackType;
	final String restRegain;
	
	public CharacterAttack(String name, int range, String area, String damage, String damageType, Boolean dc, String stat, String type){
		attackName= name;
		attackRange= range;
		attackArea= area;
		attackDamage= damage;
		attackDamageType= damageType;
		attackDC= dc;
		statUsed= stat;
		attackType= type;
		restRegain= null;
	}
	
	//Used by CharacterRace constructor for racial attacks
	public CharacterAttack(String[] s){
		attackName= s[0];
		attackRange= Integer.parseInt(s[1]);
		attackArea= s[2];
		attackDamage= s[3];
		attackDamageType= s[4];
		attackDC= s[5].substring(s[5].indexOf(":")).equals("yes");
		statUsed= s[6];
		
		if(attackArea.equals("melee")){ attackType= "unarmed strike"; }
		else{ attackType= null; }
		
		if(s.length == 8){ restRegain= s[7]; }
		else{ restRegain= null; }
	}
	
	public String toString(){
		String s= attackName + ": " + attackDamage;
		
		if(!attackDC) s += statUsed;
		s += " " + attackDamageType.toLowerCase() + " damage,\n\t";
		
		if(attackDC) s += attackArea.substring(0, 1).toUpperCase() + attackArea.substring(1) + ", " + attackRange + " ft. reach";
		else{ s += attackRange + " ft. " + attackArea + ", Saving Throw DC " + statUsed; }
		return s;
	}
}

package tabletop_5e_character_design;

import lombok.Getter;

@Getter
public class CharacterAttack{
	private String attackName;
	private int attackRange;
	private String attackArea;
	private String attackDamage;
	private String attackDamageType;
	private boolean attackDC;
	private String statUsed;
	private String attackType;
	private String restRegain;
	
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
	CharacterAttack(String[] s){
		attackName= s[0];
		attackRange= Integer.parseInt(s[1]);
		attackArea= s[2];
		attackDamage= s[3];
		attackDamageType= s[4];
		attackDC= s[5].substring(s[5].indexOf(":")).equals("yes");
		statUsed= s[6];
		
		attackType=attackArea.equals("melee") ? "unarmed strike" : null;
		
		restRegain=s.length == 8 ? s[7] : null;
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

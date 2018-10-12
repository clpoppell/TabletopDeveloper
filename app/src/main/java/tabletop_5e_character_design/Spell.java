package tabletop_5e_character_design;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true) @Getter
public class Spell{
	private static int NAME= "Name: ".length();
	private static final int LEVEL= "Level: ".length();
	private static int SCHOOL= "School: ".length();
	private static int ACTION= "Action: ".length();
	private static int COMPONENTS= "Components: ".length();
	private static int CONCENTRATION= "Concentration: ".length();
	private static int DESC= "Desc: ".length();
	
	@EqualsAndHashCode.Include private String name;
	private int level;
	
	public Spell(String[] spellInfo){
		name= spellInfo[0].substring(NAME);
		level= Integer.parseInt(spellInfo[1].substring(LEVEL));
	}
	
	Spell(String name, int level){
		this.name= name;
		this.level= level;
	}
}

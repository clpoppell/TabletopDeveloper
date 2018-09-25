package tabletop_5e_character_design;

import lombok.Getter;

@Getter
public class Spell{
	private String spellName;
	private int spellLevel;
	
	Spell(String name, int level){
		spellName= name;
		spellLevel= level;
	}
}

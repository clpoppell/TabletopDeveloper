package tabletop_5e_character_design;

import java.util.ArrayList;

import tabletop_5e_character_design.CharacterSpell;

public class RacialSpellList{
	final public String restRegain;
	private final ArrayList<CharacterSpell> spellList;
	
	RacialSpellList(String restRegain, ArrayList<CharacterSpell> spells){
		this.restRegain= restRegain;
		spellList= spells;
	}
	
	public ArrayList<CharacterSpell> getSpellList(){ return spellList; }
}

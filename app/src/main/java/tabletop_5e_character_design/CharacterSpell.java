package tabletop_5e_character_design;

import com.tadbolmont.tabletopdeveloper.Spell;

public class CharacterSpell extends Spell{
	final public String statUsed;
	final public int levelGained;
	
	public CharacterSpell(String stat, String name, int level){
		super(name, level);
		
		statUsed= stat;
		levelGained= 0;
	}
	
	public CharacterSpell(String stat, String name, int level, int levelGained){
		super(name, level);
		
		statUsed= stat;
		this.levelGained= levelGained;
	}
	
	public CharacterSpell(String[] cantripInfo){
		super(cantripInfo[1], 0);
		
		statUsed= cantripInfo[0].substring(0, 3);
		levelGained= 0;
	}
}

package tabletop_5e_character_design;

import lombok.Getter;

@Getter
public class CharacterSpell extends Spell{
	private String statUsed;
	private int levelGained;
	
	CharacterSpell(String stat, String name, int level){
		super(name, level);
		
		statUsed= stat;
		levelGained= 1;
	}
	
	CharacterSpell(String stat, String name, int level, int levelGained){
		super(name, level);
		
		statUsed= stat;
		this.levelGained= levelGained;
	}
	
	CharacterSpell(String[] cantripInfo){
		super(cantripInfo[1], 0);
		
		statUsed= cantripInfo[0].substring(0, 3);
		levelGained= 1;
	}
}

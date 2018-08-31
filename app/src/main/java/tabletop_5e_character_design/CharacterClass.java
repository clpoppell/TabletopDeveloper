package tabletop_5e_character_design;

import com.google.common.base.Joiner;
import com.tadbolmont.tabletopdeveloper.GameInfo;

public class CharacterClass{
	final String name;
	final ClassSkillList classSkillChoices;
	final ClassEquipmentList classEquipmentChoices;
	
	public CharacterClass(String[] equipment){
		name= equipment[0].trim();
		classSkillChoices= null; //classSkillChoices;
		classEquipmentChoices= GameInfo.getClassEquipmentList(name);
	}
	
	public String toString(){
		String s= name;
		s += "\n" + classEquipmentChoices.pack;
		s += "\n" + Joiner.on("\n").skipNulls().join(classEquipmentChoices.getWeaponEquipment());
		s += "\n" + classEquipmentChoices.tool;
		return s;
	}
}

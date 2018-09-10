package tabletop_5e_character_design;

import android.util.SparseArray;

import com.google.common.base.Joiner;
import com.tadbolmont.tabletopdeveloper.GameInfo;

public class CharacterClass{
	private static final int NAME= "Name: ".length();
	private static final int HIT_DICE= "HitDice: ".length();
	private static final int ARMOR_PROFICIENCIES= "ArmorProficiencies: ".length();
	private static final int WEAPON_PROFICIENCIES= "WeaponProficiencies: ".length();
	private static final int TOOL_PROFICIENCIES= "ToolProficiencies: ".length();
	private static final int SAVING_THROWS= "SavingThrows: ".length();
	private static final int SKILL_PROFICIENCIES= "SkillProficiencies: ".length();
	private static final int ASI_LEVELS= "ASI: ".length();
	
	private final String name;
	private final String hitDice;
	private final String[] armorProficiencies;
	private final String[] weaponProficiencies;
	private final String[] toolProficiencies;
	private final String[] savingThrows;
	private final ClassSkillList classSkillChoices;
	private final int[] asiLevels;
	private final ClassEquipmentList classEquipmentChoices;
	private int level= 0;
	private SparseArray<String[]> features= new SparseArray<>();
	
	public CharacterClass(String[] classInfo){
		name= classInfo[0].substring(NAME).trim();
		hitDice= classInfo[1].substring(HIT_DICE);
		armorProficiencies= classInfo[2].substring(ARMOR_PROFICIENCIES).split(", ");
		weaponProficiencies= classInfo[3].substring(WEAPON_PROFICIENCIES).split(", ");
		toolProficiencies= classInfo[4].substring(TOOL_PROFICIENCIES).split(", ");
		savingThrows= classInfo[5].substring(SAVING_THROWS).split(", ");
		
		String[] skillStrings= classInfo[6].substring(SKILL_PROFICIENCIES).split(" - ");
		classSkillChoices= new ClassSkillList(Integer.parseInt(skillStrings[0]), skillStrings[1].split(", "));
		
		String[] asiLevelsString= classInfo[7].substring(ASI_LEVELS).split(", ");
		asiLevels= new int[asiLevelsString.length];
		for(int i=0; i < asiLevels.length; i++){ asiLevels[i]= Integer.parseInt(asiLevelsString[i]); }
		
		classEquipmentChoices= GameInfo.getClassEquipmentList(name);
		
		String[] featureStrings= classInfo[9].split(" -");
		for(String level : featureStrings){
			String[] levelStrings= level.split(": ");
			int key= Integer.parseInt(levelStrings[0]);
			String[] value= levelStrings[1].split(", ");
			features.put(key, value);
		}
		level= 0;
	}
	
	public CharacterClass(CharacterClass charClass){
		name= charClass.name;
		hitDice= charClass.hitDice;
		armorProficiencies= charClass.armorProficiencies;
		weaponProficiencies= charClass.weaponProficiencies;
		toolProficiencies= charClass.toolProficiencies;
		savingThrows= charClass.savingThrows;
		classSkillChoices= charClass.classSkillChoices;
		asiLevels= charClass.asiLevels;
		classEquipmentChoices= charClass.classEquipmentChoices;
		level= 1;
		features= charClass.features;
	}
	
	public void incrementLevel(){ level++; }
	
	public String getHitDice(){ return hitDice; }
	
	public String[] getArmorProficiencies(){ return armorProficiencies; }
	
	public String[] getWeaponProficiencies(){ return weaponProficiencies; }
	
	public String[] getToolProficiencies(){ return toolProficiencies; }
	
	public String[] getSavingThrows(){ return savingThrows; }
	
	public ClassSkillList getClassSkillChoices(){ return classSkillChoices; }
	
	public int[] getAsiLevels(){ return asiLevels; }
	
	public ClassEquipmentList getClassEquipmentChoices(){ return classEquipmentChoices; }
	
	public String[] getFeatureListForLevel(){ return features.get(level); }
	
	public String toString(){
		String s= name;
		s += "\n" + getClassEquipmentChoices().pack;
		s += "\n" + Joiner.on("\n").skipNulls().join(getClassEquipmentChoices().getWeaponEquipment());
		s += "\n" + getClassEquipmentChoices().tool;
		return s;
	}
}

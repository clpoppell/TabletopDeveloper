package tabletop_5e_character_design;

import android.util.SparseArray;

import com.tadbolmont.tabletopdeveloper.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(onlyExplicitlyIncluded= true) @Getter
public class CharacterClass{
	private static final int NAME= "Name: ".length();
	private static final int HIT_DICE= "HitDice: ".length();
	private static final int ARMOR_PROFICIENCIES= "ArmorProficiencies: ".length();
	private static final int WEAPON_PROFICIENCIES= "WeaponProficiencies: ".length();
	private static final int TOOL_PROFICIENCIES= "ToolProficiencies: ".length();
	private static final int SAVING_THROWS= "SavingThrows: ".length();
	private static final int SKILL_PROFICIENCIES= "SkillProficiencies: ".length();
	private static final int ASI_LEVELS= "ASI: ".length();
	private static final int ARCHETYPES= "Archetypes: ".length();
	
	@EqualsAndHashCode.Include private String name;
	private int hitDice;
	private String[] armorProficiencies;
	private String[] weaponProficiencies;
	private String[] toolProficiencies;
	private String[] savingThrows;
	private ClassSkillList classSkillChoices;
	private ClassEquipmentList classEquipmentChoices;
	private int level;
	private String[] archetypes;
	@Setter private Archetype archetypeChoice= null;
	private SparseArray<String[]> features= new SparseArray<>();
	
	public CharacterClass(String[] classInfo){
		name= classInfo[0].substring(NAME).trim();
		hitDice= Integer.parseInt(classInfo[1].substring(HIT_DICE));
		armorProficiencies= classInfo[2].substring(ARMOR_PROFICIENCIES).split(", ");
		weaponProficiencies= classInfo[3].substring(WEAPON_PROFICIENCIES).split(", ");
		toolProficiencies= classInfo[4].substring(TOOL_PROFICIENCIES).split(", ");
		savingThrows= classInfo[5].substring(SAVING_THROWS).split(", ");
		
		String[] skillStrings= classInfo[6].substring(SKILL_PROFICIENCIES).split(" - ");
		classSkillChoices= new ClassSkillList(Integer.parseInt(skillStrings[0]), skillStrings[1].split(", "));
		
		archetypes= classInfo[7].substring(ARCHETYPES).split(", ");
		
		classEquipmentChoices= GameInfo.getClassEquipmentList(name);
		
		String[] featureStrings= classInfo[9].split(" - ");
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
		classEquipmentChoices= charClass.classEquipmentChoices;
		level= 0;
		archetypes= charClass.archetypes;
		features= charClass.features;
	}
	
	public void incrementLevel(){ level++; }
	
	public List<String> getFeatureListForLevel(int lvl){ return archetypeChoice == null ? new ArrayList<>(Arrays.asList(features.get(lvl))) : archetypeChoice.getFeatureListForLevel(lvl); }
	
	@Override
	public String toString(){ return name + " " + level; }
}

package com.tadbolmont.tabletopdeveloper;

import android.content.res.Resources;
import android.util.SparseArray;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tabletop_5e_character_design.Archetype;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.CharacterRace;
import tabletop_5e_character_design.CharacterSpell;
import tabletop_5e_character_design.ClassEquipmentList;
import tabletop_5e_character_design.Spell;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.equipment.Armor;
import tabletop_5e_character_design.equipment.Tool;
import tabletop_5e_character_design.equipment.Weapon;

@SuppressWarnings("ConstantConditions")
public final class GameInfo{
	private static final Resources RES= App.context.getResources();
	private static final int NAME= "Name: ".length();
	
	private static final Map<String, CharacterRace> RACE_INFO= createRaceInfo();
	private static final Map<String, String> SKILL_MAP= createSkillInfo();
	private static final Map<String, String> FEAT_INFO= createFeatInfo();
	
	private static final Map<String, ClassEquipmentList> CLASS_EQUIPMENT_LISTS= createClassEquipmentLists();
	private static final Map<String, CharacterClass> CLASS_INFO= createClassInfo();
	private static final Map<String, Archetype> ARCHETYPE_INFO= createArchetypeInfo();
	private static final Map<String, ClassFeature> CLASS_FEATURE_INFO= createClassFeatureInfo();
	
	private static final Map<String, String[]> EQUIPMENT_PACKS= createEquipmentPacks();
	private static final Map<String, Weapon> WEAPON_INFO= createWeaponInfo();
	private static final Map<String, Armor> ARMOR_INFO= createArmorInfo();
	private static final Map<String, Tool> TOOL_INFO= createToolInfo();
	
	private static final Map<String, SparseArray<String[]>> SPELL_LISTS= createSpellLists();
	private static final Map<String, Spell> SPELL_INFO= createSpellInfo();
	
	public enum armorState{ NoArmor, LightArmor, MedArmor, HeavyArmor }
	public enum damageType{ Acid, Bludgeoning, Cold, Fire, Force, Lightning, Necrotic, Piercing, Poison, Psychic, Radiant, Slashing, Thunder, NOTFOUND }
	public enum status{ Blinded, Charmed, Deafened, Frightened, Grappled, Incapacitated, Invisible, Paralyzed, Petrified, Poisoned, Prone, Restrained, Stunned, Unconscious, NOTFOUND }
	
	static String formatNumbersPlusMinus(int n){
		NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
		return plusMinusNF.format(n);
	}
	
	//region Create List Methods
	private static Map<String, CharacterRace> createRaceInfo(){
		Map<String, CharacterRace> races= new HashMap<>();
		String[] raceDesc= RES.getStringArray(R.array.race_info);
		for(String charRace : raceDesc){
			String[] raceFeatures= charRace.split(" / ");
			CharacterRace race= new CharacterRace(raceFeatures);
			races.put(raceFeatures[0].trim(), race);
		}
		return races;
	}
	
	private static Map<String, String> createSkillInfo(){
		Map<String, String> skills= new TreeMap<>();
		String[] skillStrings= RES.getStringArray(R.array.skill_list);
		for(String skill : skillStrings){
			String[] s= skill.split(" - ");
			String key= s[0];
			String value= s[1];
			skills.put(key, value);
		}
		return skills;
	}
	
	private static Map<String, String> createFeatInfo(){
		
		return null;
	}
	
	private static Map<String, ClassEquipmentList> createClassEquipmentLists(){
		Map<String, ClassEquipmentList> equipMap= new HashMap<>();
		String[] equipLists= RES.getStringArray(R.array.class_equipment_lists);
		for(String classEquipString : equipLists){
			String[] equipment= classEquipString.split(" / ");
			equipMap.put(equipment[0], new ClassEquipmentList(equipment));
		}
		return equipMap;
	}
	
	private static Map<String, CharacterClass> createClassInfo(){
		Map<String, CharacterClass> classMap= new HashMap<>();
		String[] classLists= RES.getStringArray(R.array.class_info);
		for(String charClassString : classLists){
			String[] classFeatures= charClassString.split(" / ");
			CharacterClass classInfo= new CharacterClass(classFeatures);
			classMap.put(classFeatures[0].substring(6), classInfo);
		}
		return classMap;
	}
	
	private static Map<String,Archetype> createArchetypeInfo(){
		Map<String, Archetype> archetypeMap= new HashMap<>();
		String[] archetypeLists= RES.getStringArray(R.array.archetype_info);
		for(String archetypeString : archetypeLists){
			String[] archetypeFeatures= archetypeString.split(" / ");
			Archetype archetype= new Archetype(archetypeFeatures);
			archetypeMap.put(archetype.getName(), archetype);
		}
		return archetypeMap;
	}
	
	private static Map<String, ClassFeature> createClassFeatureInfo(){
		Map<String, ClassFeature> features= new HashMap<>();
		String[] featureStrings= RES.getStringArray(R.array.class_features);
		for(String feature : featureStrings){
			String[] f= feature.split(" / ");
			String key= f[0].substring(6);
			features.put(key, ClassFeature.getClassFeature(f));
		}
		return features;
	}
	
	private static Map<String,String[]> createEquipmentPacks(){
		final int ITEMS= "Items: ".length();
		
		Map<String, String[]> packs= new TreeMap<>();
		String[] packList= RES.getStringArray(R.array.equipment_pack_lists);
		for(String packString : packList){
			String[] pack= packString.split(" # ");
			packs.put(pack[0].substring(NAME), pack[1].substring(ITEMS).split(", "));
		}
		return packs;
	}
	
	private static Map<String, Weapon> createWeaponInfo(){
		Map<String, Weapon> weapons= new LinkedHashMap<>();
		String[] weaponStrings= RES.getStringArray(R.array.weapon_info);
		for(String weapon : weaponStrings){
			String[] w= weapon.split(" # ");
			String name= w[0].substring(NAME);
			weapons.put(name, new Weapon(name, w));
		}
		return weapons;
	}
	
	private static Map<String, Armor> createArmorInfo(){
		Map<String, Armor> armors= new LinkedHashMap<>();
		String[] armorStrings= RES.getStringArray(R.array.armor_info);
		for(String armor : armorStrings){
			String[] a= armor.split(" # ");
			String name= a[0].substring(NAME);
			armors.put(name, new Armor(name, a));
		}
		return armors;
	}
	
	private static Map<String, Tool> createToolInfo(){
		Map<String, Tool> tools= new LinkedHashMap<>();
		String[] toolStrings= RES.getStringArray(R.array.tool_info);
		for(String tool : toolStrings){
			String[] t= tool.split(" # ");
			String name= t[0].substring(NAME);
			tools.put(name, new Tool(name, t));
		}
		return tools;
	}
	
	private static Map<String, SparseArray<String[]>> createSpellLists(){
		Map<String, SparseArray<String[]>> spellLists= new LinkedHashMap<>();
		String[] listStrings = RES.getStringArray(R.array.spell_lists);
		for(String list : listStrings){
			String[] l= list.split(" - ");
			String[] keys= l[0].split(" ");
			if(spellLists.containsKey(keys[0])){
				spellLists.get(keys[0]).put(Integer.parseInt(keys[1]), l[1].split(", "));
			}
			else{
				SparseArray<String[]> value= new SparseArray<>();
				value.put(Integer.parseInt(keys[1]), l[1].split(", "));
				spellLists.put(keys[0], value);
			}
		}
		return spellLists;
	}
	
	private static Map<String, Spell> createSpellInfo(){
		Map<String, Spell> spells= new LinkedHashMap<>();
		
		List<String> spellStrings= new ArrayList<>(Arrays.asList(RES.getStringArray(R.array.cantrips)));
		spellStrings.addAll(Arrays.asList(RES.getStringArray(R.array.spell_info_level_1)));
		spellStrings.addAll(Arrays.asList(RES.getStringArray(R.array.spell_info_level_2)));
		spellStrings.addAll(Arrays.asList(RES.getStringArray(R.array.spell_info_level_3)));
		
		for(String spell : spellStrings){
			String[] s= spell.split(" # ");
			String key= s[0].substring(NAME);
			spells.put(key, new Spell(s));
		}
		return spells;
	}
	//endregion
	
	//region List Accessors
	public static CharacterRace getRace(String key){ return RACE_INFO.get(key.trim()); }
	
	public static String getSkillStat(String key){ return SKILL_MAP.get(key.trim()); }
	
	public static Map<String, String> getSkillMap(){ return SKILL_MAP; }
	
	public static String getFeatDesc(String key){ return FEAT_INFO.get(key.trim()); }
	
	public static CharacterClass getClass(String key){ return new CharacterClass(CLASS_INFO.get(key.trim())); }
	
	public static Archetype getArchetype(String key){ return new Archetype(ARCHETYPE_INFO.get(key.trim())); }
	
	public static ClassEquipmentList getClassEquipmentList(String className){ return CLASS_EQUIPMENT_LISTS.get(className.trim()); }
	
	public static ClassFeature getClassFeature(String featureName){ return CLASS_FEATURE_INFO.get(featureName.trim()).copy(); }
	
	public static String[] getSpellList(String className, int level){
		if("Any".equalsIgnoreCase(className.trim())){
			Set<String> list= new LinkedHashSet<>();
			for(String cName : SPELL_LISTS.keySet()){
				List<String> spells= Arrays.asList(SPELL_LISTS.get(cName).get(level));
				list.addAll(spells);
			}
			return list.toArray(new String[list.size()]);
		}
		return SPELL_LISTS.get(className.trim()).get(level);
	}
	
	public static Spell getSpell(String spellName){ return SPELL_INFO.get(spellName.trim()); }
	
	public static CharacterSpell makeCharacterSpell(String name, String stat, String className){ return new CharacterSpell(stat, className, getSpell(name)); }
	
	public static String[] getEquipmentPack(String key){ return EQUIPMENT_PACKS.get(key); }
	
	public static ArrayList<String> getWeaponsByType(String group, String type, boolean isProfCheck){
		ArrayList<String> weaponNames= new ArrayList<>();
		for(String w: WEAPON_INFO.keySet()){
			Weapon weapon= WEAPON_INFO.get(w);
			if(!isProfCheck && "Unarmed Strike".equalsIgnoreCase(weapon.getName())){ continue; }
			if(weapon.getGroup().equalsIgnoreCase(group)){
				if(weapon.getType().equalsIgnoreCase(type) || "any".equalsIgnoreCase(type)){
					weaponNames.add(w);
				}
			}
		}
		return weaponNames;
	}
	
	public static Armor getArmor(String key){ return ARMOR_INFO.get(key.trim()); }
	
	public static Weapon getWeapon(String key){ return WEAPON_INFO.get(key.trim()); }
	
	public static Tool getTool(String key){ return TOOL_INFO.get(key.trim()); }
	//endregion
}

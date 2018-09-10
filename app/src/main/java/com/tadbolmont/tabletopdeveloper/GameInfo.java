package com.tadbolmont.tabletopdeveloper;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.CharacterRace;
import tabletop_5e_character_design.ClassEquipmentList;
import tabletop_5e_character_design.class_features.ClassFeature;

public final class GameInfo{
	private static final Resources RES= App.getContext().getResources();
	
	private static final Map<String, CharacterRace> RACE_INFO= createRaceInfo();
	private static final Map<String, String> SKILL_MAP= createSkillInfo();
	private static final Map<String, String> FEAT_INFO= createFeatInfo();
	private static final Map<String, ClassEquipmentList> CLASS_EQUIPMENT_LISTS= createClassEquipmentLists();
	private static final Map<String, CharacterClass> CLASS_INFO= createClassInfo();
	private static final Map<String, String[]> WEAPON_INFO= createWeaponInfo();
	private static final Map<String, ClassFeature> CLASS_FEATURE_INFO= createClassFeatureInfo();
	
	private static final Map<String, String[]> SPELL_INFO= createSpellInfo();
	private static final Map<String, String[]> SPELL_LISTS= createSpellLists();
	
	public enum armorState{ NoArmor, LightArmor, MedArmor, HeavyArmor }
	public enum damageType{ Bludgeoning, Piercing, Slashing }
	
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
	
	private static Map<String, CharacterClass> createClassInfo(){
		Map<String, CharacterClass> classMap= new HashMap<>();
		String[] equipLists= RES.getStringArray(R.array.class_info);
		for(String charClassString : equipLists){
			String[] classFeatures= charClassString.split(" / ");
			CharacterClass classInfo= new CharacterClass(classFeatures);
			classMap.put(classFeatures[0].substring(6), classInfo);
		}
		return classMap;
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
	
	private static Map<String, String[]> createWeaponInfo(){
		Map<String, String[]> weapons= new LinkedHashMap<>();
		String[] weaponStrings= RES.getStringArray(R.array.weapon_list);
		for(String weapon : weaponStrings){
			String[] w= weapon.split(" # ");
			String key= w[0];
			weapons.put(key, w);
		}
		return weapons;
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
	
	private static Map<String, String[]> createSpellInfo(){
		Map<String, String[]> spells= new LinkedHashMap<>();
		String[] spellStrings= RES.getStringArray(R.array.cantrips);
		for(String spell : spellStrings){
			String[] s= spell.split(" # ");
			String key= s[0];
			spells.put(key, s);
		}
		return spells;
	}
	
	private static Map<String, String[]> createSpellLists(){
		Map<String, String[]> spellLists= new LinkedHashMap<>();
		String[] listStrings = RES.getStringArray(R.array.spell_lists);
		for(String list : listStrings){
			String[] l= list.split(" - ");
			String key= l[0];
			String[] value= l[1].split(", ");
			spellLists.put(key, value);
		}
		return spellLists;
	}
	
	//region List Accessor Methods
	public static CharacterRace getRace(String key){ return RACE_INFO.get(key.trim()); }
	
	public static String getSkillStat(String key){ return SKILL_MAP.get(key.trim()); }
	
	public static Map<String, String> getSkillMap(){ return SKILL_MAP; }
	
	public static String getFeatDesc(String key){ return FEAT_INFO.get(key.trim()); }
	
	public static CharacterClass getClass(String key){ return CLASS_INFO.get(key.trim()); }
	
	public static ClassEquipmentList getClassEquipmentList(String key){ return CLASS_EQUIPMENT_LISTS.get(key.trim()); }
	
	public static ClassFeature getClassFeature(String key){ return ClassFeature.getClassFeature(CLASS_FEATURE_INFO.get(key.trim())); }
	
	public static String[] getSpellList(String key){ return SPELL_LISTS.get(key.trim()); }
	
	public static String[] getMartialWeapons(){
		List<String> martialWeaponNames= new ArrayList<>();
		for(String w: WEAPON_INFO.keySet()){
			if(WEAPON_INFO.get(w)[1].equals("Martial")){ martialWeaponNames.add(w); }
		}
		return martialWeaponNames.toArray(new String[martialWeaponNames.size()]);
	}
	
	//endregion
}

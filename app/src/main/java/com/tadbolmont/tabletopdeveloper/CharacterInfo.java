package com.tadbolmont.tabletopdeveloper;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class CharacterInfo{
	private static final Resources RES= App.getContext().getResources();
	
	private static final Map<String, CharacterRace> RACE_INFO= createRaceInfo();
	private static final Map<String, String> SKILL_MAP= createSkillInfo();
	private static final Map<String, String> FEAT_INFO= createFeatInfo();
	private static final Map<String, String[]> WEAPON_INFO= createWeaponInfo();
	
	
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
	
	private static Map<String,String[]> createWeaponInfo(){
		Map<String, String[]> weapons= new LinkedHashMap<>();
		String[] weaponStrings= RES.getStringArray(R.array.weapon_list);
		for(String weapon : weaponStrings){
			String[] w= weapon.split(" # ");
			String key= w[0];
			String[] value= w;
			weapons.put(key, value);
		}
		return weapons;
	}
	
	//region List Accessor Methods
	public static CharacterRace getRace(String key){ return RACE_INFO.get(key.trim()); }
	
	public static String getSkillStat(String key){ return SKILL_MAP.get(key.trim()); }
	
	public static Map<String, String> getSkillMap(){ return SKILL_MAP; }
	
	public static String getFeatDesc(String key){ return FEAT_INFO.get(key.trim()); }
	
	public static String[] getMartialWeapons(){
		List<String> martialWeaponNames= new ArrayList<>();
		for(String w: WEAPON_INFO.keySet()){
			if(WEAPON_INFO.get(w)[1].equals("Martial")){ martialWeaponNames.add(w); }
		}
		return martialWeaponNames.toArray(new String[martialWeaponNames.size()]);
	}
	
	//endregion
}

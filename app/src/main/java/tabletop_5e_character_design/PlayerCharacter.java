package tabletop_5e_character_design;

import com.tadbolmont.tabletopdeveloper.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tabletop_5e_character_design.class_features.ClassFeature;

public class PlayerCharacter{
	private static PlayerCharacter playerCharacter= null;
	
	private String name;
	private CharacterRace charRace;
	
	private int proficiencyBonus= 2;
	private int hitPoints;
	
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	private int strMod;
	private int dexMod;
	private int conMod;
	private int intMod;
	private int wisMod;
	private int chaMod;
	
	private List<String> savingThrowProficiencies= new ArrayList<>();
	private int strSaving;
	private int dexSaving;
	private int conSaving;
	private int intSaving;
	private int wisSaving;
	private int chaSaving;
	
	private List<CharacterClass> classList= new ArrayList<>();
	private List<String> armorProficiencies= new ArrayList<>();
	private List<String> weaponProficiencies= new ArrayList<>();
	private List<String> toolProficiencies= new ArrayList<>();
	private Map<String, ClassFeature> classFeatures= new LinkedHashMap<>();
	private List<String> rituals= new ArrayList<>();
	
	private PlayerCharacter(String name, CharacterRace charRace, int str, int dex, int con, int intel, int wis, int cha, CharacterClass charClass){
		this.name= name;
		this.charRace= charRace;
		
		Map<String, Integer> statMods= charRace.getStatMods();
		
		strength= str;
		if(statMods.containsKey("Str")){
			int strRacialMod= statMods.get("Str");
			strength += strRacialMod;
		}
		dexterity= dex;
		if(statMods.containsKey("Dex")){
			int dexRacialMod= statMods.get("Dex");
			dexterity += dexRacialMod;
		}
		constitution= con;
		if(statMods.containsKey("Con")){
			int conRacialMod= statMods.get("Con");
			constitution += conRacialMod;
		}
		intelligence= intel;
		if(statMods.containsKey("Int")){
			int intRacialMod= statMods.get("Int");
			intelligence += intRacialMod;
		}
		wisdom= wis;
		if(statMods.containsKey("Wis")){
			int wisRacialMod= statMods.get("Wis");
			wisdom += wisRacialMod;
		}
		charisma= cha;
		if(statMods.containsKey("Cha")){
			int chaRacialMod= statMods.get("Cha");
			charisma += chaRacialMod;
		}
		
		strMod= calculateMod(strength);
		dexMod= calculateMod(dexterity);
		conMod= calculateMod(constitution);
		intMod= calculateMod(intelligence);
		wisMod= calculateMod(wisdom);
		chaMod= calculateMod(charisma);
		
		classList.add(charClass);
		addClassFeatures(0);
		
		if(charRace.getRacialArmorTraining() != null){ armorProficiencies.addAll(Arrays.asList(charRace.getRacialArmorTraining())); }
		for(String armor : charClass.getArmorProficiencies()){
			if(!armorProficiencies.contains(armor)){ armorProficiencies.add(armor); }
		}
		if(charRace.getRacialWeaponTraining() != null){ weaponProficiencies.addAll(Arrays.asList(charRace.getRacialWeaponTraining())); }
		for(String weapon : charClass.getWeaponProficiencies()){
			if(!weaponProficiencies.contains(weapon)){ weaponProficiencies.add(weapon); }
		}
		String[] temp= charRace.getRacialToolTraining();
		if(temp != null){
			toolProficiencies.addAll(Arrays.asList(charRace.getRacialToolTraining())); }
		for(String tool : charClass.getToolProficiencies()){
			if(!toolProficiencies.contains(tool)){
				toolProficiencies.add(tool); }
		}
		
		String[] classSavingThrows= charClass.getSavingThrows();
		savingThrowProficiencies.addAll(Arrays.asList(classSavingThrows));
		strSaving= strMod;
		if(savingThrowProficiencies.contains("Str")){ strSaving += proficiencyBonus; }
		dexSaving= dexMod;
		if(savingThrowProficiencies.contains("Dex")){ dexSaving += proficiencyBonus; }
		conSaving= conMod;
		if(savingThrowProficiencies.contains("Con")){ conSaving += proficiencyBonus; }
		intSaving= intMod;
		if(savingThrowProficiencies.contains("Int")){ intSaving += proficiencyBonus; }
		wisSaving= wisMod;
		if(savingThrowProficiencies.contains("Wis")){ wisSaving += proficiencyBonus; }
		chaSaving= chaMod;
		if(savingThrowProficiencies.contains("Cha")){ chaSaving += proficiencyBonus; }
	}
	
	public static PlayerCharacter getPlayerCharacter(){ return playerCharacter; }
	
	public static void makePlayerCharacter(String name, CharacterRace charRace, int str, int dex, int con, int intel, int wis, int cha, CharacterClass charClass){
		playerCharacter= new PlayerCharacter(name, charRace, str, dex, con, intel, wis, cha, charClass);
	}
	
	private int calculateMod(int n){
		double base= 10.0;
		double dividend= n - base;
		double divisor= 2.0;
		double quotient= dividend/divisor;
		return (int)Math.floor(quotient);
	}
	
	private void addClassFeatures(int i){
		CharacterClass charClass= classList.get(i);
		String[] featureList= charClass.getFeatureListForLevel();
		for(String feature : featureList){
			ClassFeature featureToAdd= GameInfo.getClassFeature(feature);
			if(featureToAdd == null){}
			else if(!"none".equalsIgnoreCase(featureToAdd.parentFeature)){ classFeatures.get(featureToAdd.parentFeature).addChildFeature(featureToAdd); }
			else{ classFeatures.put(featureToAdd.name, featureToAdd); }
		}
	}
	
	//region Accessors
	public String getName(){ return name; }
	
	public CharacterRace getCharRace(){ return charRace; }
	
	public int getStrength(){ return strength; }
	
	public int getDexterity(){ return dexterity; }
	
	public int getConstitution(){ return constitution; }
	
	public int getIntelligence(){ return intelligence; }
	
	public int getWisdom(){ return wisdom; }
	
	public int getCharisma(){ return charisma; }
	
	public int getStrMod(){ return strMod; }
	
	public int getDexMod(){ return dexMod; }
	
	public int getConMod(){ return conMod; }
	
	public int getIntMod(){ return intMod; }
	
	public int getWisMod(){ return wisMod; }
	
	public int getChaMod(){ return chaMod; }
	
	public int getStrSaving(){ return strSaving; }
	
	public int getDexSaving(){ return dexSaving; }
	
	public int getConSaving(){ return conSaving; }
	
	public int getIntSaving(){ return intSaving; }
	
	public int getWisSaving(){ return wisSaving; }
	
	public int getChaSaving(){ return chaSaving; }
	//endregion
}

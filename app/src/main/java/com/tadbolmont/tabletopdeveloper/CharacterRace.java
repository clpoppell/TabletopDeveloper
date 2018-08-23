package com.tadbolmont.tabletopdeveloper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterRace{
	private String name;
	private String[] statMods;
	private int[] ageRange;
	private String racialSize;
	private int racialSpeed;
	private String[] racialLanguages;
	private int darkvisionRange;
	private String additionalMovementTypes= null;
	private String[] racialSkills= null;
	private String racialFeats= null;
	private String racialAttacks= null;
	private String[] racialCantrips= null;
	private String[] racialSpells= null;
	private String racialDamageResistances= null;
	private String racialDefenses= null;
	private String[] racialWeaponTraining= null;
	private String racialToolTraining= null;
	private String racialConditionalExpertise= null;
	private String buildSize= null;
	private int naturalArmor= 10;
	private int reachMod= 0;
	private ArrayList<String> plainTextFeatures= new ArrayList<>();
	
	public CharacterRace(String[] raceFeatures){
		name= raceFeatures[0].trim();
		statMods= raceFeatures[1].substring(5).split(", ");
		String[] ageStrings= raceFeatures[2].substring(5).split("-");
		ageRange= new int[] {Integer.parseInt(ageStrings[0].trim()), Integer.parseInt(ageStrings[1].trim())};
		racialSize= raceFeatures[3].substring(5).trim();
		racialSpeed= Integer.parseInt(raceFeatures[4].substring(6).trim());
		racialLanguages= raceFeatures[5].substring(11).split(", ");
		darkvisionRange= Integer.parseInt(raceFeatures[6].substring(11).trim());
		
		buildSize= racialSize;
		
		for(int i=7; i<raceFeatures.length; i++){
			String[] feature= raceFeatures[i].split(": ", 2);
			switch(feature[0]){
				case "Additional Movement Types":
					additionalMovementTypes= feature[1];
					break;
				case "Skill Proficiencies":
					racialSkills= feature[1].split("; ");
					break;
				case "Feats":
					racialFeats= feature[1];
					break;
				case "Attacks":
					racialAttacks= feature[1];
					break;
				case "Cantrips":
					racialCantrips= feature[1].split(", ");
					break;
				case "Spells":
					racialSpells= feature[1].split(", ");
					break;
				case "Damage Resistances":
					racialDamageResistances= feature[1];
					break;
				case "Advantages/Immunities":
					racialDefenses= feature[1];
					break;
				case "Weapon Proficiencies":
					racialWeaponTraining= feature[1].split(", ");
					break;
				case "Tool Proficiencies":
					racialToolTraining= feature[1];
					break;
				case "Conditional Expertise":
					racialConditionalExpertise= feature[1];
					break;
				case "Powerful Build":
					buildSize= feature[1];
					break;
				case "Natural Armor":
					naturalArmor= Integer.parseInt(feature[1].trim());
					break;
				case "Reach":
					reachMod= Integer.parseInt(feature[1].trim());
					break;
				case "Plain Text":
					plainTextFeatures.add(feature[1]);
					break;
			}
		}
		int o= 0;
	}
	
	public CharacterRace(){}

	//region Selection Checks
	public Boolean hasASIChoice(){ return statMods[1].split(" ")[1].trim().equals("Choice"); }

	public static Boolean isSkillSelection(String skill){ return skill.contains("("); }
	
	public Boolean hasFeatChoice(){ return !(racialFeats == null); }
	
	public Boolean hasWeaponChoice(){
		for(String s : racialWeaponTraining){
			if(s.split(" ")[0].equals("Choice")){ return true; }
		}
		return false;
	}
	//endregion
	
	//region Accessors
	public String getName(){ return name; }
	
	public Map<String, Integer> getStatMods(){
		Map<String, Integer> statModMap= new LinkedHashMap<>();
		for(String stat : statMods){
			String[] statModSplit= stat.split(" ");
			statModMap.put(statModSplit[1], Integer.parseInt(statModSplit[0].trim()));
		}
		return statModMap;
	}
	
	public String[] getRacialSkills(){
		if(racialSkills != null){
			String[] skills= new String[racialSkills.length];
			for(int i= 0; i < racialSkills.length; i++){
				if(isSkillSelection(racialSkills[i])){
					int openIndex=racialSkills[i].indexOf("(");
					int closeIndex=racialSkills[i].indexOf(")");
					skills[i]= racialSkills[i].substring(openIndex, closeIndex + 1);
				}
				else{ skills[i]= racialSkills[i]; }
			}
			return skills;
		}
		return null;
	}
	
	public String[] getRacialWeaponTraining(){ return racialWeaponTraining; }
	
	public String[] getRacialLanguages(){ return racialLanguages; }
	//endregion
	
	@Override
	public String toString(){
		String s= ageRange[0] + "-" + ageRange[1] + "\n";
		s += racialSize + "\n";
		s += racialSpeed + "\n";
		s += "Darkvision: " + darkvisionRange + "\n\n";
		
		if(additionalMovementTypes != null){ s += additionalMovementTypes + "\n"; }
		if(racialFeats != null){ s += racialFeats + "\n"; }
		if(racialAttacks != null){ s += racialAttacks + "\n"; }
		if(racialCantrips != null){
			s += "Racial Cantrips: ";
			for(String cantrip : racialCantrips){ s += cantrip + "\n"; }
		}
		if(racialSpells != null){
			s += "Racial Spells: ";
			for(String spell : racialSpells){ s += spell; }
			s += ", once per " + " rest." + "\n";
		}
		if(racialDamageResistances != null){ s += racialDamageResistances + "\n"; }
		if(racialDefenses != null){ s += racialDefenses + "\n"; }
		if(racialToolTraining != null){ s += racialToolTraining + "\n"; }
		if(racialConditionalExpertise != null){ s += racialConditionalExpertise + "\n"; }
		if(!plainTextFeatures.isEmpty()){
			for(String feature : plainTextFeatures){ s += feature + "\n"; }
		}
		
		return s;
	}
}
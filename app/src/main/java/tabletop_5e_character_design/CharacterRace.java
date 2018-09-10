package tabletop_5e_character_design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterRace{
	//region Fields
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
	private CharacterAttack racialAttack= null;
	private CharacterSpell racialCantrip= null;
	private RacialSpellList racialSpellList= null;
	private ArrayList<DamageResistance> racialDamageResistances= null;
	private ArrayList<ConditionDefense> racialDefenses= null;
	private String[] racialWeaponTraining= null;
	private String[] racialArmorTraining= null;
	private String[] racialToolTraining= null;
	private String racialConditionalExpertise= null;
	private String buildSize= null;
	private int naturalArmor= 10;
	private int reachMod= 0;
	private ArrayList<String> plainTextFeatures= new ArrayList<>();
	//endregion
	
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
					racialAttack= new CharacterAttack(feature[1].split(", "));
					break;
				case "Cantrips":
					racialCantrip= new CharacterSpell(feature[1].split(" - "));
					break;
				case "Spells":
					racialSpellList= createRacialSpellList(feature[1].split(", "));
					break;
				case "Damage Resistances":
					racialDamageResistances= new ArrayList<>();
					String[] resistanceStrings= feature[1].split(", ");
					for(String s : resistanceStrings){ racialDamageResistances.add(new DamageResistance(s.split(" "))); }
					break;
				case "Advantages/Immunities":
					racialDefenses= new ArrayList<>();
					String[] defenseStrings= feature[1].split(", ");
					for(String s : defenseStrings){ racialDefenses.add(new ConditionDefense(s.split(" "))); }
					break;
				case "Weapon Proficiencies":
					racialWeaponTraining= feature[1].split(", ");
					break;
				case "Armor Proficiencies":
					racialArmorTraining= feature[1].split(", ");
					break;
				case "Tool Proficiencies":
					racialToolTraining= feature[1].split(", ");
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
	}
	
	public CharacterRace(CharacterRace raceInfo, String asi1, String asi2, String skill1, String skill2, String feat, String language, String cantrip, String tool, String weapon1, String weapon2){
		name= raceInfo.name;
		statMods= new String[raceInfo.statMods.length];
		for(int i= 0; i < statMods.length; i++){
			statMods[i]= raceInfo.statMods[i];
		}
		if("+1 Choice".equalsIgnoreCase(statMods[0])){
			statMods[0]= "+1 " + asi1;
			statMods[1]= "+1 " + asi2;
		}
		ageRange= raceInfo.ageRange;
		racialSize= raceInfo.racialSize;
		racialSpeed= raceInfo.racialSpeed;
		racialLanguages= new String[raceInfo.racialLanguages.length];
		for(int i= 0; i < racialLanguages.length; i++){
			if("Choice".equalsIgnoreCase(raceInfo.racialLanguages[i])){ racialLanguages[i]= language; }
			else{ racialLanguages[i]= raceInfo.racialLanguages[i]; }
		}
		darkvisionRange= raceInfo.darkvisionRange;
		additionalMovementTypes= raceInfo.additionalMovementTypes;
		if(raceInfo.racialSkills != null){
			racialSkills= new String[raceInfo.racialSkills.length];
			for(int i= 0; i < racialSkills.length; i++){
				racialSkills[i]= raceInfo.racialSkills[i];
			}
			if("Choice".equalsIgnoreCase(racialSkills[0].substring(0, 6))){
				racialSkills[0]= skill1;
				if(racialSkills.length > 1 && "Choice".equalsIgnoreCase(racialSkills[1].substring(0, 6))){
					racialSkills[1]=skill2;
				}
			}
		}
		if(raceInfo.racialFeats != null && "Choice".equalsIgnoreCase(raceInfo.racialFeats)){ racialFeats= feat; }
		racialAttack= raceInfo.racialAttack;
		if(raceInfo.racialCantrip != null && "Choice".equalsIgnoreCase(raceInfo.racialCantrip.spellName.substring(0,6))){ racialCantrip= new CharacterSpell(raceInfo.racialCantrip.statUsed, cantrip, 0); }
		racialSpellList= raceInfo.racialSpellList;
		racialDamageResistances= raceInfo.racialDamageResistances;
		racialDefenses= raceInfo.racialDefenses;
		if(raceInfo.racialWeaponTraining != null){
			racialWeaponTraining= new String[raceInfo.racialWeaponTraining.length];
			for(int i= 0; i < racialWeaponTraining.length; i++){
				racialWeaponTraining[i]= raceInfo.racialWeaponTraining[i];
			}
			if("Choice".equalsIgnoreCase(racialWeaponTraining[0].substring(0, 6))){
				racialWeaponTraining[0]=weapon1;
				racialWeaponTraining[1]=weapon2;
			}
		}
		racialArmorTraining= raceInfo.racialArmorTraining;
		if(racialToolTraining != null){
			racialToolTraining=new String[raceInfo.racialToolTraining.length];
			for(int i= 0; i < racialToolTraining.length; i++){
				racialToolTraining[i]= raceInfo.racialToolTraining[i];
			}
			if("Choice".equalsIgnoreCase(racialToolTraining[0].substring(0,6))){ racialToolTraining[0]= tool; }
		}
		racialConditionalExpertise= raceInfo.racialConditionalExpertise;
		buildSize= raceInfo.buildSize;
		naturalArmor= raceInfo.naturalArmor;
		reachMod= raceInfo.reachMod;
		plainTextFeatures= raceInfo.plainTextFeatures;
	}
	
	private RacialSpellList createRacialSpellList(String[] spells){
		ArrayList<CharacterSpell> spellList= new ArrayList<>();
		String[] generalSpellInfo= spells[0].split(" ");
		String spellStat= generalSpellInfo[0].trim().substring(1, 4);
		String spellRest= generalSpellInfo[1].trim().substring(1, 3);
		for(int i= 1; i < spells.length; i++){
			String[] spellInfo= spells[i].split("-");
			String spellName= spellInfo[0];
			int spellLevel= 1;
			int levelGained= Integer.parseInt(spellInfo[1].trim());
			spellList.add(new CharacterSpell(spellStat, spellName, spellLevel, levelGained));
		}
		return new RacialSpellList(spellRest, spellList);
	}

	//region Selection Checks
	public Boolean hasASIChoice(){ return statMods[1].split(" ")[1].trim().equals("Choice"); }

	public static Boolean isSkillSelection(String skill){ return skill.contains("("); }
	
	public Boolean hasFeatChoice(){ return !(racialFeats == null); }
	
	public boolean hasCantripChoice(){ return racialCantrip.spellName.contains("("); }
	
	public Boolean hasWeaponChoice(){
		for(String s : racialWeaponTraining){
			if(s.split(" ")[0].equals("Choice")){ return true; }
		}
		return false;
	}
	
	public static Boolean isToolChoice(String tool){ return tool.contains("("); }
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
	
	public int[] getAgeRange(){ return Arrays.copyOf(ageRange, ageRange.length); }
	
	public String getSize(){ return racialSize; }
	
	public int getSpeed(){ return racialSpeed; }
	
	public int getDarkvisionRange(){ return darkvisionRange; }
	
	public String getAdditionalMovementTypes(){ return additionalMovementTypes; }
	
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
	
	public CharacterAttack getRacialAttack(){ return racialAttack; }
	
	public String[] getRacialWeaponTraining(){ return racialWeaponTraining; }
	
	public String[] getRacialArmorTraining(){ return racialArmorTraining; }
	
	public String[] getRacialToolTraining(){ return racialToolTraining; }
	
	public CharacterSpell getRacialCantrip(){ return racialCantrip; }
	
	public RacialSpellList getRacialSpellList(){ return racialSpellList; }
	
	public ArrayList<DamageResistance> getRacialDamageResistances(){
		if(racialDamageResistances != null){ return new ArrayList<>(racialDamageResistances); }
		return null;
	}
	
	public ArrayList<ConditionDefense> getRacialDefenses(){
		if(racialDefenses != null){ return new ArrayList<>(racialDefenses); }
		return null;
	}
	
	public String getRacialConditionalExpertise(){ return racialConditionalExpertise; }
	
	public ArrayList<String> getPlainTextFeatures(){
		if(plainTextFeatures != null){ return new ArrayList<>(plainTextFeatures); }
		return null;
	}
	
	public String[] getRacialLanguages(){ return Arrays.copyOf(racialLanguages, racialLanguages.length); }
	//endregion
	
	@Override
	public String toString(){
		String s= ageRange[0] + "-" + ageRange[1] + "\n";
		if(racialConditionalExpertise != null) s+=racialConditionalExpertise + "\n";
		
		return s;
	}
}
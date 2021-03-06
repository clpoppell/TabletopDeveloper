package tabletop_5e_character_design;

import com.tadbolmont.tabletopdeveloper.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import lombok.Getter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.ElementGainClassFeature;
import tabletop_5e_character_design.class_features.IncreaseClassFeature;
import tabletop_5e_character_design.class_features.ValueSetClassFeature;

@Getter
public class PlayerCharacter{
	private static PlayerCharacter playerCharacter= null;
	
	//region Fields
	private String name;
	private CharacterRace charRace;
	
	private int proficiencyBonus= 2;
	private int characterLevel;
	private int currentHitPoints;
	private int maxHitPoints= 0;
	private int additionalMaxHP= 0;
	private int tempHP= 0;
	
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
	
	private int strSaving;
	private int dexSaving;
	private int conSaving;
	private int intSaving;
	private int wisSaving;
	private int chaSaving;
	
	private List<CharacterClass> classList= new ArrayList<>();
	private List<String> savingThrowProficiencies= new ArrayList<>();
	private Set<String> armorProficiencies= new LinkedHashSet<>();
	private Set<String> weaponProficiencies= new LinkedHashSet<>();
	private Set<String> toolProficiencies= new LinkedHashSet<>();
	
	private Set<String> skillProficiencies= new LinkedHashSet<>();
	private Set<String> skillExpertise= new LinkedHashSet<>();
	private boolean halfProf;
	
	private Map<String, ClassFeature> classFeatures= new LinkedHashMap<>();
	
	private Map<GameInfo.damageType, Double> resistances= new TreeMap<>();
	private Map<GameInfo.status, String> statusDefenses= new TreeMap<>();
	
	private List<CharacterSpell> cantrips= new ArrayList<>();
	private List<CharacterSpell> spells= new ArrayList<>();
	private List<String> rituals= new ArrayList<>();
	
	private Map<String, Integer> miscItemsList= new LinkedHashMap<>();
	private Map<String, Integer> armorList= new LinkedHashMap<>();
	private Map<String, Integer> weaponList= new LinkedHashMap<>();
	private Map<String, Integer> toolItemsList= new LinkedHashMap<>();
	//endregion
	
	private PlayerCharacter(String name, CharacterRace charRace, int str, int dex, int con, int intel, int wis, int cha, CharacterClass startingClass,
							ArrayList<String> charPackList, ArrayList<String> charArmorList, ArrayList<String> charWeaponList, ArrayList<String> charToolList){
		this.name= name;
		this.charRace= charRace;
		
		strength= str;
		dexterity= dex;
		constitution= con;
		intelligence= intel;
		wisdom= wis;
		charisma= cha;
		
		Map<String, Integer> asiList= charRace.getStatModMap();
		applyASI(asiList);
		
		strMod= calculateMod(strength);
		dexMod= calculateMod(dexterity);
		conMod= calculateMod(constitution);
		intMod= calculateMod(intelligence);
		wisMod= calculateMod(wisdom);
		chaMod= calculateMod(charisma);
		
		if(charRace.getRacialArmorTraining() != null){ armorProficiencies.addAll(Arrays.asList(charRace.getRacialArmorTraining())); }
		armorProficiencies.addAll(Arrays.asList(startingClass.getArmorProficiencies()));
		
		if(charRace.getRacialWeaponTraining() != null){ weaponProficiencies.addAll(Arrays.asList(charRace.getRacialWeaponTraining())); }
		for(String weapon : startingClass.getWeaponProficiencies()){
			if("simple".equalsIgnoreCase(weapon) || "martial".equalsIgnoreCase(weapon)){ weaponProficiencies.addAll(GameInfo.getWeaponsByType(weapon, "any", true)); }
			else{ weaponProficiencies.add(weapon); }
		}
		if(charRace.getRacialToolTraining() != null){
			toolProficiencies.addAll(Arrays.asList(charRace.getRacialToolTraining())); }
		toolProficiencies.addAll(Arrays.asList(startingClass.getToolProficiencies()));
		
		if(charRace.getRacialSkills() != null){ skillProficiencies.addAll(Arrays.asList(charRace.getRacialSkills())); }
		halfProf= false;
		
		if(charRace.getRacialDamageResistances() != null){
			for(DamageResistance resis : charRace.getRacialDamageResistances()){
				resistances.put(resis.getDamageType(), resis.resistanceLevel);
			}
		}
		
		savingThrowProficiencies.addAll(Arrays.asList(startingClass.getSavingThrows()));
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
		
		createStartingInventory(charPackList, charArmorList, charWeaponList, charToolList);
	}
	
	public static void makePlayerCharacter(String name, CharacterRace charRace, int str, int dex, int con, int intel, int wis, int cha, CharacterClass charClass,
										   ArrayList<String> charPackList, ArrayList<String> charArmorList, ArrayList<String> charWeaponList, ArrayList<String> charToolList){
		playerCharacter= new PlayerCharacter(name, charRace, str, dex, con, intel, wis, cha, charClass, charPackList, charArmorList, charWeaponList, charToolList);
	}
	
	public static PlayerCharacter getPlayerCharacter(){ return playerCharacter; }
	
	private int calculateMod(int n){
		double base= 10.0;
		double dividend= n - base;
		double divisor= 2.0;
		double quotient= dividend/divisor;
		return (int)Math.floor(quotient);
	}
	
	private void addToMaxHitPoints(int hpToAdd){
		maxHitPoints += hpToAdd;
		currentHitPoints += hpToAdd;
	}
	
	public void addLevel(String className){
		CharacterClass charClass= GameInfo.getClass(className);
		int index= classList.indexOf(charClass);
		if(index == -1){
			classList.add(charClass);
			charClass.incrementLevel();
		}
		else{ classList.get(index).incrementLevel(); }
		characterLevel++;
		
		int hpToAdd= conMod + additionalMaxHP;
		Random random= new Random();
		
		hpToAdd += characterLevel == 1 ? charClass.getHitDice() : random.nextInt(charClass.getHitDice()) + 1;
		addToMaxHitPoints(hpToAdd);
	}
	
	public PlayerCharacter addClassFeatures(List<String> features){
		for(String feature : features){
			if(feature == null){ return this; }
			ClassFeature featureToAdd= GameInfo.getClassFeature(feature);
			String parent= featureToAdd.getParentFeature();
			
			if("none".equalsIgnoreCase(parent)){
				classFeatures.put(featureToAdd.getName(), featureToAdd);
				applyClassFeatureEffect(featureToAdd);
			}
			else if(classFeatures.containsKey(parent)){ classFeatures.get(parent).addChildFeature(featureToAdd); }
			else{
				for(String f : classFeatures.keySet()){
					List<ClassFeature> children= new ArrayList<>(classFeatures.get(f).getChildFeatures());
					if(children.contains(GameInfo.getClassFeature(parent))){
						children.get(children.indexOf(GameInfo.getClassFeature(parent))).addChildFeature(featureToAdd);
						break;
					}
				}
			}
		}
		return this;
	}
	
	public PlayerCharacter applyASI(Map<String, Integer> asi){
		for(String a : asi.keySet()){
			switch(a){
				case "HP":
					additionalMaxHP += asi.get(a);
					break;
				case "Str":
					strength += asi.get(a);
					break;
				case "Dex":
					dexterity += asi.get(a);
					break;
				case "Con":
					constitution += asi.get(a);
					break;
				case "Int":
					intelligence += asi.get(a);
					break;
				case "Wis":
					wisdom += asi.get(a);
					break;
				case "Cha":
					charisma += asi.get(a);
					break;
			}
		}
		return this;
	}
	
	public PlayerCharacter addSkills(List<String> skills){
		skillProficiencies.addAll(skills);
		return this;
	}
	
	public PlayerCharacter addExpertise(List<String> expertise){
		skillExpertise.addAll(expertise);
		return this;
	}
	
	public PlayerCharacter addCantrips(String stat, String className, List<String> spells){
		if(stat == null){ return this; }
		
		for(String spell : spells){
			Spell c= GameInfo.getSpell(spell);
			cantrips.add(new CharacterSpell(stat, className, c));
		}
		return this;
	}
	
	public void addSpells(List<String> spellList, String stat, String className, boolean hasRitual){
		if(stat == null){ return; }
		
		for(String spell : spellList){
			spells.add(GameInfo.makeCharacterSpell(spell, stat, className));
			if(hasRitual){ rituals.add(spell); }
		}
	}
	
	@SuppressWarnings("SwitchStatementWithTooFewBranches")
	private void applyClassFeatureEffect(ClassFeature feature){
		if(feature instanceof ElementGainClassFeature){
			switch(((ElementGainClassFeature)feature).getElementGained()){
				case "Ritual":
					rituals.addAll(Arrays.asList(((ElementGainClassFeature) feature).getElements()));
					break;
			}
		}
		else if(feature instanceof IncreaseClassFeature){
			switch(((IncreaseClassFeature) feature).getValueToIncrease()){
				case "Spell Slots":
					//TODO add logic
					break;
			}
		}
		else if(feature instanceof ValueSetClassFeature){
			switch(((ValueSetClassFeature)feature).getValueToSet()){
				case "Half Prof":
					halfProf= true;
					break;
			}
		}
	}
	
	public void setCurrentHitPoints(int hp){ currentHitPoints= hp < maxHitPoints ? (hp > 0 ? hp : 0)  : maxHitPoints; }
	
	public void setTempHP(int hp){ tempHP= hp > 0 ? hp : 0; }
	
	public int getSkillModifierForSkill(String skill){
		int mod;
		switch(GameInfo.getSkillStat(skill)){
			case "Str":
				mod= strMod;
				break;
			case "Dex":
				mod= dexMod;
				break;
			case "Con":
				mod= conMod;
				break;
			case "Int":
				mod= intMod;
				break;
			case "Wis":
				mod= wisMod;
				break;
			case "Cha":
				mod= chaMod;
				break;
			default: mod= 0;
		}
		if(skillExpertise.contains(skill)){ mod += (proficiencyBonus * 2); }
		else if(skillProficiencies.contains(skill)){ mod += proficiencyBonus; }
		else if(halfProf){ mod += (proficiencyBonus / 2); }
		return mod;
	}
	
	public CharacterClass getClass(String name){
		for(CharacterClass c : classList){
			if(c.getName().equalsIgnoreCase(name)){ return c; }
		}
		return null;
	}
	
	//region Inventory Management
	private void createStartingInventory(List<String> packList, List<String> armorList, List<String> weaponList, ArrayList<String> toolList){
		for(int i= 0; i < packList.size(); i++){
			String equip= packList.get(i);
			if(Character.isDigit(equip.charAt(0))){
				String[] multi= equip.split(" ", 2);
				addMiscItem(multi[1], Integer.parseInt(multi[0].trim()));
			}
			else{ addMiscItem(equip, 1); }
		}
		
		for(int i= 0; i < armorList.size(); i++){
			String equip= armorList.get(i);
			if(Character.isDigit(equip.charAt(0))){
				String[] multi= equip.split(" ", 2);
				addMiscItem(multi[1], Integer.parseInt(multi[0].trim()));
			}
			else{ addArmorItem(equip, 1); }
		}
		
		for(int i= 0; i < weaponList.size(); i++){
			String equip= weaponList.get(i);
			if(Character.isDigit(equip.charAt(0))){
				String[] multi= equip.split(" ", 2);
				addWeaponItem(multi[1], Integer.parseInt(multi[0].trim()));
			}
			else{ addWeaponItem(equip, 1); }
		}
		
		for(int i= 0; i < toolList.size(); i++){
			String equip= toolList.get(i);
			if(Character.isDigit(equip.charAt(0))){
				String[] multi= equip.split(" ", 2);
				addToolItem(multi[1], Integer.parseInt(multi[0].trim()));
			}
			else{ addToolItem(equip, 1); }
		}
	}
	
	public void addArmorItem(String itemName, int amount){
		int amountToAdd= armorList.containsKey(itemName) ? armorList.get(itemName) + amount : amount;
		armorList.put(itemName, amountToAdd);
	}
	
	public void addWeaponItem(String itemName, int amount){
		int amountToAdd= weaponList.containsKey(itemName) ? weaponList.get(itemName) + amount : amount;
		weaponList.put(itemName, amountToAdd);
	}
	
	private void addToolItem(String itemName, int amount){
		int amountToAdd= toolItemsList.containsKey(itemName) ? toolItemsList.get(itemName) + amount : amount;
		toolItemsList.put(itemName, amountToAdd);
	}
	public void addMiscItem(String itemName, int amount){
		int amountToAdd= miscItemsList.containsKey(itemName) ? miscItemsList.get(itemName) + amount : amount;
		miscItemsList.put(itemName, amountToAdd);
	}
	//endregion
}
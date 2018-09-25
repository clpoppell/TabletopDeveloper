package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Joiner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import tabletop_5e_character_design.CharacterAttack;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.CharacterRace;
import tabletop_5e_character_design.CharacterSpell;
import tabletop_5e_character_design.ClassEquipmentList;
import tabletop_5e_character_design.ClassSkillList;
import tabletop_5e_character_design.ConditionDefense;
import tabletop_5e_character_design.DamageResistance;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.RacialSpellList;

public class NewCharacterForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
	private static boolean ON= true;
	private static boolean OFF= false;
	
	private static final Integer[] LEVELS= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	
	//region Fields
	private Spinner raceSpinner;
	private Spinner asiSpinner1;
	private Spinner asiSpinner2;
	private Spinner skillSpinner1;
	private Spinner skillSpinner2;
	private Spinner featSpinner;
	private Spinner weaponSpinner1;
	private Spinner weaponSpinner2;
	private Spinner toolSpinner;
	private Spinner cantripSpinner;
	private Spinner languageSpinner;
	
	private Spinner classSpinner;
	private List<CheckBox> classSkillCheckBoxList= new ArrayList<>();
	private Spinner equipmentPackSpinner;
	private Spinner equipmentArmorSpinner;
	private LinearLayout equipmentWeaponLayout;
	private LinearLayout equipmentToolLayout;
	
	private Spinner levelSpinner;
	
	private String[] raceList;
	private String subRaceName;
	private CharacterRace subRaceInfo;
	
	private ArrayList<String> statsList;
	private String asiExclusion;
	
	private ArrayList<String> skillList;
	private List<String> skillsToExclude= new ArrayList<>();
	private int classSkillCount= 0;
	private ArrayList<String> charSkills= new ArrayList<>();
	
	private String[] languageList;
	private ArrayList<String> languageExclusions;
	
	private String[] classList;
	private String className;
	private CharacterClass classInfo= GameInfo.getClass("Barbarian");
	//endregion
	
	//TODO Check for testing blocks
	//region Set-Up Methods
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_character_form);
		
		raceSpinner= findViewById(R.id.race_spinner);
		asiSpinner1= findViewById(R.id.asi_select_spinner1);
		asiSpinner2=findViewById(R.id.asi_select_spinner2);
		skillSpinner1= findViewById(R.id.skill_select_spinner1);
		skillSpinner2=findViewById(R.id.skill_select_spinner2);
		featSpinner= findViewById(R.id.feat_select_spinner);
		weaponSpinner1= findViewById(R.id.weapon_select_spinner1);
		weaponSpinner2=findViewById(R.id.weapon_select_spinner2);
		toolSpinner= findViewById(R.id.tool_select_spinner);
		cantripSpinner= findViewById(R.id.cantrip_select_spinner);
		languageSpinner= findViewById(R.id.language_select_spinner);
		classSpinner= findViewById(R.id.class_spinner);
		equipmentPackSpinner= findViewById(R.id.pack_selection_spinner);
		equipmentArmorSpinner= findViewById(R.id.armor_selection_spinner);
		equipmentWeaponLayout= findViewById(R.id.equip_weapon_selection_layout);
		equipmentToolLayout= findViewById(R.id.equipment_tool_selection_layout);
		levelSpinner= findViewById(R.id.level_spinner);
		
		languageList= getResources().getStringArray(R.array.language_list);
		
		createRaceSpinner();
		createClassSpinner();
		createLevelSpinner();
	}
	
	private void createRaceSpinner(){
		//Create an ArrayAdapter
		raceList= getResources().getStringArray(R.array.race_list);
		ArrayList<String> mainRaceNames= new ArrayList<>();
		for(String race : raceList){
			String raceName= (race.split(" # "))[0];
			mainRaceNames.add(raceName);
		}
		
		ArrayAdapter<String> raceAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, mainRaceNames);
		//Specify the layout to use when the list of choices appears
		raceAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the raceAdapter to the spinner
		raceSpinner.setAdapter(raceAdapter);
		
		raceSpinner.setOnItemSelectedListener(this);
	}
	
	private void createClassSpinner(){
		//Create an ArrayAdapter
		classList= getResources().getStringArray(R.array.class_list);
		ArrayList<String> mainClassNames= new ArrayList<>();
		for(String classN : classList){
			String className= (classN.split(" # "))[0];
			mainClassNames.add(className);
		}
		
		ArrayAdapter<String> classAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, mainClassNames);
		//Specify the layout to use when the list of choices appears
		classAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the raceAdapter to the spinner
		classSpinner.setAdapter(classAdapter);
		
		classSpinner.setOnItemSelectedListener(this);
	}
	
	private void createLevelSpinner(){
		levelSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the int array and a spinner layout
		ArrayAdapter<Integer> levelAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, LEVELS);
		//Specify the layout to use when the list of choices appears
		levelAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the classAdapter to the spinner
		levelSpinner.setAdapter(levelAdapter);
	}
	
	private void asiSelectionSetup(){
		asiSpinner1.setVisibility(Spinner.VISIBLE);
		findViewById(R.id.asi_select_mod_textView1).setVisibility(TextView.VISIBLE);
		asiSpinner2.setVisibility(LinearLayout.VISIBLE);
		findViewById(R.id.asi_select_mod_textView2).setVisibility(TextView.VISIBLE);

		TextView asiSelectMod1= findViewById(R.id.asi_select_mod_textView1);
		TextView asiSelectMod2= findViewById(R.id.asi_select_mod_textView2);
		asiSelectMod1.setText("+1, ");
		asiSelectMod2.setText(" +1");
		
		//Create an ArrayAdapter
		statsList= new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.stats)));
		statsList.remove(asiExclusion);
		final ArrayAdapter<String> statAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, statsList);
		//Specify the layout to use when the list of choices appears
		statAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		asiSpinner1.setAdapter(statAdapter1);
		//Sets the item selection listener
		asiSpinner1.setOnItemSelectedListener(this);
	}
	
	private void physFeaturesSetup(){
		TextView physFeaturesTextView= findViewById(R.id.physical_features_textView);
		CharacterRace charRace= GameInfo.getRace(subRaceName);
		
		StringBuilder s= new StringBuilder("Size: ").append(charRace.getRacialSize()).append("\n");
		s.append("Speed: ").append(charRace.getRacialSpeed()).append(" ft.");
		
		String additionalMovement= charRace.getAdditionalMovementTypes();
		if(additionalMovement != null){ s.append(", ").append(additionalMovement).append(" ft."); }
		
		int darkvision= charRace.getDarkvisionRange();
		if(darkvision > 0){ s.append("\n").append("Darkvision: ").append(darkvision).append(" ft."); }
		
		physFeaturesTextView.setText(s);
	}
	
	private void racialSkillSelectionSetup(){
		String[] skills= GameInfo.getRace(subRaceName).getRacialSkills();
		int numSkillSelection= 0;
		StringBuilder s= new StringBuilder("Racial Skills: ");
		if(skills != null){
			findViewById(R.id.race_skill_layout).setVisibility(LinearLayout.VISIBLE);
			findViewById(R.id.skill_textView1).setVisibility(TextView.VISIBLE);
			for(String skill : skills){
				if(CharacterRace.isSkillSelection(skill)){
					numSkillSelection++;
				} else{
					s.append(skill).append(" , ");
					skillsToExclude.add(skill);
				}
			}
			
			switch(numSkillSelection){
				case 2:
					findViewById(R.id.skill_textView2).setVisibility(Spinner.VISIBLE);
					findViewById(R.id.skill_select_spinner2).setVisibility(Spinner.VISIBLE);
				case 1:
					skillSpinner1.setVisibility(Spinner.VISIBLE);
					//Create an ArrayAdapter
					skillList= (ArrayList<String>) createSkillList(skills[0]);
					ArrayAdapter<String> skillAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, skillList);
					//Specify the layout to use when the list of choices appears
					skillAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
					//Apply the statAdapter to the spinner
					skillSpinner1.setAdapter(skillAdapter1);
					//Sets the item selection listener
					skillSpinner1.setOnItemSelectedListener(this);
					break;
				default:
					s.deleteCharAt(s.lastIndexOf(", "));
			}
			TextView skillTextView= findViewById(R.id.skill_textView1);
			skillTextView.setText(s);
		}
	}
	
	private List<String> createSkillList(String s){
		if(s.equals("(Any)")){
			Map<String, String> skillMap= GameInfo.getSkillMap();
			return new ArrayList<>(skillMap.keySet());
		}
		else{
			String skillChoices= s.substring(1, s.length() - 1);
			return new ArrayList<>(Arrays.asList(skillChoices.split(", ")));
		}
	}
	
	private void featSelectionSetup(){
		findViewById(R.id.feat_selection_layout).setVisibility(LinearLayout.VISIBLE);
		featSpinner.setVisibility(Spinner.VISIBLE);
		
		//Create an ArrayAdapter
		String[] featList= getResources().getStringArray(R.array.feat_list);
		final ArrayAdapter<String> featAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, featList);
		//Specify the layout to use when the list of choices appears
		featAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		featSpinner.setAdapter(featAdapter);
	}
	
	private void weaponSelectionSetup(){
		String[] weaponProficiencies= subRaceInfo.getRacialWeaponTraining();
		String s= "Weapons: ";
		if(weaponProficiencies != null){
			findViewById(R.id.weapon_proficiency_layout).setVisibility(LinearLayout.VISIBLE);
			findViewById(R.id.weapon_textView).setVisibility(TextView.VISIBLE);
			if(subRaceInfo.hasWeaponChoice()){
				weaponSpinner1.setVisibility(Spinner.VISIBLE);
				//Create an ArrayAdapter
				ArrayList<String> weaponList= GameInfo.getWeaponsByType("Martial", "any", true);
				ArrayAdapter<String> weaponAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, weaponList);
				//Specify the layout to use when the list of choices appears
				weaponAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				weaponSpinner1.setAdapter(weaponAdapter1);
				//Sets the item selection listener
				weaponSpinner1.setOnItemSelectedListener(this);
			}
			else{
				s += Joiner.on(", ").skipNulls().join(weaponProficiencies);
			}
		}
		TextView weaponTextView1= findViewById(R.id.weapon_textView);
		weaponTextView1.setText(s);
	}
	
	private void toolSelectionSetup(){
		String[] toolProficiencies= subRaceInfo.getRacialToolTraining();
		StringBuilder s= new StringBuilder("Tools: ");
		if(toolProficiencies != null){
			findViewById(R.id.tool_selection_layout).setVisibility(LinearLayout.VISIBLE);
			findViewById(R.id.tool_select_spinner).setVisibility(Spinner.VISIBLE);
			for(String tool : toolProficiencies){
				if(CharacterRace.isToolChoice(tool)){
					s.append(",");
					toolSpinner.setVisibility(Spinner.VISIBLE);
					//Create an ArrayAdapter
					String choiceString= tool.substring(tool.indexOf("(") + 1, tool.indexOf(")"));
					String [] toolList= choiceString.split(" - ");
					final ArrayAdapter<String> toolAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, toolList);
					//Specify the layout to use when the list of choices appears
					toolAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
					//Apply the statAdapter to the spinner
					toolSpinner.setAdapter(toolAdapter);
					//Sets the item selection listener
					toolSpinner.setOnItemSelectedListener(this);
				}
				else{ s.append(tool).append(", "); }
			}
			TextView toolTextView= findViewById(R.id.tool_select_textView);
			toolTextView.setText(s.substring(0, s.lastIndexOf(",")));
		}
	}
	
	private void cantripSelectionSetup(){
		CharacterRace charRace= GameInfo.getRace(subRaceName);
		CharacterSpell cantrip= charRace.getRacialCantrip();
		String s= "Cantrip: ";
		
		if(cantrip != null){
			findViewById(R.id.cantrip_layout).setVisibility(LinearLayout.VISIBLE);
			findViewById(R.id.cantrip_textView).setVisibility(TextView.VISIBLE);
			if(charRace.hasCantripChoice()){
				String key= cantrip.getSpellName().substring(cantrip.getSpellName().indexOf("(") + 1, cantrip.getSpellName().indexOf(")"));
				String[] cantrips= GameInfo.getSpellList(key + " Cantrips");
				
				TextView cantripTextView2= findViewById(R.id.cantrip_textView2);
				cantripTextView2.setVisibility(TextView.VISIBLE);
				String cantripStat= " (" + charRace.getRacialCantrip().getStatUsed() + ")";
				cantripTextView2.setText(cantripStat);
				
				cantripSpinner.setVisibility(Spinner.VISIBLE);
				//Create an ArrayAdapter;
				ArrayAdapter<String> cantripAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, cantrips);
				//Specify the layout to use when the list of choices appears
				cantripAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				cantripSpinner.setAdapter(cantripAdapter);
			}
			else{
				s += cantrip.getSpellName() + " (" + cantrip.getStatUsed() + ")";
			}
			TextView cantripTextView= findViewById(R.id.cantrip_textView);
			cantripTextView.setText(s);
		}
	}
	
	private void spellInfoSetup(){
		TextView racialSpellTextView= findViewById(R.id.racial_spell_textView);
		RacialSpellList racialSpells= GameInfo.getRace(subRaceName).getRacialSpellList();
		if(racialSpells != null){
			StringBuilder s= new StringBuilder("Racial Spells: ");
			for(CharacterSpell spell : racialSpells.getSpellList()){
				s.append(spell.getSpellName()).append(" - Level ").append(spell.getLevelGained()).append("\n\t");
			}
			s.append("Regain: ").append(racialSpells.restRegain).append(", Casting Stat: ").append(racialSpells.getSpellList().get(0).getStatUsed());
			racialSpellTextView.setVisibility(TextView.VISIBLE);
			racialSpellTextView.setText(s);
		}
	}
	
	private void otherInfoSetup(){
		TextView otherInfoTextView= findViewById(R.id.other_race_info_display);
		StringBuilder s= new StringBuilder("");
		
		ArrayList<DamageResistance> resisList= subRaceInfo.getRacialDamageResistances();
		if(resisList != null){
			for(DamageResistance r : resisList){ s.append(r.getDamageType()).append(" resistance, "); }
			s.deleteCharAt(s.lastIndexOf(","));
			s.append("\n");
		}
		
		ArrayList<ConditionDefense> defenseList= subRaceInfo.getRacialDefenses();
		if(defenseList != null){
			s.append(Joiner.on(", ").join(defenseList));
			s.append("\n");
		}
		
		String conditionalExpertise= subRaceInfo.getRacialConditionalExpertise();
		if(conditionalExpertise != null){
			String[] conditional= conditionalExpertise.split(" - ");
			s.append("Expertise on ").append(conditional[0]).append(" checks related to ").append(conditional[1]);
			s.append("\n");
		}
		
		ArrayList<String> plainTextFeatureList= subRaceInfo.getPlainTextFeatures();
		if(plainTextFeatureList != null){ s.append(Joiner.on("\n").join(plainTextFeatureList)); }
		else{ s.deleteCharAt(s.lastIndexOf("\n")); }
		
		if(s.length() > 0){
			otherInfoTextView.setVisibility(TextView.VISIBLE);
			otherInfoTextView.setText(s.toString().replace("-", ", "));
		}
	}
	
	private void languageSelectionSetup(){
		languageSpinner.setVisibility(LinearLayout.VISIBLE);
		
		//Create an ArrayAdapter
		ArrayList<String> languages= new ArrayList<>(Arrays.asList(languageList));
		for(String language : languageExclusions){ languages.remove(language); }
		final ArrayAdapter<String> languageAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, languages);
		//Specify the layout to use when the list of choices appears
		languageAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		languageSpinner.setAdapter(languageAdapter);
	}
	
	private void classSkillSelectionSetup(){
		final ClassSkillList classSkillList= classInfo.getClassSkillChoices();
		ArrayList<String> skills= new ArrayList<>(Arrays.asList(classSkillList.getSkills()));
		skills.removeAll(skillsToExclude);
		classSkillCount= 0;
		
		LinearLayout classSkillLayout= findViewById(R.id.class_skill_layout);
		classSkillCheckBoxList.clear();
		classSkillLayout.removeAllViews();
		
		TextView textView= new TextView(this);
		textView.setText("Class Skills");
		textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		classSkillLayout.addView(textView);
		
		for(int i= 0; i < skills.size(); i++){
			CheckBox skillCheckBox= new CheckBox(this);
			skillCheckBox.setText(skills.get(i));
			skillCheckBox.setTextColor(getResources().getColor(R.color.textColorPrimary));
			skillCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			
			skillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
					if(classSkillCount == classSkillList.getNumSkills() && isChecked){ buttonView.setChecked(false); }
					else if(isChecked){ classSkillCount++; }
					else{ classSkillCount--; }
				}
			});
			
			classSkillLayout.addView(skillCheckBox);
			classSkillCheckBoxList.add(skillCheckBox);
		}
	}
	
	private void classEquipmentSelectionSetup(){
		ClassEquipmentList equipment= GameInfo.getClassEquipmentList(className);
		
		setupClassPackEquipment(equipment);
		setupClassArmorEquipment(equipment);
		setupClassWeaponEquipment(equipment);
		setupClassToolEquipment(equipment);
	}
	
	public void setupClassPackEquipment(ClassEquipmentList equipment){
		TextView packLabel= findViewById(R.id.equip_pack_label);
		TextView packText= findViewById(R.id.pack_equipment_textView);
		
		packLabel.setPaintFlags(packLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		String[] packChoice= equipment.pack.split(" \\(or\\) ");
		
		if(packChoice.length > 1){
			toggleViewVisibility(OFF, packText);
			toggleViewVisibility(ON, equipmentPackSpinner);
			
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, packChoice);
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			equipmentPackSpinner.setAdapter(spinnerAdapter);
		}
		else{
			toggleViewVisibility(ON, packText);
			toggleViewVisibility(OFF, equipmentPackSpinner);
			
			packText.setText(packChoice[0]);
		}
	}
		
	public void setupClassArmorEquipment(ClassEquipmentList equipment){
		LinearLayout armorSelectionLayout= findViewById(R.id.equip_armor_selection_layout);
		TextView armorLabel= findViewById(R.id.equip_armor_label);
		TextView armorText= findViewById(R.id.armor_equipment_textView);
		
		String[] armorChoice= equipment.armor.split(" \\(or\\) ");
		
		if(armorChoice.length > 1){
			toggleViewVisibility(ON, armorSelectionLayout);
			toggleViewVisibility(ON, armorLabel);
			toggleViewVisibility(OFF, armorText);
			toggleViewVisibility(ON, equipmentArmorSpinner);
			
			armorLabel.setPaintFlags(armorLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, armorChoice);
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			equipmentArmorSpinner.setAdapter(spinnerAdapter);
		}
		else if("none".equalsIgnoreCase(armorChoice[0])){
			toggleViewVisibility(OFF, armorLabel);
			toggleViewVisibility(OFF, armorSelectionLayout);
		}
		else{
			findViewById(R.id.equip_armor_selection_layout).setVisibility(LinearLayout.VISIBLE);
			toggleViewVisibility(ON, armorLabel);
			toggleViewVisibility(ON, armorText);
			toggleViewVisibility(OFF, equipmentArmorSpinner);
			
			armorLabel.setPaintFlags(armorLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			armorText.setText(armorChoice[0]);
		}
	}
	
	public void setupClassWeaponEquipment(ClassEquipmentList equipment){
		equipmentWeaponLayout.removeAllViews();
		TextView weaponLabel= findViewById(R.id.equip_weapon_label);
		weaponLabel.setPaintFlags(weaponLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		List<String> weaponChoicesList= equipment.getWeaponEquipment();
		for(String weaponChoices : weaponChoicesList){
			String[] weaponChoice= weaponChoices.split(" \\(or\\) ");
			
			if(weaponChoice.length > 1 || weaponChoice[0].contains("-")){
				Spinner spinner= new Spinner(this, Spinner.MODE_DIALOG);
				final HashSet<String> weaponSet= new LinkedHashSet<>();
				
				for(String weapon : weaponChoice){
					if(weapon.contains("-")){
						String[] weaponType= weapon.split("-");
						weaponSet.addAll(GameInfo.getWeaponsByType(weaponType[0], weaponType[1], false));
					}
					else{ weaponSet.add(weapon); }
				}
				
				ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>(weaponSet));
				spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
				spinner.setAdapter(spinnerAdapter);
				
				spinner.setBackground(getDrawable(R.drawable.text_input_border));
				equipmentWeaponLayout.addView(spinner);
			}
			else{
				TextView textView= new TextView(this);
				textView.setText(weaponChoice[0].replace("#", ""));
				textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				
				equipmentWeaponLayout.addView(textView);
			}
		}
	}
	
	public void setupClassToolEquipment(ClassEquipmentList equipment){
		equipmentToolLayout.removeAllViews();
		TextView toolLabel= findViewById(R.id.equip_tool_label);
		toolLabel.setPaintFlags(toolLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		String[] toolChoice= equipment.tool.split(" \\(or\\) ");
		
		//noinspection IfStatementWithTooManyBranches
		if(toolChoice.length > 1){
			toggleViewVisibility(ON, toolLabel);
			toggleViewVisibility(ON, equipmentToolLayout);
			
			Spinner spinner= new Spinner(this, Spinner.MODE_DIALOG);
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, toolChoice);
			
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			spinner.setAdapter(spinnerAdapter);
			spinner.setBackground(getDrawable(R.drawable.text_input_border));
			
			equipmentToolLayout.addView(spinner);
		}
		else if("instrument".equalsIgnoreCase(toolChoice[0])){
			toggleViewVisibility(ON, toolLabel);
			toggleViewVisibility(ON, equipmentToolLayout);
			
			Spinner spinner= new Spinner(this, Spinner.MODE_DIALOG);
			
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.instrument_list));
			
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			spinner.setAdapter(spinnerAdapter);
			spinner.setBackground(getDrawable(R.drawable.text_input_border));
			
			equipmentToolLayout.addView(spinner);
		}
		else if(!"none".equalsIgnoreCase(toolChoice[0])){
			toggleViewVisibility(ON, toolLabel);
			toggleViewVisibility(ON, equipmentToolLayout);
			
			TextView toolText= new TextView(this);
			toolText.setText(toolChoice[0]);
			toolText.setTextColor(getResources().getColor(R.color.textColorPrimary));
			toolText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			
			equipmentToolLayout.addView(toolText);
		}
		else{
			toggleViewVisibility(OFF, toolLabel);
			toggleViewVisibility(OFF, equipmentToolLayout);
		}
	}
	
	private void resetViewVisibility(){
		toggleViewVisibility( OFF,
				findViewById(R.id.asi_select_spinner1),
				findViewById(R.id.asi_select_mod_textView1),
				findViewById(R.id.asi_select_spinner2),
				findViewById(R.id.asi_select_mod_textView2),
				
				findViewById(R.id.race_skill_layout),
				findViewById(R.id.skill_textView1),
				findViewById(R.id.skill_select_spinner1),
				findViewById(R.id.skill_textView2),
				findViewById(R.id.skill_select_spinner2),
				
				findViewById(R.id.feat_selection_layout),
				findViewById(R.id.feat_select_spinner),
				
				findViewById(R.id.racial_attack_textView),
				
				findViewById(R.id.weapon_proficiency_layout),
				findViewById(R.id.weapon_textView),
				findViewById(R.id.weapon_select_spinner1),
				findViewById(R.id.weapon_textView2),
				findViewById(R.id.weapon_select_spinner2),
				
				findViewById(R.id.tool_selection_layout),
				findViewById(R.id.tool_select_spinner),
				
				findViewById(R.id.cantrip_layout),
				findViewById(R.id.cantrip_textView),
				findViewById(R.id.cantrip_select_spinner),
				findViewById(R.id.cantrip_textView2),
				
				findViewById(R.id.racial_spell_textView),
				
				findViewById(R.id.other_race_info_display),
				
				findViewById(R.id.language_select_spinner)
		);
	}
	
	private void toggleViewVisibility(Boolean on, View ... views){
		for(View v : views){
			if(on){ v.setVisibility(View.VISIBLE); }
			else{ v.setVisibility(View.GONE); }
		}
	}
	//endregion
	
	public void completeCharacter(View view){
		EditText nameBox= findViewById(R.id.name_editText);
		
		String asi1= null;
		String asi2= null;
		String skill1= null;
		String skill2= null;
		String feat= null;
		String language= null;
		String cantrip= null;
		String tool= null;
		String weapon1= null;
		String weapon2= null;
		
		int visible= View.VISIBLE;
		if(asiSpinner1.getVisibility() == visible){
			asi1= asiSpinner1.getSelectedItem().toString();
			asi2= asiSpinner2.getSelectedItem().toString();
		}
		if(skillSpinner1.getVisibility() == visible){
			skill1= skillSpinner1.getSelectedItem().toString();
			if(skillSpinner2.getVisibility() == visible){ skill2= skillSpinner2.getSelectedItem().toString(); }
			for(CheckBox checkBox : classSkillCheckBoxList){
				if(checkBox.isChecked()){ charSkills.add(checkBox.getText().toString()); }
			}
		}
		if(featSpinner.getVisibility() == visible){ feat= featSpinner.getSelectedItem().toString(); }
		if(languageSpinner.getVisibility() == visible){ language= languageSpinner.getSelectedItem().toString(); }
		if(cantripSpinner.getVisibility() == visible){ cantrip= cantripSpinner.getSelectedItem().toString(); }
		if(toolSpinner.getVisibility() == visible){ tool= toolSpinner.getSelectedItem().toString(); }
		if(weaponSpinner1.getVisibility() == visible){
			weapon1= weaponSpinner1.getSelectedItem().toString();
			weapon2= weaponSpinner2.getSelectedItem().toString();
		}
		
		CharacterRace characterRace= new CharacterRace(subRaceInfo, asi1, asi2, skill1, skill2, feat, language, cantrip, tool, weapon1, weapon2);
		
		int str= 8;
		int dex= 14;
		int con= 13;
		int intel= 15;
		int wis= 10;
		int cha= 12;
		
		String className= classSpinner.getSelectedItem().toString();
		CharacterClass charClass= new CharacterClass(GameInfo.getClass(className));
		
		PlayerCharacter.makePlayerCharacter(nameBox.getText().toString(), characterRace, str, dex, con, intel, wis, cha, charSkills, charClass,
				getCharPackList(), getCharArmorList(), getCharWeaponList(), getCharToolList());
		
		Intent intent= new Intent(this, CharacterDisplay.class);
		startActivity(intent);
	}
	
	private ArrayList<String> getCharPackList(){
		String pack;
		Spinner packSpinner= findViewById(R.id.pack_selection_spinner);
		TextView packText= findViewById(R.id.pack_equipment_textView);
		pack= packSpinner.getVisibility() == Spinner.VISIBLE ? packSpinner.getSelectedItem().toString() : packText.getText().toString();
		ArrayList<String> charEquipment= new ArrayList<>(Arrays.asList(GameInfo.getEquipmentPack(pack)));
		
		LinearLayout toolLayout= findViewById(R.id.equipment_tool_selection_layout);
		if(toolLayout.getVisibility() == LinearLayout.VISIBLE){
			View view= toolLayout.getChildAt(0);
			if(view instanceof Spinner){ charEquipment.add(((Spinner) view).getSelectedItem().toString()); }
			else if(view instanceof TextView){ charEquipment.add(((TextView) view).getText().toString()); }
		}
		return charEquipment;
	}
	
	private ArrayList<String> getCharArmorList(){
		ArrayList<String> charArmors= new ArrayList<>();
		
		if(findViewById(R.id.equip_armor_selection_layout).getVisibility() == LinearLayout.VISIBLE){
			Spinner armorSpinner= findViewById(R.id.armor_selection_spinner);
			TextView armorText= findViewById(R.id.armor_equipment_textView);
			if(armorSpinner.getVisibility() == Spinner.VISIBLE){ charArmors.add(armorSpinner.getSelectedItem().toString()); }
			else{ charArmors.add(armorText.getText().toString()); }
		}
		return charArmors;
	}
	
	private ArrayList<String> getCharWeaponList(){
		ArrayList<String> charWeapons= new ArrayList<>();
		
		LinearLayout weaponLayout= findViewById(R.id.equip_weapon_selection_layout);
		for(int i= 0; i < weaponLayout.getChildCount(); i++){
			View v= weaponLayout.getChildAt(i);
			if(v instanceof Spinner){ charWeapons.add(((Spinner) v).getSelectedItem().toString()); }
			else if(v instanceof TextView){ charWeapons.add(((TextView) v).getText().toString()); }
		}
		return charWeapons;
	}
	
	private ArrayList<String> getCharToolList(){
		ArrayList<String> charTools= new ArrayList<>();
		
		LinearLayout toolLayout= findViewById(R.id.equipment_tool_selection_layout);
		for(int i= 0; i < toolLayout.getChildCount(); i++){
			View v= toolLayout.getChildAt(i);
			String tool= "";
			if(v instanceof Spinner){ tool= ((Spinner) v).getSelectedItem().toString(); }
			else if(v instanceof TextView){ tool= ((TextView) v).getText().toString(); }
			
			charTools.addAll(Arrays.asList(tool.split(", ")));
		}
		return charTools;
	}
	
	//region onItemSelected
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
		switch(parent.getId()){
			case R.id.race_spinner:
				onRaceItemSelected();
				break;
			case R.id.asi_select_spinner1:
				onASIItemSelected();
				break;
			case R.id.skill_select_spinner1:
				onSkillItemSelected();
				break;
			case R.id.weapon_select_spinner1:
				onWeaponItemSelected();
				break;
			case R.id.class_spinner:
				className= classSpinner.getSelectedItem().toString();
				classInfo= GameInfo.getClass(className);
				
				classSkillSelectionSetup();
				classEquipmentSelectionSetup();
				break;
		}
	}
	
	private void onRaceItemSelected(){
		int index= raceSpinner.getSelectedItemPosition();
		
		//Creates radio buttons for subrace selection
		RadioGroup subRaceSelection= findViewById(R.id.sub_race);
		subRaceSelection.removeAllViews();
		String selectedRace= raceList[index];
		
		final String[] raceInfo= selectedRace.split(" # ");
		
		subRaceSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId){
				RadioButton rb= group.findViewById(checkedId);
				
				resetViewVisibility();
				
				String subRaceNameRaw= rb.getText().toString();
				int index= subRaceNameRaw.indexOf("(");
				subRaceName= subRaceNameRaw.substring(0, index);
				subRaceInfo= GameInfo.getRace(subRaceName);
				
				otherInfoSetup();
				
				TextView asiTextView= findViewById(R.id.asi_textView);
				Map<String, Integer> asis= subRaceInfo.getStatModMap();
				asiExclusion= "";
				NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
				StringBuilder asiString= new StringBuilder("ASI: ");
				for(String s : asis.keySet()){
					if(!s.equals("Choice")){
						asiTextView.setVisibility(TextView.VISIBLE);
						asiString.append(s).append(" ").append(plusMinusNF.format(asis.get(s))).append(", ");
						asiExclusion= s;
					}
				}
				if(subRaceInfo.hasASIChoice()){ asiSelectionSetup(); }
				else{ asiString.deleteCharAt(asiString.lastIndexOf(",")); }
				asiTextView.setText(asiString);
				
				physFeaturesSetup();
				
				skillsToExclude.clear();
				racialSkillSelectionSetup();
				classSkillSelectionSetup();
				
				if(subRaceInfo.hasFeatChoice()){ featSelectionSetup(); }
				
				CharacterAttack racialAttack= GameInfo.getRace(subRaceName).getRacialAttack();
				if(racialAttack != null){
					TextView racialAttackTextView= findViewById(R.id.racial_attack_textView);
					racialAttackTextView.setText(racialAttack.toString());
					racialAttackTextView.setVisibility(TextView.VISIBLE);
				}
				
				weaponSelectionSetup();
				
				toolSelectionSetup();
				
				cantripSelectionSetup();
				
				spellInfoSetup();
				
				TextView languageTextView= findViewById(R.id.language_textView);
				String[] raceLanguages= subRaceInfo.getRacialLanguages();
				Boolean hasChoice= false;
				StringBuilder s= new StringBuilder("Languages: ");
				languageExclusions= new ArrayList<>();
				for(String language : raceLanguages){
					if(language.equals("Choice")){ hasChoice= true; }
					else{
						s.append(language).append(", ");
						languageExclusions.add(language);
					}
				}
				if(hasChoice){ languageSelectionSetup(); }
				else{ s.deleteCharAt(s.lastIndexOf(",")); }
				languageTextView.setText(s);
			}
		});
		
		for(int i= 1; i<raceInfo.length; i++){
			RadioButton rb= new RadioButton(NewCharacterForm.this);
			subRaceSelection.addView(rb);
			rb.setText(raceInfo[i]);
			rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			if(i == 1){ subRaceSelection.check(rb.getId()); } //sets first radio button as default
		}
	}
	
	private void onASIItemSelected(){
		String selection= asiSpinner1.getSelectedItem().toString();
		//Create an ArrayAdapter
		ArrayList<String> statList2= new ArrayList<>(statsList);
		statList2.remove(selection);
		statList2.remove(asiExclusion);
		ArrayAdapter<String> statAdapter2= new ArrayAdapter<>(this, R.layout.spinner_item, statList2);
		//Specify the layout to use when the list of choices appears
		statAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		asiSpinner2.setAdapter(statAdapter2);
	}
	
	private void onSkillItemSelected(){
		String selection= skillSpinner1.getSelectedItem().toString();
		//Create an ArrayAdapter
		ArrayList<String> skillList2= new ArrayList<>(skillList);
		skillList2.remove(selection);
		ArrayAdapter<String> skillAdapter2= new ArrayAdapter<>(this, R.layout.spinner_item, skillList2);
		//Specify the layout to use when the list of choices appears
		skillAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the Adapter to the spinner
		skillSpinner2.setAdapter(skillAdapter2);
		
		skillsToExclude.clear();
		skillsToExclude.add(skillSpinner1.getSelectedItem().toString());
		if(skillSpinner2.getVisibility() == Spinner.VISIBLE){ skillsToExclude.add(skillSpinner2.getSelectedItem().toString()); }
		classSkillSelectionSetup();
	}
	
	private void onWeaponItemSelected(){
		String selection= weaponSpinner1.getSelectedItem().toString();
		weaponSpinner2.setVisibility(Spinner.VISIBLE);
		findViewById(R.id.weapon_textView2).setVisibility(TextView.VISIBLE);
		//Create an ArrayAdapter
		ArrayList<String> weaponList2= new ArrayList<>(GameInfo.getWeaponsByType("Martial", "any", true));
		weaponList2.remove(selection);
		ArrayAdapter<String> weaponAdapter2= new ArrayAdapter<>(this, R.layout.spinner_item, weaponList2);
		//Specify the layout to use when the list of choices appears
		weaponAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the Adapter to the spinner
		weaponSpinner2.setAdapter(weaponAdapter2);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent){}
	//endregion
}
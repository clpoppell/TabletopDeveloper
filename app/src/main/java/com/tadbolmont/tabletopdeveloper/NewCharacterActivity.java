package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Objects;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import tabletop_5e_character_design.CharacterAttack;
import tabletop_5e_character_design.CharacterRace;
import tabletop_5e_character_design.CharacterSpell;
import tabletop_5e_character_design.ClassEquipmentList;
import tabletop_5e_character_design.ConditionDefense;
import tabletop_5e_character_design.DamageResistance;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.RacialSpellList;

import static com.tadbolmont.tabletopdeveloper.App.OFF;
import static com.tadbolmont.tabletopdeveloper.App.ON;
import static com.tadbolmont.tabletopdeveloper.App.toggleViewVisibility;

//TODO complete class selection
public class NewCharacterActivity extends AppCompatActivity implements ClassSelectionFragment.OnClassSelectedListener{
	private static final Integer[] LEVELS= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	
	//region Views
	@BindView(R.id.name_editText) EditText nameBox;
	@BindView(R.id.race_spinner) Spinner raceSpinner;
	@BindView(R.id.sub_race) RadioGroup subRaceSelection;
	
	@BindView(R.id.asi_textView) TextView asiTextView;
	@BindView(R.id.asi_select_spinner1) Spinner asiSpinner1;
	@BindView(R.id.asi_select_spinner2) Spinner asiSpinner2;
	@BindView(R.id.asi_select_mod_textView1) TextView asiSelectMod1;
	@BindView(R.id.asi_select_mod_textView2) TextView asiSelectMod2;
	
	@BindView(R.id.race_skill_layout) LinearLayout racialSkillLayout;
	@BindView(R.id.skill_textView1) TextView skillTextView1;
	@BindView(R.id.skill_select_spinner1) Spinner skillSpinner1;
	@BindView(R.id.skill_textView2) TextView skillTextView2;
	@BindView(R.id.skill_select_spinner2) Spinner skillSpinner2;

	@BindView(R.id.feat_selection_layout) LinearLayout featLayout;
	@BindView(R.id.feat_select_spinner) Spinner featSpinner;
	@BindView(R.id.racial_attack_textView) TextView racialAttackTextView;
	
	@BindView(R.id.weapon_proficiency_layout) LinearLayout weaponProfLayout;
	@BindView(R.id.weapon_textView) TextView weaponTextView1;
	@BindView(R.id.weapon_textView2) TextView weaponTextView2;
	@BindView(R.id.weapon_select_spinner1) Spinner weaponSpinner1;
	@BindView(R.id.weapon_select_spinner2) Spinner weaponSpinner2;
	
	@BindView(R.id.tool_selection_layout) LinearLayout toolSelectionLayout;
	@BindView(R.id.tool_select_spinner) Spinner toolSpinner;
	@BindView(R.id.tool_select_textView) TextView toolTextView;
	
	@BindView(R.id.cantrip_layout) LinearLayout cantripLayout;
	@BindView(R.id.cantrip_select_spinner) Spinner cantripSpinner;
	@BindView(R.id.cantrip_textView) TextView cantripTextView;
	@BindView(R.id.cantrip_textView2) TextView cantripTextView2;
	@BindView(R.id.racial_spell_textView) TextView racialSpellTextView;
	
	@BindView(R.id.language_textView) TextView languageTextView;
	@BindView(R.id.language_select_spinner) Spinner languageSpinner;
	@BindView(R.id.physical_features_textView) TextView physFeaturesTextView;
	@BindView(R.id.other_race_info_display) TextView otherInfoTextView;
	
	@BindView(R.id.class_skill_layout) LinearLayout classSkillLayout;
	
	@BindView(R.id.equip_pack_label) TextView packLabel;
	@BindView(R.id.pack_equipment_textView) TextView packText;
	@BindView(R.id.pack_selection_spinner) Spinner equipmentPackSpinner;
	
	@BindView(R.id.equip_armor_selection_layout) LinearLayout equipmentArmorLayout;
	@BindView(R.id.equip_armor_label) TextView armorLabel;
	@BindView(R.id.armor_equipment_textView) TextView armorText;
	@BindView(R.id.armor_selection_spinner) Spinner equipmentArmorSpinner;
	
	@BindView(R.id.equip_weapon_label) TextView weaponLabel;
	@BindView(R.id.equip_weapon_selection_layout) LinearLayout equipmentWeaponLayout;
	
	@BindView(R.id.equipment_tool_selection_layout) LinearLayout equipmentToolLayout;
	@BindView(R.id.equip_tool_label) TextView toolLabel;
	
	@BindView(R.id.level_spinner) Spinner levelSpinner;
	
	ClassSelectionFragment classFragment;
	//endregion
	
	//region Fields
	private String[] raceList;
	private String subRaceName;
	private CharacterRace subRaceInfo;
	
	private ArrayList<String> statsList;
	private String asiExclusion;
	
	private ArrayList<String> skillList;
	
	@BindArray(R.array.language_list) String[] languageList;
	private ArrayList<String> languageExclusions;
	
	private String className= "Barbarian";
	//endregion
	
	private void resetViewVisibility(){
		toggleViewVisibility(OFF,
				asiSpinner1, asiSelectMod1, asiSpinner2, asiSelectMod2,
				racialSkillLayout, skillTextView1, skillSpinner1, skillTextView2, skillSpinner2,
				featLayout, featSpinner, racialAttackTextView,
				weaponProfLayout, weaponTextView1, weaponSpinner1, weaponTextView2, weaponSpinner2,
				toolSelectionLayout, toolSpinner,
				cantripLayout, cantripSpinner, cantripTextView, cantripTextView2, racialSpellTextView,
				otherInfoTextView, languageSpinner);
	}
	
	@SuppressWarnings("unused")
	public void collapseSection(View view){
		View layout;
		
		switch(view.getId()){
			case R.id.class_selection_btn:
				layout= findViewById(R.id.class_layout);
				break;
			case R.id.skill_section_btn:
				layout= findViewById(R.id.skill_display_layout);
				break;
			case R.id.defenses_section_btn:
				layout= findViewById(R.id.defenses_display_layout);
				break;
			case R.id.health_section_btn:
				layout= findViewById(R.id.health_display_layout);
				break;
			case R.id.attack_section_btn:
				layout= findViewById(R.id.attacks_display_layout);
				break;
			default: layout= view;
		}
		if(layout.getVisibility() == ON){
			toggleViewVisibility(OFF, layout);
			((TextView)view).setText("Open");
		}
		else{
			toggleViewVisibility(ON, layout);
			((TextView)view).setText("Close");
		}
	}
	
	//TODO Check for testing blocks
	//region Race Set-Up Methods
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_character_form);
		ButterKnife.bind(this);
		
		classFragment= (ClassSelectionFragment)getSupportFragmentManager().findFragmentById(R.id.class_selection_fragment);
		
		createRaceSpinner();
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
	}
	
	private void createLevelSpinner(){
		//Create an ArrayAdapter using the int array and a spinner layout
		ArrayAdapter<Integer> levelAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, LEVELS);
		//Specify the layout to use when the list of choices appears
		levelAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the classAdapter to the spinner
		levelSpinner.setAdapter(levelAdapter);
	}
	
	private void asiSelectionSetup(){
		toggleViewVisibility(ON, asiSpinner1, asiSpinner2, asiSelectMod1, asiSelectMod2);

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
	}
	
	private void physFeaturesSetup(){
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
			toggleViewVisibility(ON, racialSkillLayout, skillTextView1);
			for(String skill : skills){
				if(CharacterRace.isSkillSelection(skill)){
					numSkillSelection++;
				} else{
					s.append(skill).append(" , ");
					classFragment.skillsAlreadySelected.add(skill);
				}
			}
			
			switch(numSkillSelection){
				case 2: toggleViewVisibility(ON, skillTextView2, skillSpinner2);
				case 1:
					toggleViewVisibility(ON, skillSpinner1);
					//Create an ArrayAdapter
					skillList= (ArrayList<String>) createSkillList(skills[0]);
					ArrayAdapter<String> skillAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, skillList);
					//Specify the layout to use when the list of choices appears
					skillAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
					//Apply the statAdapter to the spinner
					skillSpinner1.setAdapter(skillAdapter1);
					//Sets the item selection listener
					break;
				default:
					s.deleteCharAt(s.lastIndexOf(", "));
			}
			skillTextView1.setText(s);
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
		toggleViewVisibility(ON, featLayout, featSpinner);
		
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
			toggleViewVisibility(ON, weaponProfLayout, weaponTextView1);
			
			if(subRaceInfo.hasWeaponChoice()){
				toggleViewVisibility(ON, weaponSpinner1);
//				weaponSpinner1.setVisibility(Spinner.VISIBLE);
				//Create an ArrayAdapter
				ArrayList<String> weaponList= GameInfo.getWeaponsByType("Martial", "any", true);
				ArrayAdapter<String> weaponAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, weaponList);
				//Specify the layout to use when the list of choices appears
				weaponAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				weaponSpinner1.setAdapter(weaponAdapter1);
				//Sets the item selection listener
			}
			else{
				s += Joiner.on(", ").skipNulls().join(weaponProficiencies);
			}
		}
		weaponTextView1.setText(s);
	}
	
	private void toolSelectionSetup(){
		String[] toolProficiencies= subRaceInfo.getRacialToolTraining();
		StringBuilder s= new StringBuilder("Tools: ");
		if(toolProficiencies != null){
			
			for(String tool : toolProficiencies){
				if(CharacterRace.isToolChoice(tool)){
					s.append(", ");
					toggleViewVisibility(ON, toolSpinner);
					//Create an ArrayAdapter
					String choiceString= tool.substring(tool.indexOf("(") + 1, tool.indexOf(")"));
					String [] toolList= choiceString.split(" - ");
					final ArrayAdapter<String> toolAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, toolList);
					//Specify the layout to use when the list of choices appears
					toolAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
					//Apply the statAdapter to the spinner
					toolSpinner.setAdapter(toolAdapter);
				}
				else{ s.append(tool).append(", "); }
			}
			toolTextView.setText(s.substring(0, s.lastIndexOf(",")));
		}
	}
	
	private void cantripSelectionSetup(){
		CharacterRace charRace= GameInfo.getRace(subRaceName);
		CharacterSpell cantrip= charRace.getRacialCantrip();
		String s= "Cantrip: ";
		
		if(cantrip != null){
			toggleViewVisibility(ON, cantripLayout, cantripTextView);
			if(charRace.hasCantripChoice()){
				String key= cantrip.getName().substring(cantrip.getName().indexOf("(") + 1, cantrip.getName().indexOf(")"));
				String[] cantrips= GameInfo.getSpellList(key + " Cantrips");
				
				toggleViewVisibility(ON, cantripTextView2, cantripSpinner);
				String cantripStat= " (" + charRace.getRacialCantrip().getStatUsed() + ")";
				cantripTextView2.setText(cantripStat);
				
				//Create an ArrayAdapter;
				ArrayAdapter<String> cantripAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, cantrips);
				//Specify the layout to use when the list of choices appears
				cantripAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				cantripSpinner.setAdapter(cantripAdapter);
			}
			else{
				s += cantrip.getName() + " (" + cantrip.getStatUsed() + ")";
			}
			cantripTextView.setText(s);
		}
	}
	
	private void spellInfoSetup(){
		RacialSpellList racialSpells= GameInfo.getRace(subRaceName).getRacialSpellList();
		if(racialSpells != null){
			StringBuilder s= new StringBuilder("Racial Spells: ");
			for(CharacterSpell spell : racialSpells.getSpellList()){
				s.append(spell.getName()).append(" - Level ").append(spell.getLevelGained()).append("\n\t");
			}
			s.append("Regain: ").append(racialSpells.restRegain).append(", Casting Stat: ").append(racialSpells.getSpellList().get(0).getStatUsed());
			toggleViewVisibility(ON, racialSpellTextView);
			racialSpellTextView.setText(s);
		}
	}
	
	private void otherInfoSetup(){
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
			toggleViewVisibility(ON, otherInfoTextView);
			otherInfoTextView.setText(s.toString().replace("-", ", "));
		}
	}
	
	private void languageSelectionSetup(){
		toggleViewVisibility(ON, languageSpinner);
		
		//Create an ArrayAdapter
		ArrayList<String> languages= new ArrayList<>(Arrays.asList(languageList));
		for(String language : languageExclusions){ languages.remove(language); }
		final ArrayAdapter<String> languageAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, languages);
		//Specify the layout to use when the list of choices appears
		languageAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		languageSpinner.setAdapter(languageAdapter);
	}
	//endregion
	
	//region Class Set-Up Methods
	private void classEquipmentSelectionSetup(){
		ClassEquipmentList equipment= GameInfo.getClassEquipmentList(className);
		
		setupClassPackEquipment(equipment);
		setupClassArmorEquipment(equipment);
		setupClassWeaponEquipment(equipment);
		setupClassToolEquipment(equipment);
	}
	
	public void setupClassPackEquipment(ClassEquipmentList equipment){
		packLabel.setPaintFlags(packLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		String[] packChoice= equipment.pack.split(" \\(or\\) ");
		
		if(packChoice.length > 1){
			toggleViewVisibility(ON, equipmentPackSpinner);
			toggleViewVisibility(OFF, packText);
			
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
		String[] armorChoice= equipment.armor.split(" \\(or\\) ");
		
		if(armorChoice.length > 1){
			toggleViewVisibility(ON, equipmentArmorLayout, armorLabel, armorText, equipmentArmorSpinner);
			armorLabel.setPaintFlags(armorLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, armorChoice);
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			equipmentArmorSpinner.setAdapter(spinnerAdapter);
		}
		else if("none".equalsIgnoreCase(armorChoice[0])){ toggleViewVisibility(OFF, equipmentArmorLayout, armorLabel); }
		else{
			toggleViewVisibility(ON, equipmentArmorLayout, armorLabel, armorText);
			toggleViewVisibility(OFF, equipmentArmorSpinner);
			
			armorLabel.setPaintFlags(armorLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			armorText.setText(armorChoice[0]);
		}
	}
	
	public void setupClassWeaponEquipment(ClassEquipmentList equipment){
		equipmentWeaponLayout.removeAllViews();
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
		toolLabel.setPaintFlags(toolLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		String[] toolChoice= equipment.tool.split(" \\(or\\) ");
		
		//noinspection IfStatementWithTooManyBranches
		if(toolChoice.length > 1){
			toggleViewVisibility(ON, toolLabel, equipmentToolLayout);
			
			Spinner spinner= new Spinner(this, Spinner.MODE_DIALOG);
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, toolChoice);
			
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			spinner.setAdapter(spinnerAdapter);
			spinner.setBackground(getDrawable(R.drawable.text_input_border));
			
			equipmentToolLayout.addView(spinner);
		}
		else if("instrument".equalsIgnoreCase(toolChoice[0])){
			toggleViewVisibility(ON, equipmentToolLayout, toolLabel);
			
			Spinner spinner= new Spinner(this, Spinner.MODE_DIALOG);
			
			ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.instrument_list));
			
			spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
			spinner.setAdapter(spinnerAdapter);
			spinner.setBackground(getDrawable(R.drawable.text_input_border));
			
			equipmentToolLayout.addView(spinner);
		}
		else if(!"none".equalsIgnoreCase(toolChoice[0])){
			toggleViewVisibility(ON, equipmentToolLayout, toolLabel);
			
			TextView toolText= new TextView(this);
			toolText.setText(toolChoice[0]);
			toolText.setTextColor(getResources().getColor(R.color.textColorPrimary));
			toolText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			
			equipmentToolLayout.addView(toolText);
		}
		else{ toggleViewVisibility(OFF, equipmentToolLayout, toolLabel); }
	}
	//endregion
	
	//region Complete Character Creation
	public void completeCharacter(View view){
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
		
		if(asiSpinner1.getVisibility() == ON){
			asi1= asiSpinner1.getSelectedItem().toString();
			asi2= asiSpinner2.getSelectedItem().toString();
		}
		if(skillSpinner1.getVisibility() == ON){
			skill1= skillSpinner1.getSelectedItem().toString();
			if(skillSpinner2.getVisibility() == ON){
				skill2= skillSpinner2.getSelectedItem().toString();
			}
		}
		if(featSpinner.getVisibility() == ON){ feat= featSpinner.getSelectedItem().toString(); }
		if(languageSpinner.getVisibility() == ON){ language= languageSpinner.getSelectedItem().toString(); }
		if(cantripSpinner.getVisibility() == ON){ cantrip= cantripSpinner.getSelectedItem().toString(); }
		if(toolSpinner.getVisibility() == ON){ tool= toolSpinner.getSelectedItem().toString(); }
		if(weaponSpinner1.getVisibility() == ON){
			weapon1= weaponSpinner1.getSelectedItem().toString();
			weapon2= weaponSpinner2.getSelectedItem().toString();
		}
		
		CharacterRace characterRace= new CharacterRace(subRaceInfo, asi1, asi2, skill1, skill2, feat, language, cantrip, tool, weapon1, weapon2);
		
		int str= 13;
		int dex= 13;
		int con= 12;
		int intel= 12;
		int wis= 12;
		int cha= 12;
		
		PlayerCharacter.makePlayerCharacter(nameBox.getText().toString(), characterRace, str, dex, con, intel, wis, cha, classFragment.charClass,
				getCharPackList(), getCharArmorList(), getCharWeaponList(), getCharToolList());
		
		if((Objects.requireNonNull(classFragment)).applyLevelToPC()){ startActivity(new Intent(this, CharacterDisplayActivity.class)); }
	}
	
	private ArrayList<String> getCharPackList(){
		String pack;
		pack= equipmentPackSpinner.getVisibility() == ON ? equipmentPackSpinner.getSelectedItem().toString() : packText.getText().toString();
		ArrayList<String> charEquipment= new ArrayList<>(Arrays.asList(GameInfo.getEquipmentPack(pack)));
		
		if(equipmentToolLayout.getVisibility() == ON){
			View view= equipmentToolLayout.getChildAt(0);
			if(view instanceof Spinner){ charEquipment.add(((Spinner) view).getSelectedItem().toString()); }
			else if(view instanceof TextView){ charEquipment.add(((TextView) view).getText().toString()); }
		}
		return charEquipment;
	}
	
	private ArrayList<String> getCharArmorList(){
		ArrayList<String> charArmors= new ArrayList<>();
		
		if(equipmentArmorLayout.getVisibility() == ON){
			if(equipmentArmorSpinner.getVisibility() == ON){ charArmors.add(equipmentArmorSpinner.getSelectedItem().toString()); }
			else{ charArmors.add(armorText.getText().toString()); }
		}
		return charArmors;
	}
	
	private ArrayList<String> getCharWeaponList(){
		ArrayList<String> charWeapons= new ArrayList<>();
		
		for(int i= 0; i < equipmentWeaponLayout.getChildCount(); i++){
			View v= equipmentWeaponLayout.getChildAt(i);
			if(v instanceof Spinner){ charWeapons.add(((Spinner) v).getSelectedItem().toString()); }
			else if(v instanceof TextView){ charWeapons.add(((TextView) v).getText().toString()); }
		}
		return charWeapons;
	}
	
	private ArrayList<String> getCharToolList(){
		ArrayList<String> charTools= new ArrayList<>();
		
		for(int i= 0; i < equipmentToolLayout.getChildCount(); i++){
			View v= equipmentToolLayout.getChildAt(i);
			String tool= "";
			if(v instanceof Spinner){ tool= ((Spinner) v).getSelectedItem().toString(); }
			else if(v instanceof TextView){ tool= ((TextView) v).getText().toString(); }
			
			charTools.addAll(Arrays.asList(tool.split(", ")));
		}
		return charTools;
	}
	//endregion
	
	//region onItemSelected
	@OnItemSelected(R.id.race_spinner)
	public void onRaceItemSelected(AdapterView<?> parent){
		int index= parent.getSelectedItemPosition();
		
		//Creates radio buttons for subrace selection
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
				
				classFragment.skillsAlreadySelected.clear();
				racialSkillSelectionSetup();
				classFragment.populateSkillSelection();
				
				if(subRaceInfo.hasFeatChoice()){ featSelectionSetup(); }
				
				CharacterAttack racialAttack= GameInfo.getRace(subRaceName).getRacialAttack();
				if(racialAttack != null){
					racialAttackTextView.setText(racialAttack.toString());
					racialAttackTextView.setVisibility(TextView.VISIBLE);
				}
				
				weaponSelectionSetup();
				
				toolSelectionSetup();
				
				cantripSelectionSetup();
				
				spellInfoSetup();
				
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
			RadioButton rb= new RadioButton(NewCharacterActivity.this);
			subRaceSelection.addView(rb);
			rb.setText(raceInfo[i]);
			rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			if(i == 1){ subRaceSelection.check(rb.getId()); } //sets first radio button as default
		}
	}
	
	@OnItemSelected(R.id.asi_select_spinner1)
	public void onASIItemSelected(AdapterView<?> parent){
		String selection= parent.getSelectedItem().toString();
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
	
	@OnItemSelected(R.id.skill_select_spinner1)
	public void onSkillItemSelected(AdapterView<?> parent){
		String selection= parent.getSelectedItem().toString();
		//Create an ArrayAdapter
		ArrayList<String> skillList2= new ArrayList<>(skillList);
		skillList2.remove(selection);
		ArrayAdapter<String> skillAdapter2= new ArrayAdapter<>(this, R.layout.spinner_item, skillList2);
		//Specify the layout to use when the list of choices appears
		skillAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the Adapter to the spinner
		skillSpinner2.setAdapter(skillAdapter2);
		
		classFragment.skillsAlreadySelected.clear();
		if(skillSpinner2.getVisibility() == OFF){
			classFragment.skillsAlreadySelected.add(skillSpinner1.getSelectedItem().toString());
			classFragment.populateSkillSelection();
		}
	}
	
	@OnItemSelected(R.id.skill_select_spinner2)
	public void onSkillItem2Selected(AdapterView<?> parent){
		classFragment.skillsAlreadySelected.clear();
		classFragment.skillsAlreadySelected.add(skillSpinner1.getSelectedItem().toString());
		classFragment.skillsAlreadySelected.add(skillSpinner2.getSelectedItem().toString());
		classFragment.populateSkillSelection();
	}
	
	@OnItemSelected(R.id.weapon_select_spinner1)
	public void onWeaponItemSelected(AdapterView<?> parent){
		String selection= parent.getSelectedItem().toString();
		toggleViewVisibility(ON, weaponSpinner2, weaponTextView2);
		
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
	public void onClassItemSelected(){
		className= classFragment.charClass.getName();
		classFragment.populateSkillSelection();
		classEquipmentSelectionSetup();
	}
	//endregion
}
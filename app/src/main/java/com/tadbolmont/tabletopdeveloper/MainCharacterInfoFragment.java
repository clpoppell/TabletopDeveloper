package com.tadbolmont.tabletopdeveloper;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.common.base.Joiner;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.val;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.ValueSetClassFeature;
import tabletop_5e_character_design.equipment.Weapon;

import static com.tadbolmont.tabletopdeveloper.GameInfo.formatNumbersPlusMinus;

public class MainCharacterInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener,
		CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener{
	public MainCharacterInfoFragment(){}
	//region Fields
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	View v;
	
	EditText strDisplay;
	EditText dexDisplay;
	EditText conDisplay;
	EditText intDisplay;
	EditText wisDisplay;
	EditText chaDisplay;
	
	TextView strModDisplay;
	TextView dexModDisplay;
	TextView conModDisplay;
	TextView intModDisplay;
	TextView wisModDisplay;
	TextView chaModDisplay;
	
	TextView strSavingThrowDisplay;
	TextView dexSavingThrowDisplay;
	TextView conSavingThrowDisplay;
	TextView intSavingThrowDisplay;
	TextView wisSavingThrowDisplay;
	TextView chaSavingThrowDisplay;
	//endregion
	
	@Override
	public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); }
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_main_character_info, container, false);
		
		strDisplay= v.findViewById(R.id.str_editText);
		dexDisplay= v.findViewById(R.id.dex_editText);
		conDisplay= v.findViewById(R.id.con_editText);
		intDisplay= v.findViewById(R.id.int_editText);
		wisDisplay= v.findViewById(R.id.wis_editText);
		chaDisplay= v.findViewById(R.id.cha_editText);
		
		strModDisplay= v.findViewById(R.id.str_mod);
		dexModDisplay= v.findViewById(R.id.dex_mod);
		conModDisplay= v.findViewById(R.id.con_mod);
		intModDisplay= v.findViewById(R.id.int_mod);
		wisModDisplay= v.findViewById(R.id.wis_mod);
		chaModDisplay= v.findViewById(R.id.cha_mod);
		
		strSavingThrowDisplay= v.findViewById(R.id.str_saving_throw);
		dexSavingThrowDisplay= v.findViewById(R.id.dex_saving_throw);
		conSavingThrowDisplay= v.findViewById(R.id.con_saving_throw);
		intSavingThrowDisplay= v.findViewById(R.id.int_saving_throw);
		wisSavingThrowDisplay= v.findViewById(R.id.wis_saving_throw);
		chaSavingThrowDisplay= v.findViewById(R.id.cha_saving_throw);
		
		populate();
		
		return v;
	}
	
	//TODO Work on Attack Display
	//region Populate Methods
	private void populate(){
		TextView nameDisplay= v.findViewById(R.id.char_name);
		nameDisplay.setText(character.getName());
		
		TextView raceNameDisplay= v.findViewById(R.id.char_race);
		raceNameDisplay.setText(character.getCharRace().getName());
		
		TextView classNameDisplay= v.findViewById(R.id.char_class);
		val classList= character.getClassList();
		classNameDisplay.setText(Joiner.on("\n").join(classList));
		
		populateStatDisplay();
		populateSkillDisplay();
		populateDefensesDisplay();
		populateHealthDisplay();
		populateAttacksDisplay();
	}
	
	private void populateStatDisplay(){
		TextView statNumberLabel= v.findViewById(R.id.stat_number_label);
		statNumberLabel.setPaintFlags(statNumberLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView statModLabel= v.findViewById(R.id.stat_mod_label);
		statModLabel.setPaintFlags(statModLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView savingThrowLabel= v.findViewById(R.id.saving_throw_label);
		savingThrowLabel.setPaintFlags(savingThrowLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		strDisplay.setText(formatNumbersLeadingZeros(character.getStrength()));
		dexDisplay.setText(formatNumbersLeadingZeros(character.getDexterity()));
		conDisplay.setText(formatNumbersLeadingZeros(character.getConstitution()));
		intDisplay.setText(formatNumbersLeadingZeros(character.getIntelligence()));
		wisDisplay.setText(formatNumbersLeadingZeros(character.getWisdom()));
		chaDisplay.setText(formatNumbersLeadingZeros(character.getCharisma()));
		
		strDisplay.setEnabled(false);
		dexDisplay.setEnabled(false);
		conDisplay.setEnabled(false);
		intDisplay.setEnabled(false);
		wisDisplay.setEnabled(false);
		chaDisplay.setEnabled(false);
		
		strModDisplay.setText(formatNumbersPlusMinus(character.getStrMod()));
		dexModDisplay.setText(formatNumbersPlusMinus(character.getDexMod()));
		conModDisplay.setText(formatNumbersPlusMinus(character.getConMod()));
		intModDisplay.setText(formatNumbersPlusMinus(character.getIntMod()));
		wisModDisplay.setText(formatNumbersPlusMinus(character.getWisMod()));
		chaModDisplay.setText(formatNumbersPlusMinus(character.getChaMod()));
		
		strSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getStrSaving()));
		dexSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getDexSaving()));
		conSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getConSaving()));
		intSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getIntSaving()));
		wisSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getWisSaving()));
		chaSavingThrowDisplay.setText(formatNumbersPlusMinus(character.getChaSaving()));
	}
	
	private void populateSkillDisplay(){
		((TextView)v.findViewById(R.id.acrobatics_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Acrobatics"))); //Acrobatics mod display
		((TextView)v.findViewById(R.id.animal_handling_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Animal Handling"))); //Animal Handling mod display
		((TextView)v.findViewById(R.id.arcana_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Arcana"))); //Arcana mod display
		((TextView)v.findViewById(R.id.athletics_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Athletics"))); //Athletics mod display
		((TextView)v.findViewById(R.id.deception_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Deception"))); //Deception mod display
		((TextView)v.findViewById(R.id.history_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("History"))); //History mod display
		((TextView)v.findViewById(R.id.insight_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Insight"))); //Insight mod display
		((TextView)v.findViewById(R.id.intimidation_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Intimidation"))); //Intimidation mod display
		((TextView)v.findViewById(R.id.investigation_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Investigation"))); //Investigation mod display
		((TextView)v.findViewById(R.id.medicine_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Medicine"))); //Medicine mod display
		((TextView)v.findViewById(R.id.nature_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Nature"))); //Nature mod display
		((TextView)v.findViewById(R.id.perception_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Perception"))); //Perception mod display
		((TextView)v.findViewById(R.id.performance_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Performance"))); //Performance mod display
		((TextView)v.findViewById(R.id.persuasion_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Persuasion"))); //Persuasion mod display
		((TextView)v.findViewById(R.id.religion_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Religion"))); //Religion mod display
		((TextView)v.findViewById(R.id.sleight_of_hand_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Sleight of Hand"))); //Sleight of Hand mod display
		((TextView)v.findViewById(R.id.stealth_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Stealth"))); //Stealth mod display
		((TextView)v.findViewById(R.id.survival_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Survival"))); //Survival mod display
	}
	
	private void populateDefensesDisplay(){
		TextView acLabel= v.findViewById(R.id.ac_label);
		acLabel.setPaintFlags(acLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		populateArmorChoices();
		((CheckBox)v.findViewById(R.id.shield_checkBox)).setOnCheckedChangeListener(this);
		
		TextView resistanceLabel= v.findViewById(R.id.resistance_label);
		resistanceLabel.setPaintFlags(resistanceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView resistanceTextView= v.findViewById(R.id.resistance_list);
		StringBuilder s= new StringBuilder("");
		Map<GameInfo.damageType, Double> resis= character.getResistances();
		for(GameInfo.damageType type : resis.keySet()){
			s.append(type).append(" - ");
			
			double resisAmout= resis.get(type);
			if(resisAmout == 0.5){ s.append("R\n"); }
			else if(resisAmout == 1.0){ s.append("I\n"); }
		}
		resistanceTextView.setText(s.toString());
	}
	
	private void populateACDisplay(){
		int acBase= 10;
		int dexMod= character.getDexMod();
		int miscMod= 0;
		String armorChoiceName= ((Spinner)v.findViewById(R.id.armor_choices)).getSelectedItem().toString();
		
		EditText miscStatDisplay= v.findViewById(R.id.misc_stat_bonus_display);
		TextView miscStatLabel= v.findViewById(R.id.misc_stat_bonus_label);
		
		if("None".equalsIgnoreCase(armorChoiceName)){
			miscStatDisplay.setText("");
			miscStatLabel.setText("Misc\nMod");
		}
		else if(character.getClassFeatures().containsKey(armorChoiceName)){
			String[] formula=((ValueSetClassFeature) character.getClassFeatures().get(armorChoiceName)).value.split(" \\+ ");
			for(String part : formula){
				//noinspection IfStatementWithNegatedCondition
				if(!part.matches("\\d+")){
					switch(part){
						case "Str":
							miscStatDisplay.setText(character.getStrMod() + "");
							miscStatLabel.setText("Str\nMod");
							miscMod= character.getStrMod();
							break;
						case "Dex":
							break;
						case "Con":
							miscStatDisplay.setText(character.getConMod() + "");
							miscStatLabel.setText("Con\nMod");
							miscMod=character.getConMod();
							break;
						case "Int":
							miscStatDisplay.setText(character.getIntMod() + "");
							miscStatLabel.setText("Int\nMod");
							miscMod=character.getIntMod();
							break;
						case "Wis":
							miscStatDisplay.setText(character.getWisMod() + "");
							miscStatLabel.setText("Wis\nMod");
							miscMod=character.getWisMod();
							break;
						case "Cha":
							miscStatDisplay.setText(character.getChaMod() + "");
							miscStatLabel.setText("Cha\nMod");
							miscMod=character.getChaMod();
							break;
					}
				}
				else{ acBase=Integer.parseInt(part); }
			}
		}
		int shieldAC= 0;
		if(((CheckBox)v.findViewById(R.id.shield_checkBox)).isChecked()){
			shieldAC= 2;
			((EditText)v.findViewById(R.id.shield_bonus_display)).setText(shieldAC + "");
		}
		else{ ((EditText)v.findViewById(R.id.shield_bonus_display)).setText(""); }
		
		int totalAC= acBase + dexMod + shieldAC + miscMod;
		
		((EditText)v.findViewById(R.id.armor_bonus_display)).setText(acBase + "");
		((EditText)v.findViewById(R.id.dexterity_mod_display)).setText(character.getDexMod() + "");
		((TextView)v.findViewById(R.id.ac_display)).setText(totalAC + "");
	}
	
	private void populateArmorChoices(){
		Spinner armorChoiceSpinner= v.findViewById(R.id.armor_choices);
		List<String> armorChoices= new ArrayList<>();
		armorChoices.add("None");
		
		for(ClassFeature feature : character.getClassFeatures().values()){
			if(feature instanceof ValueSetClassFeature && ((ValueSetClassFeature)feature).valueToSet.equals("AC")){
				armorChoices.add(feature.getName());
			}
		}
		
		ArrayAdapter<String> armorChoiceAdapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, armorChoices);
		armorChoiceAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		armorChoiceSpinner.setAdapter(armorChoiceAdapter);
		armorChoiceSpinner.setOnItemSelectedListener(this);
	}
	
	public void populateHealthDisplay(){
		EditText currentHPDisplay= v.findViewById(R.id.current_hp_display);
		EditText maxHPDisplay= v.findViewById(R.id.max_hp_display);
		EditText tempHPDisplay= v.findViewById(R.id.temp_hp_display);
		
		currentHPDisplay.setText(character.getCurrentHitPoints() + "");
		maxHPDisplay.setText(character.getMaxHitPoints() + "");
		tempHPDisplay.setText(character.getTempHP() + "");
		
		currentHPDisplay.setOnFocusChangeListener(this);
		currentHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		tempHPDisplay.setOnFocusChangeListener(this);
		tempHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		TextView hitDiceLabel= v.findViewById(R.id.hit_dice_label);
		hitDiceLabel.setPaintFlags(hitDiceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		LinearLayout hitDiceLayout= v.findViewById(R.id.hit_dice_layout);
		for(int i= hitDiceLayout.getChildCount() - 1; i > 0; i--){
			hitDiceLayout.removeView(hitDiceLayout.getChildAt(i));
		}
		for(CharacterClass charClass : character.getClassList()){
			TextView textView= new TextView(getActivity());
			textView.setText("" + charClass.getLevel() + "D" + charClass.getHitDice() + " + " + character.getConMod());
			textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			
			hitDiceLayout.addView(textView);
		}
	}
	
	private void populateAttacksDisplay(){
		TableLayout attackDisplay= v.findViewById(R.id.attacks_display_layout);
		List<String> attackList= new ArrayList<>(character.getWeaponList().keySet());
		attackList.add("Unarmed Strike");
		
		for(String attack : attackList){
			Weapon w= GameInfo.getWeapon(attack);
			TableRow row= new TableRow(getActivity());
			TextView name= new TextView(getActivity());
			TextView range= new TextView(getActivity());
			Button toHit= new Button(getActivity());
			Button damage= new Button(getActivity());
			TextView damageType= new TextView(getActivity());
			
			name.setText(w.getName());
			range.setText(w.getRange());
			
			int mod= w.usesDex() ? character.getDexMod() : character.getStrMod();
			
			toHit.setText(character.getWeaponProficiencies().contains(attack) ? formatNumbersPlusMinus(mod + character.getProficiencyBonus()) : formatNumbersPlusMinus(mod));
			
			damage.setText(w.getDamage() + "" + formatNumbersPlusMinus(mod));
			damageType.setText(w.getDamageType());
			
			name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			range.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			toHit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			damage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			damageType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			
			name.setTextColor(getResources().getColor(R.color.textColorPrimary));
			range.setTextColor(getResources().getColor(R.color.textColorPrimary));
			toHit.setTextColor(getResources().getColor(R.color.textColorPrimary));
			damage.setTextColor(getResources().getColor(R.color.textColorPrimary));
			damageType.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			row.addView(name);
			row.addView(range);
			row.addView(toHit);
			row.addView(damage);
			row.addView(damageType);
			
			attackDisplay.addView(row);
		}
	}
	//endregion
	
	private String formatNumbersLeadingZeros(int n){
		if(Integer.toString(n).length() < 2){ return "0"+n; }
		return "" + n;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id){ populateACDisplay(); }
	
	@Override
	public void onNothingSelected(AdapterView<?> parent){}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){ populateACDisplay(); }
	
	@Override
	public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus){ ((EditText)v).setText(""); }
		else{
			String number= ((EditText)v).getText().toString();
			int hp= StringUtils.isNumeric(number) ? Integer.parseInt(number) : 0;
			
			switch(v.getId()){
				case R.id.current_hp_display:
					character.setCurrentHitPoints(hp);
					((EditText) v).setText(character.getCurrentHitPoints() + "");
					break;
				case R.id.temp_hp_display:
					character.setTempHP(hp);
					((EditText) v).setText(character.getTempHP() + "");
					break;
			}
		}
	}
}

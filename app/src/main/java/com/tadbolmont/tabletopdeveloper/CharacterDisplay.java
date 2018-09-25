package com.tadbolmont.tabletopdeveloper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.val;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.Weapon;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.ValueSetClassFeature;

public class CharacterDisplay extends Activity implements DiceRollDialogFragment.DiceRollDialogListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener{
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
	
	private PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_display);
		
		strDisplay= findViewById(R.id.str_editText);
		dexDisplay= findViewById(R.id.dex_editText);
		conDisplay= findViewById(R.id.con_editText);
		intDisplay= findViewById(R.id.int_editText);
		wisDisplay= findViewById(R.id.wis_editText);
		chaDisplay= findViewById(R.id.cha_editText);
		
		strModDisplay= findViewById(R.id.str_mod);
		dexModDisplay= findViewById(R.id.dex_mod);
		conModDisplay= findViewById(R.id.con_mod);
		intModDisplay= findViewById(R.id.int_mod);
		wisModDisplay= findViewById(R.id.wis_mod);
		chaModDisplay= findViewById(R.id.cha_mod);
		
		strSavingThrowDisplay= findViewById(R.id.str_saving_throw);
		dexSavingThrowDisplay= findViewById(R.id.dex_saving_throw);
		conSavingThrowDisplay= findViewById(R.id.con_saving_throw);
		intSavingThrowDisplay= findViewById(R.id.int_saving_throw);
		wisSavingThrowDisplay= findViewById(R.id.wis_saving_throw);
		chaSavingThrowDisplay= findViewById(R.id.cha_saving_throw);
		
		populate();
	}
	
	//region Populate Methods
	private void populate(){
		TextView nameDisplay= findViewById(R.id.char_name);
		nameDisplay.setText(character.getName());
		
		TextView raceNameDisplay= findViewById(R.id.char_race);
		raceNameDisplay.setText(character.getCharRace().getName());
		
		TextView classNameDisplay= findViewById(R.id.char_class);
		val classList= character.getClassList();
		classNameDisplay.setText(Joiner.on("\n").join(classList));
		
		populateStatDisplay();
		populateSkillDisplay();
		populateDefensesDisplay();
		populateHealthDisplay();
		populateAttacksDisplay();
	}
	
	private void populateStatDisplay(){
		TextView statNumberLabel= findViewById(R.id.stat_number_label);
		statNumberLabel.setPaintFlags(statNumberLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView statModLabel= findViewById(R.id.stat_mod_label);
		statModLabel.setPaintFlags(statModLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView savingThrowLabel= findViewById(R.id.saving_throw_label);
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
		((TextView)findViewById(R.id.acrobatics_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Acrobatics"))); //Acrobatics mod display
		((TextView)findViewById(R.id.animal_handling_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Animal Handling"))); //Animal Handling mod display
		((TextView)findViewById(R.id.arcana_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Arcana"))); //Arcana mod display
		((TextView)findViewById(R.id.athletics_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Athletics"))); //Athletics mod display
		((TextView)findViewById(R.id.deception_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Deception"))); //Deception mod display
		((TextView)findViewById(R.id.history_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("History"))); //History mod display
		((TextView)findViewById(R.id.insight_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Insight"))); //Insight mod display
		((TextView)findViewById(R.id.intimidation_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Intimidation"))); //Intimidation mod display
		((TextView)findViewById(R.id.investigation_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Investigation"))); //Investigation mod display
		((TextView)findViewById(R.id.medicine_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Medicine"))); //Medicine mod display
		((TextView)findViewById(R.id.nature_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Nature"))); //Nature mod display
		((TextView)findViewById(R.id.perception_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Perception"))); //Perception mod display
		((TextView)findViewById(R.id.performance_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Performance"))); //Performance mod display
		((TextView)findViewById(R.id.persuasion_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Persuasion"))); //Persuasion mod display
		((TextView)findViewById(R.id.religion_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Religion"))); //Religion mod display
		((TextView)findViewById(R.id.sleight_of_hand_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Sleight of Hand"))); //Sleight of Hand mod display
		((TextView)findViewById(R.id.stealth_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Stealth"))); //Stealth mod display
		((TextView)findViewById(R.id.survival_modifier)).setText(formatNumbersPlusMinus(character.getSkillModifierForSkill("Survival"))); //Survival mod display
	}
	
	public void populateDefensesDisplay(){
		TextView acLabel= findViewById(R.id.ac_label);
		acLabel.setPaintFlags(acLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		populateArmorChoices();
		((CheckBox)findViewById(R.id.shield_checkBox)).setOnCheckedChangeListener(this);
		
		TextView resistanceLabel= findViewById(R.id.resistance_label);
		resistanceLabel.setPaintFlags(resistanceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView resistanceTextView= findViewById(R.id.resistance_list);
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
		String armorChoiceName= ((Spinner)findViewById(R.id.armor_choices)).getSelectedItem().toString();
		
		EditText miscStatDisplay= findViewById(R.id.misc_stat_bonus_display);
		TextView miscStatLabel= findViewById(R.id.misc_stat_bonus_label);
		
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
		if(((CheckBox)findViewById(R.id.shield_checkBox)).isChecked()){
			shieldAC= 2;
			((EditText)findViewById(R.id.shield_bonus_display)).setText(shieldAC + "");
		}
		else{ ((EditText)findViewById(R.id.shield_bonus_display)).setText(""); }
		
		int totalAC= acBase + dexMod + shieldAC + miscMod;
		
		((EditText)findViewById(R.id.armor_bonus_display)).setText(acBase + "");
		((EditText)findViewById(R.id.dexterity_mod_display)).setText(character.getDexMod() + "");
		((TextView)findViewById(R.id.ac_display)).setText(totalAC + "");
	}
	
	private void populateArmorChoices(){
		Spinner armorChoiceSpinner= findViewById(R.id.armor_choices);
		List<String> armorChoices= new ArrayList<>();
		armorChoices.add("None");
		
		for(ClassFeature feature : character.getClassFeatures().values()){
			if(feature instanceof ValueSetClassFeature && ((ValueSetClassFeature)feature).valueToSet.equals("AC")){
				armorChoices.add(feature.name);
			}
		}
		
		ArrayAdapter<String> armorChoiceAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, armorChoices);
		armorChoiceAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		armorChoiceSpinner.setAdapter(armorChoiceAdapter);
		armorChoiceSpinner.setOnItemSelectedListener(this);
	}
	
	private void populateHealthDisplay(){
		EditText currentHPDisplay= findViewById(R.id.current_hp_display);
		EditText maxHPDisplay= findViewById(R.id.max_hp_display);
		EditText tempHPDisplay= findViewById(R.id.temp_hp_display);
		
		currentHPDisplay.setText(character.getCurrentHitPoints() + "");
		maxHPDisplay.setText(character.getMaxHitPoints() + "");
		tempHPDisplay.setText(character.getTempHP() + "");
		
		currentHPDisplay.setOnFocusChangeListener(this);
		currentHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		tempHPDisplay.setOnFocusChangeListener(this);
		tempHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		TextView hitDiceLabel= findViewById(R.id.hit_dice_label);
		hitDiceLabel.setPaintFlags(hitDiceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		LinearLayout hitDiceLayout= findViewById(R.id.hit_dice_layout);
		for(int i= hitDiceLayout.getChildCount() - 1; i > 0; i--){
			hitDiceLayout.removeView(hitDiceLayout.getChildAt(i));
		}
		for(CharacterClass charClass : character.getClassList()){
			TextView textView= new TextView(this);
			textView.setText("" + charClass.getLevel() + "D" + charClass.getHitDice() + " + " + character.getConMod());
			textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			
			hitDiceLayout.addView(textView);
		}
	}
	
	private void populateAttacksDisplay(){
		TableLayout attackDisplay= findViewById(R.id.attacks_display_layout);
		List<String> attackList= new ArrayList<>(character.getWeaponList().keySet());
		attackList.add("Unarmed Strike");
		
		for(String attack : attackList){
			Weapon w= GameInfo.getWeapon(attack);
			TableRow row= new TableRow(this);
			TextView name= new TextView(this);
			TextView range= new TextView(this);
			Button toHit= new Button(this);
			Button damage= new Button(this);
			TextView damageType= new TextView(this);
			
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
	
	public void displayCharacterFeatures(View view){
		Intent intent= new Intent(this, CharacterFeatures.class);
		startActivity(intent);
	}
	//endregion
	
	//testing
	public void scroll(View view){
		final NestedScrollView scrollView= findViewById(R.id.char_display_scrollview);
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.smoothScrollTo(0, findViewById(R.id.attacks_display_layout).getBottom());
			}
		});
	}
	
	//TODO Change to go to LevelUpScreen activity
	public void levelUp(View view){
		character.addLevel("Barbarian");
		recreate();
	}
	
	@SuppressWarnings("unused")
	public void savingThrowRoll(View view){
		Bundle bundle= new Bundle();
		bundle.putInt("dieSize", 20);
		
		switch(view.getId()){
			case R.id.str_saving_throw_btn:
				bundle.putString("title", "Strength Saving Throw");
				bundle.putInt("mod", character.getStrSaving());
				break;
			case R.id.dex_saving_throw_btn:
				bundle.putString("title", "Dexterity Saving Throw");
				bundle.putInt("mod", character.getDexSaving());
				break;
			case R.id.con_saving_throw_btn:
				bundle.putString("title", "Constitution Saving Throw");
				bundle.putInt("mod", character.getConSaving());
				break;
			case R.id.int_saving_throw_btn:
				bundle.putString("title", "Intelligence Saving Throw");
				bundle.putInt("mod", character.getIntSaving());
				break;
			case R.id.wis_saving_throw_btn:
				bundle.putString("title", "Wisdom Saving Throw");
				bundle.putInt("mod", character.getWisSaving());
				break;
			case R.id.cha_saving_throw_btn:
				bundle.putString("title", "Charisma Saving Throw");
				bundle.putInt("mod", character.getChaSaving());
				break;
		}
		DiceRollDialogFragment dialog= new DiceRollDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "roll");
	}
	
	@SuppressWarnings("unused")
	public void skillRoll(View view){
		Bundle bundle= new Bundle();
		bundle.putInt("dieSize", 20);
		
		switch(view.getId()){
			case R.id.acrobatics_btn:
				bundle.putString("title", "Acrobatics");
				bundle.putInt("mod", character.getSkillModifierForSkill("Acrobatics"));
				break;
			case R.id.animal_handling_btn:
				bundle.putString("title", "Animal Handling");
				bundle.putInt("mod", character.getSkillModifierForSkill("Animal Handling"));
				break;
			case R.id.arcana_btn:
				bundle.putString("title", "Arcana");
				bundle.putInt("mod", character.getSkillModifierForSkill("Arcana"));
				break;
			case R.id.athletics_btn:
				bundle.putString("title", "Athletics");
				bundle.putInt("mod", character.getSkillModifierForSkill("Athletics"));
				break;
			case R.id.deception_btn:
				bundle.putString("title", "Deception");
				bundle.putInt("mod", character.getSkillModifierForSkill("Deception"));
				break;
			case R.id.history_btn:
				bundle.putString("title", "History");
				bundle.putInt("mod", character.getSkillModifierForSkill("History"));
				break;
			case R.id.insight_btn:
				bundle.putString("title", "Insight");
				bundle.putInt("mod", character.getSkillModifierForSkill("Insight"));
				break;
			case R.id.intimidation_btn:
				bundle.putString("title", "Intimidation");
				bundle.putInt("mod", character.getSkillModifierForSkill("Intimidation"));
				break;
			case R.id.investigation_btn:
				bundle.putString("title", "Investigation");
				bundle.putInt("mod", character.getSkillModifierForSkill("Investigation"));
				break;
			case R.id.medicine_btn:
				bundle.putString("title", "Medicine");
				bundle.putInt("mod", character.getSkillModifierForSkill("Medicine"));
				break;
			case R.id.nature_btn:
				bundle.putString("title", "Nature");
				bundle.putInt("mod", character.getSkillModifierForSkill("Nature"));
				break;
			case R.id.perception_btn:
				bundle.putString("title", "Perception");
				bundle.putInt("mod", character.getSkillModifierForSkill("Perception"));
				break;
			case R.id.performance_btn:
				bundle.putString("title", "Performance");
				bundle.putInt("mod", character.getSkillModifierForSkill("Performance"));
				break;
			case R.id.persuasion_btn:
				bundle.putString("title", "Persuasion");
				bundle.putInt("mod", character.getSkillModifierForSkill("Persuasion"));
				break;
			case R.id.religion_btn:
				bundle.putString("title", "Religion");
				bundle.putInt("mod", character.getSkillModifierForSkill("Religion"));
				break;
			case R.id.sleight_of_hand_btn:
				bundle.putString("title", "Sleight of Hand");
				bundle.putInt("mod", character.getSkillModifierForSkill("Sleight of Hand"));
				break;
			case R.id.stealth_btn:
				bundle.putString("title", "Stealth");
				bundle.putInt("mod", character.getSkillModifierForSkill("Stealth"));
				break;
			case R.id.survival_btn:
				bundle.putString("title", "Survival");
				bundle.putInt("mod", character.getSkillModifierForSkill("Survival"));
				break;
		}
		DiceRollDialogFragment dialog= new DiceRollDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "roll");
	}
	
	public void longRest(View view){
		character.setCurrentHitPoints(character.getMaxHitPoints());
		
		TableRow successes= findViewById(R.id.success_checkBoxes);
		for(int i= 1; i < successes.getChildCount(); i++){
			((CheckBox)successes.getChildAt(i)).setChecked(false);
		}
		
		TableRow failures= findViewById(R.id.failures_checkBoxes);
		for(int i= 1; i < failures.getChildCount(); i++){
			((CheckBox)failures.getChildAt(i)).setChecked(false);
		}
		
		populateHealthDisplay();
	}
	
	private String formatNumbersLeadingZeros(int n){
		if(Integer.toString(n).length() < 2){ return "0"+n; }
		return "" + n;
	}
	
	private String formatNumbersPlusMinus(int n){
		NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
		return plusMinusNF.format(n);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			View v= getCurrentFocus();
			if(v instanceof EditText){
				Rect outRect= new Rect();
				v.getGlobalVisibleRect(outRect);
				if(!outRect.contains((int)event.getRawX(), (int)event.getRawY())){
					v.clearFocus();
					InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					Objects.requireNonNull(imm).hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent( event );
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
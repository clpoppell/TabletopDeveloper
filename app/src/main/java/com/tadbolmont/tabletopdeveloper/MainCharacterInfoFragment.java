package com.tadbolmont.tabletopdeveloper;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.ValueSetClassFeature;
import tabletop_5e_character_design.equipment.Weapon;

import static com.tadbolmont.tabletopdeveloper.GameInfo.formatNumbersPlusMinus;

public class MainCharacterInfoFragment extends Fragment{
	
	//region Fields
	private PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	SparseArray<String> attackMap;
	private View v;
	
	@BindView(R.id.char_name) TextView nameDisplay;
	@BindView(R.id.char_race) TextView raceNameDisplay;
	@BindView(R.id.char_class) TextView classNameDisplay;
	
	@BindView(R.id.stat_number_label) TextView statNumberLabel;
	@BindView(R.id.str_editText) EditText strDisplay;
	@BindView(R.id.dex_editText) EditText dexDisplay;
	@BindView(R.id.con_editText) EditText conDisplay;
	@BindView(R.id.int_editText) EditText intDisplay;
	@BindView(R.id.wis_editText) EditText wisDisplay;
	@BindView(R.id.cha_editText) EditText chaDisplay;
	
	@BindView(R.id.stat_mod_label) TextView statModLabel;
	@BindView(R.id.str_mod) TextView strModDisplay;
	@BindView(R.id.dex_mod) TextView dexModDisplay;
	@BindView(R.id.con_mod) TextView conModDisplay;
	@BindView(R.id.int_mod) TextView intModDisplay;
	@BindView(R.id.wis_mod) TextView wisModDisplay;
	@BindView(R.id.cha_mod) TextView chaModDisplay;
	
	@BindView(R.id.saving_throw_label) TextView savingThrowLabel;
	@BindView(R.id.str_saving_throw) TextView strSavingThrowDisplay;
	@BindView(R.id.dex_saving_throw) TextView dexSavingThrowDisplay;
	@BindView(R.id.con_saving_throw) TextView conSavingThrowDisplay;
	@BindView(R.id.int_saving_throw) TextView intSavingThrowDisplay;
	@BindView(R.id.wis_saving_throw) TextView wisSavingThrowDisplay;
	@BindView(R.id.cha_saving_throw) TextView chaSavingThrowDisplay;
	
	@BindView(R.id.ac_label) TextView acLabel;
	@BindView(R.id.resistance_label) TextView resistanceLabel;
	@BindView(R.id.resistance_list) TextView resistanceTextView;
	@BindView(R.id.shield_checkBox) CheckBox shieldCheckBox;
	@BindView(R.id.armor_choices) Spinner armorChoices;
	@BindView(R.id.misc_stat_bonus_display) EditText miscStatDisplay;
	@BindView(R.id.misc_stat_bonus_label) TextView miscStatLabel;
	
	@BindView(R.id.shield_bonus_display) EditText shieldBonusDisplay;
	@BindView(R.id.armor_bonus_display) EditText armorBonusDisplay;
	@BindView(R.id.dexterity_mod_display) EditText acDexModDisplay;
	@BindView(R.id.ac_display) TextView acDisplay;
	
	@BindView(R.id.hit_dice_layout) LinearLayout hitDiceLayout;
	@BindView(R.id.current_hp_display) EditText currentHPDisplay;
	@BindView(R.id.max_hp_display) EditText maxHPDisplay;
	@BindView(R.id.temp_hp_display) EditText tempHPDisplay;
	@BindView(R.id.hit_dice_label) TextView hitDiceLabel;
	
	@BindView(R.id.attacks_display_layout) TableLayout attackDisplay;
	
	private Unbinder unbinder;
	//endregion
	
	public MainCharacterInfoFragment(){}
	
	//region View Methods
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_main_character_info, container, false);
		unbinder=ButterKnife.bind(this, v);
		return v;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState){ populate(); }
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		unbinder.unbind();
	}
	//endregion
	
	//TODO Work on Attack Display
	//region Populate Methods
	public void populate(){
		nameDisplay.setText(character.getName());
		raceNameDisplay.setText(character.getCharRace().getName());
		
		List<CharacterClass> classList= character.getClassList();
		classNameDisplay.setText(Joiner.on("\n").join(classList));
		
		populateStatDisplay();
		populateSkillDisplay();
		populateDefensesDisplay();
		populateHealthDisplay();
		populateAttacksDisplay();
	}
	
	private void populateStatDisplay(){
		statNumberLabel.setPaintFlags(statNumberLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		statModLabel.setPaintFlags(statModLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
		acLabel.setPaintFlags(acLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		resistanceLabel.setPaintFlags(resistanceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		populateArmorChoices();
		
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
		String armorChoiceName= armorChoices.getSelectedItem().toString();
		
		if("None".equalsIgnoreCase(armorChoiceName)){
			miscStatDisplay.setText("");
			miscStatLabel.setText("Misc\nMod");
		}
		else if(character.getClassFeatures().containsKey(armorChoiceName)){
			String[] formula=((ValueSetClassFeature) character.getClassFeatures().get(armorChoiceName)).getValue().split(" \\+ ");
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
							miscMod= character.getConMod();
							break;
						case "Int":
							miscStatDisplay.setText(character.getIntMod() + "");
							miscStatLabel.setText("Int\nMod");
							miscMod= character.getIntMod();
							break;
						case "Wis":
							miscStatDisplay.setText(character.getWisMod() + "");
							miscStatLabel.setText("Wis\nMod");
							miscMod= character.getWisMod();
							break;
						case "Cha":
							miscStatDisplay.setText(character.getChaMod() + "");
							miscStatLabel.setText("Cha\nMod");
							miscMod= character.getChaMod();
							break;
					}
				}
				else{ acBase= Integer.parseInt(part); }
			}
		}
		int shieldAC= 0;
		if(shieldCheckBox.isChecked()){
			shieldAC= 2;
			shieldBonusDisplay.setText(shieldAC + "");
		}
		else{ shieldBonusDisplay.setText(""); }
		
		int totalAC= acBase + dexMod + shieldAC + miscMod;
		
		armorBonusDisplay.setText(acBase + "");
		acDexModDisplay.setText(character.getDexMod() + "");
		acDisplay.setText(totalAC + "");
	}
	
	private void populateArmorChoices(){
		List<String> armorChoiceList= new ArrayList<>();
		armorChoiceList.add("None");
		
		for(ClassFeature feature : character.getClassFeatures().values()){
			if(feature instanceof ValueSetClassFeature && ((ValueSetClassFeature)feature).getValueToSet().equals("AC")){
				armorChoiceList.add(feature.getName());
			}
		}
		
		ArrayAdapter<String> armorChoiceAdapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, armorChoiceList);
		armorChoiceAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		armorChoices.setAdapter(armorChoiceAdapter);
	}
	
	public void populateHealthDisplay(){
		currentHPDisplay.setText(character.getCurrentHitPoints() + "");
		maxHPDisplay.setText(character.getMaxHitPoints() + "");
		tempHPDisplay.setText(character.getTempHP() + "");
		
		currentHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		tempHPDisplay.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		hitDiceLabel.setPaintFlags(hitDiceLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
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
		attackMap= new SparseArray<>();
		List<String> attacks= new ArrayList<>(character.getWeaponList().keySet());
		attacks.add("Unarmed Strike");
		int id= -1;
		
		for(String attack : attacks){
			id++;
			
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
			toHit.setOnClickListener(handleDamageRollClick(toHit));
			
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
			
			row.setId(id);
			
			attackDisplay.addView(row);
			attackMap.put(row.getId(), attack);
		}
	}
	//endregion
	
	private static String formatNumbersLeadingZeros(int n){
		if(Integer.toString(n).length() < 2){ return "0"+n; }
		return "" + n;
	}
	
	View.OnClickListener handleDamageRollClick(final Button button){
		return new View.OnClickListener(){
			public void onClick(View v){
				int key= ((View)button.getParent()).getId();
				String attackName= attackMap.get(key);
				Weapon w= GameInfo.getWeapon(attackName);
				
				int profeciency= character.getWeaponProficiencies().contains(attackName) ? character.getProficiencyBonus() : 0;
				int mod= w.usesDex() ? character.getDexMod() : character.getStrMod();
				int attackMod= mod + profeciency;
				
				Bundle bundle= new Bundle();
				bundle.putInt("dieSize", 20);
				bundle.putString("title", attackName);
				bundle.putInt("mod", attackMod);
				
				DiceRollDialogFragment dialog= new DiceRollDialogFragment();
				dialog.setArguments(bundle);
				dialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "roll");
			}
		};
	}
	
	@OnItemSelected(R.id.armor_choices)
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id){ populateACDisplay(); }
	
	@OnCheckedChanged(R.id.shield_checkBox)
	public void onShieldEquipCheckedChange(){ populateACDisplay(); }
	
	@OnFocusChange({R.id.current_hp_display, R.id.temp_hp_display})
	public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus){ ((EditText)v).setText(""); }
		else{
			String number= ((EditText)v).getText().toString();
			int hp;
			if(StringUtils.isNumeric(number)){ hp= Integer.parseInt(number); }
			else{
				hp= v.getId() == R.id.current_hp_display ? character.getCurrentHitPoints() : character.getTempHP();
			}
			
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

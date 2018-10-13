package com.tadbolmont.tabletopdeveloper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import tabletop_5e_character_design.Archetype;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.CharacterSpell;
import tabletop_5e_character_design.ClassSkillList;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ChoiceClassFeature;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.ElementGainClassFeature;
import tabletop_5e_character_design.class_features.ValueSetClassFeature;

import static com.tadbolmont.tabletopdeveloper.App.OFF;
import static com.tadbolmont.tabletopdeveloper.App.ON;
import static com.tadbolmont.tabletopdeveloper.App.toggleViewVisibility;

/**
 * A simple {@link Fragment} subclass.
 */
//TODO Possibly rework to account for multi-part choice features
public class ClassSelectionFragment extends Fragment{
	//region Views
	@BindView(R.id.level_up_class_spinner) Spinner levelUpClassSpinner;
	@BindView(R.id.class_feature_textView) TextView classFeatureDisplay;
	@BindView(R.id.archetype_selection) RadioGroup archetypeSelection;
	@BindView(R.id.archetype_feature_textView) TextView archetypeFeatureTextView;
	
	@BindView(R.id.choice_selection) RadioGroup choiceSelection;
	@BindView(R.id.choice_feature_textView) TextView choiceFeatureTextView;
	@BindView(R.id.asi_choice_layout) LinearLayout asiChoiceLayout;
	
	@BindView(R.id.cantrip_selection_label) TextView cantripSelectionLabel;
	@BindView(R.id.cantrip_selection_layout) LinearLayout cantripChoiceLayout;
	@BindView(R.id.spell_selection_label) TextView spellSelectionLabel;
	@BindView(R.id.spell_selection_layout) LinearLayout spellChoiceLayout;
	
	@BindView(R.id.skill_selection_label) TextView skillSelectionLabel;
	@BindView(R.id.skill_selection_layout) LinearLayout skillSelectionLayout;
	//endregion
	
	//region Fields
	OnClassSelectedListener callback;
	private PlayerCharacter character;
	private View v;
	private Unbinder unbinder;
	
	CharacterClass charClass= GameInfo.getClass("Barbarian");
	private Map<String, CharacterClass> classMap;
	private Archetype archetype;
	private Map<String, ClassFeature> featuresToDisplay;
	private Map<String, ClassFeature> archetypeFeatures;
	
	private String choiceFeatureName;
	private Map<String, Integer> asiChoices;
	private int asiCount;
	
	private String castingStat;
	private List<String> cantrips;
	private List<String> spells;
	private boolean hasSpellcasterLevelIncrease;
	private boolean hasRitual;
	
	ClassSkillList classSkillList;
	private List<String> skillProficiencies;
	private List<String> expertise;
	List<String> skillsAlreadySelected= new ArrayList<>();
	private int numSkill;
	private int numExpertise;
	private int classSkillCount= 0;
	private int expertiseCount= 0;
	boolean hasSkillChoice= false;
	private boolean hasExpertiseChoice= false;
	//endregion
	
	public ClassSelectionFragment(){}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_class_selection, container, false);
		
		unbinder= ButterKnife.bind(this, v);
		populate();
		
		return v;
	}
	
	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		
		if(context instanceof OnClassSelectedListener){ callback= (OnClassSelectedListener)context; }
		else{ throw new RuntimeException(context.toString() + " must implement OnClassSelectedListener"); }
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		unbinder.unbind();
	}
	
	//TODO implement full class list
	private void populate(){
		character= PlayerCharacter.getPlayerCharacter();
		classMap= new LinkedHashMap<>();
		
		//String[] baseClassList= getResources().getStringArray(R.array.class_list);
		String[] baseClassList= new String[]{"Barbarian", "Bard", "Cleric"};
		
		if(callback instanceof NewCharacterActivity){
			for(String s : baseClassList){ classMap.put(s, GameInfo.getClass(s)); }
		}
		else if(character.getCharacterLevel() < 20){
			Set<CharacterClass> classList= new LinkedHashSet<>(character.getClassList());
			
			for(CharacterClass characterClass : classList){
				classMap.put(characterClass.getName(), characterClass);
			}
			for(String s : baseClassList){
				if(!classMap.containsKey(s)){ classMap.put(s, GameInfo.getClass(s)); }
			}
		}
		
		ArrayAdapter<String> classAdapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, new ArrayList<>(classMap.keySet()));
		classAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		levelUpClassSpinner.setAdapter(classAdapter);
	}
	
	private void populateArchetypeChoice(){
		toggleViewVisibility(ON, archetypeSelection, archetypeFeatureTextView);
		archetypeSelection.removeAllViews();
		
		final String[] archetypeChoice= charClass.getArchetypes();
		
		archetypeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId){
				toggleViewVisibility(OFF, choiceFeatureTextView, choiceSelection);
				
				RadioButton rb= group.findViewById(checkedId);
				archetype= GameInfo.getArchetype(rb.getText().toString());
				archetypeFeatures= putFeatures(archetype.getFeatureListForLevel(charClass.getLevel() + 1));
				
				StringBuilder s2= new StringBuilder("");
				for(String n : archetypeFeatures.keySet()){
					s2.append(archetypeFeatures.get(n)).append("\n");
					archetypeFeatureTextView.setText(s2.toString());
				}
			}
		});
		
		for(int i= 0; i < archetypeChoice.length; i++){
			RadioButton rb= new RadioButton(getActivity());
			archetypeSelection.addView(rb);
			rb.setText(archetypeChoice[i]);
			rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			if(i == 0){ archetypeSelection.check(rb.getId()); } //sets first radio button as default
		}
	}
	
	private void populateChoiceFeature(final ClassFeature choiceFeature){
		toggleViewVisibility(ON, choiceSelection, choiceFeatureTextView);
		
		String[] choices= ((ChoiceClassFeature)choiceFeature).getChoices();
		
		choiceSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId){
				RadioButton rb= group.findViewById(checkedId);
				
				ClassFeature feature= GameInfo.getClassFeature(rb.getText().toString());
				
				choiceFeatureName= feature.getName();
				choiceFeatureTextView.setText(choiceFeatureName + "\n\t" + feature.getDesc());
			}
		});
		
		choiceSelection.removeAllViews();
		for(int i= 0; i < choices.length; i++){
			RadioButton rb= new RadioButton(getActivity());
			choiceSelection.addView(rb);
			rb.setText(choices[i]);
			rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			if(i == 0){ choiceSelection.check(rb.getId()); } //sets first radio button as default
		}
	}
	
	private void populateASI(){
		toggleViewVisibility(ON, asiChoiceLayout);
		
		for(int i= 0; i < asiChoiceLayout.getChildCount(); i++){
			((CheckBox)asiChoiceLayout.getChildAt(i)).setChecked(false);
			((CheckBox)asiChoiceLayout.getChildAt(i)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
					if(asiCount == 2 && isChecked){ buttonView.setChecked(false); }
					else if(isChecked){ asiCount++; }
					else{ asiCount--; }
				}
			});
		}
	}
	
	private void populateCantripSelection(Iterator<String> features){
		toggleViewVisibility(ON, cantripSelectionLabel, cantripChoiceLayout);
		cantripChoiceLayout.removeAllViews();
		
		while(features.hasNext()){
			String f= features.next();
			ClassFeature feature= GameInfo.getClassFeature(f);
			
			if(feature instanceof ValueSetClassFeature){
				castingStat= ((ValueSetClassFeature)feature).getValue();
				break;
			}
			else{ cantripChoiceLayout.addView(createSpellSelection((ElementGainClassFeature)feature)); }
		}
	}
	
	private void populateSpellSelection(Iterator<String> features){
		toggleViewVisibility(ON, spellSelectionLabel, spellChoiceLayout);
		spellChoiceLayout.removeAllViews();
		hasRitual= false;
		
		while(features.hasNext()){
			String f= features.next();
			ClassFeature feature= GameInfo.getClassFeature(f);
			
			switch(f){
				case "(Spellcasting Cha casting stat)":
					castingStat= ((ValueSetClassFeature) feature).getValue();
					break;
				case "(Spellcasting level increase)":
					hasSpellcasterLevelIncrease= true;
					break;
				case "(Ritual Casting)":
					hasRitual= true;
					break;
				default:
					spellChoiceLayout.addView(createSpellSelection((ElementGainClassFeature)feature));
			}
		}
	}
	
	private Spinner createSpellSelection(ElementGainClassFeature feature){
		List<String> spellChoices= new ArrayList<>();
		
		String[] elements= feature.getElements();
		for(String element : elements){
			if(element.contains("*")){
				String[] keys= element.substring(1).split(" ");
				String className= keys[0];
				int level= Integer.parseInt(keys[1]);
				spellChoices.addAll(Arrays.asList(GameInfo.getSpellList(className, level)));
			}
		}
		Spinner spinner= new Spinner(getActivity());
		ArrayAdapter<String> adapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, spellChoices);
		adapter.setDropDownViewResource(R.layout.spinner_item_bg);
		spinner.setAdapter(adapter);
		spinner.setBackground(getActivity().getDrawable(R.drawable.text_input_border));
		
		return spinner;
	}
	
	void populateSkillSelection(){
		toggleViewVisibility(ON, skillSelectionLabel, skillSelectionLayout);
		classSkillCount= 0;
		expertiseCount= 0;
		
		Set<String> skills;
		if(callback instanceof NewCharacterActivity || hasSkillChoice){
			classSkillList= charClass.getClassSkillChoices();
			skills= new TreeSet<>(Arrays.asList(classSkillList.getSkills()));
			numSkill= classSkillList.getNumSkills();
		}
		else{ skills= new TreeSet<>(character.getSkillProficiencies()); }
		skills.addAll(skillsAlreadySelected);
		
		skillSelectionLayout.removeAllViews();
		
		TableRow row1= new TableRow(getActivity());
		TextView skillLabel= new TextView(getActivity());
		setTextViewText(skillLabel, "Skills");
		row1.addView(skillLabel);
		skillSelectionLayout.addView(row1);
		
		if(hasExpertiseChoice){
			TextView expertiseLabel= new TextView(getActivity());
			setTextViewText(expertiseLabel, "Expertise");
			row1.addView(expertiseLabel);
		}
		for(String skill : skills){
			TableRow row= new TableRow(getActivity());
			final CheckBox skillCheckBox= new CheckBox(getActivity());
			
			if(skillsAlreadySelected.contains(skill)){
				skillCheckBox.setChecked(true);
				skillCheckBox.setClickable(false);
			}
			setTextViewText(skillCheckBox, skill);
			skillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
					if(classSkillCount == numSkill && isChecked){ buttonView.setChecked(false); }
					else if(isChecked){ classSkillCount++; }
					else{ classSkillCount--; }
				}
			});
			row.addView(skillCheckBox);
			
			if(hasExpertiseChoice){
				CheckBox expertiseCheckBox= new CheckBox(getActivity());
				
				Set<String> charSkills= character.getSkillProficiencies();
				if(!hasSkillChoice || charSkills.contains(skill)){
					skillCheckBox.setOnCheckedChangeListener(null);
					skillCheckBox.setChecked(true);
					skillCheckBox.setClickable(false);
				}
				expertiseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
						if(isChecked){
							if(!skillCheckBox.isChecked() || expertiseCount == numExpertise){ buttonView.setChecked(false); }
							else{ expertiseCount++; }
						}
						else{ expertiseCount--; }
					}
				});
				row.addView(expertiseCheckBox);
			}
			skillSelectionLayout.addView(row);
		}
	}
	
	private void setTextViewText(TextView view, String text){
		view.setText(text);
		view.setTextColor(getResources().getColor(R.color.textColorPrimary));
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
	}
	
	private void reset(){
		toggleViewVisibility(OFF, archetypeSelection, archetypeFeatureTextView, choiceSelection, choiceFeatureTextView, asiChoiceLayout,
				cantripSelectionLabel, cantripChoiceLayout, spellSelectionLabel, spellChoiceLayout, skillSelectionLabel, skillSelectionLayout);
		
		asiCount= 0;
		asiChoices= new HashMap<>();
		castingStat= null;
		cantrips= new ArrayList<>();
		spells= new ArrayList<>();
		skillProficiencies= new ArrayList<>();
		expertise= new ArrayList<>();
		if(callback instanceof LevelUpActivity){ hasSkillChoice= false; }
		hasExpertiseChoice= false;
	}
	
	private Map<String, ClassFeature> putFeatures(List<String> features){
		Map<String, ClassFeature> featureList= new LinkedHashMap<>();
		
		Iterator<String> fList= features.iterator();
		while(fList.hasNext()){
			String f= fList.next();
			if(f != null){
				switch(f){
					case "Archetype":
						populateArchetypeChoice();
						break;
					case "ASI":
						populateASI();
						break;
					case "Cantrips":
						populateCantripSelection(fList);
						break;
					case "Spellcasting":
						populateSpellSelection(fList);
						break;
					default:
						ClassFeature feature= GameInfo.getClassFeature(f);
						if(f.contains("(")){
							if(feature instanceof ElementGainClassFeature){
								if("Expertise".equalsIgnoreCase(((ElementGainClassFeature)feature).getElementGained()) && ((ElementGainClassFeature) feature).getElements()[0].contains("*")){
									hasExpertiseChoice= true;
									numExpertise= ((ElementGainClassFeature) feature).getElements().length;
								}
								else if("Skills".equalsIgnoreCase(((ElementGainClassFeature)feature).getElementGained()) && ((ElementGainClassFeature) feature).getElements()[0].contains("*")){
									hasSkillChoice= true;
									numSkill= ((ElementGainClassFeature) feature).getElements().length;
								}
							}
							if(f.substring(0, f.indexOf("(")).length() > 0){ featureList.put(f, feature); }
						}
						else{
							if(feature instanceof ChoiceClassFeature){ populateChoiceFeature(feature); }
							else{ featureList.put(f, feature); }
						}
				}
			}
		}
		return featureList;
	}
	
	boolean applyLevelToPC(){
		character= PlayerCharacter.getPlayerCharacter();
		cantrips= new ArrayList<>();
		spells= new ArrayList<>();
		skillProficiencies= new ArrayList<>();
		expertise= new ArrayList<>();
		
		if(asiChoiceLayout.getVisibility() == ON){
			if(asiCount == 0){
				Toast.makeText(getActivity(), "Ability Score Increase not selected.", Toast.LENGTH_SHORT).show();
				return false;
			}
			for(int i= 0; i < asiChoiceLayout.getChildCount(); i++){
				CheckBox c= (CheckBox)asiChoiceLayout.getChildAt(i);
				int asi= asiCount == 1 ? 2 : 1;
				if(c.isChecked()){ asiChoices.put(c.getText().toString(), asi); }
			}
		}
		
		if(cantripChoiceLayout.getVisibility() == ON){
			for(int i= 0; i < cantripChoiceLayout.getChildCount(); i++){
				String c= ((Spinner)cantripChoiceLayout.getChildAt(i)).getSelectedItem().toString();
				CharacterSpell cSpell= GameInfo.makeCharacterSpell(c, castingStat, charClass.getName());
				if(cantrips.contains(c)){
					Toast.makeText(getActivity(), "Cantrips not selected.", Toast.LENGTH_SHORT).show();
					return false;
				}
				else if(character.getSpells().contains(cSpell)){
					Toast.makeText(getActivity() ,"Spell already known.", Toast.LENGTH_SHORT).show();
					return false;
				}
				else{ cantrips.add(c); }
			}
		}
		
		if(spellChoiceLayout.getVisibility() == ON){
			for(int i= 0; i < spellChoiceLayout.getChildCount(); i++){
				String s= ((Spinner)spellChoiceLayout.getChildAt(i)).getSelectedItem().toString();
				CharacterSpell cSpell= GameInfo.makeCharacterSpell(s, castingStat, charClass.getName());
				if(spells.contains(s)){
					Toast.makeText(getActivity(), "Spells not selected.", Toast.LENGTH_SHORT).show();
					return false;
				}
				else if(character.getSpells().contains(cSpell)){
					Toast.makeText(getActivity() ,"Spell already known.", Toast.LENGTH_SHORT).show();
					return false;
				}
				else{ spells.add(s); }
			}
		}
		
		if(skillSelectionLayout.getVisibility() == ON){
			if(classSkillCount < numSkill){
				Toast.makeText(getActivity(), "Skills not selected.", Toast.LENGTH_SHORT).show();
				return false;
			}
			for(int i= 1; i < skillSelectionLayout.getChildCount(); i++){
				TableRow r= (TableRow)skillSelectionLayout.getChildAt(i);
				CheckBox skill= ((CheckBox)r.getChildAt(0));
				if(skill.isChecked()){ skillProficiencies.add(skill.getText().toString()); }
				if(hasExpertiseChoice){
					if(expertiseCount < numExpertise){
						Toast.makeText(getActivity(), "Expertise choices not selected.", Toast.LENGTH_SHORT).show();
						return false;
					}
					CheckBox ex= ((CheckBox)r.getChildAt(1));
					if(ex.isChecked()){ expertise.add(skill.getText().toString()); }
				}
			}
		}

		character.addLevel(charClass.getName());
		charClass= character.getClass(charClass.getName());
		List<String> featureList;

		if(archetypeSelection.getVisibility() == ON){ charClass.setArchetypeChoice(archetype); }
		
		featureList= charClass.getFeatureListForLevel(charClass.getLevel());
		
		Iterator<String> fList= featureList.iterator();
		while(fList.hasNext()){
			String f= fList.next();
			if(f == null){ break; }
			else if("ASI".equalsIgnoreCase(f)){
				fList.remove();
			}
			else if(GameInfo.getClassFeature(f) instanceof ChoiceClassFeature){
				fList.remove();
				featureList.add(choiceFeatureName);
				break;
			}
		}
		character.addClassFeatures(featureList)
				.applyASI(asiChoices)
				.addSkills(skillProficiencies)
				.addExpertise(expertise)
				.addCantrips(castingStat, charClass.getName(), cantrips)
				.addSpells(spells, castingStat, charClass.getName(), hasRitual);
		
		return true;
	}
	
	@OnItemSelected(R.id.level_up_class_spinner)
	public void onClassSpinnerItemSelected(AdapterView<?> parent){
		reset();
		
		StringBuilder s= new StringBuilder("");
		charClass= classMap.get(parent.getSelectedItem().toString());
		
		List<String> featuresForLevel= charClass.getFeatureListForLevel(charClass.getLevel() + 1);
		
		featuresToDisplay= putFeatures(featuresForLevel);
		for(String n : featuresToDisplay.keySet()){
			s.append(featuresToDisplay.get(n)).append("\n");
		}
		if(hasSkillChoice || hasExpertiseChoice){ populateSkillSelection(); }
		
		classFeatureDisplay.setText(s.toString());
		
		callback.onClassItemSelected();
	}
	
	public interface OnClassSelectedListener{
		void onClassItemSelected();
	}
}

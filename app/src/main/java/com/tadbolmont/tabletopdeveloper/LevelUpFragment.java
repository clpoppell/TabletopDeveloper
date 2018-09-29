package com.tadbolmont.tabletopdeveloper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import tabletop_5e_character_design.Archetype;
import tabletop_5e_character_design.CharacterClass;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ChoiceClassFeature;
import tabletop_5e_character_design.class_features.ClassFeature;

//TODO Possibly rework to account for multi-part choice features
public class LevelUpFragment extends Fragment implements AdapterView.OnItemSelectedListener{
	private static int ON= View.VISIBLE;
	private static int OFF= View.GONE;
	
	//region Fields
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	View v;
	
	Spinner levelUpClassSpinner;
	TextView classFeatureDisplay;
	RadioGroup archetypeSelection;
	TextView archetypeFeatureTextView;
	RadioGroup choiceSelection;
	TextView choiceFeatureTextView;
	
	Map<String, CharacterClass> classMap;
	
	CharacterClass charClass;
	Archetype archetype;
	Map<String, ClassFeature> classFeatures;
	Map<String, ClassFeature> archetypeFeatures;
	String choiceFeatureName;
	//endregion
	
	public LevelUpFragment(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); }
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v=inflater.inflate(R.layout.fragment_level_up_screen, container, false);
		
		levelUpClassSpinner= v.findViewById(R.id.level_up_class_spinner);
		classFeatureDisplay= v.findViewById(R.id.class_feature_textView);
		archetypeSelection= v.findViewById(R.id.archetype_selection);
		archetypeFeatureTextView= v.findViewById(R.id.archetype_feature_textView);
		choiceSelection= v.findViewById(R.id.choice_selection);
		choiceFeatureTextView= v.findViewById(R.id.choice_feature_textView);
		
		populate();
		
		return v;
	}
	
	//TODO Implement full class list
	private void populate(){
		Set<CharacterClass> classList= new LinkedHashSet<>(character.getClassList());
		classMap= new LinkedHashMap<>();
		
		for(CharacterClass characterClass : classList){
			classMap.put(characterClass.getName(), characterClass);
		}
		//String[] baseClassList= getResources().getStringArray(R.array.class_list);
		String[] baseClassList= new String[]{ "Barbarian", "Bard", "Cleric" };
		
		for(String s : baseClassList){
			if(!classMap.containsKey(s)){ classMap.put(s, GameInfo.getClass(s)); }
		}
		
		ArrayAdapter<String> classAdapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, new ArrayList<>(classMap.keySet()));
		classAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		levelUpClassSpinner.setAdapter(classAdapter);
		
		levelUpClassSpinner.setOnItemSelectedListener(this);
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
		
		for(int i= 0; i < choices.length; i++){
			RadioButton rb= new RadioButton(getActivity());
			choiceSelection.addView(rb);
			rb.setText(choices[i]);
			rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			if(i == 0){ choiceSelection.check(rb.getId()); } //sets first radio button as default
		}
	}
	
	private Map<String, ClassFeature> putFeatures(List<String> features){
		Map<String, ClassFeature> featureMap= new LinkedHashMap<>();
		
		for(String f : features){
			ClassFeature feature= GameInfo.getClassFeature(f);
			String p= feature.getParentFeature();
			
			if("none".equalsIgnoreCase(p)){
				if(feature instanceof ChoiceClassFeature){ populateChoiceFeature(feature); }
				else{ featureMap.put(feature.getName(), feature); }
			}
			else if(featureMap.containsKey(p)){ featureMap.get(p).addChildFeature(feature); }
			else{
				for(String cF : featureMap.keySet()){
					List<ClassFeature> children= new ArrayList<>(featureMap.get(cF).getChildFeatures());
					if(children.contains(GameInfo.getClassFeature(p))){
						children.get(children.indexOf(GameInfo.getClassFeature(p))).addChildFeature(feature);
						break;
					}
				}
				featureMap.put(feature.getName(), feature);
			}
		}
		
		return featureMap;
	}
	
	private void toggleViewVisibility(int state, View ... views){
		for(View v : views){
			v.setVisibility(state);
		}
	}

	public void applyLevelToPC(){
		character.addLevel(charClass.getName());
		charClass= character.getClass(charClass.getName());
		List<String> featureList;
		
		if(archetypeSelection.getVisibility() == ON){
			featureList= archetype.getFeatureListForLevel(charClass.getLevel());
			charClass.setArchetypeChoice(archetype);
		}
		else{ featureList= charClass.getFeatureListForLevel(charClass.getLevel()); }
		
		for(String f : featureList){
			if(GameInfo.getClassFeature(f) instanceof ChoiceClassFeature){
				featureList.remove(f);
				featureList.add(choiceFeatureName);
				break;
			}
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
		toggleViewVisibility(OFF, archetypeSelection, archetypeFeatureTextView, choiceSelection, choiceFeatureTextView);
		
		StringBuilder s= new StringBuilder("");
		charClass= classMap.get(parent.getSelectedItem().toString());
		
		List<String> featuresForLevel= charClass.getFeatureListForLevel(charClass.getLevel() + 1);
		if(featuresForLevel.contains("Archetype")){
			
			toggleViewVisibility(ON, archetypeSelection, archetypeFeatureTextView);
			archetypeSelection.removeAllViews();
			
			final String[] archetypeChoice= charClass.getArchetypes();
			
			archetypeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId){
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
		
		classFeatures= putFeatures(featuresForLevel);
		for(String n : classFeatures.keySet()){
			s.append(classFeatures.get(n)).append("\n");
		}
		classFeatureDisplay.setText(s.toString());
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent){}
}

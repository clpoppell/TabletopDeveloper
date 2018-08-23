package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NewCharacterForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
	
	public static final String EXTRA_MESSAGE_CHARACTER= "com.tadbolmont.homecoming.CHARACTER";
	private static final Integer[] LEVELS= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	
	private Spinner raceSpinner;
	private Spinner asiSpinner1;
	private Spinner skillSpinner1;
	private Spinner skillSelectionSpinner2;
	private Spinner featSpinner;
	private Spinner weaponSpinner1;
	private Spinner weaponSpinner2;
	private Spinner languageSpinner;
	private Spinner classSpinner;
	private Spinner levelSpinner;
	
	private String[] raceList;
	private ArrayList<String> statsList;
	private ArrayList<String> skillList;
	private String[] languageList;
	private String[] classList;
	
	private String subRaceName;
	private String asiExclusion;
	private ArrayList<String> languageExclusions;
	private ArrayList<String> charSkills= new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_character_form);
		
		languageList= getResources().getStringArray(R.array.language_list);
		
		createRaceSpinner();
		createClassSpinner();
		createLevelSpinner();
	}
	
	//Populates Race Spinner
	private void createRaceSpinner(){
		raceSpinner= findViewById(R.id.race_spinner);
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
	
	//Populates Class Spinner
	private void createClassSpinner(){
		classSpinner= findViewById(R.id.class_spinner);
		classSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter
		classList = getResources().getStringArray(R.array.class_list);
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
		
		classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> adapterView, View view, int in, long l){
				final TextView classTest= findViewById(R.id.testing2);
				int index= classSpinner.getSelectedItemPosition();
				classTest.setText(classList[index]);
				
				
				//Creates radio buttons for class archetype selection
				RadioGroup archetypeSelection= findViewById(R.id.archetype);
				archetypeSelection.removeAllViews();
				String selectedClass= classList[index];
				String[] classInfo= selectedClass.split(" # ");
				
				archetypeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						RadioButton rb= group.findViewById(checkedId);
						classTest.setText(rb.getText());
					}
				});
				
				for(int i=1; i<classInfo.length; i++){
					RadioButton rb= new RadioButton(NewCharacterForm.this);
					archetypeSelection.addView(rb);
					rb.setText(classInfo[i]);
					rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
					
					if(i==1){ archetypeSelection.check(rb.getId()); } //sets first radio button as default
				}
				
				
			}
			public void onNothingSelected(AdapterView<?> adapterView){}
		});
	}
	
	//Populates Level Spinner
	private void createLevelSpinner(){
		levelSpinner= findViewById(R.id.level_spinner);
		levelSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the int array and a spinner layout
		ArrayAdapter<Integer> levelAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, LEVELS);
		//Specify the layout to use when the list of choices appears
		levelAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the classAdapter to the spinner
		levelSpinner.setAdapter(levelAdapter);
	}
	
	//region Set-Up Methods
	private void asiSelectionSetup(){
		findViewById(R.id.asi_select_spinner1).setVisibility(Spinner.VISIBLE);
		findViewById(R.id.asi_select_mod_textView1).setVisibility(TextView.VISIBLE);
		findViewById(R.id.asi_select_spinner2).setVisibility(LinearLayout.VISIBLE);
		findViewById(R.id.asi_select_mod_textView2).setVisibility(TextView.VISIBLE);

		TextView asiSelectMod1= findViewById(R.id.asi_select_mod_textView1);
		TextView asiSelectMod2= findViewById(R.id.asi_select_mod_textView2);
		asiSelectMod1.setText("+1, ");
		asiSelectMod2.setText(" +1");
		
		asiSpinner1= findViewById(R.id.asi_select_spinner1);
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
	
	private void skillSelectionSetup(){
		String[] skills= CharacterInfo.getRace(subRaceName).getRacialSkills();
		int numSkillSelection= 0;
		String s= "Racial Skills: ";
		if(skills != null){
			findViewById(R.id.skill_layout).setVisibility(LinearLayout.VISIBLE);
			for(int i= 0; i < skills.length; i++){
				if(CharacterRace.isSkillSelection(skills[i])){ numSkillSelection++; }
				else{
					s += skills[i] + ", ";
				}
			}
			
			switch(numSkillSelection){
				case 2:
					findViewById(R.id.skill_textView2).setVisibility(Spinner.VISIBLE);
					findViewById(R.id.skill_select_spinner2).setVisibility(Spinner.VISIBLE);
				case 1:
					skillSpinner1= findViewById(R.id.skill_select_spinner1);
					skillSpinner1.setVisibility(Spinner.VISIBLE);
					//Create an ArrayAdapter
					skillList= (ArrayList<String>) createSkillList(skills[0]);
					final ArrayAdapter<String> skillAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, skillList);
					//Specify the layout to use when the list of choices appears
					skillAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
					//Apply the statAdapter to the spinner
					skillSpinner1.setAdapter(skillAdapter1);
					//Sets the item selection listener
					skillSpinner1.setOnItemSelectedListener(this);
					break;
				default:
					s= s.substring(0, s.lastIndexOf(","));
			}
			TextView skillTextView= findViewById(R.id.skill_textView1);
			skillTextView.setText(s);
		}
	}
	
	private void featSelectionSetup(){
		findViewById(R.id.feat_selection_layout).setVisibility(LinearLayout.VISIBLE);
		
		featSpinner= findViewById(R.id.feat_select_spinner);
		//Create an ArrayAdapter
		String[] featList= getResources().getStringArray(R.array.feat_list);
		final ArrayAdapter<String> featAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, featList);
		//Specify the layout to use when the list of choices appears
		featAdapter.setDropDownViewResource(R.layout.spinner_item_bg);
		//Apply the statAdapter to the spinner
		featSpinner.setAdapter(featAdapter);
	}
	
	private void weaponSelectionSetup(){
		String[] weaponProficiencies= CharacterInfo.getRace(subRaceName).getRacialWeaponTraining();
		String s= "Weapons: ";
		if(weaponProficiencies != null){
			findViewById(R.id.weapon_proficiency_layout).setVisibility(LinearLayout.VISIBLE);
			if(CharacterInfo.getRace(subRaceName).hasWeaponChoice()){
				weaponSpinner1= findViewById(R.id.weapon_select_spinner1);
				weaponSpinner1.setVisibility(Spinner.VISIBLE);
				//Create an ArrayAdapter
				String[] weaponList= CharacterInfo.getMartialWeapons();
				final ArrayAdapter<String> weaponAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, weaponList);
				//Specify the layout to use when the list of choices appears
				weaponAdapter1.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				weaponSpinner1.setAdapter(weaponAdapter1);
				//Sets the item selection listener
				weaponSpinner1.setOnItemSelectedListener(this);
			}
			else{
				for(String weapon : weaponProficiencies){ s += weapon + ", "; }
				s= s.substring(0, s.lastIndexOf(","));
			}
		}
		TextView weaponTextView1= findViewById(R.id.weapon_textView);
		weaponTextView1.setText(s);
	}
	
	private void languageSelectionSetup(){
		languageSpinner= findViewById(R.id.language_select_spinner);
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
	//endregion
	
	private List<String> createSkillList(String s){
		if(s.equals("(Any)")){
			Map<String, String> skillMap= CharacterInfo.getSkillMap();
			return new ArrayList<>(skillMap.keySet());
		}
		else{
			String skillChoices= s.substring(1, s.length() - 1);
			return new ArrayList<>(Arrays.asList(skillChoices.split(", ")));
		}
	}
	
	public void completeCharacter(View view){
		Intent intent= new Intent(this, CharacterDisplay.class);
		EditText nameBox= findViewById(R.id.name_editText);
		String[] charInfo= {nameBox.getText().toString(), subRaceName};
		intent.putExtra(EXTRA_MESSAGE_CHARACTER, charInfo);
		startActivity(intent);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
		String selection;
		
		switch(parent.getId()){
			case R.id.race_spinner:
				int index= raceSpinner.getSelectedItemPosition();
				
				//Creates radio buttons for subrace selection
				RadioGroup subRaceSelection= findViewById(R.id.sub_race);
				subRaceSelection.removeAllViews();
				String selectedRace= raceList[index];
				
				String[] raceInfo= selectedRace.split(" # ");
				
				subRaceSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						RadioButton rb= group.findViewById(checkedId);
						
						//region Visibility Turn Off
						findViewById(R.id.asi_select_spinner1).setVisibility(Spinner.GONE);
						findViewById(R.id.asi_select_mod_textView1).setVisibility(TextView.GONE);
						findViewById(R.id.asi_select_spinner2).setVisibility(Spinner.GONE);
						findViewById(R.id.asi_select_mod_textView2).setVisibility(TextView.GONE);
						
						findViewById(R.id.skill_layout).setVisibility(LinearLayout.GONE);
						findViewById(R.id.skill_select_spinner1).setVisibility(Spinner.GONE);
						findViewById(R.id.skill_textView2).setVisibility(TextView.GONE);
						findViewById(R.id.skill_select_spinner2).setVisibility(Spinner.GONE);
						
						findViewById(R.id.feat_selection_layout).setVisibility(LinearLayout.GONE);
						
						findViewById(R.id.weapon_proficiency_layout).setVisibility(LinearLayout.GONE);
						findViewById(R.id.weapon_select_spinner1).setVisibility(Spinner.GONE);
						
						findViewById(R.id.language_select_spinner).setVisibility(Spinner.GONE);
						//endregion
						
						//TESTING
						TextView raceInfoDisplay= findViewById(R.id.race_info_display);
						raceInfoDisplay.setText("");
						String subRaceNameRaw= rb.getText().toString();
						int index= subRaceNameRaw.indexOf("(");
						subRaceName= subRaceNameRaw.substring(0, index);
						CharacterRace subRaceInfo= CharacterInfo.getRace(subRaceName);
						
						raceInfoDisplay.setText(subRaceInfo.toString());
						//TESTING
						
						TextView asiTextView= findViewById(R.id.asi_textView);
						Map<String, Integer> asis= subRaceInfo.getStatMods();
						asiExclusion= "";
						NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
						String asiString= "ASI: ";
						for(String s : asis.keySet()){
							if(!s.equals("Choice")){
								asiTextView.setVisibility(TextView.VISIBLE);
								asiString += s + " " + plusMinusNF.format(asis.get(s)) + ", ";
								asiExclusion= s;
							}
						}
						if(subRaceInfo.hasASIChoice()){ asiSelectionSetup(); }
						else{ asiString= asiString.substring(0, asiString.lastIndexOf(",")); }
						asiTextView.setText(asiString);
						
						skillSelectionSetup();
						
						if(subRaceInfo.hasFeatChoice()){ featSelectionSetup(); }
						
						weaponSelectionSetup();
						
						TextView languageTextView= findViewById(R.id.language_textView);
						String[] raceLanguages= subRaceInfo.getRacialLanguages();
						Boolean hasChoice= false;
						String s= "Languages: ";
						languageExclusions= new ArrayList<>();
						for(String language : raceLanguages){
							if(language.equals("Choice")){ hasChoice= true; }
							else{
								s += language + ", ";
								languageExclusions.add(language);
							}
						}
						if(hasChoice){ languageSelectionSetup(); }
						else{ s = s.substring(0, s.lastIndexOf(", ")); }
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
				break;
			case R.id.asi_select_spinner1:
				selection= asiSpinner1.getSelectedItem().toString();
				Spinner asiSpinner2= findViewById(R.id.asi_select_spinner2);
				//Create an ArrayAdapter
				ArrayList<String> statList2= new ArrayList<>(statsList);
				statList2.remove(selection);
				statList2.remove(asiExclusion);
				ArrayAdapter<String> statAdapter2= new ArrayAdapter<>(App.getContext(), R.layout.spinner_item, statList2);
				//Specify the layout to use when the list of choices appears
				statAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the statAdapter to the spinner
				asiSpinner2.setAdapter(statAdapter2);
				break;
			case R.id.skill_select_spinner1:
				selection= skillSpinner1.getSelectedItem().toString();
				skillSelectionSpinner2= findViewById(R.id.skill_select_spinner2);
				//Create an ArrayAdapter
				ArrayList<String> skillList2= new ArrayList<>(skillList);
				skillList2.remove(selection);
				ArrayAdapter<String> skillAdapter2= new ArrayAdapter<>(App.getContext(), R.layout.spinner_item, skillList2);
				//Specify the layout to use when the list of choices appears
				skillAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the Adapter to the spinner
				skillSelectionSpinner2.setAdapter(skillAdapter2);
				break;
			case R.id.weapon_select_spinner1:
				selection= weaponSpinner1.getSelectedItem().toString();
				weaponSpinner2= findViewById(R.id.weapon_select_spinner2);
				weaponSpinner2.setVisibility(Spinner.VISIBLE);
				findViewById(R.id.weapon_textView2).setVisibility(TextView.VISIBLE);
				//Create an ArrayAdapter
				ArrayList<String> weaponList2= new ArrayList<>(Arrays.asList(CharacterInfo.getMartialWeapons()));
				weaponList2.remove(selection);
				ArrayAdapter<String> weaponAdapter2= new ArrayAdapter<>(App.getContext(), R.layout.spinner_item, weaponList2);
				//Specify the layout to use when the list of choices appears
				weaponAdapter2.setDropDownViewResource(R.layout.spinner_item_bg);
				//Apply the Adapter to the spinner
				weaponSpinner2.setAdapter(weaponAdapter2);
				break;
		}
	}

	public void onNothingSelected(AdapterView<?> parent){}
}

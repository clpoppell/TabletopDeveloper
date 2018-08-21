package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class NewCharacterForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
	
	public static final String EXTRA_MESSAGE_CHARACTER= "com.tadbolmont.homecoming.CHARACTER";
	public static final Integer[] LEVELS= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};

	Spinner raceSpinner;
	Spinner classSpinner;
	Spinner levelSpinner;

	String[] raceList;
	String[] classList;
	
	private String subRaceName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_character_form);
		
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
		raceAdapter.setDropDownViewResource(R.layout.spinner_item);
		//Apply the raceAdapter to the spinner
		raceSpinner.setAdapter(raceAdapter);
		
		raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> adapterView, View view, int in, long l){
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
						//TESTING
						TextView raceInfoDisplay= findViewById(R.id.race_info_display);
						raceInfoDisplay.setText("");
						String subRaceNameRaw= rb.getText().toString();
						subRaceName= subRaceNameRaw.substring(0,subRaceNameRaw.indexOf(" ("));
						CharacterRace subRaceInfo= CharacterInfo.getRace(subRaceName);
						
						raceInfoDisplay.setText(subRaceInfo.toString());
						//TESTING
						
						Boolean check= subRaceInfo.hasASIChoice();
						if(check){
							raceInfoDisplay.append("\n +1 Any Stat");
							setupASISelection();
						}
						else{
							findViewById(R.id.asi_selection_layout).setVisibility(LinearLayout.GONE);
							findViewById(R.id.asi_selection_layout2).setVisibility(LinearLayout.GONE);
						}
					}
				});
				
				for(int i=1; i<raceInfo.length; i++){
					RadioButton rb= new RadioButton(NewCharacterForm.this);
					subRaceSelection.addView(rb);
					rb.setText(raceInfo[i]);
					rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
					
					if(i==1){ subRaceSelection.check(rb.getId()); } //sets first radio button as default
				}
			}
			public void onNothingSelected(AdapterView<?> adapterView){}
		});
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
		classAdapter.setDropDownViewResource(R.layout.spinner_item);
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
		levelAdapter.setDropDownViewResource(R.layout.spinner_item);
		//Apply the classAdapter to the spinner
		levelSpinner.setAdapter(levelAdapter);
	}
	
	private void setupASISelection(){
		findViewById(R.id.asi_selection_layout).setVisibility(LinearLayout.VISIBLE);
		findViewById(R.id.asi_selection_layout2).setVisibility(LinearLayout.VISIBLE);
		
		final Spinner asiSpinner1= findViewById(R.id.asi_select_spinner);
		//Create an ArrayAdapter
		String[] stats1= getResources().getStringArray(R.array.stats);
		final ArrayList<String> statList1= new ArrayList<>(Arrays.asList(stats1));
		final ArrayAdapter<String> statAdapter1= new ArrayAdapter<>(this, R.layout.spinner_item, statList1);
		//Specify the layout to use when the list of choices appears
		statAdapter1.setDropDownViewResource(R.layout.spinner_item);
		//Apply the statAdapter to the spinner
		asiSpinner1.setAdapter(statAdapter1);
		
		asiSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> adapterView, View view, int in, long l){
				String selection= asiSpinner1.getSelectedItem().toString();
				Spinner asiSpinner2= findViewById(R.id.asi_select_spinner2);
				//Create an ArrayAdapter
				ArrayList<String> statList2= new ArrayList<>(statList1);
				statList2.remove(selection);
				ArrayAdapter<String> statAdapter2= new ArrayAdapter<>(App.getContext(), R.layout.spinner_item, statList2);
				//Specify the layout to use when the list of choices appears
				statAdapter2.setDropDownViewResource(R.layout.spinner_item);
				//Apply the statAdapter to the spinner
				asiSpinner2.setAdapter(statAdapter2);
			}
			public void onNothingSelected(AdapterView<?> adapterView){}
		});
	}
	
	
	
	public void completeCharacter(View view){
		Intent intent= new Intent(this, CharacterDisplay.class);
		EditText nameBox= findViewById(R.id.name_editText);
		String[] charInfo= {nameBox.getText().toString(), subRaceName};
		intent.putExtra(EXTRA_MESSAGE_CHARACTER, charInfo);
		startActivity(intent);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){}

	public void onNothingSelected(AdapterView<?> parent){}
}

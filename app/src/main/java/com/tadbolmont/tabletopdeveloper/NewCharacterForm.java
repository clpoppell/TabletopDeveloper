package com.tadbolmont.tabletopdeveloper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class NewCharacterForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

	public static final Integer[] LEVELS= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};

	Spinner raceSpinner;
	Spinner classSpinner;
	Spinner levelSpinner;

	String[] raceList;
	String[] classList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_character_form);
		
		createRaceSpinner();
		createClassSpinner();

		//Populates Level Spinner
		levelSpinner= findViewById(R.id.level_spinner);
		levelSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the int array and a spinner layout
		ArrayAdapter<Integer> levelAdapter= new ArrayAdapter<>(this, R.layout.spinner_item, LEVELS);
		//Specify the layout to use when the list of choices appears
		levelAdapter.setDropDownViewResource(R.layout.spinner_item);
		//Apply the classAdapter to the spinner
		levelSpinner.setAdapter(levelAdapter);
	}
	
	//Populates Race Spinner
	private void createRaceSpinner(){
		raceSpinner= findViewById(R.id.race_spinner);
		raceSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter
		raceList = getResources().getStringArray(R.array.race_list);
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
				final TextView raceTest= findViewById(R.id.testing);
				int index= raceSpinner.getSelectedItemPosition();
				raceTest.setText(raceList[index]);
				
				//Creates radio buttons for subrace selection
				RadioGroup subRaceSelection= findViewById(R.id.sub_race);
				subRaceSelection.removeAllViews();
				String selectedRace= raceList[index];
				String[] raceInfo= selectedRace.split(" # ");
				for(int i=1; i<raceInfo.length; i++){
					RadioButton rb= new RadioButton(NewCharacterForm.this);
					subRaceSelection.addView(rb);
					rb.setText(raceInfo[i]);
					rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
				}
				
				subRaceSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						RadioButton rb= group.findViewById(checkedId);
						raceTest.setText(rb.getText());
					}
				});
			}
			public void onNothingSelected(AdapterView<?> adapterView){}
		});
	}
	
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
				for(int i=1; i<classInfo.length; i++){
					RadioButton rb= new RadioButton(NewCharacterForm.this);
					archetypeSelection.addView(rb);
					rb.setText(classInfo[i]);
					rb.setTextColor(getResources().getColor(R.color.textColorPrimary));
				}
				archetypeSelection.setVisibility(VISIBLE);
				
				archetypeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						RadioButton rb= group.findViewById(checkedId);
						classTest.setText(rb.getText());
					}
				});
			}
			public void onNothingSelected(AdapterView<?> adapterView){}
		});
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){}

	public void onNothingSelected(AdapterView<?> parent){}
}

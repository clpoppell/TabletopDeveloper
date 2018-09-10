package com.tadbolmont.tabletopdeveloper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import tabletop_5e_character_design.PlayerCharacter;

public class CharacterDisplay extends Activity{
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
	
	private PlayerCharacter character;
	
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
	
	private void populate(){
		character= PlayerCharacter.getPlayerCharacter();
		
		TextView nameDisplay= findViewById(R.id.char_name);
		nameDisplay.setText(character.getName());
		
		TextView raceNameDisplay= findViewById(R.id.char_race);
		raceNameDisplay.setText(character.getCharRace().getName());

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
	
	public void displayCharacterFeatures(View view){
		Intent intent= new Intent(this, CharacterFeatures.class);
		startActivity(intent);
	}
	
	private String formatNumbersLeadingZeros(int n){
		if(Integer.toString(n).length() < 2){ return "0"+n; }
		return "" + n;
	}
	
	private String formatNumbersPlusMinus(int n){
		NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
		return plusMinusNF.format(n);
	}
}
package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.util.Map;

public class CharacterDisplay extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_display);
		populateText();
	}
	
	private void populateText(){
		// Get the Intent that started this activity and extract the string array extra
		Intent pIntent=getIntent();
		String[] charInfo= pIntent.getStringArrayExtra(NewCharacterForm.EXTRA_MESSAGE_CHARACTER);
		TextView display= findViewById(R.id.char_display);
		CharacterRace race= CharacterInfo.getRace(charInfo[1]);
		Map<String,Integer> stats= race.getStatMods();
		String s= "";
		for(Map.Entry<String,Integer> stat : stats.entrySet()){ s += stat.getKey() + ": " + stat.getValue();  }
		// CharacterInfo.getRace(charInfo[1]).toString();
		display.setText(s);
	}
}

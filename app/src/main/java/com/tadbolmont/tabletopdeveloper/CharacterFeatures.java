package com.tadbolmont.tabletopdeveloper;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Joiner;

import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.UsableClassFeature;

public class CharacterFeatures extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_features);
		
		PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
		
		((TextView)findViewById(R.id.character_armor_proficiency)).setText(Joiner.on(", ").join(character.getArmorProficiencies()));
		((TextView)findViewById(R.id.character_weapon_proficiency)).setText(Joiner.on(", ").join(character.getWeaponProficiencies()));
		((TextView)findViewById(R.id.character_tool_proficiency)).setText(Joiner.on(", ").join(character.getToolProficiencies()));
		
		LinearLayout featureList= findViewById(R.id.character_feature_list);
		for (ClassFeature feature : character.getClassFeatures().values()){
			TextView textView= new TextView(this);
			textView.setText(feature.toString());
			textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
			textView.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
			
			if(feature instanceof UsableClassFeature){
				textView.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v){
						ColorDrawable bg=(ColorDrawable) v.getBackground();
						if(bg.getColor() == getResources().getColor(R.color.backgroundColor)){
							v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
						} else{
							v.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
						}
					}
				});
			}
			featureList.addView(textView);
		}
	}
}

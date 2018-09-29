package com.tadbolmont.tabletopdeveloper;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Joiner;

import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.UsableClassFeature;

public class CharacterFeatureFragment extends Fragment implements View.OnClickListener{
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	View v;
	
	public CharacterFeatureFragment(){};
	
	@Override
	public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); }
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_character_feature_display, container, false);
		
		((TextView)v.findViewById(R.id.character_armor_proficiency)).setText(Joiner.on(", ").join(character.getArmorProficiencies()));
		((TextView)v.findViewById(R.id.character_weapon_proficiency)).setText(Joiner.on(", ").join(character.getWeaponProficiencies()));
		((TextView)v.findViewById(R.id.character_tool_proficiency)).setText(Joiner.on(", ").join(character.getToolProficiencies()));
		
		LinearLayout featureList= v.findViewById(R.id.character_feature_list);
		for (ClassFeature feature : character.getClassFeatures().values()){
			TextView textView= new TextView(getActivity());
			textView.setText(feature.toString());
			textView.setTextColor(getResources().getColor(R.color.textColorPrimary));
			textView.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
			
			if(feature instanceof UsableClassFeature){ textView.setOnClickListener(this); }
			featureList.addView(textView);
		}
		return v;
	}
	
	@Override
	public void onClick(View view){
		ColorDrawable bg= (ColorDrawable) view.getBackground();
		if(bg.getColor() == getResources().getColor(R.color.backgroundColor)){ view.setBackgroundColor(getResources().getColor(R.color.colorAccent)); }
		else{ view.setBackgroundColor(getResources().getColor(R.color.backgroundColor)); }
	}
}

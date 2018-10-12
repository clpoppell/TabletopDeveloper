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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.class_features.ClassFeature;
import tabletop_5e_character_design.class_features.UsableClassFeature;

public class CharacterFeatureFragment extends Fragment implements View.OnClickListener{
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	View v;
	private Unbinder unbinder;
	
	@BindView(R.id.character_armor_proficiency) TextView armorProf;
	@BindView(R.id.character_weapon_proficiency) TextView weaponProf;
	@BindView(R.id.character_tool_proficiency) TextView toolProf;
	@BindView(R.id.character_feature_list) LinearLayout featureList;
	
	public CharacterFeatureFragment(){}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_character_feature_display, container, false);
		
		unbinder= ButterKnife.bind(this, v);
		
		armorProf.setText(Joiner.on(", ").join(character.getArmorProficiencies()));
		weaponProf.setText(Joiner.on(", ").join(character.getWeaponProficiencies()));
		toolProf.setText(Joiner.on(", ").join(character.getToolProficiencies()));
		
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
	public void onDestroyView(){
		super.onDestroyView();
		unbinder.unbind();
	}
	
	@Override
	public void onClick(View view){
		ColorDrawable bg= (ColorDrawable) view.getBackground();
		if(bg.getColor() == getResources().getColor(R.color.backgroundColor)){ view.setBackgroundColor(getResources().getColor(R.color.colorAccent)); }
		else{ view.setBackgroundColor(getResources().getColor(R.color.backgroundColor)); }
	}
}

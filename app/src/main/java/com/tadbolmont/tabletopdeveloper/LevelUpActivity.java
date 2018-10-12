package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.Objects;

public class LevelUpActivity extends FragmentActivity implements ClassSelectionFragment.OnClassSelectedListener{
	public LevelUpActivity(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_up_screen);
	}
	
	public void completeLevelUp(View view){
		ClassSelectionFragment frag= (ClassSelectionFragment)getSupportFragmentManager().findFragmentById(R.id.class_selection_fragment);
		if((Objects.requireNonNull(frag)).applyLevelToPC()){ startActivity(new Intent(this, CharacterDisplayActivity.class)); }
	}
	
	@Override
	public void onClassItemSelected(){}
}

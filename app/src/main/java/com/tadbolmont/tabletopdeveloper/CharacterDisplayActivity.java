package com.tadbolmont.tabletopdeveloper;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;

import java.util.Objects;

import tabletop_5e_character_design.PlayerCharacter;

public class CharacterDisplayActivity extends FragmentActivity implements DiceRollDialogFragment.DiceRollDialogListener{
	private Fragment fragmentToShow;
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_display);
		
		fragmentToShow= new MainCharacterInfoFragment();
		showFragment();
	}
	
	private void showFragment(){
		FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.placeholder, fragmentToShow).commit();
	}
	
	public void displayMainInfo(View view){
		fragmentToShow= new MainCharacterInfoFragment();
		showFragment();
	}
	
	@SuppressWarnings("unused")
	public void savingThrowRoll(View view){
		Bundle bundle= new Bundle();
		bundle.putInt("dieSize", 20);
		
		switch(view.getId()){
			case R.id.str_saving_throw_btn:
				bundle.putString("title", "Strength Saving Throw");
				bundle.putInt("mod", character.getStrSaving());
				break;
			case R.id.dex_saving_throw_btn:
				bundle.putString("title", "Dexterity Saving Throw");
				bundle.putInt("mod", character.getDexSaving());
				break;
			case R.id.con_saving_throw_btn:
				bundle.putString("title", "Constitution Saving Throw");
				bundle.putInt("mod", character.getConSaving());
				break;
			case R.id.int_saving_throw_btn:
				bundle.putString("title", "Intelligence Saving Throw");
				bundle.putInt("mod", character.getIntSaving());
				break;
			case R.id.wis_saving_throw_btn:
				bundle.putString("title", "Wisdom Saving Throw");
				bundle.putInt("mod", character.getWisSaving());
				break;
			case R.id.cha_saving_throw_btn:
				bundle.putString("title", "Charisma Saving Throw");
				bundle.putInt("mod", character.getChaSaving());
				break;
		}
		DiceRollDialogFragment dialog= new DiceRollDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(this.getFragmentManager(), "roll");
	}
	
	@SuppressWarnings("unused")
	public void skillRoll(View view){
		Bundle bundle= new Bundle();
		bundle.putInt("dieSize", 20);
		
		switch(view.getId()){
			case R.id.acrobatics_btn:
				bundle.putString("title", "Acrobatics");
				bundle.putInt("mod", character.getSkillModifierForSkill("Acrobatics"));
				break;
			case R.id.animal_handling_btn:
				bundle.putString("title", "Animal Handling");
				bundle.putInt("mod", character.getSkillModifierForSkill("Animal Handling"));
				break;
			case R.id.arcana_btn:
				bundle.putString("title", "Arcana");
				bundle.putInt("mod", character.getSkillModifierForSkill("Arcana"));
				break;
			case R.id.athletics_btn:
				bundle.putString("title", "Athletics");
				bundle.putInt("mod", character.getSkillModifierForSkill("Athletics"));
				break;
			case R.id.deception_btn:
				bundle.putString("title", "Deception");
				bundle.putInt("mod", character.getSkillModifierForSkill("Deception"));
				break;
			case R.id.history_btn:
				bundle.putString("title", "History");
				bundle.putInt("mod", character.getSkillModifierForSkill("History"));
				break;
			case R.id.insight_btn:
				bundle.putString("title", "Insight");
				bundle.putInt("mod", character.getSkillModifierForSkill("Insight"));
				break;
			case R.id.intimidation_btn:
				bundle.putString("title", "Intimidation");
				bundle.putInt("mod", character.getSkillModifierForSkill("Intimidation"));
				break;
			case R.id.investigation_btn:
				bundle.putString("title", "Investigation");
				bundle.putInt("mod", character.getSkillModifierForSkill("Investigation"));
				break;
			case R.id.medicine_btn:
				bundle.putString("title", "Medicine");
				bundle.putInt("mod", character.getSkillModifierForSkill("Medicine"));
				break;
			case R.id.nature_btn:
				bundle.putString("title", "Nature");
				bundle.putInt("mod", character.getSkillModifierForSkill("Nature"));
				break;
			case R.id.perception_btn:
				bundle.putString("title", "Perception");
				bundle.putInt("mod", character.getSkillModifierForSkill("Perception"));
				break;
			case R.id.performance_btn:
				bundle.putString("title", "Performance");
				bundle.putInt("mod", character.getSkillModifierForSkill("Performance"));
				break;
			case R.id.persuasion_btn:
				bundle.putString("title", "Persuasion");
				bundle.putInt("mod", character.getSkillModifierForSkill("Persuasion"));
				break;
			case R.id.religion_btn:
				bundle.putString("title", "Religion");
				bundle.putInt("mod", character.getSkillModifierForSkill("Religion"));
				break;
			case R.id.sleight_of_hand_btn:
				bundle.putString("title", "Sleight of Hand");
				bundle.putInt("mod", character.getSkillModifierForSkill("Sleight of Hand"));
				break;
			case R.id.stealth_btn:
				bundle.putString("title", "Stealth");
				bundle.putInt("mod", character.getSkillModifierForSkill("Stealth"));
				break;
			case R.id.survival_btn:
				bundle.putString("title", "Survival");
				bundle.putInt("mod", character.getSkillModifierForSkill("Survival"));
				break;
		}
		DiceRollDialogFragment dialog= new DiceRollDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(this.getFragmentManager(), "roll");
	}
	
	public void longRest(View view){
		character.setCurrentHitPoints(character.getMaxHitPoints());
		
		TableRow successes= findViewById(R.id.success_checkBoxes);
		for(int i= 1; i < successes.getChildCount(); i++){
			((CheckBox)successes.getChildAt(i)).setChecked(false);
		}
		
		TableRow failures= findViewById(R.id.failures_checkBoxes);
		for(int i= 1; i < failures.getChildCount(); i++){
			((CheckBox)failures.getChildAt(i)).setChecked(false);
		}
		
		((MainCharacterInfoFragment)fragmentToShow).populateHealthDisplay();
	}
	
	public void displayInventory(View view){
		fragmentToShow= new InventoryFragment();
		showFragment();
	}
	
	public void displayCharacterFeatures(View view){
		fragmentToShow= new CharacterFeatureFragment();
		showFragment();
	}
	
	public void levelUp(View view){
		fragmentToShow= new LevelUpFragment();
		showFragment();
	}
	
	public void completeLevelUp(View view){
		((LevelUpFragment)fragmentToShow).applyLevelToPC();
	}
	
	//region Interface Methods
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			View v= getCurrentFocus();
			if(v instanceof EditText){
				Rect outRect= new Rect();
				v.getGlobalVisibleRect(outRect);
				if(!outRect.contains((int)event.getRawX(), (int)event.getRawY())){
					v.clearFocus();
					InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					Objects.requireNonNull(imm).hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent( event );
	}
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture){}
	//endregion
}
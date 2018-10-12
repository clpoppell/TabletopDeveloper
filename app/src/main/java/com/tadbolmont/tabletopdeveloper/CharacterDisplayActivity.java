package com.tadbolmont.tabletopdeveloper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;

import tabletop_5e_character_design.PlayerCharacter;

import static com.tadbolmont.tabletopdeveloper.App.OFF;
import static com.tadbolmont.tabletopdeveloper.App.ON;
import static com.tadbolmont.tabletopdeveloper.App.toggleViewVisibility;

public class CharacterDisplayActivity extends AppCompatActivity implements DiceRollDialogFragment.DiceRollDialogListener{
	static final int QUERY_CLASS_LEVEL= 0;
	
	private MyAdapter adapter;
	private ViewPager pager;
	private PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_display);
		
		Toolbar myToolbar= findViewById(R.id.toolbar);
		myToolbar.setTitle(character.getName());
		setSupportActionBar(myToolbar);
		
		adapter= new MyAdapter(getSupportFragmentManager());
		pager= findViewById(R.id.placeholder);
		pager.setAdapter(adapter);
		
		TabLayout tabLayout= findViewById(R.id.tab_layout);
		// Set the text for each tab.
		tabLayout.addTab(tabLayout.newTab().setText("Main"));
		tabLayout.addTab(tabLayout.newTab().setText("Items"));
		tabLayout.addTab(tabLayout.newTab().setText("Features"));
		// Set the tabs to fill the entire layout.
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		
		pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
			@Override
			public void onTabSelected(TabLayout.Tab tab){ pager.setCurrentItem(tab.getPosition()); }
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab){}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab){}
		});
	}
	
	//region Button onClick Methods
	@SuppressWarnings("unused")
	public void collapseSection(View view){
		View layout;
		
		switch(view.getId()){
			case R.id.stat_section_btn:
				layout= findViewById(R.id.stat_display_layout);
				break;
			case R.id.skill_section_btn:
				layout= findViewById(R.id.skill_display_layout);
				break;
			case R.id.defenses_section_btn:
				layout= findViewById(R.id.defenses_display_layout);
				break;
			case R.id.health_section_btn:
				layout= findViewById(R.id.health_display_layout);
				break;
			case R.id.attack_section_btn:
				layout= findViewById(R.id.attacks_display_layout);
				break;
			default: layout= view;
		}
		if(layout.getVisibility() == ON){
			toggleViewVisibility(OFF, layout);
			((TextView)view).setText("Open");
		}
		else{
			toggleViewVisibility(ON, layout);
			((TextView)view).setText("Close");
		}
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
		
		Fragment page= getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.placeholder + ":" + pager.getCurrentItem());
		((MainCharacterInfoFragment)page).populateHealthDisplay();
	}
	
	public void levelUp(View view){
		Intent intent= new Intent(this, LevelUpActivity.class);
		startActivityForResult(intent, QUERY_CLASS_LEVEL);
	}
	//endregion
	
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
	
	public static class MyAdapter extends FragmentPagerAdapter{
		MyAdapter(FragmentManager fm){ super(fm); }
		
		@Override
		public Fragment getItem(int position){
			switch(position){
				case 0: return new MainCharacterInfoFragment();
				case 1: return new InventoryFragment();
				case 2: return new CharacterFeatureFragment();
				default: return new CharacterFeatureFragment();
			}
		}
		
		@Override
		public int getCount(){ return 4; }
		
		@Override
		public CharSequence getPageTitle(int position){
			switch (position) {
				case 0: return "Main";
				case 1: return "Inventory";
				case 2: return "Features";
			}
			return null;
		}
	}
}
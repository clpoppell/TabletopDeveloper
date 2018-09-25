package com.tadbolmont.tabletopdeveloper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class DiceRollDialogFragment extends DialogFragment{
	public DiceRollDialogFragment(){ super(); }
	/*
	The activity that creates an instance of this dialog fragment must implement this interface in order to receive event callbacks.
	Each method passes the DialogFragment in case the host needs to query it.
	*/
	interface DiceRollDialogListener{}
	DiceRollDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try{
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener= (DiceRollDialogListener)activity;
		}
		catch(ClassCastException e){
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		int roll= getArguments().getInt("mod") + diceRoll(getArguments().getInt("dieSize"));
		
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
		
		SpannableString titleText= new SpannableString(getArguments().getString("title"));
		titleText.setSpan(new UnderlineSpan(), 0, titleText.length(), 0);
		
		TextView title= new TextView(getActivity());
		title.setText(titleText);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setPadding(0, 20, 0, 0);
		title.setTextColor(getResources().getColor(R.color.textColorPrimary));
		title.setTextSize(20);
		builder.setCustomTitle(title);
		
		LayoutInflater inflater= getActivity().getLayoutInflater();
		@SuppressLint("InflateParams") View view= inflater.inflate(R.layout.dice_roll_dialog_layout, null);
		((TextView)view.findViewById(R.id.dice_roll_result)).setText(roll + "");
		builder.setView(view);
		
		return builder.create();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		if (getDialog() != null){
			int dialogWidth= 1000;
			int dialogHeight= WindowManager.LayoutParams.WRAP_CONTENT;
			
			Objects.requireNonNull(getDialog().getWindow()).setLayout(dialogWidth, dialogHeight);
		}
	}
	
	private int diceRoll(int dieSize){
		Random random= new Random();
		return random.nextInt(dieSize) + 1;
	}
}

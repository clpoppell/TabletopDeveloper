package com.tadbolmont.tabletopdeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newCharacter(View view){
        Intent intent= new Intent(this, NewCharacterActivity.class);
        startActivity(intent);
    }
    
    public void loadCharacter(View view){
    	//Intent intent= new Intent(this, CharacterSheetDisplay.class);
        //startActivity(intent);
    }
}

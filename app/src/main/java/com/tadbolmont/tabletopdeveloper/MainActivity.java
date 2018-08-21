package com.tadbolmont.tabletopdeveloper;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newCharacter(View view){
        Intent intent= new Intent(this, NewCharacterForm.class);
        startActivity(intent);
    }
}

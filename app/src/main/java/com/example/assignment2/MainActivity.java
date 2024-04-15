package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    //TODO: Compare differences with Kotlin
    //SQL: To store all player of app high scores
    //SharedPreferences: To store personal multiple high scores
    //Add 2 player option (phone number notification)

    Button explicitBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.name);

        explicitBtn = findViewById(R.id.expButton);

        explicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tag1", "explicitPressed");
                goToSecondActivity();
            }
        });
        editText.setOnClickListener(null);
    }

    public void goToSecondActivity() {
        String text = editText.getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name", "Player: " + text);
        startActivity(intent);
    }
}
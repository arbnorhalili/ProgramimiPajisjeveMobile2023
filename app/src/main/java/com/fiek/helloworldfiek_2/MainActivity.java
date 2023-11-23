package com.fiek.helloworldfiek_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String strUsername = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().hasExtra("Username"))
        {
            strUsername = getIntent().getExtras().getString("Username");
            Toast.makeText(MainActivity.this,
                    "Mire se erdhe, "+strUsername,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
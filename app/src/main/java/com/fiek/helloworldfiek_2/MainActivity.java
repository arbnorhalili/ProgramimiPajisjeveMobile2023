package com.fiek.helloworldfiek_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String strUsername = "";
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvWelcome = findViewById(R.id.tvWelcome);

        if(getIntent().hasExtra("Username"))
        {
            strUsername = getIntent().getExtras().getString("Username");
//            Toast.makeText(MainActivity.this,
//                    "Mire se erdhe, "+strUsername,
//                    Toast.LENGTH_SHORT).show();
            tvWelcome.setText("Mire se erdhe, "+
                    strUsername);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addNewUser)
        {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
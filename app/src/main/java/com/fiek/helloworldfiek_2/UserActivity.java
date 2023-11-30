package com.fiek.helloworldfiek_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    EditText etName, etUsername, etPassword, etAddress;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername1);
        etPassword = findViewById(R.id.etPw);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase objDb
                        = (new DatabaseHelper(UserActivity.this)).getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("Name", etName.getText().toString());
                cv.put("Username", etUsername.getText().toString());
                cv.put("Password", etPassword.getText().toString());
                cv.put("Address", etAddress.getText().toString());

                try {
                    long result = objDb.insertOrThrow("Perdoruesit", null, cv);
                    if(result>0)
                    {
                        Toast.makeText(UserActivity.this,
                                "Perdoruesi u regjistrua me sukses.",
                                Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    else
                    {
                        Toast.makeText(UserActivity.this,
                                "Regjistrimi deshtoi.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(UserActivity.this,
                            ex.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
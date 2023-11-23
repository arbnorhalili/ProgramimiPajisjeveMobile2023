package com.fiek.helloworldfiek_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SQLiteDatabase objDb;
    Button btnLogin;
    EditText etUsername, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        objDb = (new DatabaseHelper(LoginActivity.this)).getReadableDatabase();
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setText("Kycu");

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = etUsername.getText().toString();
                String strPassword = etPassword.getText().toString();

                Log.i("LoginFormData", strUsername+" - "+strPassword);

                if(CheckCredentials(strUsername, strPassword))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,
                            "Kredencialet jane gabim",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean CheckCredentials(String username, String password)
    {
        Cursor c = objDb.query("Perdoruesit",
                new String[]{"Id", "Username", "Password"},
                "Username=?",
                new String[]{ username },
                "",
                "",
                "");

        if(c.getCount()>0)
        {
            c.moveToFirst();
            String strUserPasswordDb = c.getString(2);
            if(password.equals(strUserPasswordDb))
                return true;
            else
                return false;
        }
        else
        {
            return false;
        }
    }
}
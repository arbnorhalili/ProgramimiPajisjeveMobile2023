package com.fiek.helloworldfiek_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    String strUsername = "";
    TextView tvWelcome;
    ListView lvUsers;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUsers = findViewById(R.id.lvUsers);
        userAdapter = new UserAdapter(MainActivity.this);

        lvUsers.setAdapter(userAdapter);

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                GetUsersFromDb();
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
        new LoadData().execute();
        lvUsers.setOnItemClickListener(this);
        lvUsers.setOnItemLongClickListener(this);
//        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,
//                        "Klikuar: "+userAdapter.userList.get(i).getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,
//                        "LongClick: "+userAdapter.userList.get(i).getName(),
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.addNewUser:
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.universityList:
                intent = new Intent(MainActivity.this, UniversitiesActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                ClearSharedPreferences();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            case R.id.onlineRadio:
                intent = new Intent(MainActivity.this, OnlineRadioActivity.class);
                startActivity(intent);
        }

        return true;
    }

    private void GetUsersFromDb()
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SQLiteDatabase objDb = (new DatabaseHelper(MainActivity.this)).getReadableDatabase();
        Cursor cursor = objDb.query("Perdoruesit",
                new String[]{"Id", "Name", "Username", "Address"},
                "",
                new String[]{},
                "",
                "",
                "");
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while(cursor.isAfterLast()==false)
            {
                User tempUser = new User(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                userAdapter.userList.add(tempUser);
                cursor.moveToNext();
            }
            cursor.close();

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    userAdapter.notifyDataSetChanged();
//                }
//            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainActivity.this,
                "Klikuar: "+userAdapter.userList.get(i).getName(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainActivity.this,
                "LongClick: "+userAdapter.userList.get(i).getName(),
                Toast.LENGTH_SHORT).show();
        User tempUser = userAdapter.userList.get(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("A jeni i sigurte qe deshironi te fshini kete rekord?");
        builder.setTitle("Kujdes!");
        builder.setPositiveButton("Po", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDatabase objDb = (new DatabaseHelper(MainActivity.this)).getReadableDatabase();
                objDb.delete("Perdoruesit","Id=?",
                        new String[]{ tempUser.getId()+""});
                userAdapter.userList.remove(tempUser);
                userAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Jo", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    public class LoadData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            GetUsersFromDb();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            userAdapter.notifyDataSetChanged();
        }
    }

    private void ClearSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "FiekAppSharedPrefs", MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Username");
        editor.commit();
    }


}
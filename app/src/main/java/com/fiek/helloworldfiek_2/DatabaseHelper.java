package com.fiek.helloworldfiek_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "FiekDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Perdoruesit (Id integer primary key autoincrement," +
                "Name text," +
                "Username text," +
                "Password text," +
                "Address text)");

        ContentValues cv = new ContentValues();
        cv.put("Name", "Filan Fisteku");
        cv.put("Username", "filan@gmail.com");
        cv.put("Password", "123");
        cv.put("Address", "Prishtine");

        sqLiteDatabase.insert("Perdoruesit", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Insert into Perdoruesit(Name,Username,Password,Address) " +
                "values (" +
                "'Filan Gashi','gashi@gmail.com','123','Peje')");
    }
}

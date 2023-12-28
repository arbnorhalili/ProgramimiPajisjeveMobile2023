package com.fiek.helloworldfiek_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UniversitiesActivity extends AppCompatActivity
    implements View.OnClickListener {

    EditText etCountry;
    ListView lvUnivesities;
    Button btnSearch;
    OkHttpClient client;
    List<University> universityList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        etCountry = findViewById(R.id.etCountry);
        lvUnivesities = findViewById(R.id.lvUniversities);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        client = new OkHttpClient();
        CallUniversityList("Kosovo");
    }

    private void CallUniversityList(String country)
    {
        universityList = new ArrayList<>();
        Request.Builder builder =
                new Request.Builder()
                        .get()
                        .url("http://universities.hipolabs.com/search?country="+country);
        Call call = client.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("FiekAppLog", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String strResponse = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(strResponse);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject tempObject = jsonArray.getJSONObject(i);
                            String name = tempObject.getString("name");
                            String country = tempObject.getString("country");
                            String alphaTwoCode = tempObject.getString("alpha_two_code");
                            JSONArray webPages = tempObject.getJSONArray("web_pages");
                            JSONArray domains = tempObject.getJSONArray("domains");

                            University tempUniversity =
                                    new University(name,webPages,country,alphaTwoCode,domains);
                            universityList.add(tempUniversity);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    finally {
                        Log.i("FiekAppLog", universityList.toString());
                    }
                }
                else
                {
                    Log.i("FiekAppLog", response.message());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        CallUniversityList(etCountry.getText().toString());
    }
}
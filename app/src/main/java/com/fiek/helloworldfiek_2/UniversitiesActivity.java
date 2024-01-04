package com.fiek.helloworldfiek_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

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
    List<UniversityGson> universityGsonList = new ArrayList<>();
    UniversityAdapter adapter;
    ProgressBar pbProgress;
    int min = 0, max = 100, initialValue = 10, increment = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        etCountry = findViewById(R.id.etCountry);
        lvUnivesities = findViewById(R.id.lvUniversities);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        pbProgress = findViewById(R.id.pbProgress);

        adapter = new UniversityAdapter(UniversitiesActivity.this);

        lvUnivesities.setAdapter(adapter);

        client = new OkHttpClient();
        GetUniversitiesGson("Kosovo");
    }

    private void GetUniversities(String country)
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

    private void GetUniversitiesGson(String country)
    {
        universityGsonList.clear();
        pbProgress.setProgress(initialValue);
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
                        increment = (max-initialValue)/jsonArray.length();
                        increment = increment<1?1:increment;
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject tempObject = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            UniversityGson tempUniversity
                                    = gson.fromJson(tempObject.toString(),UniversityGson.class);
                            universityGsonList.add(tempUniversity);

                            Thread.sleep(10);
                            final int k = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(increment==1)
                                    {
                                        int currentValue = pbProgress.getProgress();
                                        double incrementD = (double)(max-initialValue)/jsonArray.length();
                                        double iterations = (double)1/incrementD;
                                        if(k>0 && k%iterations==0)
                                            pbProgress.setProgress(currentValue+increment);
                                    }
                                    else
                                    {
                                        int currentValue = pbProgress.getProgress();
                                        pbProgress.setProgress(currentValue+increment);
                                    }

                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        Log.i("FiekAppLog", "Total: "+universityGsonList.size());
                        adapter.universityGsonList = universityGsonList;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                pbProgress.setProgress(max);
                            }
                        });
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
        GetUniversitiesGson(etCountry.getText().toString());
    }
}
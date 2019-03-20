package com.example.steve.myasynctaskloader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>> {
    RecyclerView recyclerView;
    WeatherAdapter adapter;
    EditText edtCity;
    Button btnSearch;
    static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        edtCity = findViewById(R.id.editCity);
        btnSearch = findViewById(R.id.btnCity);
        btnSearch.setOnClickListener(myListener);

        String city = edtCity.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, city);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String cities = "";
        Log.d("test","test");
        if (bundle != null) {
            cities = bundle.getString(EXTRAS_CITY);
        }
        return new MyAsyncTaskLoader(this, cities);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<WeatherItems>> loader, ArrayList<WeatherItems> weatherItems) {
        adapter.setData(weatherItems);
        Log.d("Json2" ,weatherItems.toString());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<WeatherItems>> loader) {
        adapter.setData(null);
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("test","test2");
            String city = edtCity.getText().toString();
            if (TextUtils.isEmpty(city)) return;
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_CITY, city);
            getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}

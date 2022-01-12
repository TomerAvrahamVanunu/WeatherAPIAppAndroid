package com.example.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    Button btn_cityID, btn_getWeatherByID, btn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign values to each control on the layout
        btn_cityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);

        lv_weatherReport = findViewById(R.id.lv_weatherReports);
        // if it's get ERROR - you should add final
        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        // click listeners for each button
        btn_cityID.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Callback-1-------
                 weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener()
                 {
                    @Override
                    public void onError(String message)
                    {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID)
                    {
                        Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });    // End Callback

        btn_getWeatherByID.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Callback-2--------
                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(), new WeatherDataService.ForecastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel>weatherReportModels) {
                         // put the entire list into listview control

                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        lv_weatherReport.setAdapter(arrayAdapter);
                    }
                });    // END Callback-2

            }
        });

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Callback-3-------
                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel>weatherReportModels) {
                        // put the entire list into listview control

                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        lv_weatherReport.setAdapter(arrayAdapter);
                    }
                });   // END Callback-3
            }
        });
    }

}  // END MainActivity Class
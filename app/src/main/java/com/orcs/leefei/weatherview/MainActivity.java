package com.orcs.leefei.weatherview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orcs.leefei.weatherview.component.WeatherRender;
import com.orcs.leefei.weatherview.component.WeatherRenderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherRenderView renderView = (WeatherRenderView) findViewById(R.id.sky_view);
        renderView.setWeatherType(WeatherRender.TYPE_CLEAR_SKY_DAY);
    }
}

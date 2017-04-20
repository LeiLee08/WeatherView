package com.orcs.leefei.weatherview.component;

import android.view.View;

/**
 * Created by Administrator on 2017/4/17.
 */

public class WeatherRainNightRender extends WeatherRainDayRender {

    public WeatherRainNightRender(View view) {
        super(view);
    }

    @Override
    protected int getLightCloudColor() {
        return COLOR_NIGHT_CLOUD_LIGHT;
    }
}

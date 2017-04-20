package com.orcs.leefei.weatherview.component;

import android.view.View;

/**
 * Created by Administrator on 2017/4/17.
 */

public class WeatherSnowNightRender extends WeatherSnowDayRender {

    public WeatherSnowNightRender(View view) {
        super(view);
    }

    @Override
    protected int getLightCloudColor() {
        return COLOR_NIGHT_CLOUD_LIGHT;
    }
}

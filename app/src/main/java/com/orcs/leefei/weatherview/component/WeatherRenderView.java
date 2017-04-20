package com.orcs.leefei.weatherview.component;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_BROKEN_CLOUD_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_BROKEN_CLOUD_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_CLEAR_SKY_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_CLEAR_SKY_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_FEW_CLOUDS_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_FEW_CLOUDS_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_MIST_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_RAIN_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_RAIN_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_SCATTERED_CLOUD;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_SHOWER_RAIN_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_SHOWER_RAIN_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_SNOW_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_SNOW_NIGHT;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_THUNDERSTROM_DAY;
import static com.orcs.leefei.weatherview.component.WeatherRender.TYPE_THUNDERSTROM_NIGHT;


/**
 * Created by Administrator on 2017/4/19.
 */

public class WeatherRenderView extends View {

    private WeatherRender weatherRender;
    private int defaultSize;


    public WeatherRenderView(Context context) {
        this(context, null);
    }

    public WeatherRenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defaultSize = MeasureUtils.dpTopx(context, 120);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(MeasureUtils.resolveSize(defaultSize, widthMeasureSpec),
                MeasureUtils.resolveSize(defaultSize, heightMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (weatherRender != null) {
            weatherRender.draw(canvas);
        }
    }

    public void setWeatherType(int type) {
        createWeatherRender(type);
        invalidate();
    }

    public void createWeatherRender(int type) {
        weatherRender = null;
        if (type == TYPE_SCATTERED_CLOUD) {
            weatherRender = new WeatherScatteredCloudRender(this);
        } else if (type == TYPE_BROKEN_CLOUD_DAY) {
            weatherRender = new WeatherBrokenCloudsDayRender(this);
        } else if (type == TYPE_BROKEN_CLOUD_NIGHT) {
            weatherRender = new WeatherBrokenCloudsNightRender(this);
        } else if (type == TYPE_CLEAR_SKY_DAY) {
            weatherRender = new WeatherClearSkyDayRender(this);
        } else if (type == TYPE_CLEAR_SKY_NIGHT) {
            weatherRender = new WeatherClearSkyNightRender(this);
        } else if (type == TYPE_FEW_CLOUDS_DAY) {
            weatherRender = new WeatherFewCloudsDayRender(this);
        } else if (type == TYPE_FEW_CLOUDS_NIGHT) {
            weatherRender = new WeatherFewFloudsNightRender(this);
        } else if (type == TYPE_MIST_DAY) {
            weatherRender = new WeatherMistDayRender(this);
        } else if (type == TYPE_RAIN_DAY) {
            weatherRender = new WeatherRainDayRender(this);
        } else if (type == TYPE_RAIN_NIGHT) {
            weatherRender = new WeatherRainNightRender(this);
        } else if (type == TYPE_SHOWER_RAIN_DAY) {
            weatherRender = new WeatherShowerRainDayRender(this);
        } else if (type == TYPE_SHOWER_RAIN_NIGHT) {
            weatherRender = new WeatherShowerRainNightRender(this);
        } else if (type == TYPE_SNOW_DAY) {
            weatherRender = new WeatherSnowDayRender(this);
        } else if (type == TYPE_SNOW_NIGHT) {
            weatherRender = new WeatherSnowNightRender(this);
        } else if (type == TYPE_THUNDERSTROM_DAY) {
            weatherRender = new WeatherThunderstormDayRender(this);
        } else if (type == TYPE_THUNDERSTROM_NIGHT) {
            weatherRender = new WeatherThunderstormNightRender(this);
        }
    }
}

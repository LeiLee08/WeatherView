package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

import com.orcs.leefei.weatherview.R;


/**
 * Created by Administrator on 2017/4/10.
 */

public class WeatherClearSkyDayRender extends WeatherRender {

    private Path pathStar;
    private Path pathCircle;
    private float degrees;


    public WeatherClearSkyDayRender(View view) {
        super(view);
        pathStar = createPath(R.string.weather_day_star);
        pathCircle = createPath(R.string.weather_day_circle);
    }


    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathStar, SCALE_DEFAULT);
        scaleToViewBounds(pathCircle, SCALE_DEFAULT * 0.6F);
    }


    @Override
    protected void draw(Canvas canvas) {
        //Draw star
        canvas.save();
        paint.setColor(COLOR_STAR);
        canvas.rotate(degrees, viewCenterX, viewCenterY);
        adjustToCenter(canvas, pathStar);
        canvas.drawPath(pathStar, paint);
        canvas.restore();
        //draw sun
        canvas.save();
        paint.setColor(COLOR_SUN);
        adjustToCenter(canvas, pathCircle);
        canvas.drawPath(pathCircle, paint);
        canvas.restore();
        degrees += 2;
        if (degrees > 360) {
            degrees = 0;
        }
        view.postInvalidateDelayed(80);
    }

}

package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

import com.orcs.leefei.weatherview.R;


/**
 * Created by Administrator on 2017/4/10.
 */

public class WeatherFewCloudsDayRender extends WeatherRender {

    public static final int STEP = 2;

    private Path pathStar;
    private Path pathCircle;
    private Path pathCloud;
    private float degrees;
    private float translate;
    private int startX;
    private int endX;
    private boolean directionRight = true;

    public WeatherFewCloudsDayRender(View view) {
        super(view);
        pathStar = createPath(R.string.weather_day_star);
        pathCircle = createPath(R.string.weather_day_circle);
        pathCloud = createPath(R.string.weather_day_cloud);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathStar, 0.5F);
        scaleToViewBounds(pathCircle, 0.3F);
        scaleToViewBounds(pathCloud, 0.45F);

        startX = (int) (viewWidth * 0.1F);
        endX = (int) (viewWidth * 0.5F);
        translate = startX;
    }

    @Override
    protected void draw(Canvas canvas) {
        //Draw star
        canvas.save();
        paint.setColor(COLOR_STAR);
        canvas.rotate(degrees % 360, viewCenterX, viewCenterY);
        adjustToCenter(canvas, pathStar);
        canvas.drawPath(pathStar, paint);
        canvas.restore();
        //draw sun
        canvas.save();
        paint.setColor(COLOR_SUN);
        adjustToCenter(canvas, pathCircle);
        canvas.drawPath(pathCircle, paint);
        canvas.restore();

        canvas.save();
        paint.setColor(COLOR_CLOUD);
        canvas.translate(translate, viewHeight * 0.38F);
        canvas.drawPath(pathCloud, paint);
        canvas.restore();

        degrees++;
        animateCloud();

        view.postInvalidateDelayed(80);
    }

    private void animateCloud() {
        if (directionRight) {
            translate += STEP;
            if (translate >= endX) {
                directionRight = false;
            }
        } else {
            translate -= STEP;
            if (translate <= startX) {
                directionRight = true;
            }
        }
    }
}

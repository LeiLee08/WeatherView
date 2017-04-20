package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.View;
import com.orcs.leefei.weatherview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WeatherRainDayRender extends WeatherRender {

    private static final float CLOUD_DRIFT_STEP = 0.02F;
    private static final float RAIN_DROP_STEP = 0.02F;
    private static final float MAX_CLOUD_DRIFT_RATIO = 0.4F;
    private static final float MIN_CLOUD_DRIFT_RATIO = 0.1F;
    private static final float CLOUD_SCALE = 0.6F;
    private static final float CLOUD_LIGHT_SCALE = 0.5F;
    private static final float MIN_DROP_DRIFT_RATION = 0.65F;
    private static final float MAX_DROP_DRIFT_RATION = 1.05F;
    private static final float RAIN_DROP_SCALE = 0.04F;

    private Path pathCloud;
    private Path pathCloudLight;

    private float cloudDriftRatio;
    private float lightCloudDriftRatio;
    private boolean driftRight = true;
    private boolean lightDriftRight = false;
    private List<RainDrop> rainRops;


    public WeatherRainDayRender(View view) {
        super(view);
        pathCloud = createPath(R.string.weather_day_cloud);
        pathCloudLight = createPath(R.string.weather_day_cloud);
        rainRops = createRainDrops(2);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathCloud, CLOUD_SCALE);
        scaleToViewBounds(pathCloudLight, CLOUD_LIGHT_SCALE);
        initRainRops(rainRops);

        cloudDriftRatio = MIN_CLOUD_DRIFT_RATIO;
        lightCloudDriftRatio = MAX_CLOUD_DRIFT_RATIO;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        paint.setColor(getLightCloudColor());
        canvas.translate(viewWidth * lightCloudDriftRatio, viewHeight * 0.05F);
        canvas.drawPath(pathCloudLight, paint);
        //draw light cloud
        paint.setColor(COLOR_CLOUD);
        canvas.translate((-viewWidth * lightCloudDriftRatio) + viewWidth * cloudDriftRatio,
                viewHeight * 0.1F);
        canvas.drawPath(pathCloud, paint);
        canvas.restore();

        paint.setColor(Color.WHITE);
        for (int i = 0; i < rainRops.size(); i++) {
            RainDrop rainDrop = rainRops.get(i);
            canvas.save();
            canvas.translate(rainDrop.offsetHorizontal, rainDrop.offsetVertical);
            canvas.drawPath(rainDrop.path, paint);
            canvas.restore();
            updateRainDrop(rainDrop);
        }

        updateCloud();
        updateLightCloud();

        view.postInvalidateDelayed(150);
    }

    protected int getLightCloudColor() {
        return COLOR_CLOUD_LIGHT;
    }

    private void updateRainDrop(RainDrop rainDrop) {
        rainDrop.dropDriftRatio += RAIN_DROP_STEP;
        rainDrop.offsetVertical = viewHeight * rainDrop.dropDriftRatio;
        if (rainDrop.dropDriftRatio >= MAX_DROP_DRIFT_RATION) {
            //reset
            setupRainDrop(rainDrop);
        }
    }

    private void updateCloud() {
        if (driftRight) {
            cloudDriftRatio += CLOUD_DRIFT_STEP;
            if (cloudDriftRatio >= MAX_CLOUD_DRIFT_RATIO) {
                driftRight = false;
            }
        } else {
            cloudDriftRatio -= CLOUD_DRIFT_STEP;
            if (cloudDriftRatio <= MIN_CLOUD_DRIFT_RATIO) {
                driftRight = true;
            }
        }
    }

    private void updateLightCloud() {
        if (lightDriftRight) {
            lightCloudDriftRatio += CLOUD_DRIFT_STEP;
            if (lightCloudDriftRatio >= MAX_CLOUD_DRIFT_RATIO) {
                lightDriftRight = false;
            }
        } else {
            lightCloudDriftRatio -= CLOUD_DRIFT_STEP;
            if (lightCloudDriftRatio <= MIN_CLOUD_DRIFT_RATIO) {
                lightDriftRight = true;
            }
        }
    }

    private List<RainDrop> createRainDrops(int count) {
        List<RainDrop> paths = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            RainDrop drop = new RainDrop();
            drop.path = createPath(R.string.weather_rain_drop);
            paths.add(drop);
        }
        return paths;
    }

    private void initRainRops(List<RainDrop> rainDrops) {
        for (int i = 0, size = rainDrops.size(); i < size; i++) {
            RainDrop rainDrop = rainDrops.get(i);
            rainDrop.offsetHorizontal = (i + 1F) * viewWidth / (size + 1);
            setupRainDrop(rainDrop);
        }
    }

    private void setupRainDrop(RainDrop rainDrop) {
        rainDrop.dropDriftRatio = (float) (MIN_DROP_DRIFT_RATION + Math.random() * 0.1F);
        rainDrop.offsetVertical = viewHeight * rainDrop.dropDriftRatio;
        scaleToViewBounds(rainDrop.path, (float) (RAIN_DROP_SCALE + Math.random() * RAIN_DROP_SCALE));
    }

    class RainDrop {
        Path path;
        float offsetVertical;
        float offsetHorizontal;
        float dropDriftRatio;

        @Override
        public String toString() {
            return "RainDrop{" +
                    "path=" + path +
                    ", offsetVertical=" + offsetVertical +
                    ", offsetHorizontal=" + offsetHorizontal +
                    ", dropDriftRatio=" + dropDriftRatio +
                    '}';
        }
    }
}

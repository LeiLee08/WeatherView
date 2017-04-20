package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.view.View;

import com.orcs.leefei.weatherview.R;


/**
 * Created by Administrator on 2017/4/19.
 */

public class WeatherBrokenCloudsDayRender extends WeatherRender {
    private static final float CLOUD_DRIFT_STEP = 0.02F;
    private static final float MAX_CLOUD_DRIFT_RATIO = 0.4F;
    private static final float MIN_CLOUD_DRIFT_RATIO = 0.1F;
    private static final float CLOUD_SCALE = 0.6F;
    private static final float CLOUD_LIGHT_SCALE = 0.5F;

    private Path pathCloud;
    private Path pathCloudLight;
    private float cloudDriftRatio;
    private float lightCloudDriftRatio;
    private boolean driftRight = true;
    private boolean lightDriftRight = false;

    public WeatherBrokenCloudsDayRender(View view) {
        super(view);
        pathCloud = createPath(R.string.weather_day_cloud);
        pathCloudLight = createPath(R.string.weather_day_cloud);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathCloud, CLOUD_SCALE);
        scaleToViewBounds(pathCloudLight, CLOUD_LIGHT_SCALE);

        cloudDriftRatio = MIN_CLOUD_DRIFT_RATIO;
        lightCloudDriftRatio = MAX_CLOUD_DRIFT_RATIO;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        paint.setColor(getLightCloudColor());
        canvas.translate(viewWidth * lightCloudDriftRatio, viewHeight * 0.2F);
        canvas.drawPath(pathCloudLight, paint);
        //draw light cloud
        paint.setColor(COLOR_CLOUD);
        canvas.translate((-viewWidth * lightCloudDriftRatio) + viewWidth * cloudDriftRatio,
                viewHeight * 0.1F);
        canvas.drawPath(pathCloud, paint);
        canvas.restore();

        updateCloud();
        updateLightCloud();

        view.postInvalidateDelayed(150);
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
}

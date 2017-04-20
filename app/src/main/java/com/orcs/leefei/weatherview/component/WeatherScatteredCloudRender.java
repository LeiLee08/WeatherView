package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.view.View;

import com.orcs.leefei.weatherview.R;

/**
 * Created by Administrator on 2017/4/19.
 */

public class WeatherScatteredCloudRender extends WeatherRender{
    private static final float CLOUD_DRIFT_STEP = 0.02F;
    private static final float MAX_CLOUD_DRIFT_RATIO = 0.4F;
    private static final float MIN_CLOUD_DRIFT_RATIO = 0.1F;

    private float cloudDriftRatio;
    private boolean driftRight = true;
    private Path cloudPath;

    public WeatherScatteredCloudRender(View view) {
        super(view);
        cloudPath = createPath(R.string.weather_day_cloud);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(cloudPath);
        cloudDriftRatio = 0.2F;
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.translate(300 * cloudDriftRatio, 300 * 0.25F);
        canvas.drawPath(cloudPath, paint);
        canvas.restore();

        updateCloud();

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
}

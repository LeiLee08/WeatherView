package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

import com.orcs.leefei.weatherview.R;

/**
 * Created by Administrator on 2017/4/17.
 */

public class WeatherThunderstormDayRender extends WeatherRender {
    private static final float CLOUD_DRIFT_STEP = 0.02F;
    private static final float LIGHTNING_DRIFT_STEP = 0.02F;
    private static final float MAX_CLOUD_DRIFT_RATIO = 0.4F;
    private static final float MIN_CLOUD_DRIFT_RATIO = 0.1F;
    private static final float CLOUD_SCALE = 0.6F;
    private static final float CLOUD_LIGHT_SCALE = 0.5F;
    private static final float LIGHTNING_SCALE = 0.24F;
    private static final float MIN_LIGHTNING_DRIFT_RATIO = 0.55F;
    private static final float MAX_LIGHTNING_DRIFT_RATIO = 1.05F;

    private Path pathCloud;
    private Path pathCloudLight;
    private float cloudDriftRatio;
    private float lightCloudDriftRatio;
    private float lightningDriftRatio;
    private boolean driftRight = true;
    private boolean lightDriftRight = false;
    private final Path lightningPath;

    public WeatherThunderstormDayRender(View view) {
        super(view);
        pathCloud = createPath(R.string.weather_day_cloud);
        pathCloudLight = createPath(R.string.weather_day_cloud);
        lightningPath = createPath(R.string.weather_thunderstorm_lightning);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathCloud, CLOUD_SCALE);
        scaleToViewBounds(pathCloudLight, CLOUD_LIGHT_SCALE);
        scaleToViewBounds(lightningPath, LIGHTNING_SCALE);

        cloudDriftRatio = MIN_CLOUD_DRIFT_RATIO;
        lightCloudDriftRatio = MAX_CLOUD_DRIFT_RATIO;
        lightningDriftRatio = MIN_LIGHTNING_DRIFT_RATIO;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        paint.setColor(getLightCloudColor());
        canvas.translate(viewWidth * lightCloudDriftRatio, viewHeight * 0.1F);
        canvas.drawPath(pathCloudLight, paint);
        //draw light cloud
        paint.setColor(COLOR_CLOUD);
        canvas.translate((-viewWidth * lightCloudDriftRatio) + viewWidth * cloudDriftRatio,
                viewHeight * 0.1F);
        canvas.drawPath(pathCloud, paint);
        canvas.restore();

        paint.setColor(COLOR_LIGHTNING);
        canvas.save();
        canvas.translate(viewWidth * 0.35F, viewHeight * lightningDriftRatio);
//        paint.setAlpha((int) ((1 - lightningDriftRatio + 0.5F) * 255));
        canvas.drawPath(lightningPath, paint);
        canvas.restore();

        updateCloud();
        updateLightCloud();
        updateLightning();

        view.postInvalidateDelayed(150);
    }

    private void updateLightning() {
        lightningDriftRatio += LIGHTNING_DRIFT_STEP;
        if (lightningDriftRatio >= MAX_LIGHTNING_DRIFT_RATIO) {
            lightningDriftRatio = MIN_LIGHTNING_DRIFT_RATIO;
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
}

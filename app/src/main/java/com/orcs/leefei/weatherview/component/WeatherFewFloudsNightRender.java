package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

import com.orcs.leefei.weatherview.R;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WeatherFewFloudsNightRender extends WeatherRender {
    private static final float CLOUD_DRIFT_STEP = 0.02F;
    private static final float MOON_DRIFT_STEP = 0.03F;
    private static final float MIN_CLOUD_DRIFT_RATIO = 0.1F;
    private static final float MAX_CLOUD_DRIFT_RATIO = 0.5F;

    private Path pathCloud;
    private float cloudDriftRatio;
    private boolean driftRight;

    private RectF oval;
    private float radius;
    private float ratio;
    private boolean incMoonRatio;
    private final PorterDuffXfermode porterDuff;

    public WeatherFewFloudsNightRender(View view) {
        super(view);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        porterDuff = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        pathCloud = createPath(R.string.weather_day_cloud);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(pathCloud, 0.5F);
        cloudDriftRatio = MIN_CLOUD_DRIFT_RATIO;

        radius = viewCenterX * 0.5F;
        oval = new RectF(viewCenterX - radius, viewCenterX - radius,
                viewCenterX + radius, viewCenterX + radius);
    }


    @Override
    protected void draw(Canvas canvas) {
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(),
                null, Canvas.ALL_SAVE_FLAG);
        canvas.drawArc(oval, 0, 360, true, paint);
        paint.setXfermode(porterDuff);
        canvas.translate(-radius * ratio, 0);
        canvas.drawArc(oval, 0, 360, true, paint);
        paint.setXfermode(null);
        canvas.restore();

        canvas.save();
        paint.setColor(COLOR_CLOUD);
        canvas.translate(viewWidth * cloudDriftRatio, viewHeight * 0.3F);
        canvas.drawPath(pathCloud, paint);
        canvas.restore();

        updateCloudDriftRatio();
        updateMoonnDriftRatio();

        view.postInvalidateDelayed(150);
    }

    private void updateCloudDriftRatio() {
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

    private void updateMoonnDriftRatio() {
        if (incMoonRatio) {
            ratio += MOON_DRIFT_STEP;
            if (ratio >= 0.8F) {
                incMoonRatio = false;
            }
        } else {
            ratio -= MOON_DRIFT_STEP;
            if (ratio <= -0.2F) {
                incMoonRatio = true;
            }
        }
    }
}

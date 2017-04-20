package com.orcs.leefei.weatherview.component;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Administrator on 2017/4/11.
 */

public class WeatherClearSkyNightRender extends WeatherRender {

    private static final float MOON_DRIFT_STEP = 0.02F;

    private RectF oval;
    private float radius;
    private float ratio;
    private boolean incRatio;
    private PorterDuffXfermode porterDuff;

    public WeatherClearSkyNightRender(View view) {
        super(view);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        porterDuff = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    public void onGetViewSize(int w, int h) {
        radius = viewCenterX * SCALE_DEFAULT * 0.85F;
        oval = new RectF(viewCenterX - radius, viewCenterX - radius,
                viewCenterX + radius, viewCenterX + radius);
        ratio = 0.8F;
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

        animateSkyNight();

        view.postInvalidateDelayed(60);
    }

    private void animateSkyNight() {
        if (incRatio) {
            ratio += MOON_DRIFT_STEP;
            if (ratio >= 0.9F) {
                incRatio = false;
            }
        } else {
            ratio -= MOON_DRIFT_STEP;
            if (ratio <= 0.25F) {
                incRatio = true;
            }
        }
    }
}

package com.orcs.leefei.weatherview.component;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

import com.orcs.leefei.weatherview.R;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WeatherMistDayRender extends WeatherRender {

    private static final int BLUR_MASK_STEP = 2;
    private static final int MAX_BLUR_MASK = 8;
    private static final int MIN_BLUR_MASK = 1;

    private final Path mistPath;
    private int maskRadius = MIN_BLUR_MASK;
    private boolean incBlurMask;

    public WeatherMistDayRender(View view) {
        super(view);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mistPath = createPath(R.string.weather_mist_day);
    }

    @Override
    public void onGetViewSize(int w, int h) {
        scaleToViewBounds(mistPath, SCALE_DEFAULT * 0.85F);
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        paint.setMaskFilter(new BlurMaskFilter(maskRadius, BlurMaskFilter.Blur.NORMAL));
        adjustToCenter(canvas, mistPath);
        canvas.drawPath(mistPath, paint);
        canvas.restore();

        updateBlurMask();

        view.postInvalidateDelayed(300);
    }

    private void updateBlurMask() {
        if (incBlurMask) {
            maskRadius += BLUR_MASK_STEP;
            if (maskRadius >= MAX_BLUR_MASK) {
                incBlurMask = false;
            }
        } else {
            maskRadius -= BLUR_MASK_STEP;
            if (maskRadius <= MIN_BLUR_MASK) {
                if (maskRadius < 1) {
                    maskRadius = 1;
                }
                incBlurMask = true;
            }
        }
    }
}

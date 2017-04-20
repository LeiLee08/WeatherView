package com.orcs.leefei.weatherview.component;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/4/10.
 */

public class MeasureUtils {

    public static int resolveSize(int size, int measureSpec) {
        int result = size;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                //big as we want to be
                result = size;
                break;
            case View.MeasureSpec.AT_MOST:
                //big as we want to be, up to the spec
                result = Math.min(size, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                //Must be the spec size
                result = specSize;
                break;
        }
        return result;
    }

    public static int dpTopx(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }
}

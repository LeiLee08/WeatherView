package com.orcs.leefei.weatherview.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewTreeObserver;

import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class WeatherRender implements ViewTreeObserver.OnPreDrawListener {
    public static final int TYPE_CLEAR_SKY_NIGHT = 1;
    public static final int TYPE_SCATTERED_CLOUD = 2;
    public static final int TYPE_BROKEN_CLOUD_DAY = 3;
    public static final int TYPE_BROKEN_CLOUD_NIGHT = 4;
    public static final int TYPE_CLEAR_SKY_DAY = 5;
    public static final int TYPE_FEW_CLOUDS_DAY = 6;
    public static final int TYPE_FEW_CLOUDS_NIGHT = 7;
    public static final int TYPE_MIST_DAY = 8;
    public static final int TYPE_RAIN_DAY = 9;
    public static final int TYPE_RAIN_NIGHT = 10;
    public static final int TYPE_SHOWER_RAIN_DAY = 11;
    public static final int TYPE_SHOWER_RAIN_NIGHT = 12;
    public static final int TYPE_SNOW_DAY = 13;
    public static final int TYPE_SNOW_NIGHT = 14;
    public static final int TYPE_THUNDERSTROM_DAY = 15;
    public static final int TYPE_THUNDERSTROM_NIGHT = 16;

    public static int COLOR_CLOUD = Color.parseColor("#FFFFFF");
    public static int COLOR_CLOUD_LIGHT = Color.parseColor("#d0d9ff");
    public static int COLOR_NIGHT_CLOUD_LIGHT = Color.parseColor("#0277bd");
    public static int COLOR_STAR = Color.parseColor("#F2BB35");
    public static int COLOR_SUN = Color.parseColor("#FAE2AA");
    public static int COLOR_LIGHTNING = Color.parseColor("#F2BB35");

    public static float SCALE_DEFAULT = 0.57F;

    protected final Context context;
    protected final Paint paint;
    protected final View view;

    protected RectF pathBounds;
    protected Matrix scaleMatrix;

    protected int viewWidth;
    protected int viewHeight;
    protected int viewCenterX;
    protected int viewCenterY;

    public WeatherRender(final View view) {
        this.context = view.getContext();
        this.view = view;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        pathBounds = new RectF();
        scaleMatrix = new Matrix();
        view.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public void onGetViewSize(int w, int h) {
    }

    protected abstract void draw(@NonNull Canvas canvas);

    protected int getLightCloudColor() {
        return COLOR_CLOUD_LIGHT;
    }


    protected void adjustToCenter(Canvas canvas, Path path) {
        computeBounds(path);
        canvas.translate(viewCenterX - pathBounds.centerX(), viewCenterY - pathBounds.centerY());
    }

    protected Path createPath(@StringRes int resId) {
        return SVGParser.parsePath(context.getString(resId));
    }

    protected void computeBounds(Path path) {
        path.computeBounds(pathBounds, true);
    }

    protected void scaleToViewBounds(Path path) {
        scaleToViewBounds(path, SCALE_DEFAULT);
    }

    protected void scaleToViewBounds(Path path, float scale) {
        scaleMatrix.reset();
        computeBounds(path);
        scale = getScale(pathBounds) * scale;
        scaleMatrix.setScale(scale, scale);
        path.transform(scaleMatrix);
    }

    protected float getScale(RectF bounds) {
        return Math.min(viewWidth / bounds.width(), viewHeight / bounds.height());
    }

    @Override
    public boolean onPreDraw() {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        viewWidth = view.getMeasuredWidth();
        viewHeight = view.getMeasuredHeight();
        viewCenterX = viewWidth / 2;
        viewCenterY = viewHeight / 2;
        onGetViewSize(viewWidth, viewHeight);
        return true;
    }
}

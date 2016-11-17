package com.cinemast.cinemast.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Triangle extends View {

    public Triangle(Context context) {
        super(context);
    }

    public Triangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        Path path = new Path();
        path.moveTo( 0, h);
        path.lineTo( w , h);
        path.lineTo( w , 0);
        path.lineTo( 0 , h);
        path.close();

        Paint p = new Paint();
        p.setColor( Color.WHITE );

        canvas.drawPath(path, p);
    }
}
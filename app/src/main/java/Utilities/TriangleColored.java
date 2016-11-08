package Utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.cinemast.cinemast.R;

public class TriangleColored extends View {

    public TriangleColored(Context context) {
        super(context);
    }

    public TriangleColored(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TriangleColored(Context context, AttributeSet attrs) {
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
        p.setColor( getResources().getColor(R.color.colorPrimary) );

        canvas.drawPath(path, p);
    }
}
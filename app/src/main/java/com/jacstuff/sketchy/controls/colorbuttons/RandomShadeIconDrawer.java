package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import com.jacstuff.sketchy.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RandomShadeIconDrawer {

    private final Paint paint;
    private int backgroundColor;

    public RandomShadeIconDrawer(Context context){
        backgroundColor = context.getColor(R.color.multi_shade_bg);
        paint = new Paint();
    }


    public void drawBackgroundOf(Button button, final List<Integer> shades){

        if(shades == null){
            return;
        }

        Drawable drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Rect bounds = this.getBounds();
                paint.setColor(backgroundColor);
                canvas.drawRect(bounds, paint);
                float centerX = (bounds.right - bounds.left) /2f;
                float centerY = (bounds.bottom - bounds.top) / 2f;
                float radius  = centerX / 2;
                float thirdOfRadius = radius / 3;
                float x = (centerX - radius) + thirdOfRadius;
                float y = (centerY + radius) - thirdOfRadius;

                int lastShadeIndex = shades.size() -1;
                int[] shadesIndexesToDraw = {0, lastShadeIndex/3, (lastShadeIndex/3) * 2, lastShadeIndex};

                for (int j : shadesIndexesToDraw) {
                    paint.setColor(shades.get(j));
                    canvas.drawCircle(x, y, radius, paint);
                    x += thirdOfRadius;
                    y -= thirdOfRadius;
                }
            }

            @Override
            public void setAlpha(int i) { }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) { }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }
        };
        button.setBackground(drawable);
    }

}

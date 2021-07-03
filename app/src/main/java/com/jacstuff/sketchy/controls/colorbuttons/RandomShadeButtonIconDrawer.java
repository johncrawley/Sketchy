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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RandomShadeButtonIconDrawer {



    private final Paint paint;
    private final String text;

    public RandomShadeButtonIconDrawer(Context context){

        paint = new Paint();
        paint.setTextSize(48);
        paint.setShadowLayer(2, 3, 3, Color.BLACK);
        text = context.getString(R.string.random_shade_button_text);
    }


    public void drawBackgroundOf(Button button, final int shade){

        Drawable drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Rect bounds = this.getBounds();
                paint.setColor(shade);
                canvas.drawRect(bounds, paint);
                float textX = bounds.width()/7f;
                float textY = bounds.height()/2.5f;

                paint.setColor(Color.LTGRAY);
                canvas.drawText(text, textX, textY, paint);

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

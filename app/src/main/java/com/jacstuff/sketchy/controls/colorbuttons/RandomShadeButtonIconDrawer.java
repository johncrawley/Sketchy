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

import com.jacstuff.sketchy.ColorUtils;
import com.jacstuff.sketchy.R;
import static com.jacstuff.sketchy.ColorUtils.Rgb.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RandomShadeButtonIconDrawer {

    private final Paint paint;
    private final String text;


    public RandomShadeButtonIconDrawer(Context context){
        paint = new Paint();
        text = context.getString(R.string.random_shade_button_text);
        paint.setFakeBoldText(true);
    }


    public void drawBackgroundOf(Button button, final int shade){

        Drawable drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Rect bounds = this.getBounds();
                paint.setColor(shade);
                canvas.drawRect(bounds, paint);
                paint.setColor(nextShadeUpFrom(shade));
                float textX = bounds.width()/19f * 7;
                float textY = bounds.height()/16f * 11;
                paint.setTextSize(bounds.width() /1.8f);
                canvas.drawText(text, textX, textY, paint);
            }


            private int nextShadeUpFrom(int color){
                if(color == Color.BLACK){
                    return Color.rgb(80,80, 80);
                }
                if(color == Color.BLUE){
                    return Color.rgb(100,100, 255);
                }

                return Color.rgb(getModified(color, RED),
                        getModified(color, GREEN),
                        getModified(color, BLUE));
            }


            private int getModified(int color, ColorUtils.Rgb rgb){
                int colorComponent =  ColorUtils.getComponentFrom(color, rgb);
                int diff = 32;
                return colorComponent + diff >= 255 ? colorComponent -diff : colorComponent + diff;
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

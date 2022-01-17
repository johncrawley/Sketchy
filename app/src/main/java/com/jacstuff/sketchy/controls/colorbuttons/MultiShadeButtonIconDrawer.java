package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MultiShadeButtonIconDrawer {

    private final Paint paint, drawPaint;
    private final int backgroundColor;
    private final MainViewModel viewModel;

    public MultiShadeButtonIconDrawer(Context context, MainViewModel viewModel){
        backgroundColor = context.getColor(R.color.multi_shade_bg);
        this.viewModel = viewModel;
        paint = new Paint();
        drawPaint = new Paint();
    }


    public void drawBackgroundOf(Button button, final List<Integer> shades){

        if(shades == null){
            return;
        }
        int key = (int)button.getTag(R.string.tag_button_color);
        Drawable drawable = viewModel.buttonDrawableMap.get(key);
        if(drawable == null){
            drawable =  createDrawableFor(shades) ;
            viewModel.buttonDrawableMap.put(key, drawable);
        }
        button.setBackground(drawable);
    }


    private Drawable createDrawableFor(List<Integer> shades){
        return new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Rect bounds = this.getBounds();
                paint.setColor(backgroundColor);
                // Bitmap bitmap = Bitmap.createBitmap(bounds.right - bounds.left, bounds.bottom - bounds.top, Bitmap.Config.ARGB_8888);
                //canvas.setBitmap(bitmap);
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

    }

}

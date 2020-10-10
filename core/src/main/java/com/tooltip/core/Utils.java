/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2018. Viнt@rь
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tooltip.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Tooltip utils
 */
final class Utils {

    public static Rect calculateRectOnScreen(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Rect(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static Rect calculateRectInWindow(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Rect(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static int getScreenWidth(@NonNull Context context) {
        return getScreenSize(context).x;
    }

    public static int getScreenHeight(@NonNull Context context) {
        return getScreenSize(context).y;
    }

    public static int getScreenRealWidth(@NonNull Context context) {
        return getScreenRealSize(context).x;
    }

    public static int getScreenRealHeight(@NonNull Context context) {
        return getScreenRealSize(context).y;
    }

    private static DisplayMetrics getDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    private static Point getScreenSize(@NonNull Context context) {
        Display display;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            display = context.getDisplay();
        } else {
            display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay();
        }
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private static Point getScreenRealSize(@NonNull Context context) {
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                display = context.getDisplay();
            } else {
                display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay();
            }
            display.getRealSize(size);
        } else {
            DisplayMetrics dm = getDisplayMetrics();
            size.set(dm.widthPixels, dm.heightPixels);
        }
        return size;
    }

    public static float pxToDp(@Px float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static float dpToPx(@Dimension(unit = Dimension.DP) float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}

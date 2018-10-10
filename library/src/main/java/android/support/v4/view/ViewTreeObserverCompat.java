/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2017. Viнt@rь
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

package android.support.v4.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.view.ViewTreeObserver;

/**
 * Helper for accessing features in {@link ViewTreeObserver} introduced after API
 * level 4 in a backwards compatible fashion.
 */
public final class ViewTreeObserverCompat {

    static class ViewTreeObserverCompatBaseImpl {

        public void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener victim) {
            viewTreeObserver.removeGlobalOnLayoutListener(victim);
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    static class ViewTreeObserverCompatApi16Impl extends ViewTreeObserverCompatBaseImpl {

        @Override
        public void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener victim) {
            viewTreeObserver.removeOnGlobalLayoutListener(victim);
        }
    }

    static final ViewTreeObserverCompatBaseImpl IMPL;
    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= VERSION_CODES.JELLY_BEAN) {
            IMPL = new ViewTreeObserverCompatApi16Impl();
        } else {
            IMPL = new ViewTreeObserverCompatBaseImpl();
        }
    }

    /*
     * Hide the constructor.
     */
    private ViewTreeObserverCompat() {
    }

    /**
     * Remove a previously installed global layout callback
     *
     * @param victim The callback to remove
     *
     * @throws IllegalStateException If {@link ViewTreeObserver#isAlive()} returns false
     *
     * @see ViewTreeObserver#addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener)
     */
    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener victim) {
        IMPL.removeOnGlobalLayoutListener(viewTreeObserver, victim);
    }
}

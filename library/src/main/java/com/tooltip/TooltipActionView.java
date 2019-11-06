/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016. Viнt@rь
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

package com.tooltip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.tooltip.core.TooltipActionMenuItemView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @deprecated Use {@link TooltipActionMenuItemView} instead.
 */
@Deprecated
public class TooltipActionView extends TooltipActionMenuItemView {

    public TooltipActionView(Context context) {
        super(context, null);
    }

    public TooltipActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TooltipActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @deprecated Implementation not longer needed. Use {@link TooltipActionMenuItemView} instead.
     */
    @Deprecated
    public void setVisible(boolean visible) {

    }

    /**
     * @deprecated Implementation not longer needed. Use {@link TooltipActionMenuItemView} instead.
     */
    @Deprecated
    @Nullable
    public MenuItem getMenuItem() {
        return null;
    }

    /**
     * @deprecated Implementation not longer needed. Use {@link TooltipActionMenuItemView} instead.
     */
    @Deprecated
    public void setMenuItem(@NonNull MenuItem menuItem) {

    }

    /**
     * @deprecated Implementation not longer needed. Use {@link TooltipActionMenuItemView} instead.
     */
    @Deprecated
    public void setOnMenuItemClick(MenuItem.OnMenuItemClickListener l) {
    }
}

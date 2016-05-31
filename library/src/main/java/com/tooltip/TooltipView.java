package com.tooltip;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.tooltip.R;

/**
 * Created by Viнt@rь on 26.05.2016
 */
public class TooltipView extends FrameLayout {

    public TooltipView(Context context) {
        this(context, null);
    }

    public TooltipView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tooltipViewStyle);
    }

    public TooltipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TooltipView, defStyleAttr, 0);
        a.recycle();
    }
}

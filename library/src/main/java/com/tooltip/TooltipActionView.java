package com.tooltip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Viнt@rь on 03.12.2015
 */
public class TooltipActionView extends FrameLayout implements View.OnClickListener, View.OnLongClickListener {

    private TextView mTextView;
    private ImageView mImageView;

    public TooltipActionView(Context context) {
        this(context, null);
    }

    public TooltipActionView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.actionButtonStyle);
    }

    public TooltipActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int itemWidth = getResources().getDimensionPixelSize(R.dimen.action_button_width);
        LayoutParams layoutParams = new LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        mTextView = new TextView(context);
        mImageView = new ImageView(context);

        mTextView.setLayoutParams(layoutParams);
        mTextView.setDuplicateParentStateEnabled(true);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setDuplicateParentStateEnabled(true);

        addView(mTextView);
        addView(mImageView);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public void setMenuItem(MenuItem menuItem) {
        View actionView = menuItem.getActionView();
        if (actionView != null && actionView.equals(this)) {
            if (menuItem.getIcon() != null) {
                mImageView.setImageDrawable(menuItem.getIcon());
            } else if (menuItem.getTitle() != null) {
                mTextView.setText(menuItem.getTitle());
            }
        }
    }
}

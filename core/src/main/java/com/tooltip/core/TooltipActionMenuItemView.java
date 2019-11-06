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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.TextViewCompat;

/**
 * Created by Viнt@rь on 14.09.2016
 * <p>
 * Tooltip action menu item view implementation
 */
public class TooltipActionMenuItemView extends FrameLayout {
    private static final int MAX_ICON_SIZE = 32; // dp

    private boolean mAllowTextWithIcon;

    private int mMaxIconSize;

    private CharSequence mTitle;

    private MenuItem mMenuItem;
    private TextView mTextView;

    private MenuBuilder.ItemInvoker mItemInvoker;

    public TooltipActionMenuItemView(Context context) {
        this(context, null);
    }

    public TooltipActionMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.actionButtonStyle);
    }

    public TooltipActionMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);

        final Resources res = context.getResources();
        final float density = res.getDisplayMetrics().density;
        mMaxIconSize = (int) (MAX_ICON_SIZE * density + 0.5f);

        mAllowTextWithIcon = shouldAllowTextWithIcon();

        mTextView = new AppCompatTextView(context);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(getAttr(context, android.R.attr.actionMenuTextColor));
        TextViewCompat.setTextAppearance(mTextView, getAttr(context, android.R.attr.actionMenuTextAppearance));

        LayoutParams textLayoutParams = generateDefaultLayoutParams();
        textLayoutParams.gravity = Gravity.CENTER;
        addView(mTextView, textLayoutParams);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean performClick() {
        if (mItemInvoker != null) {
            mItemInvoker.invokeItem((MenuItemImpl) mMenuItem);
        }
        return super.performClick();
    }

    /**
     * Only for internal usage
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (mMenuItem != null) {
            initialize(mMenuItem);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mMenuItem != null) {
            mMenuItem.setEnabled(enabled);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ActionMenuView actionMenuView = (ActionMenuView) getParent();

        mItemInvoker = actionMenuView;

        Menu menu = actionMenuView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getItemId() == getId()) {
                initialize(menuItem);
                return;
            }
        }
    }

    private void initialize(@NonNull MenuItem menuItem) {
        mMenuItem = menuItem;

        setIcon(menuItem.getIcon());
        setTitle(menuItem.getTitleCondensed());
        super.setEnabled(menuItem.isEnabled());

        updateTextButtonVisibility();
    }

    private void setIcon(Drawable icon) {
        if (icon != null) {
            int width = icon.getIntrinsicWidth();
            int height = icon.getIntrinsicHeight();
            if (width > mMaxIconSize) {
                final float scale = (float) mMaxIconSize / width;
                width = mMaxIconSize;
                height = (int) (height * scale);
            }
            if (height > mMaxIconSize) {
                final float scale = (float) mMaxIconSize / height;
                height = mMaxIconSize;
                width = (int) (width * scale);
            }
            icon.setBounds(0, 0, width, height);
        }
        mTextView.setCompoundDrawables(icon, null, null, null);
    }

    private void setTitle(CharSequence title) {
        mTitle = title;
    }

    @SuppressLint("RestrictedApi")
    private void updateTextButtonVisibility() {
        boolean visible = !TextUtils.isEmpty(mTitle);
        visible &= mMenuItem.getIcon() == null || (((MenuItemImpl) mMenuItem).showsTextAsAction() && mAllowTextWithIcon);

        mTextView.setText(visible ? mTitle : null);

        // Show the tooltip for items that do not already show text.
        final CharSequence contentDescription = MenuItemCompat.getContentDescription(mMenuItem);
        if (TextUtils.isEmpty(contentDescription)) {
            // Use the uncondensed title for content description, but only if the title is not shown already.
            setContentDescription(visible ? null : mMenuItem.getTitle());
        } else {
            setContentDescription(contentDescription);
        }

        final CharSequence tooltipText = MenuItemCompat.getTooltipText(mMenuItem);
        if (TextUtils.isEmpty(tooltipText)) {
            // Use the uncondensed title for tooltip, but only if the title is not shown already.
            TooltipCompat.setTooltipText(this, visible ? null : mMenuItem.getTitle());
        } else {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
    }

    /**
     * @return the resource ID value in the {@code context} specified by {@code attr}
     */
    private static int getAttr(@NonNull Context context, @AttrRes int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.resourceId;
    }

    /**
     * Whether action menu items should obey the "withText" showAsAction flag. This may be set to
     * false for situations where space is extremely limited
     */
    private boolean shouldAllowTextWithIcon() {
        final Configuration config = getContext().getResources().getConfiguration();
        final int widthDp = config.screenWidthDp;
        final int heightDp = config.screenHeightDp;

        return widthDp >= 480 || (widthDp >= 640 && heightDp >= 480) || config.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Simple {@code Tooltip} text implementation
 */
public class Tooltip extends com.tooltip.core.Tooltip<Tooltip.Builder> {
    private static final String TAG = "Tooltip";

    protected Tooltip(Builder builder) {
        super(builder);
    }

    @NonNull
    @Override
    protected View createContentView(@NonNull Builder builder) {
        TextView textView = new TextView(mContext);

        TextViewCompat.setTextAppearance(textView, builder.mTextAppearance);
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, builder.mDrawableStart, builder.mDrawableTop, builder.mDrawableEnd, builder.mDrawableBottom);

        textView.setText(builder.mText);
        textView.setPadding(builder.mPadding, builder.mPadding, builder.mPadding, builder.mPadding);
        textView.setLineSpacing(builder.mLineSpacingExtra, builder.mLineSpacingMultiplier);
        textView.setTypeface(builder.mTypeface, builder.mTextStyle);
        textView.setCompoundDrawablePadding(builder.mDrawablePadding);

        if (builder.mMaxWidth >= 0) {
            textView.setMaxWidth(builder.mMaxWidth);
        }

        if (builder.mTextSize >= 0) {
            textView.setTextSize(TypedValue.TYPE_NULL, builder.mTextSize);
        }

        if (builder.mTextColor != null) {
            textView.setTextColor(builder.mTextColor);
        }

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        textViewParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(textViewParams);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(builder.mBackgroundColor);
        drawable.setCornerRadius(builder.mCornerRadius);

        ViewCompat.setBackground(textView, drawable);

        return textView;
    }

    @Override
    protected int getArrowColor(@NonNull Builder builder) {
        return builder.mBackgroundColor;
    }

    public static class Builder extends com.tooltip.core.Tooltip.Builder {
        private int mBackgroundColor;
        private int mTextAppearance;
        private int mTextStyle;
        private int mPadding;
        private int mMaxWidth;
        private int mDrawablePadding;

        private float mCornerRadius;
        private float mTextSize;
        private float mLineSpacingExtra;
        private float mLineSpacingMultiplier = 1f;

        private Drawable mDrawableBottom;
        private Drawable mDrawableEnd;
        private Drawable mDrawableStart;
        private Drawable mDrawableTop;

        private CharSequence mText;
        private ColorStateList mTextColor;
        private Typeface mTypeface = Typeface.DEFAULT;

        public Builder(@NonNull MenuItem anchorMenuItem) {
            super(anchorMenuItem);
        }

        public Builder(@NonNull MenuItem anchorMenuItem, int resId) {
            super(anchorMenuItem, resId);
        }

        public Builder(@NonNull View anchorView) {
            super(anchorView);
        }

        public Builder(@NonNull View anchorView, int resId) {
            super(anchorView, resId);
        }

        @Override
        protected void init(@NonNull Context context, @NonNull View anchorView, @StyleRes int resId) {
            super.init(context, anchorView, resId);

            TypedArray a = context.obtainStyledAttributes(resId, R.styleable.Tooltip);

            mBackgroundColor = a.getColor(com.tooltip.R.styleable.Tooltip_backgroundColor, Color.GRAY);
            mCornerRadius = a.getDimension(com.tooltip.R.styleable.Tooltip_cornerRadius, -1);
            mPadding = a.getDimensionPixelSize(R.styleable.Tooltip_android_padding, mContext.getResources().getDimensionPixelSize(R.dimen.default_tooltip_padding));
            mMaxWidth = a.getDimensionPixelSize(R.styleable.Tooltip_android_maxWidth, -1);
            mDrawablePadding = a.getDimensionPixelSize(R.styleable.Tooltip_android_drawablePadding, 0);
            mDrawableBottom = a.getDrawable(R.styleable.Tooltip_android_drawableBottom);
            mDrawableEnd = a.getDrawable(R.styleable.Tooltip_android_drawableEnd);
            mDrawableStart = a.getDrawable(R.styleable.Tooltip_android_drawableStart);
            mDrawableTop = a.getDrawable(R.styleable.Tooltip_android_drawableTop);
            mTextAppearance = a.getResourceId(R.styleable.Tooltip_textAppearance, -1);
            mText = a.getString(R.styleable.Tooltip_android_text);
            mTextSize = a.getDimension(R.styleable.Tooltip_android_textSize, -1);
            mTextColor = a.getColorStateList(R.styleable.Tooltip_android_textColor);
            mTextStyle = a.getInteger(R.styleable.Tooltip_android_textStyle, -1);
            mLineSpacingExtra = a.getDimensionPixelSize(R.styleable.Tooltip_android_lineSpacingExtra, 0);
            mLineSpacingMultiplier = a.getFloat(R.styleable.Tooltip_android_lineSpacingMultiplier, mLineSpacingMultiplier);

            final String fontFamily = a.getString(R.styleable.Tooltip_android_fontFamily);
            final int typefaceIndex = a.getInt(R.styleable.Tooltip_android_typeface, -1);
            mTypeface = getTypefaceFromAttr(fontFamily, typefaceIndex, mTextStyle);

            a.recycle();
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            super.setCancelable(cancelable);
            return this;
        }

        @Override
        public Builder setDismissOnClick(boolean isDismissOnClick) {
            super.setDismissOnClick(isDismissOnClick);
            return this;
        }

        @Override
        public Builder setArrowEnabled(boolean isArrowEnabled) {
            super.setArrowEnabled(isArrowEnabled);
            return this;
        }

        @Override
        public Builder setArrowHeight(float height) {
            super.setArrowHeight(height);
            return this;
        }

        @Override
        public Builder setArrowWidth(float width) {
            super.setArrowWidth(width);
            return this;
        }

        @Override
        public Builder setArrow(Drawable arrowDrawable) {
            super.setArrow(arrowDrawable);
            return this;
        }

        @Override
        public Builder setMargin(float margin) {
            super.setMargin(margin);
            return this;
        }

        @Override
        public Builder setGravity(int gravity) {
            super.setGravity(gravity);
            return this;
        }

        /**
         * Sets {@link Tooltip} background color
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            mBackgroundColor = color;
            return this;
        }

        /**
         * Sets {@link Tooltip} background drawable corner radius from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setCornerRadius(@DimenRes int resId) {
            return setCornerRadius(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets {@link Tooltip} background drawable corner radius
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setCornerRadius(float radius) {
            mCornerRadius = radius;
            return this;
        }

        /**
         * Sets {@link Tooltip} padding
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setPadding(int padding) {
            mPadding = padding;
            return this;
        }

        /**
         * Sets {@link Tooltip} padding
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         *
         * @deprecated Use {@link #setPadding(int)} instead
         */
        @Deprecated
        public Builder setPadding(float padding) {
            return setPadding((int) padding);
        }

        /***
         * Sets {@link Tooltip} max width
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setMaxWidth(int maxWidth) {
            mMaxWidth = maxWidth;
            return this;
        }

        /**
         * Sets the size of the padding between the drawables and
         * the {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawablePadding(int padding) {
            mDrawablePadding = padding;
            return this;
        }

        /**
         * Sets drawable from resource to the bottom of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableBottom(@DrawableRes int resId) {
            return setDrawableBottom(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets drawable to the bottom of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableBottom(Drawable drawable) {
            mDrawableBottom = drawable;
            return this;
        }

        /**
         * Sets drawable from resource to the end of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableEnd(@DrawableRes int resId) {
            return setDrawableBottom(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets drawable to the end of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableEnd(Drawable drawable) {
            mDrawableEnd = drawable;
            return this;
        }

        /**
         * Sets drawable from resource to the start of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableStart(@DrawableRes int resId) {
            return setDrawableStart(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets drawable to the start of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableStart(Drawable drawable) {
            mDrawableStart = drawable;
            return this;
        }

        /**
         * Sets drawable from resource to the top of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableTop(@DrawableRes int resId) {
            return setDrawableTop(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets drawable to the top of {@link Tooltip} text.
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setDrawableTop(Drawable drawable) {
            mDrawableTop = drawable;
            return this;
        }

        /**
         * Sets {@link Tooltip} text appearance from the specified style resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTextAppearance(@StyleRes int resId) {
            mTextAppearance = resId;
            return this;
        }

        /**
         * Sets {@link Tooltip} text from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setText(@StringRes int resId) {
            return setText(mContext.getString(resId));
        }

        /**
         * Sets {@link Tooltip} text
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setText(CharSequence text) {
            mText = text;
            return this;
        }

        /**
         * Sets {@link Tooltip} text size from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTextSize(@DimenRes int resId) {
            mTextSize = mContext.getResources().getDimension(resId);
            return this;
        }

        /**
         * Sets {@link Tooltip} text size
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTextSize(float size) {
            mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, mContext.getResources().getDisplayMetrics());
            return this;
        }

        /**
         * Sets {@link Tooltip} text color
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTextColor(@ColorInt int color) {
            mTextColor = ColorStateList.valueOf(color);
            return this;
        }

        /**
         * Sets {@link Tooltip} text style
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTextStyle(int style) {
            mTextStyle = style;
            return this;
        }

        /**
         * Sets {@link Tooltip} line spacing. Each line will have its height
         * multiplied by <code>mult</code> and have <code>add</code> added to it
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setLineSpacing(@DimenRes int addResId, float mult) {
            mLineSpacingExtra = mContext.getResources().getDimensionPixelSize(addResId);
            mLineSpacingMultiplier = mult;
            return this;
        }

        /**
         * Sets {@link Tooltip} line spacing. Each line will have its height
         * multiplied by <code>mult</code> and have <code>add</code> added to it
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setLineSpacing(float add, float mult) {
            mLineSpacingExtra = add;
            mLineSpacingMultiplier = mult;
            return this;
        }

        /**
         * Sets {@link Tooltip} text typeface
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setTypeface(Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        @NonNull
        @Override
        public Tooltip build() { // TODO
            return new Tooltip(this);
        }

        private Typeface getTypefaceFromAttr(String familyName, int typefaceIndex, int styleIndex) {
            Typeface tf = null;
            if (familyName != null) {
                tf = Typeface.create(familyName, styleIndex);
                if (tf != null) {
                    return tf;
                }
            }
            switch (typefaceIndex) {
                case 1: // SANS
                    tf = Typeface.SANS_SERIF;
                    break;
                case 2: // SERIF
                    tf = Typeface.SERIF;
                    break;
                case 3: // MONOSPACE
                    tf = Typeface.MONOSPACE;
                    break;
            }
            return tf;
        }
    }
}

package com.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Viнt@rь on 26.05.2016
 */
public class Tooltip {

    private View mAnchorView;
    private PopupWindow mPopupWindow;

    private Tooltip(Builder builder) {
        mAnchorView = builder.mAnchorView;

        int padding = (int) builder.mPadding;

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(builder.mBackgroundColor);
        drawable.setCornerRadius(builder.mCornerRadius);

        TooltipView tooltipView = new TooltipView(builder.mContext);
        tooltipView.setText(builder.mText);
        tooltipView.setTextSize(builder.mTextSize);
        tooltipView.setTextColor(builder.mTextColor);
        tooltipView.setTextAppearance(builder.mTextAppearance);
        tooltipView.setPadding(padding, padding, padding, padding);
        tooltipView.setTypeface(builder.mTypeface, builder.mTextStyle);
        tooltipView.setBackgroundDrawable(drawable);

        mPopupWindow = new PopupWindow(builder.mContext);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setContentView(tooltipView);
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public void show() {
        mAnchorView.post(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.showAsDropDown(mAnchorView);
            }
        });
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public static final class Builder {

        private boolean isDismissOnInsideTouch;
        private boolean isDismissOnOutsideTouch;

        private int mGravity = Gravity.BOTTOM;
        private int mBackgroundColor = Color.BLACK;
        private int mTextAppearance;
        private int mTextStyle;

        private float mPadding;
        private float mTextSize;
        private float mCornerRadius;

        private Context mContext;
        private View mAnchorView;
        private String mText;
        private ColorStateList mTextColor;
        private Typeface mTypeface = Typeface.DEFAULT;

        public Builder(@NonNull Context context, @NonNull View anchorView) {
            this(context, anchorView, 0);
        }

        public Builder(@NonNull Context context, @NonNull View anchorView, @StyleRes int resId) {
            mContext = context;
            mAnchorView = anchorView;

            TypedArray a = context.obtainStyledAttributes(null, R.styleable.Tooltip, 0, resId);

            setDismissOnInsideTouch(a.getBoolean(R.styleable.Tooltip_dismissOnInsideTouch, false));
            setDismissOnOutsideTouch(a.getBoolean(R.styleable.Tooltip_dismissOnOutsideTouch, false));
            setBackgroundColor(a.getColor(R.styleable.Tooltip_backgroundColor, Color.BLACK));
            setCornerRadius(a.getDimension(R.styleable.Tooltip_cornerRadius, -1));
            setPadding(a.getDimension(R.styleable.Tooltip_android_padding, -1));
            setGravity(a.getInteger(R.styleable.Tooltip_android_gravity, -1));
            setText(a.getString(R.styleable.Tooltip_android_text));
            setTextSize(a.getDimension(R.styleable.Tooltip_android_textSize, -1));
            setTextColor(a.getColorStateList(R.styleable.Tooltip_android_textColor));
            setTextStyle(a.getInteger(R.styleable.Tooltip_android_textStyle, -1));
            setTextAppearance(a.getResourceId(R.styleable.Tooltip_android_textAppearance, -1));

            final String fontFamily = a.getString(R.styleable.Tooltip_android_fontFamily);
            final int typefaceIndex = a.getInt(R.styleable.Tooltip_android_typeface, -1);
            setTypeface(getTypefaceFromAttr(fontFamily, typefaceIndex, mTextStyle));

            a.recycle();
        }

        public Builder setDismissOnInsideTouch(boolean isDismissOnInsideTouch) {
            this.isDismissOnInsideTouch = isDismissOnInsideTouch;
            return this;
        }

        public Builder setDismissOnOutsideTouch(boolean isDismissOnOutsideTouch) {
            this.isDismissOnOutsideTouch = isDismissOnOutsideTouch;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int color) {
            mBackgroundColor = color;
            return this;
        }

        public Builder setCornerRadius(@DimenRes int resId) {
            return setCornerRadius(mContext.getResources().getDimension(resId));
        }

        public Builder setCornerRadius(float radius) {
            mCornerRadius = radius;
            return this;
        }

        public Builder setPadding(@DimenRes int resId) {
            return setPadding(mContext.getResources().getDimension(resId));
        }

        public Builder setPadding(float padding) {
            mPadding = padding;
            return this;
        }

        /**
         * Sets tool tip gravity
         */
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        public Builder setText(@StringRes int resId) {
            return setText(mContext.getString(resId));
        }

        public Builder setText(String text) {
            mText = text;
            return this;
        }

        public Builder setTextSize(@DimenRes int resId) {
            return setTextSize(mContext.getResources().getDimension(resId));
        }

        public Builder setTextSize(float size) {
            mTextSize = size;
            return this;
        }

        public Builder setTextColor(@ColorInt int color) {
            return setTextColor(ColorStateList.valueOf(color));
        }

        public Builder setTextColor(ColorStateList colors) {
            mTextColor = colors;
            return this;
        }

        public Builder setTextStyle(int style) {
            mTextStyle = style;
            return this;
        }

        /**
         * Set the text appearance for tool tip
         */
        public Builder setTextAppearance(@StyleRes int resId) {
            mTextAppearance = resId;
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        public Tooltip build() {
            if (mTextSize == -1) {
                mTextSize = mContext.getResources().getDimension(R.dimen.default_tooltip_text_size);
            }
            if (mPadding == -1) {
                mPadding = mContext.getResources().getDimension(R.dimen.default_tooltip_padding);
            }

            return new Tooltip(this);
        }

        public Tooltip show() {
            Tooltip tooltip = build();
            tooltip.show();
            return tooltip;
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

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
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

/**
 * Base {@code Tooltip} implementation
 */
public abstract class Tooltip<T extends Tooltip.Builder> {
    private static final int MAX_POPUP_WIDTH_PERCENT = 80;
    private static final String TAG = "Tooltip";

    private final boolean isDismissOnClick;

    private int mGravity;

    private final float mMargin;

    private final View mAnchorView;
    private final PopupWindow mPopupWindow;

    protected final Context mContext;

    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;
    private OnDismissListener mOnDismissListener;

    private LinearLayout mContentView;
    private ImageView mArrowView;

    protected Tooltip(T builder) {
        isDismissOnClick = builder.isDismissOnClick;

        mContext = builder.mContext;

        //mGravity = Gravity.getAbsoluteGravity(builder.mGravity, ViewCompat.getLayoutDirection(builder.mAnchorView));
        mGravity = builder.mGravity;
        mMargin = builder.mMargin;
        mAnchorView = builder.mAnchorView;
        mOnClickListener = builder.mOnClickListener;
        mOnLongClickListener = builder.mOnLongClickListener;
        mOnDismissListener = builder.mOnDismissListener;

        mContentView = createContentViewInternal(builder);

        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //mPopupWindow.setClippingEnabled(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(builder.isCancelable);
        mPopupWindow.setFocusable(builder.isCancelable);
        mPopupWindow.setOnDismissListener(() -> {
            mAnchorView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);

            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }
        });
    }

    private LinearLayout createContentViewInternal(T builder) {
        View customContentView = createContentView(builder);

        LinearLayout contentView = new LinearLayout(builder.mContext);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setOrientation(Gravity.isHorizontal(mGravity) ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);

        if (builder.isArrowEnabled) {
            mArrowView = new ImageView(builder.mContext);

            LinearLayout.LayoutParams arrowLayoutParams;
            if (builder.mArrowDrawable == null) {
                mAnchorView.post(() -> {
                    int absoluteGravity = Gravity.getAbsoluteGravity(mGravity, ViewCompat.getLayoutDirection(mAnchorView));
                    mArrowView.setImageDrawable(new ArrowDrawable(getArrowColor(builder), absoluteGravity));
                });
                //mArrowView.setImageDrawable(new ArrowDrawable(getArrowColor(builder), mGravity));

                if (Gravity.isVertical(mGravity)) {
                    arrowLayoutParams = new LinearLayout.LayoutParams((int) builder.mArrowWidth, (int) builder.mArrowHeight);
                } else {
                    arrowLayoutParams = new LinearLayout.LayoutParams((int) builder.mArrowHeight, (int) builder.mArrowWidth);
                }
            } else {
                mArrowView.setImageDrawable(builder.mArrowDrawable);

                arrowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            arrowLayoutParams.gravity = Gravity.CENTER;
            mArrowView.setLayoutParams(arrowLayoutParams);

            if (mGravity == Gravity.TOP || mGravity == Gravity.START) {
                contentView.addView(customContentView);
                contentView.addView(mArrowView);
            } else {
                contentView.addView(mArrowView);
                contentView.addView(customContentView);
            }
        } else {
            contentView.addView(customContentView);
        }

        int padding = (int) Utils.dpToPx(5);
        switch (mGravity) {
            case Gravity.START:
                contentView.setPadding(padding, 0, 0, 0);
                break;
            case Gravity.TOP:
            case Gravity.BOTTOM:
                contentView.setPadding(padding, 0, padding, 0);
                break;
            case Gravity.END:
                contentView.setPadding(0, 0, padding, 0);
                break;
        }

        contentView.setOnClickListener(v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(Tooltip.this);
            }
            if (isDismissOnClick) {
                dismiss();
            }
        });
        contentView.setOnLongClickListener(v -> mOnLongClickListener != null && mOnLongClickListener.onLongClick(Tooltip.this));

        return contentView;
    }

    @NonNull
    protected abstract View createContentView(@NonNull T builder);

    /**
     * @deprecated Deprecated and will be removed.
     */
    @Deprecated
    @ColorInt
    protected int getArrowColor(@NonNull T builder) {
        return Color.GRAY;
    }

    /**
     * Indicate whether this {@link Tooltip} is showing on screen
     *
     * @return true if {@link Tooltip} is showing, false otherwise
     */
    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    /**
     * Display the {@link Tooltip} anchored to the custom gravity of the anchor view
     *
     * @see #dismiss()
     */
    public void show() {
        if (!isShowing()) {
            mAnchorView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
            mAnchorView.post(() -> {
                if (mAnchorView.isShown()) {
                    if (mArrowView != null) {
                        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mArrowLayoutListener);
                    }

                    int layoutDirection = ViewCompat.getLayoutDirection(mAnchorView);

                    mContentView.setMeasureWithLargestChildEnabled(true);
                    mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    int measuredWidth = mContentView.getMeasuredWidth();
                    int measuredHeight = mContentView.getMeasuredHeight();

                    int maxWidth = Utils.getScreenWidth(mContext);
                    switch (mGravity) {
                        case Gravity.START:
                            maxWidth = (int) mAnchorView.getX();
                            break;
                        case Gravity.END:
                            maxWidth = maxWidth - ((int) mAnchorView.getX() + mAnchorView.getMeasuredWidth());
                            break;
                    }

                    measuredWidth = Math.min(measuredWidth, maxWidth * MAX_POPUP_WIDTH_PERCENT / 100);
                    mPopupWindow.setWidth(measuredWidth);

                    int xoff, yoff;
                    xoff = yoff = (int) mMargin;

                    switch (mGravity) {
                        case Gravity.START:
                            xoff = measuredWidth + xoff;
                            if (layoutDirection == ViewCompat.LAYOUT_DIRECTION_LTR) {
                                xoff = -xoff;
                            }
                            yoff = (-mAnchorView.getMeasuredHeight() / 2) - (measuredHeight / 2) + yoff;
                            break;
                        case Gravity.END:
                            xoff = mAnchorView.getMeasuredWidth() + (int) Utils.dpToPx(8) + xoff;
                            if (layoutDirection != ViewCompat.LAYOUT_DIRECTION_LTR) {
                                xoff = -xoff;
                            }
                            yoff = (-mAnchorView.getMeasuredHeight() / 2) - (measuredHeight / 2) + yoff;
                            break;
                        case Gravity.TOP:
                            xoff = (mAnchorView.getMeasuredWidth() / 2) - (measuredWidth / 2);
                            yoff = -mAnchorView.getMeasuredHeight() / 2 - measuredHeight - yoff;
                            break;
                        case Gravity.BOTTOM:
                            xoff = (mAnchorView.getMeasuredWidth() / 2) - (measuredWidth / 2);
                            break;
                    }
                    mPopupWindow.showAsDropDown(mAnchorView, xoff, yoff);
                } else {
                    Log.e(TAG, "Tooltip cannot be shown, root view is invalid or has been closed");
                }
            });
        }
    }

    /**
     * Disposes of the {@link Tooltip}. This method can be invoked only after
     * {@link #show()} has been executed. Failing that, calling this method
     * will have no effect
     *
     * @see #show()
     */
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    /**
     * Sets listener to be called when the {@link Tooltip} is clicked.
     *
     * @param listener The listener.
     */
    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    /**
     * Sets listener to be called when the {@link Tooltip} is clicked and held.
     *
     * @param listener The listener.
     */
    public void setOnLongClickListener(OnLongClickListener listener) {
        mOnLongClickListener = listener;
    }

    /**
     * Sets listener to be called when {@link Tooltip} is dismissed.
     *
     * @param listener The listener.
     */
    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mArrowLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            Rect anchorRect = Utils.calculateRectOnScreen(mAnchorView);
            Rect contentViewRect = Utils.calculateRectOnScreen(mContentView);
            float x, y;
            if (Gravity.isVertical(mGravity)) {
                x = mContentView.getPaddingLeft() + Utils.dpToPx(2);
                float centerX = (contentViewRect.width() / 2f) - (mArrowView.getWidth() / 2f);
                float newX = centerX - (contentViewRect.centerX() - anchorRect.centerX());
                if (newX > x) {
                    if (newX + mArrowView.getWidth() + x > contentViewRect.width()) {
                        x = contentViewRect.width() - mArrowView.getWidth() - x;
                    } else {
                        x = newX;
                    }
                }
                y = mArrowView.getTop();
                y = y + (mGravity == Gravity.TOP ? -1 : +1);
            } else {
                y = mContentView.getPaddingTop() + Utils.dpToPx(2);
                float centerY = (contentViewRect.height() / 2f) - (mArrowView.getHeight() / 2f);
                float newY = centerY - (contentViewRect.centerY() - anchorRect.centerY());
                if (newY > y) {
                    if (newY + mArrowView.getHeight() + y > contentViewRect.height()) {
                        y = contentViewRect.height() - mArrowView.getHeight() - y;
                    } else {
                        y = newY;
                    }
                }
                int absoluteGravity = Gravity.getAbsoluteGravity(mGravity, ViewCompat.getLayoutDirection(mAnchorView));

                x = mArrowView.getLeft();
                x = x + (absoluteGravity == Gravity.LEFT ? -1 : +1);
            }
            mArrowView.setX(x);
            mArrowView.setY(y);
        }
    };

    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {

        @Override
        public void onViewAttachedToWindow(View v) {

        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            dismiss();
        }
    };

    public static abstract class Builder<B extends Builder> {
        boolean isDismissOnClick;
        boolean isCancelable;
        boolean isArrowEnabled;

        int mGravity;

        float mArrowHeight;
        float mArrowWidth;
        float mMargin;

        Drawable mArrowDrawable;

        View mAnchorView;

        OnClickListener mOnClickListener;
        OnLongClickListener mOnLongClickListener;
        OnDismissListener mOnDismissListener;

        protected Context mContext;

        public Builder(@NonNull MenuItem anchorMenuItem) {
            this(anchorMenuItem, R.attr.tooltipStyle, 0);
        }

        public Builder(@NonNull MenuItem anchorMenuItem, @StyleRes int styleId) {
            this(anchorMenuItem, 0, styleId);
        }

        public Builder(@NonNull MenuItem anchorMenuItem, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
            View anchorView = anchorMenuItem.getActionView();
            if (anchorView != null) {
                init(anchorView.getContext(), anchorView, defStyleAttr, defStyleRes);
            } else {
                throw new NullPointerException("anchorMenuItem haven`t actionViewClass");
            }
        }

        public Builder(@NonNull View anchorView) {
            this(anchorView, R.attr.tooltipStyle, 0);
        }

        public Builder(@NonNull View anchorView, @StyleRes int styleId) {
            this(anchorView, 0, styleId);
        }

        public Builder(@NonNull View anchorView, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
            init(anchorView.getContext(), anchorView, defStyleAttr, defStyleRes);
        }

        protected void init(@NonNull Context context, @NonNull View anchorView, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
            mContext = context;
            mAnchorView = anchorView;

            TypedArray a = mContext.obtainStyledAttributes(null, R.styleable.Tooltip, defStyleAttr, defStyleRes);

            isCancelable = a.getBoolean(R.styleable.Tooltip_cancelable, false);
            isDismissOnClick = a.getBoolean(R.styleable.Tooltip_dismissOnClick, false);
            isArrowEnabled = a.getBoolean(R.styleable.Tooltip_arrowEnabled, true);
            mArrowHeight = a.getDimension(R.styleable.Tooltip_arrowHeight, mContext.getResources().getDimension(R.dimen.default_tooltip_arrow_height));
            mArrowWidth = a.getDimension(R.styleable.Tooltip_arrowWidth, mContext.getResources().getDimension(R.dimen.default_tooltip_arrow_width));
            mArrowDrawable = a.getDrawable(R.styleable.Tooltip_arrowDrawable);
            mMargin = a.getDimension(R.styleable.Tooltip_margin, 0);
            mGravity = a.getInteger(R.styleable.Tooltip_android_gravity, Gravity.BOTTOM);

            a.recycle();
        }

        /**
         * Sets whether {@link Tooltip} is cancelable or not. Default is {@code false}
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return (B) this;
        }

        /**
         * Sets whether {@link Tooltip} is dismissing on click or not. Default is {@code false}
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setDismissOnClick(boolean isDismissOnClick) {
            this.isDismissOnClick = isDismissOnClick;
            return (B) this;
        }

        /**
         * Sets whether {@link Tooltip} is arrow enabled. Default is {@code true}
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrowEnabled(boolean isArrowEnabled) {
            this.isArrowEnabled = isArrowEnabled;
            return (B) this;
        }

        /**
         * Sets {@link Tooltip} arrow height from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrowHeight(@DimenRes int resId) {
            return setArrowHeight(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets {@link Tooltip} arrow height
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrowHeight(float height) {
            mArrowHeight = height;
            return (B) this;
        }

        /**
         * Sets {@link Tooltip} arrow width from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrowWidth(@DimenRes int resId) {
            return setArrowWidth(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets {@link Tooltip} arrow width
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrowWidth(float width) {
            mArrowWidth = width;
            return (B) this;
        }

        /**
         * Sets {@link Tooltip} arrow drawable from resources
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrow(@DrawableRes int resId) {
            return setArrow(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets {@link Tooltip} arrow drawable
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setArrow(Drawable arrowDrawable) {
            mArrowDrawable = arrowDrawable;
            return (B) this;
        }

        /**
         * Sets {@link Tooltip} margin from resource
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setMargin(@DimenRes int resId) {
            return setMargin(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets {@link Tooltip} margin
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setMargin(float margin) {
            mMargin = margin;
            return (B) this;
        }

        /**
         * Sets {@link Tooltip} gravity
         *
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setGravity(int gravity) {
            mGravity = gravity;
            return (B) this;
        }

        /**
         * Sets listener to be called when the {@link Tooltip} is clicked
         *
         * @param listener The listener
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setOnClickListener(OnClickListener listener) {
            mOnClickListener = listener;
            return (B) this;
        }

        /**
         * Sets listener to be called when the {@link Tooltip} is clicked and held
         *
         * @param listener The listener
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setOnLongClickListener(OnLongClickListener listener) {
            mOnLongClickListener = listener;
            return (B) this;
        }

        /**
         * Sets listener to be called when the {@link Tooltip} is dismissed
         *
         * @param listener The listener
         * @return This {@link Builder} object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("unchecked")
        public B setOnDismissListener(OnDismissListener listener) {
            mOnDismissListener = listener;
            return (B) this;
        }

        @NonNull
        public abstract Tooltip build();

        /**
         * Builds a {@link Tooltip} with builder attributes and {@link Tooltip#show()}'s the <code>Tooltip</code>
         */
        @SuppressWarnings("unchecked")
        @NonNull
        public final <T extends Tooltip> T show() {
            T tooltip = (T) build();
            tooltip.show();
            return tooltip;
        }
    }
}

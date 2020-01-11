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
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewTreeObserverCompat;
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
    private static final String TAG = "Tooltip";

    private final boolean isDismissOnClick;

    private final int mGravity;

    private final float mMargin;

    private final View mAnchorView;
    private final PopupWindow mPopupWindow;

    protected final Context mContext;

    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;
    private OnDismissListener mOnDismissListener;

    private LinearLayout mContentView;
    private ImageView mArrowView;

    protected Tooltip(Builder builder) {
        isDismissOnClick = builder.isDismissOnClick;

        mContext = builder.mContext;

        mGravity = Gravity.getAbsoluteGravity(builder.mGravity, ViewCompat.getLayoutDirection(builder.mAnchorView));
        mMargin = builder.mMargin;
        mAnchorView = builder.mAnchorView;
        mOnClickListener = builder.mOnClickListener;
        mOnLongClickListener = builder.mOnLongClickListener;
        mOnDismissListener = builder.mOnDismissListener;

        mContentView = createContentViewInternal(builder);

        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(builder.isCancelable);
        mPopupWindow.setFocusable(builder.isCancelable);
        mPopupWindow.setOnDismissListener(() -> {
            mAnchorView.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
            mAnchorView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);

            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private LinearLayout createContentViewInternal(Builder builder) {
        View customContentView = createContentView((T) builder);

        LinearLayout contentView = new LinearLayout(builder.mContext);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setOrientation(Gravity.isHorizontal(mGravity) ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);

        if (builder.isArrowEnabled) {
            mArrowView = new ImageView(builder.mContext);

            LinearLayout.LayoutParams arrowLayoutParams;
            if (builder.mArrowDrawable == null) {
                mArrowView.setImageDrawable(new ArrowDrawable(getArrowColor((T) builder), mGravity));

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

            if (mGravity == Gravity.TOP || mGravity == Gravity.getAbsoluteGravity(Gravity.START, ViewCompat.getLayoutDirection(mAnchorView))) {
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
            case Gravity.LEFT:
                contentView.setPadding(padding, 0, 0, 0);
                break;
            case Gravity.TOP:
            case Gravity.BOTTOM:
                contentView.setPadding(padding, 0, padding, 0);
                break;
            case Gravity.RIGHT:
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
            mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mLocationLayoutListener);

            mAnchorView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
            mAnchorView.post(() -> {
                if (mAnchorView.isShown()) {
                    mPopupWindow.showAsDropDown(mAnchorView);
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

    private PointF calculateLocation() {
        PointF location = new PointF();

        final RectF anchorRect = Utils.calculateRectInWindow(mAnchorView);
        final PointF anchorCenter = new PointF(anchorRect.centerX(), anchorRect.centerY());

        switch (mGravity) {
            case Gravity.LEFT:
                location.x = anchorRect.left - mContentView.getWidth() - mMargin;
                location.y = anchorCenter.y - mContentView.getHeight() / 2f;
                break;
            case Gravity.RIGHT:
                location.x = anchorRect.right + mMargin;
                location.y = anchorCenter.y - mContentView.getHeight() / 2f;
                break;
            case Gravity.TOP:
                location.x = anchorCenter.x - mContentView.getWidth() / 2f;
                location.y = anchorRect.top - mContentView.getHeight() - mMargin;
                break;
            case Gravity.BOTTOM:
                location.x = anchorCenter.x - mContentView.getWidth() / 2f;
                location.y = anchorRect.bottom + mMargin;
                break;
        }
        return location;
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mLocationLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            ViewTreeObserverCompat.removeOnGlobalLayoutListener(mContentView.getViewTreeObserver(), this);

            final ViewTreeObserver vto = mAnchorView.getViewTreeObserver();
            if (vto != null) {
                vto.addOnScrollChangedListener(mOnScrollChangedListener);
            }

            if (mArrowView != null) {
                mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mArrowLayoutListener);
            }

            PointF location = calculateLocation();
            mPopupWindow.setClippingEnabled(true);
            mPopupWindow.update((int) location.x, (int) location.y, mPopupWindow.getWidth(), mPopupWindow.getHeight());
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mArrowLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            ViewTreeObserverCompat.removeOnGlobalLayoutListener(mContentView.getViewTreeObserver(), this);

            RectF anchorRect = Utils.calculateRectOnScreen(mAnchorView);
            RectF contentViewRect = Utils.calculateRectOnScreen(mContentView);
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
                x = mArrowView.getLeft();
                x = x + (mGravity == Gravity.LEFT ? -1 : +1);
            }
            mArrowView.setX(x);
            mArrowView.setY(y);
        }
    };

    private final ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {

        @Override
        public void onScrollChanged() {
            PointF location = calculateLocation();
            mPopupWindow.update((int) location.x, (int) location.y, mPopupWindow.getWidth(), mPopupWindow.getHeight());
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
        private boolean isDismissOnClick;
        private boolean isCancelable;
        private boolean isArrowEnabled;

        private int mGravity;

        private float mArrowHeight;
        private float mArrowWidth;
        private float mMargin;

        private Drawable mArrowDrawable;

        protected Context mContext;
        private View mAnchorView;

        private OnClickListener mOnClickListener;
        private OnLongClickListener mOnLongClickListener;
        private OnDismissListener mOnDismissListener;

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

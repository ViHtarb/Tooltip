# Android Tooltips library #

[![Licence MIT](https://img.shields.io/badge/licence-MIT-blue.svg)](https://bitbucket.org/ViHtarb/tooltip/src/ccb911a31d9749e3e607cdfd93c6485dcdde056d/LICENSE?at=master&fileviewer=file-view-default)
[![Build Status](https://travis-ci.org/ViHtarb/tooltip.svg?branch=master)](https://travis-ci.org/ViHtarb/tooltip)
[![Tooltip Maven Central](https://img.shields.io/badge/Tooltip%20Maven%20Central-0.1.6-brightgreen.svg?style=flat)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.vihtarb%22)

Android Tooltips library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html).

## Getting started ##
-----------------------
### Gradle ###
```javascript
    dependencies {
        compile 'com.github.vihtarb:tooltip:0.1.6'
    }
```
### Usage ###
```java
    Tooltip tooltip = new Tooltip.Builder(context, anchorView)
            .setCancelable(false)
            .setDismissOnClick(true)
            .setCornerRadius(20f)
            .setGravity(Gravity.BOTTOM)
            .setText("Hellow tooltip")
            .show();
```
### Advanced ###
```java
    Tooltip tooltip = new Tooltip.Builder(context, anchorView)
            .setCancelable(true) // dissmiss on outside touch by default false
            .setDismissOnClick(true) // dissmiss on inside toush by default false
            .setBackgroundColor(Color.GREEN) // background color
            .setCornerRadius(R.dimen.tooltip_corner_radius) // background drawable corner radius from resources
            .setCornerRadius(20f) // background drawable corner radius
            .setArrowHeight(R.dimen.tooltip_arrow_height) // arrow height from resources
            .setArrowHeight(5f) // arrow height from resources
            .setArrowWidth(R.dimen.tooltip_arrow_width) // arrow width from resources
            .setArrowWidth(5f) // arrow width from resources
            .setMargin(R.dimen.tooltip_margin) // margin between arrow and anchor view from resources
            .setMargin(5f) // margin between arrow and anchor view
            .setPadding(R.dimen.tooltip_padding) // content padding from resources
            .setPadding(5f) // content padding
            .setGravity(Gravity.BOTTOM) // tooltip gravity
            .setText(R.string.tooltip_text) // text from resources
            .setText("Hellow tooltip") // text
            .setTextSize(R.dimen.tooltip_text_size) // text size from resources
            .setTextSize(15f) // text size from resources
            .setTextColor(Color.GREEN) // text color
            .setTextStyle(intStyle) // text style
            .setTextAppearance(R.style.TooltipTextAppearance) // text appearance
            .setLineSpacing(R.dimen.tooltip_line_spacing, 1f) // text line spacing
            .setLineSpacing(10f, 1f) // text line spacing
            .setTypeface(typeface) // text typeface
            .build() // or
            .show();

            tooltip.isShowing(); // retruns is tooltip showing
            tooltip.show(); // shows tooltip if not showing
            tooltip.dismiss(); // dismissing tooltip
```
### Styleable ###
You can create tooltip with custom style
```java
    Tooltip tooltip = new Tooltip.Builder(context, anchorView, R.style.tooltip)
            .setText("Hello tooltip")
            .show();
```
Styleable attributes
```XML
    <declare-styleable name="Tooltip">
        <attr name="cancelable" format="boolean"/> # dissmiss on outside touch by default false
        <attr name="dismissOnClick" format="boolean"/> # dissmiss on inside toush by default false
        <attr name="colorBackground" format="color"/> # background color
        <attr name="cornerRadius" format="dimension"/> # background drawable corner radius
        <attr name="arrowHeight" format="dimension"/> # arrow height
        <attr name="arrowWidth" format="dimension"/> # arrow width
        <attr name="margin" format="dimension"/> # margin between arrow and anchor view
        <attr name="textAppearance" format="reference"/> # text appearance
        <attr name="android:padding"/> # content padding
        <attr name="android:text"/>
        <attr name="android:textSize"/>
        <attr name="android:textColor"/>
        <attr name="android:textStyle"/>
        <attr name="android:gravity"/> # tooltip gravity
        <attr name="android:fontFamily"/>
        <attr name="android:lineSpacingExtra"/>
        <attr name="android:lineSpacingMultiplier"/>
        <attr name="android:typeface"/>
    </declare-styleable>
```

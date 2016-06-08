# Android Tooltips library #

[![Licence MIT](https://img.shields.io/badge/licence-MIT-blue.svg)](https://bitbucket.org/ViHtarb/tooltip/src/ccb911a31d9749e3e607cdfd93c6485dcdde056d/LICENSE?at=master&fileviewer=file-view-default)

Android Tooltips library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html).

## Getting started ##
### Gradle ###
    dependencies {
        compile 'com.github.vihtarb:tooltip:0.1.0'
    }
### Usage ###
    View view = (Button) findViewById(R.id.view);
    Tooltip tooltip = new Tooltip.Builder(MainActivity.this, view)
            .setCancelable(false)
            .setDismissOnClick(true)
            .setCornerRadius(20f)
            .setGravity(Gravity.BOTTOM)
            .setText("Hellow tooltip")
            .show();
### Stylable ###
    // You can create tooltip with custom style
    View view = (Button) findViewById(R.id.view);
    Tooltip tooltip = new Tooltip.Builder(MainActivity.this, view, R.style.tooltip)
            .setText("Hellow tooltip")
            .show();

    // Styleable attributes
    cancelable - dissmiss tooltip on outside touch by default false - boolean
    dismissOnClick - dissmiss tooltip on inside toush by default false - boolean
    colorBackground - tooltip background color - int color
    cornerRadius - tooltip baground drawable corner radius - dp
    arrowHeight - arrow height - dp
    arrowWidth - arrow width - dp
    margin - tooltip margin between arrow and anchor view - dp
    textAppearance - tooltip text appearance - reference
    android:padding
    android:text
    android:textSize
    android:textColor
    android:textStyle
    android:gravity
    android:fontFamily
    android:typeface

### All builder methods ###
    
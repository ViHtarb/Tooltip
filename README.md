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
You can create tooltip with custom style

    View view = (Button) findViewById(R.id.view);
    Tooltip tooltip = new Tooltip.Builder(MainActivity.this, view, R.style.tooltip)
            .setText("Hellow tooltip")
            .show();

Styleable attributes

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
        <attr name="android:typeface"/>
    </declare-styleable>

### All builder methods ###
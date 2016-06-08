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
    Tooltip tooltip = new Tooltip.Builder(MainActivity.this, view, R.style.tooltip)
            .setCancelable(false)
            .setDismissOnClick(true)
            .setCornerRadius(20f)
            .setGravity(Gravity.BOTTOM)
            .setText("Hellow tooltip")
            .show();
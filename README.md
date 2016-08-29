# Android Tooltips

[![Licence MIT](https://img.shields.io/badge/licence-MIT-blue.svg)](https://bitbucket.org/ViHtarb/tooltip/src/ccb911a31d9749e3e607cdfd93c6485dcdde056d/LICENSE?at=master&fileviewer=file-view-default)
[![Build Status](https://travis-ci.org/ViHtarb/Tooltip.svg?branch=master)](https://travis-ci.org/ViHtarb/Tooltip)
[![Tooltip Maven Central](https://img.shields.io/badge/Tooltip%20Maven%20Central-0.1.7-brightgreen.svg?style=flat)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.vihtarb%22)

Android Tooltips library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html). This Tooltips does not require any custom layout. It works as [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html).

![Demo](https://raw.githubusercontent.com/ViHtarb/Tooltip/master/art/demo.gif)

## Getting started
### Gradle
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.1.7'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>0.1.7</version>
</dependency>
```
## Usage
```java
Tooltip tooltip = new Tooltip.Builder(context, anchorView)
        .setText("Hello tooltip")
        .show();
```
### Useful methods
#### Builder:
- `Builder(Context context, MenuItem anchorMenuItem)`
- `Builder(Context context, MenuItem anchorMenuItem, @StyleRes int resId)`
- `Builder(Context context, View anchorView)`
- `Builder(Context context, View anchorView, @StyleRes int resId)`
- `setCancelable(boolean cancelable)` - dismiss on outside touch. Default is `false`
- `setDismissOnClick(boolean isDissmissOnClick)` - dismiss on inside touch. Default is `false`
- `setBackgroundColor(@ColorInt int color)` - background color. Default is `Color.GRAY`
- `setCornerRadius(@DimenRes int resId)` - background drawable corner radius from resources
- `setCornerRadius(float radius)` - background drawable corner radius
- `setArrowHeight(@DimenRes int resId)`
- `setArrowHeight(float height)`
- `setArrowWidth(@DimenRes int resId)`
- `setArrowWidth(float width)`
- `setMargin(@DimenRes int resId)` - margin between arrow and anchor view from resources
- `setMargin(float margin)` - margin between arrow and anchor view
- `setPadding(@DimenRes int resId)` - content padding from resources
- `setPadding(float padding)` - content padding
- `setGravity(int gravity)` - Tooltip gravity. Default is `Gravity.BOTTOM`
- `setText(@StringRes int resId)`
- `setText(String text)`
- `setTextSize(@DimenRes int resId)`
- `setTextSize(float size)`
- `setTextColor(@ColorInt int color)`
- `setTextStyle(int style)`
- `setTextAppearance(@StyleRes int resId)`
- `setLineSpacing(@DimenRes int addResId, float mult)`
- `setLineSpacing(float add, float mult)`
- `setTypeface(Typeface typeface)`
- `build()` - creates and returns new Tooltip
- `show()` - creates, shows and returns new Tooltip

#### Tooltip:
- `isShowing()` - retruns is Tooltip showing
- `show()` - show Tooltip if not showing
- `dismiss()` - dismissing Tooltip

## Styleable
You can create Tooltip with custom style
```java
Tooltip tooltip = new Tooltip.Builder(context, anchorView, R.style.tooltip)
        .setText("Hello tooltip")
        .show();
```
### Attributes
- `<attr name="cancelable" format="boolean"/>` - dismiss on outside touch. Default is `false`
- `<attr name="dismissOnClick" format="boolean"/>` - dismiss on inside toush. Default is `false`
- `<attr name="colorBackground" format="color"/>` - background color. Default is `Color.GRAY`
- `<attr name="cornerRadius" format="dimension"/>` - background drawable corner radius
- `<attr name="arrowHeight" format="dimension"/>`
- `<attr name="arrowWidth" format="dimension"/>`
- `<attr name="margin" format="dimension"/>` - margin between arrow and anchor view
- `<attr name="textAppearance" format="reference"/>`
- `<attr name="android:padding"/>` - content padding
- `<attr name="android:text"/>`
- `<attr name="android:textSize"/>`
- `<attr name="android:textColor"/>`
- `<attr name="android:textStyle"/>`
- `<attr name="android:gravity"/>` - Tooltip gravity
- `<attr name="android:fontFamily"/>`
- `<attr name="android:lineSpacingExtra"/>`
- `<attr name="android:lineSpacingMultiplier"/>`
- `<attr name="android:typeface"/>`

## Changelog
### 0.1.7
- Min SDK changed from 7 to 11
- Removed not used dependencies(library size is reduced almost in 2 times)

### 0.1.6
This version is supported Android API 7 and large. If you want use Tooltips in projects with Min SDK 7 then use this in dependencies:
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.1.6'
}
```

## Future Work
- Animations
- Elevation(Shadow)

## License([MIT License](http://opensource.org/licenses/MIT))
<pre>
The MIT License (MIT)

Copyright (c) 2016. Viнt@rь

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
</pre>

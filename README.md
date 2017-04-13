# Android Tooltips

[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://bitbucket.org/ViHtarb/tooltip/src/ccb911a31d9749e3e607cdfd93c6485dcdde056d/LICENSE?at=master&fileviewer=file-view-default)
[![Build Status](https://travis-ci.org/ViHtarb/Tooltip.svg?branch=master)](https://travis-ci.org/ViHtarb/Tooltip)
[![Maven Central](https://img.shields.io/badge/Maven%20Central-0.1.9-brightgreen.svg?style=flat)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.vihtarb%22)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Tooltips-brightgreen.svg?style=true)](https://android-arsenal.com/details/1/4253)

Simple to use customizable Android Tooltips library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html). This Tooltips does not require any custom layout. It works as [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html).

![Demo](https://raw.githubusercontent.com/ViHtarb/Tooltip/master/art/demo.gif)

## Getting started
### Gradle
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.1.9'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>0.1.9</version>
</dependency>
```
## Usage
```java
Tooltip tooltip = new Tooltip.Builder(anchorView)
        .setText("Hello tooltip")
        .show();
```
### Useful methods
#### Builder:
- `Builder(MenuItem anchorMenuItem)`
- `Builder(MenuItem anchorMenuItem, @StyleRes int resId)`
- `Builder(View anchorView)`
- `Builder(View anchorView, @StyleRes int resId)`
- `setCancelable(boolean cancelable)` - dismiss on outside touch. Default is `false`
- `setDismissOnClick(boolean isDissmissOnClick)` - dismiss on inside touch. Default is `false`
- `setBackgroundColor(@ColorInt int color)` - background color. Default is `Color.GRAY`
- `setCornerRadius(@DimenRes int resId)` - background drawable corner radius from resources
- `setCornerRadius(float radius)` - background drawable corner radius
- `setArrowHeight(@DimenRes int resId)`
- `setArrowHeight(float height)`
- `setArrowWidth(@DimenRes int resId)`
- `setArrowWidth(float width)`
- `setArrow(@DrawableRes int resId)` - custom arrow drawable from resources
- `setArrow(Drawable arrowDrawable)` - custom arrow drawable
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
- `setOnClickListener(OnClickListener listener)`
- `setOnLongClickListener(OnLongClickListener listener)`
- `setOnDismissListener(OnDismissListener listener)`
- `build()` - creates and returns new Tooltip
- `show()` - creates, shows and returns new Tooltip

#### Tooltip:
- `isShowing()` - retruns is Tooltip showing
- `show()` - show Tooltip if not showing
- `dismiss()` - dismissing Tooltip
- `setOnClickListener(OnClickListener listener)`
- `setOnLongClickListener(OnLongClickListener listener)`
- `setOnDismissListener(OnDismissListener listener)`

## Styleable
You can create Tooltip with custom style
```java
Tooltip tooltip = new Tooltip.Builder(anchorView, R.style.tooltip)
        .setText("Hello tooltip")
        .show();
```
### Attributes
- `<attr name="cancelable" format="boolean"/>` - dismiss on outside touch. Default is `false`
- `<attr name="dismissOnClick" format="boolean"/>` - dismiss on inside toush. Default is `false`
- `<attr name="backgroundColor" format="color"/>` - background color. Default is `Color.GRAY`
- `<attr name="cornerRadius" format="dimension"/>` - background drawable corner radius
- `<attr name="arrowHeight" format="dimension"/>`
- `<attr name="arrowWidth" format="dimension"/>`
- `<attr name="arrowDrawable" format="reference"/>`
- `<attr name="margin" format="dimension"/>` - margin between arrow and anchor view
- `<attr name="textAppearance" format="reference"/>`
- `<attr name="android:padding"/>` - content padding
- `<attr name="android:text"/>`
- `<attr name="android:textSize"/>`
- `<attr name="android:textColor"/>`
- `<attr name="android:textStyle"/>`
- `<attr name="android:gravity"/>` - Tooltip gravity
- `<attr name="android:fontFamily"/>`
- `<attr name="android:typeface"/>`
- `<attr name="android:lineSpacingExtra"/>`
- `<attr name="android:lineSpacingMultiplier"/>`

## Changelog
### 0.1.9
- Min SDK changed from 11 to 14
- Implemented `OnClickListener` and `OnLongClickListener` listeners
- Implemented `setOnClickListener` and `setOnLongClickListener` methods in `Builder` and `Tooltip`
- Implemented `setOnDismissListener` in `Tooltip`
- Implemented customizing arrow drawable by `Builder` method `setArrow(Drawable)` and styleable attribute `arrowDrawable`
- Reimplemented `TooltipActionView` class
- Removed `Context context` argument in `Builder` constructors with second argument `MenuItem anchorMenuItem`
- Fixed `Activity has leaked window`

### 0.1.8
- Implemented `OnDismissListener`
- Removed `Context context` argument in `Builder` constructors with second argument `View anchorView`
- Renamed styleable attribute from `colorBackground` to `backgroundColor`

### 0.1.6
This version is supported Android API 7 and large. If you want use Tooltips in projects with Min SDK 7 then use this in dependencies:
### Gradle
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.1.6'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>0.1.6</version>
</dependency>
```
## SNAPSHOTS
For using `snapshots` of development versions you need include the snapshots repo by adding the snapshot build to a dependent project. To do this add the following to your `build.gradle` project(not module) file
```java
maven {
    url "https://oss.sonatype.org/content/repositories/snapshots"
}
```
### Gradle
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.1.9.2-SNAPSHOT'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>0.1.9.2-SNAPSHOT</version>
</dependency>
```
## Changelog-SNAPSHOTS
### 0.1.9.3-SNAPSHOT
- Implemented arrow disabling by `Builder` method `setArrowEnabled(boolean)` and styleable attribute `arrowEnabled`
- [#26](https://github.com/ViHtarb/Tooltip/issues/26) Fixed issue with `Exception android.view.WindowManager$BadTokenException:...`

### 0.1.9.2-SNAPSHOT
- [#19](https://github.com/ViHtarb/Tooltip/issues/19) Fixed issue with `Tooltip` moving on scrolling/updating list views

### 0.1.9.1-SNAPSHOT
- [#13](https://github.com/ViHtarb/Tooltip/pull/13) Implemented supporting `CharSequence` for text instead of `String` (@gkravas)

## Future Work
- Animations
- Elevation(Shadow)

## Known issues
- [#17](https://github.com/ViHtarb/Tooltip/issues/17) As temporary fix use it: `compile ('com.github.vihtarb:tooltip:0.1.9') { exclude module: "support-compat" }`

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

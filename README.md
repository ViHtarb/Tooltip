# Android Tooltips

[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://bitbucket.org/ViHtarb/tooltip/src/ccb911a31d9749e3e607cdfd93c6485dcdde056d/LICENSE?at=master&fileviewer=file-view-default)
[![Build Status](https://travis-ci.org/ViHtarb/Tooltip.svg?branch=master)](https://travis-ci.org/ViHtarb/Tooltip)
[![Maven Central](https://img.shields.io/badge/Maven%20Central-0.2.0-brightgreen.svg?style=flat)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.vihtarb%22)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Tooltips-brightgreen.svg?style=true)](https://android-arsenal.com/details/1/4253)

Simple to use customizable Android Tooltips library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html). This Tooltips does not require any custom layout. It works as [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html).

![Demo](https://raw.githubusercontent.com/ViHtarb/Tooltip/master/art/demo.gif)

## Getting started
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
    compile 'com.github.vihtarb:tooltip:1.0.0-alpha05-SNAPSHOT'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>1.0.0-alpha05-SNAPSHOT</version>
</dependency>
```
## Changelog-SNAPSHOTS
### 1.0.0-alpha05-SNAPSHOT
- Fixed [#58](https://github.com/ViHtarb/Tooltip/issues/57) issue
- Removed `android:fontFamily` and `android:typeface` attributes support for simple Tooltip. Use `textAppearance` instead
- Removed deprecated classes from simple `Tooltip` module
- Implemented `tooltipStyle` attribute

### 1.0.0-alpha04-SNAPSHOT
- Fixed [#57](https://github.com/ViHtarb/Tooltip/issues/57) issue

### 1.0.0-alpha03-SNAPSHOT
- Migrated to androidx

### 1.0.0-alpha02-SNAPSHOT
- Migrated to Java 1.8
- Fixed simple Tooltip `Builder.setText(int)` method
- Implemented sets Tooltip `setFocusable(isCancelable)` issue [#55](https://github.com/ViHtarb/Tooltip/issues/55)

### 1.0.0-alpha01-SNAPSHOT
- Library splitted to `core` and `default Tooltip` modules.
- First version with a primitive implementation of customization. From this version you can customize tooltip as you need. To do this you need extends from `core.Tooltip` and `core.Tooltip.Builder` classes and implement `core.Tooltip.createContentView` method. For example look to `detault Tooltip` implementation.

## USAGE
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
- `setArrowEnabled(boolean isArrowEnabled)`
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
- `setPadding(int padding)` - content padding
- `setPadding(float padding)` - deprecated, use `setPadding(int padding)` instead
- `setGravity(int gravity)` - Tooltip gravity. Default is `Gravity.BOTTOM`
- `setMaxWidth(int gravity)`
- `setDrawablePadding(int gravity)`
- `setDrawableBottom(@DrawableRes int resId)`
- `setDrawableBottom(Drawable drawable)`
- `setDrawableEnd(@DrawableRes int resId)`
- `setDrawableEnd(Drawable drawable)`
- `setDrawableStart(@DrawableRes int resId)`
- `setDrawableStart(Drawable drawable)`
- `setDrawableTop(@DrawableRes int resId)`
- `setDrawableTop(Drawable drawable)`
- `setTextAppearance(@StyleRes int resId)`
- `setText(@StringRes int resId)`
- `setText(String text)`
- `setTextSize(@DimenRes int resId)`
- `setTextSize(float size)`
- `setTextColor(@ColorInt int color)`
- `setTextStyle(int style)`
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
- `<attr name="dismissOnClick" format="boolean"/>` - dismiss on inside touch. Default is `false`
- `<attr name="arrowEnabled" format="boolean"/>`
- `<attr name="backgroundColor" format="color"/>` - background color. Default is `Color.GRAY`
- `<attr name="cornerRadius" format="dimension"/>` - background drawable corner radius
- `<attr name="arrowHeight" format="dimension"/>`
- `<attr name="arrowWidth" format="dimension"/>`
- `<attr name="arrowDrawable" format="reference"/>`
- `<attr name="margin" format="dimension"/>` - margin between arrow and anchor view
- `<attr name="textAppearance" format="reference"/>`
- `<attr name="android:padding"/>` - content padding
- `<attr name="android:gravity"/>` - Tooltip gravity
- `<attr name="android:maxWidth"/>`
- `<attr name="android:drawablePadding"/>`
- `<attr name="android:drawableBottom"/>`
- `<attr name="android:drawableEnd"/>`
- `<attr name="android:drawableStart"/>`
- `<attr name="android:drawableTop"/>`
- `<attr name="android:text"/>`
- `<attr name="android:textSize"/>`
- `<attr name="android:textColor"/>`
- `<attr name="android:textStyle"/>`
- `<attr name="android:fontFamily"/>`
- `<attr name="android:typeface"/>`
- `<attr name="android:lineSpacingExtra"/>`
- `<attr name="android:lineSpacingMultiplier"/>`

## RELEASES
### Gradle
```java
dependencies {
    compile 'com.github.vihtarb:tooltip:0.2.0'
}
```
### Maven
```html
<dependency>
    <groupId>com.github.vihtarb</groupId>
    <artifactId>tooltip</artifactId>
    <version>0.2.0</version>
</dependency>
```
## Changelog
### 0.2.0
- Implemented setting compound drawables(and drawables padding) by `Builder` methods `setDrawableStart`, `setDrawableEnd`, `setDrawableBottom`, `setDrawableTop` and for padding `setDrawablePadding(int)` or styleable attributes `android:drawableStart`, `android:drawableEnd`, `android:drawableBottom`, `android:drawableTop` and for padding `android:drawablePadding` (@antoninovitale)
- Implemented setting max `Tooltip` width by `Builder` method `setMaxWidth(int)` or styleable attribute `android:maxWidth` (@CapnSpellcheck)
- Implemented RTL layout direction supporting
- Implemented use `Gravity.LEFT` and `Gravity.RIGHT` as `Tooltip` gravity, but it strongly not recommended
- Implemented smooth `Tooltip` arrow
- Fixed `Tooltip` content view padding
- `Builder` method `setPadding(float)` marked as deprecated use `setPadding(int)` instead
- `Builder` method `setPadding(int)` now sets padding from argument not from resources
- Implemented arrow disabling by `Builder` method `setArrowEnabled(boolean)` or styleable attribute `arrowEnabled`
- [#26](https://github.com/ViHtarb/Tooltip/issues/26) Fixed issue with `Exception android.view.WindowManager$BadTokenException:...`
- [#19](https://github.com/ViHtarb/Tooltip/issues/19) Fixed issue with `Tooltip` moving on scrolling/updating list views
- [#13](https://github.com/ViHtarb/Tooltip/pull/13) Implemented supporting `CharSequence` for text instead of `String` (@gkravas)

### 0.1.9
- Min SDK changed from 11 to 14
- Implemented `OnClickListener` and `OnLongClickListener` listeners
- Implemented `setOnClickListener` and `setOnLongClickListener` methods in `Builder` and `Tooltip`
- Implemented `setOnDismissListener` in `Tooltip`
- Implemented customizing arrow drawable by `Builder` method `setArrow(Drawable)` and styleable attribute `arrowDrawable`
- Reimplemented `TooltipActionView` class
- Removed `Context context` argument in `Builder` constructors with second argument `MenuItem anchorMenuItem`
- Fixed `Activity has leaked window`

## Future Work
- Animations
- Elevation(Shadow)

## Known issues
- [#17](https://github.com/ViHtarb/Tooltip/issues/17) As temporary fix use it: `compile ('com.github.vihtarb:tooltip:version') { exclude module: "support-compat" }`

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

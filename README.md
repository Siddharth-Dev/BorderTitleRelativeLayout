# BorderTitleRelativeLayout
A relative layout which can show borders and title at top. It also support border color, title color and etc
[Apk](https://play.google.com/store/apps/details?id=com.sj.customview.bordertitlerelativelayout)

# Screenshot
<img src="https://github.com/Siddharth-Dev/BorderTitleRelativeLayout/blob/master/Screenshot_20151218-142701.png" width="200" height="300" /> <img src="https://github.com/Siddharth-Dev/BorderTitleRelativeLayout/blob/master/Screenshot_20151218-142709.png" width="200" height="300" /> <img src="https://github.com/Siddharth-Dev/BorderTitleRelativeLayout/blob/master/Screenshot_20151218-142713.png" width="200" height="300" />

# Xml example
```XML
<com.sj.customview.bordertitlerelativelayout.BorderTitleRelativeLayt
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/customRL"
        android:layout_centerInParent="true"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:rlTitleText="Title!"
        app:rlTitleTextColor="@color/colorPrimaryDark"
        app:rlTitleTextSize="20dp"
        app:rlBorderColor="@color/colorAccent"
        app:rlBorderStrokeWidth="5"
        app:rlTitleMarginBottom="0dp"
        app:rlShowBorder="true" />
```

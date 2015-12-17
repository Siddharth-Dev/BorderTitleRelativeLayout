package com.sj.customview.bordertitlerelativelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sid on 27/11/2015.
 */
public class BorderTitleRelativeLayout extends RelativeLayout {

    private Paint borderPaint;
    private float defaultDimen;
    private int originalTopPadding = -1;

    // Title related attributes
    private int titleColor;
    private String titleText;
    private Paint titlePaint;
    private float titleMarginLeft;
    private float titleMarginRight;
    private float titleMarginTop;
    private float titleMarginBottom;
    private float textSize;
    private float textWidth = 0;
    private float textheight = 0;

    //Border related attribute
    private int borderColor;
    private int borderWidth;
    private boolean showBorder = true;

    public BorderTitleRelativeLayout(Context context) {
        super(context);
        init(null);
    }

    public BorderTitleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BorderTitleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public BorderTitleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        calculateHeightAndPadding();
        invalidate();
    }

    public void enableBorder(boolean enable) {
        showBorder = enable;
        invalidate();
    }

    private boolean hasTitle() {
        return !TextUtils.isEmpty(titleText);
    }

    private void init(AttributeSet attr) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attr, R.styleable.BorderTitleStyle);
        defaultDimen = getContext().getResources().getDimension(R.dimen.border_title_default);
        textSize = getContext().getResources().getDimension(R.dimen.border_title_default_textsize);

        titleText = typedArray.getString(R.styleable.BorderTitleStyle_rlTitleText);
        titleColor = typedArray.getColor(R.styleable.BorderTitleStyle_rlTitleTextColor, Color.BLACK);
        titleMarginLeft = typedArray.getDimension(R.styleable.BorderTitleStyle_rlTitleMarginLeft, defaultDimen);
        titleMarginRight = typedArray.getDimension(R.styleable.BorderTitleStyle_rlTitleMarginRight, defaultDimen);
        titleMarginTop = typedArray.getDimension(R.styleable.BorderTitleStyle_rlTitleMarginTop, defaultDimen);
        titleMarginBottom = typedArray.getDimension(R.styleable.BorderTitleStyle_rlTitleMarginBottom, defaultDimen);
        textSize = typedArray.getDimension(R.styleable.BorderTitleStyle_rlTitleTextSize, textSize);

        titlePaint = new Paint();
        titlePaint.setColor(titleColor);
        titlePaint.setTextSize(textSize);

        showBorder = typedArray.getBoolean(R.styleable.BorderTitleStyle_rlShowBorder, true);
        borderColor = typedArray.getColor(R.styleable.BorderTitleStyle_rlBorderColor, Color.BLACK);
        borderWidth = typedArray.getInteger(R.styleable.BorderTitleStyle_rlBorderStrokeWidth, 2);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);                    // set the color
        borderPaint.setStrokeWidth(borderWidth);               // set the size
        borderPaint.setDither(true);                    // set the dither to true
        borderPaint.setStyle(Paint.Style.STROKE);       // set to STOKE
        borderPaint.setAntiAlias(true);                         // set anti alias so it smooths

        typedArray.recycle();

        originalTopPadding = getPaddingTop();
        calculateTextBounds();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);
        drawBorder(canvas);
    }

    private void calculateHeightAndPadding() {
        calculateTextBounds();
        if (originalTopPadding < 0) {
            originalTopPadding = getPaddingTop();
        }
        float paddingTop =  originalTopPadding;
        if (hasTitle()) {
            paddingTop =  titleMarginTop + textheight + titleMarginBottom ;
        }
        setPadding(getPaddingLeft(), (int) paddingTop, getPaddingRight(), getPaddingBottom());
    }

    private float getTextHieght() {
        return textheight;
    }

    private float getTextWidth() {
        return textWidth;
    }

    private void calculateTextBounds() {
        if (hasTitle()) {
            Rect bounds = new Rect();
            titlePaint.getTextBounds(titleText, 0, titleText.length(), bounds);


            textheight = bounds.height();
            textWidth = bounds.width();
            Log.d(getClass().getName(), "Text width normal way : " + textWidth);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Deprecated
    private void drawTextBitmap(Canvas canvas) {
        TextView textView = new TextView(getContext());
        final int width = getWidth();
        final int height = getHeight();
        float totalHorizontalMargin = titleMarginLeft + titleMarginRight;

        if (totalHorizontalMargin >= width) {
            return;
        }

        final int widthForTitle = width - (int)totalHorizontalMargin;
        LayoutParams params = new LayoutParams(widthForTitle, LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText(titleText);
        addView(textView, 0);

        textView.buildDrawingCache();
        Bitmap b = textView.getDrawingCache();
        textView.destroyDrawingCache();

        float textStartX = width;
        float textHeightHalf = 0;
        float bitMapWidth = b.getWidth();
        float bitMapHeight = b.getHeight();
        if (hasTitle()) {
            textStartX = (width - bitMapWidth)/2;
            textHeightHalf = bitMapHeight/2 + titleMarginTop;
            canvas.drawBitmap(b, textStartX, bitMapHeight + titleMarginTop, titlePaint);
        }

        if (showBorder) {
            canvas.drawLine(0, textHeightHalf, 0, height, borderPaint);        // left border
            canvas.drawLine(0, height, width, height, borderPaint);    // bottom border
            canvas.drawLine(width, textHeightHalf, width, height, borderPaint);      // right border

            canvas.drawLine(0, textHeightHalf, textStartX - titleMarginLeft, textHeightHalf, borderPaint);
            canvas.drawLine(width - textStartX + titleMarginRight, textHeightHalf, width, textHeightHalf, borderPaint);
        }

        removeViewAt(0);
        b.recycle();

    }

    private void drawBorder(Canvas canvas) {

        final int width = getWidth();
        final int height = getHeight();

        float[] textWidthArray =  new float[4];
        float maxWidth = width - titleMarginLeft - titleMarginRight;
//        String textToiMeasure = titleText;
//        int totalLength = titleText.length();
//        int charMeasured = 0;
//        String breakDownText = "";
//        while (charMeasured < totalLength) {
//            int charCouneted = titlePaint.breakText(textToiMeasure, true, maxWidth, textWidthArray);
//
//            if (charMeasured < totalLength) {
//                if (breakDownText.length() >0){
//                    breakDownText = breakDownText + "\n";
//                }
//                 breakDownText += textToiMeasure.substring(0, charCouneted);
//                textToiMeasure = textToiMeasure.substring(charCouneted);
//
//            }
//            charMeasured += charCouneted;
//            Log.d(getClass().getName(), "Char counted : " + charCouneted);
//            Log.d(getClass().getName(), "text to measure : " + textToiMeasure);
//            Log.d(getClass().getName(), "BreakDowntext : " + breakDownText);
//            Log.d(getClass().getName(), "charmeasured : " + charMeasured);
//        }
//
//        Log.d(getClass().getName(), "Break text ---");
//        Log.d(getClass().getName(), "BreakDowntext : " + breakDownText);
        float textStartX = width;
        float textHeightHalf = 0;
        if (hasTitle()) {
            textStartX = (width - textWidth)/2;
            textHeightHalf = textheight/2 + titleMarginTop;
            canvas.drawText(titleText, textStartX , textheight + titleMarginTop, titlePaint);
        }

        if (showBorder) {
            canvas.drawLine(0, textHeightHalf, 0, height, borderPaint);        // left border
            canvas.drawLine(0, height, width, height, borderPaint);    // bottom border
            canvas.drawLine(width, textHeightHalf, width, height, borderPaint);      // right border

            canvas.drawLine(0, textHeightHalf, textStartX - titleMarginLeft, textHeightHalf, borderPaint);
            canvas.drawLine(width - textStartX + titleMarginRight, textHeightHalf, width, textHeightHalf, borderPaint);
        }
    }
}

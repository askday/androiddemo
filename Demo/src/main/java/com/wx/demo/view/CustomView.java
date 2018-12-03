package com.wx.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.demo.util.LogUtil;

public class CustomView extends ViewGroup {
    int columnCount = 13;
    int width = 30;
    TextView tv;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.d(String.format("changed:%b\tl:%d\tt:%d\tr:%d\tb:%d", changed, l, t, r, b));
        if (changed) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                int height = child.getMeasuredHeight();
                int width = child.getMeasuredWidth();
                child.layout(0, 0, width, height);
            }
        }
    }

    private int getMySize(int measureSpec) {
        int mySize = 30;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d("======onMeasure====");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMySize(widthMeasureSpec) / this.columnCount;
        setMeasuredDimension(this.width, this.width);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //这个很重要，没有就不显示
            getChildAt(i).measure(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        //绘制背景
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, this.width, this.width, paint);

        // 绘制底色
        paint.setColor(Color.GREEN);
        canvas.drawRect(1, 1, this.width - 2, this.width-2, paint);
    }

    @SuppressLint("ResourceAsColor")
    public void init(int index, int columCount) {
        setBackgroundColor(Color.RED);

        this.tv = new TextView(getContext());
        tv.setText(String.valueOf(index));
        addView(tv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
}

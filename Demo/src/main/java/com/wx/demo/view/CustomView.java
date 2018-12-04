package com.wx.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.demo.R;
import com.wx.demo.model.GridItem;

import java.util.List;

public class CustomView extends ViewGroup {
    int index = 0;
    int columnCount = 13;
    int width = 30;
    GridItem item;
    TextView titleTV, bbTV, cellTV, foldTV, unassignedTV;

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
//        LogUtil.d(String.format("changed:%b\tl:%d\tt:%d\tr:%d\tb:%d", changed, l, t, r, b));
        if (changed) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                child.layout(3, 3, this.width - 3, this.width - 3);
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
//        LogUtil.d("======onMeasure====");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMySize(widthMeasureSpec) / this.columnCount;
        setMeasuredDimension(this.width, this.width);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
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
        paint.setColor(Color.GRAY);
        canvas.drawRect(1, 1, this.width - 2, this.width - 2, paint);

        if (item != null) {
            double unassigned = item.getUnassigned();
            if (unassigned > 0.99) {

            } else {
                paint.setColor(Color.rgb(212, 148, 123));
                float startX = 1;
                float endX = (float) ((this.width - 2) * item.getBb());
                canvas.drawRect(startX, 1, endX, this.width - 2, paint);

                endX = startX + (float) ((this.width - 2) * item.getCell());
                paint.setColor(Color.rgb(156, 186, 138));
                canvas.drawRect(startX, 1, endX, this.width - 2, paint);
                startX += endX;

                paint.setColor(Color.rgb(126, 160, 190));
                canvas.drawRect(startX, 1, this.width - 2, this.width - 2, paint);
            }
        }
    }

    public void init(int index, int columCount) {
        setWillNotDraw(false);

        this.index = index;
        this.columnCount = columCount;
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.layout_grid_item, this);
        titleTV = (TextView) view.findViewById(R.id.grid_title);
        bbTV = (TextView) view.findViewById(R.id.grid_title_1_v);
        cellTV = (TextView) view.findViewById(R.id.grid_title_2_v);
        foldTV = (TextView) view.findViewById(R.id.grid_title_3_v);
        unassignedTV = (TextView) view.findViewById(R.id.grid_title_4_v);
//        addView(view, this.width, this.width);
    }

    public void updateData(List<GridItem> info, int index) {
        try {
            item = info.get(index);
            this.invalidate();

            titleTV.setText(item.getName());
            bbTV.setText(String.format("%.2f%%", item.getBb() * 100));
            cellTV.setText(String.format("%.2f%%", item.getCell() * 100));
            foldTV.setText(String.format("%.2f%%", item.getFold() * 100));
            unassignedTV.setText(String.format("%.2f%%", item.getUnassigned() * 100));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

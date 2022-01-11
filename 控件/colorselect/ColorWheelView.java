package com.carocean.ckxcarsettings.colorselect;

import android.annotation.Nullable;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carocean.ckxcarsettings.R;
import com.carocean.ckxcarsettings.can.McuDataService;

import static com.carocean.ckxcarsettings.can.CanConstant.index_IHU_SetTheaterDimmingColor_64;
import static com.carocean.ckxcarsettings.colorselect.Constants.SELECTOR_RADIUS_DP;


/**
 * HSV color wheel
 */
public class ColorWheelView extends FrameLayout implements ColorObservable, Updatable {

    private float radius;
    private float centerX;
    private float centerY;

    private float selectorRadiusPx = SELECTOR_RADIUS_DP * 3;

    private PointF currentPoint = new PointF();
    private int currentColor = Color.MAGENTA;
    private boolean onlyUpdateOnTouchEventUp;

    private ImageView selector;

    private ColorObservableEmitter emitter = new ColorObservableEmitter();
    private ThrottledTouchEventHandler handler = new ThrottledTouchEventHandler(this);

    public ColorWheelView(Context context) {
        this(context, null);
    }

    public ColorWheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectorRadiusPx = SELECTOR_RADIUS_DP * getResources().getDisplayMetrics().density;

        {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ColorWheelPalette palette = new ColorWheelPalette(context);
            int padding = (int) selectorRadiusPx;
            palette.setPadding(padding, padding, padding, padding);
            addView(palette, layoutParams);
        }

        {
            selector=new ImageView(context);
            selector.setBackgroundResource(R.drawable.seekbar_thumb);
            selector.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(selector);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        width = height = Math.min(maxWidth, maxHeight);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int netWidth = w - getPaddingLeft() - getPaddingRight();
        int netHeight = h - getPaddingTop() - getPaddingBottom();
        radius = Math.min(netWidth, netHeight) * 0.5f - selectorRadiusPx;
        if (radius < 0) return;
        centerX = netWidth * 0.5f;
        centerY = netHeight * 0.5f;
        setColor(currentColor, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                handler.onTouchEvent(event);
                return true;
            case MotionEvent.ACTION_UP:
                update(event);
               int color= getColorAtPoint(event.getX(),event.getY());
                int red = (color & 0xff0000) >> 16;
                int green = (color & 0x00ff00) >> 8;
                int blue = (color & 0x0000ff);
                action_up_color.onColorUPListener(red,green,blue);
                Log.d("AtmosphereColorActivity", "onTouchEvent: ACTION_UP  red: "+red+" \tgreen "+green+"\tblue"+blue);

                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 给MCU发送信号接口，在手指抬起时才发送，不然每次move发送，数据发送太频繁
     */
    private Action_UP_Color action_up_color;
    public void setAction_up_color(Action_UP_Color action_up_colorcor){
        this.action_up_color=action_up_colorcor;
    }
    public interface Action_UP_Color{
        void onColorUPListener(int red,int green,int blue);
    }

    @Override
    public void update(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        boolean isTouchUpEvent = event.getActionMasked() == MotionEvent.ACTION_UP;
        if (!onlyUpdateOnTouchEventUp || isTouchUpEvent) {
            emitter.onColor(getColorAtPoint(x, y), true, isTouchUpEvent);
        }
        updateSelector(x, y);
    }

    private int getColorAtPoint(float eventX, float eventY) {
        float x = eventX - centerX;
        float y = eventY - centerY;
        double r = Math.sqrt(x * x + y * y);
        float[] hsv = {0, 0, 1};
        hsv[0] = (float) (Math.atan2(y, -x) / Math.PI * 180f) + 180;
        hsv[1] = Math.max(0f, Math.min(1f, (float) (r / radius)));
        return Color.HSVToColor(hsv);
    }

    public void setOnlyUpdateOnTouchEventUp(boolean onlyUpdateOnTouchEventUp) {
        this.onlyUpdateOnTouchEventUp = onlyUpdateOnTouchEventUp;
    }

    public void setColor(int color, boolean shouldPropagate) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        float r = hsv[1] * radius;
        float radian = (float) (hsv[0] / 180f * Math.PI);
        updateSelector((float) (r * Math.cos(radian) + centerX), (float) (-r * Math.sin(radian) + centerY));
        currentColor = color;
        if (!onlyUpdateOnTouchEventUp) {
            emitter.onColor(color, false, shouldPropagate);
        }
    }

    private void updateSelector(float eventX, float eventY) {
        float x = eventX - centerX;
        float y = eventY - centerY;
        double r = Math.sqrt(x * x + y * y);
        if (r > radius*0.9) {
            x *= radius / r*0.9;
            y *= radius / r*0.9;
        }
        currentPoint.x = x + centerX;
        currentPoint.y = y + centerY;
        //根据小圆点的具体大小去减相应的数值
        selector.setX(currentPoint.x-30);
        selector.setY(currentPoint.y-30);
    }

    @Override
    public void subscribe(ColorObserver observer) {
        emitter.subscribe(observer);
    }

    @Override
    public void unsubscribe(ColorObserver observer) {
        emitter.unsubscribe(observer);
    }

    @Override
    public int getColor() {
        return emitter.getColor();
    }
}

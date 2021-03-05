package com.cyberdesignz.studyup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class updateDraw extends View {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private boolean mCapturing = true;
    public static Paint mPaint = new Paint();
    Canvas canvas1;
    Button re;
    static Bitmap updtae;
    Bitmap image;
    static boolean checkremove;
    boolean check = false;
    public static Path mPath = new Path();
    public static final boolean GESTURE_RENDERING_ANTIALIAS = true;
    public static final boolean DITHER_FLAG = true;
    public float mSignatureWidth = 3f;
    public static int mSignatureColor;

    boolean down = false;

    View screen;
    private Bitmap mSignature = null;

    private final Rect mInvalidRect = new Rect();

    private float mX;
    static View v;
    private float mY;
    Context context;
    boolean onwrit = false;
    private float mCurveEndX;
    private float mCurveEndY;

    private int mInvalidateExtraBorder = 10;

    @SuppressLint({"NewApi", "NewApi"})
    public updateDraw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // screen = (View) findViewById(R.id.view1);

        init();
    }

    public updateDraw(Context context) {
        super(context);
        init();
        this.context = context;

    }

    public updateDraw(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void Oneraseclick() {
        updtae = UpdateNote.image;
        mSignatureColor = Color.WHITE;
        mPaint.setColor(mSignatureColor);
        mPath.reset();
        invalidate();
    }

    public void onwriteclick() {

        mSignatureColor = Color.BLACK;
        updtae = UpdateNote.image;
        mPaint.setColor(mSignatureColor);
        mPath.reset();
        invalidate();

    }

    public void OnClearClick() {
        updtae = null;
        mSignatureColor = Color.BLACK;
        mPaint.setColor(mSignatureColor);
        mPath.reset();
    }

    public void onclear() {

    }

    private void init() {
        setWillNotDraw(false);
        v = this;

        mPaint.setAntiAlias(GESTURE_RENDERING_ANTIALIAS);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mSignatureWidth);
        mPaint.setDither(DITHER_FLAG);
        mPath.reset();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas1 = canvas;
        if (onwrit) {
            mPaint.setColor(Color.RED);
        }

        new Rect(0, 0, getWidth(), getHeight());

        if (checkremove) {
            canvas1.drawARGB(255, 255, 255, 255);

        }
        if (updtae != null) {
            canvas1.drawBitmap(updtae, null, new Rect(0, 0, getWidth(),
                    getHeight()), null);
        } else {
            canvas1.drawARGB(255, 255, 255, 255);
        }

        if (mSignature != null) {

            canvas1.drawBitmap(mSignature, null, new Rect(0, 0, getWidth(),
                    getHeight()), null);
        } else {
            canvas1.drawPath(mPath, mPaint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mCapturing) {
            processEvent(event);

            return true;
        } else {
            return false;
        }
    }

    private boolean processEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchDown(event);
                invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:

                Rect rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }
                return true;

            case MotionEvent.ACTION_UP:

                touchUp(event, false);
                invalidate();
                return true;

            case MotionEvent.ACTION_CANCEL:

                // touchUp(event, true);
                invalidate();
                return true;

        }

        return false;

    }

    private void touchUp(MotionEvent event, boolean b) {
        float x = event.getX();
        float y = event.getY();

        mX = x + 3;
        mY = y + 3;
        mPath.moveTo(x, y);

        final int border = mInvalidateExtraBorder;
        mInvalidRect.set((int) x - border, (int) y - border, (int) x + border,
                (int) y + border);

        mCurveEndX = x;
        mCurveEndY = y;
    }

    private Rect touchMove(MotionEvent event) {
        Rect areaToRefresh = null;

        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        areaToRefresh = mInvalidRect;

        // start with the curve end
        final int border = mInvalidateExtraBorder;
        areaToRefresh.set((int) mCurveEndX - border, (int) mCurveEndY - border,
                (int) mCurveEndX + border, (int) mCurveEndY + border);

        float cX = mCurveEndX = (x + previousX) / 2;
        float cY = mCurveEndY = (y + previousY) / 2;

        mPath.quadTo(previousX, previousY, cX, cY);

        // union with the control point of the new curve
        areaToRefresh.union((int) previousX - border, (int) previousY - border,
                (int) previousX + border, (int) previousY + border);

        // union with the end point of the new curve
        areaToRefresh.union((int) cX - border, (int) cY - border, (int) cX
                + border, (int) cY + border);

        mX = x;
        mY = y;

        return areaToRefresh;

    }

    private void touchDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        mX = x + 3;
        mY = y + 3;
        mPath.moveTo(x, y);

        final int border = mInvalidateExtraBorder;
        mInvalidRect.set((int) x - border, (int) y - border, (int) x + border,
                (int) y + border);

        mCurveEndX = x;
        mCurveEndY = y;

    }

    /**
     * Erases the signature.
     */
    public void clear() {
        mSignature = null;
        mPath.rewind();
        // Repaints the entire view.
        invalidate();
    }

    public boolean isCapturing() {
        return mCapturing;
    }

    public void setIsCapturing(boolean mCapturing) {
        this.mCapturing = mCapturing;
    }

    public void setSignatureBitmap(Bitmap signature) {
        mSignature = signature;
        invalidate();
    }

    public Bitmap getSignatureBitmap() {
        if (mSignature != null) {
            return mSignature;
        } else {
            Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            c.drawPath(mPath, mPaint);
            return bmp;
        }
    }

}
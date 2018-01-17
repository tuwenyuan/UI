package com.twy.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by twy on 2018/1/17.
 */

public class LoadBigImageView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    private BitmapFactory.Options mOptions;
    private Rect mRect;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private Bitmap bitmap;

    public LoadBigImageView(Context context) {
        this(context, null, 0);
    }

    public LoadBigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadBigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //指定要加载的矩形区域
        mRect = new Rect();
        //解码图片的配置
        mOptions = new BitmapFactory.Options();
        //手势
        mGestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);
        // 滑动帮助
        mScroller = new Scroller(context);
    }

    /**
     * 由使用者输入一张图片 输入流
     *
     * @param is
     */
    public void setImage(InputStream is) {
        //先读取原图片的 宽、高
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;
        //复用
        mOptions.inMutable = true;
        //设置像素格式为 rgb565
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inJustDecodeBounds = false;
        //创建区域解码器 用于区域解码图片
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    /**
     * 测量 view的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得测量的view的大小
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        //如果解码器是null 表示没有设置过要现实的图片
        if (null == mDecoder) {
            return;
        }
        //确定要加载的图片的区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;
        //获得缩放因子
        mScale = mViewWidth / (float) mImageWidth;
        // 需要加载的高 * 缩放因子 = 视图view的高
        // x * mScale = mViewHeight
        mRect.bottom = (int) (mViewHeight / mScale);
    }

    /**
     * 把图片画上去
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 如果解码器是null 表示没有设置过要现实的图片
        if (null == mDecoder) {
            return;
        }
        //复用上一张bitmap
        mOptions.inBitmap = bitmap;
        //解码指定区域
        bitmap = mDecoder.decodeRegion(mRect, mOptions);
        //使用矩阵 对图片进行 缩放
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        //画出来
        canvas.drawBitmap(bitmap, matrix, null);
    }


    /**
     * 手指按下屏幕的回调
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //如果滑动还没有停止 强制停止
        if (!mScroller.isFinished()){
            mScroller.forceFinished(true);
        }
        //继续接收后续事件
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 手指 不离开屏幕 拖动
     * @param e1 手指按下去 的事件 -- 获取开始的坐标
     * @param e2 当前手势事件  -- 获取当前的坐标
     * @param distanceX  x轴 方向移动的距离
     * @param distanceY  y方向移动的距离
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // 手指从下往上 图片也要往上 distanceY是负数, top 和 bottom 在减
        // 手指从上往下 图片也要往下 distanceY是正数, top 和 bottom 在加
        //改变加载图片的区域
        mRect.offset(0, (int) distanceY);
        //bottom大于图片高了， 或者 top小于0了
        if (mRect.bottom > mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight-(int) (mViewHeight / mScale);
        }
        if (mRect.top < 0){
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
        }
        //Log.i("twy","top:"+mRect.top+"::bottom:"+mRect.bottom);
        //重绘
        invalidate();
        return false;
    }

    /**
     * 手指离开屏幕 滑动 惯性
     * @param e1
     * @param e2
     * @param velocityX  速度 每秒x方向 移动的像素
     * @param velocityY  y
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        /**
         * startX: 滑动开始的x坐标
         * startY: 滑动开始的y坐标
         * 两个速度
         * minX: x方向的最小值
         * max 最大
         * y
         */
        //计算器
        mScroller.fling(0,mRect.top,
                0,(int)-velocityY,
                0,0,0,
                mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    //获取计算结果并且重绘
    @Override
    public void computeScroll() {
        //已经计算结束 return
        if (mScroller.isFinished()){
            return;
        }
        //true 表示当前动画未结束
        if (mScroller.computeScrollOffset()){
            //
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top+ (int) (mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //交由手势处理
        return mGestureDetector.onTouchEvent(event);
    }
}

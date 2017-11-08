package com.twy.ui.view.pieview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.utils.StringUtil;
import com.twy.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * Description：折线数据类型饼状统计图
 * </p>
 *
 * @author tangzhijie
 */
public class LinePieView extends View {

    /**
     * 使用wrap_content时默认的尺寸
     */
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    /**
     * 斜线长度
     */
    private static final int SlASH_LINE_OFFSET = UIUtils.dip2px(15);

    /**
     * 横线长度
     */
    private static int HOR_LINE_LENGTH = UIUtils.dip2px(32);//180;

    /**
     * 横线上文字的横向偏移量
     */
    private static final int X_OFFSET = 20;

    /**
     * 横线上文字的纵向偏移量
     */
    private static final int Y_OFFSET = 10;

    /**
     * 中心坐标
     */
    private int centerX;
    private int centerY;

    /**
     * 半径
     */
    private float radius;

    /**
     * 弧形外接矩形
     */
    private RectF rectF;

    /**
     * 中间文本的大小
     */
    private Rect centerTextBound = new Rect();

    /**
     * 数据文本的大小
     */
    private Rect dataTextBound = new Rect();

    /**
     * 扇形画笔
     */
    private Paint mArcPaint;

    /**
     * 中心文本画笔
     */
    private Paint centerTextPaint;

    /**
     * 数据画笔
     */
    private Paint dataPaint;

    private Paint dataPaint1;

    /**
     * 数据源数字数组
     */
    private double[] numbers;

    /**
     * 数据源名称数组
     */
    private String[] names;

    /**
     * 数据源总和
     */
    private double sum;

    /**
     * 颜色数组
     */
    private int[] colors;

    private Random random = new Random();

    //自定义属性 Start

    /**
     * 中间字体大小
     */
    private float centerTextSize = 80;

    /**
     * 数据字体大小
     */
    private float dataTextSize = 40;

    /**
     * 中间字体颜色
     */
    private int centerTextColor = Color.BLACK;

    /**
     * 数据字体颜色
     */
    private int dataTextColor = Color.BLACK;

    /**
     * 圆圈的宽度
     */
    private float circleWidth = UIUtils.dip2px(20);

    //自定义属性 End

    public LinePieView(Context context) {
        super(context);
        init();
    }

    public LinePieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PieView);
        centerTextSize = typedArray.getDimension(R.styleable.PieView_centerTextSize, centerTextSize);
        dataTextSize = typedArray.getDimension(R.styleable.PieView_dataTextSize, dataTextSize);
        circleWidth = typedArray.getDimension(R.styleable.PieView_circleWidth, circleWidth);
        centerTextColor = typedArray.getColor(R.styleable.PieView_centerTextColor, centerTextColor);
        dataTextColor = typedArray.getColor(R.styleable.PieView_dataTextColor, dataTextColor);
        typedArray.recycle();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(circleWidth);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);

        centerTextPaint = new Paint();
        centerTextPaint.setTextSize(centerTextSize);
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setColor(centerTextColor);

        dataPaint = new Paint();
        dataPaint.setStrokeWidth(2);
        dataPaint.setTextSize(dataTextSize);
        dataPaint.setAntiAlias(true);
        dataPaint.setColor(dataTextColor);

        dataPaint1 = new Paint();
        dataPaint1.setStrokeWidth(2);
        dataPaint1.setTextSize(dataTextSize);
        dataPaint1.setAntiAlias(true);
        dataPaint1.setColor(Color.parseColor("#666666"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (measureWidthMode == MeasureSpec.AT_MOST
                && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, measureHeightSize);
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidthSize, DEFAULT_HEIGHT);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        //设置半径为宽高最小值的1/4
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 4;
        //设置扇形外接矩形
        rectF = new RectF(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateAndDraw(canvas);
    }

    List<Float> angles  = new ArrayList<>();
    List<Float> arcCenterDegrees  = new ArrayList<>();

    /**
     * 计算比例并且绘制扇形和数据
     */
    private void calculateAndDraw(Canvas canvas) {
        if (numbers == null || numbers.length == 0) {
            return;
        }

        angles.clear();
        //扇形开始度数
        int startAngle = 0;
        //所占百分比
        double percent;
        //所占度数
        float angle;
        boolean flag = false;
        arcCenterDegrees.clear();
        for (int i = 0; i < numbers.length; i++) {
            flag = false;
            for(int j = i+1;j<numbers.length;j++){
                if(numbers[j]>0){
                    flag = true;
                    break;
                }
            }
            percent = numbers[i] / sum;
            //获取百分比在360中所占度数
//            if (i == numbers.length - 1) {//保证所有度数加起来等于360
//                if(startAngle>=360) {
//                    angle = 1;
//                }else{
//                    angle = 360 - startAngle;
//                }
//            } else {
//                angle = (float) percent * 360;//(float) Math.ceil(percent * 360);
//            }
            if (!flag) {//保证所有度数加起来等于360
                angle = 360 - startAngle;
                if(angle==0 && numbers[i]>0){
                    angle = 1;
                }
            } else {
                angle = (float) percent * 360;//(float) Math.ceil(percent * 360);
                if(angle<1 && angle>0){
                    angle = 1;
                }
            }
            angles.add(angle);
            startAngle += angle;
            if(angle<=0){
                arcCenterDegrees.add(0f);
            }else {
                float arcCenterDegree = 90 + startAngle - angle / 2;
                arcCenterDegrees.add(arcCenterDegree);
            }

//            //绘制数据
//            if (numbers[i] <= 0) {
//                continue;
//            }
//
//            //绘制第i段扇形
//            drawArc(canvas, startAngle, angle, colors[i]);
//            startAngle += angle;
//
//
//            //当前扇形弧线相对于纵轴的中心点度数,由于扇形的绘制是从三点钟方向开始，所以加90
//            float arcCenterDegree = 90 + startAngle - angle / 2;
//            drawData(canvas, arcCenterDegree, i, percent);
//            /*if(startAngle>=360)
//                break;*/
        }
        startAngle = 0;
        for(int i = 0;i<angles.size();i++){
            if (numbers[i] <= 0) {
                continue;
            }
            //绘制第i段扇形
            drawArc(canvas, startAngle, angles.get(i), colors[i]);
            startAngle += angles.get(i);


            //当前扇形弧线相对于纵轴的中心点度数,由于扇形的绘制是从三点钟方向开始，所以加90
            //float arcCenterDegree = 90 + startAngle - angles.get(i) / 2;
            drawData(canvas, arcCenterDegrees.get(i), i, numbers[i] / sum,startAngle);
            lastArcCenterDegree = arcCenterDegrees.get(i);
        }

        //绘制中心数字总和
//        if(sum==0)
//            canvas.drawText("暂无数据", centerX - centerTextBound.width() / 2, centerY + centerTextBound.height() / 2, centerTextPaint);
        //canvas.drawText(sum + "", centerX - centerTextBound.width() / 2, centerY + centerTextBound.height() / 2, centerTextPaint);
    }

    float lastArcCenterDegree = -1;

    /**
     * 计算每段弧度的中心坐标
     *
     * @param degree 当前扇形中心度数
     */
    private float[] calculatePosition(float degree) {
        //由于Math.sin(double a)中参数a不是度数而是弧度，所以需要将度数转化为弧度
        //而Math.toRadians(degree)的作用就是将度数转化为弧度
        //sin 一二正，三四负 sin（180-a）=sin(a)
        //扇形弧线中心点距离圆心的x坐标
        //float x = (float) (Math.sin(Math.toRadians(degree)) * radius);
        float x = (float) (Math.sin(Math.toRadians(degree)) * (radius+circleWidth/2));
        //cos 一四正，二三负
        //扇形弧线中心点距离圆心的y坐标
        //float y = (float) (Math.cos(Math.toRadians(degree)) * radius);
        float y = (float) (Math.cos(Math.toRadians(degree)) * (radius+circleWidth/2));

        //每段弧度的中心坐标(扇形弧线中心点相对于view的坐标)
        float startX = centerX + x;
        float startY = centerY - y;

        float[] position = new float[2];
        position[0] = startX;
        position[1] = startY;
        return position;
    }

    /**
     * 绘制数据
     *
     * @param canvas  画布
     * @param degree  第i段弧线中心点相对于纵轴的夹角度数
     * @param i       第i段弧线
     * @param percent 数据百分比
     */
    private void drawData(Canvas canvas, float degree, int i, double percent, float startAngle) {
        //弧度中心坐标
        float startX = calculatePosition(degree)[0];
        float startY = calculatePosition(degree)[1];
        //斜线结束坐标
        float endX = 0;
        float endY = 0;
        //横线结束坐标
        float horEndX = 0;
        float horEndY = 0;
        //数字开始坐标
        float numberStartX = 0;
        float numberStartY = 0;
        //文本开始坐标
        float textStartX = 0;
        float textStartY = 0;

        //根据每个弧度的中心点坐标绘制数据

        dataPaint.getTextBounds(names[i], 0, names[i].length(), dataTextBound);
        //canvas.drawText(numbers[i] + " ", numberStartX, numberStartY, dataPaint);
        float width = dataPaint.measureText("￥"+StringUtil.getResult(numbers[i]) + " ")+UIUtils.dip2px(15);
        //Log.i("width",width+"width");
        //Log.i("width",width+"HOR_LINE_LENGTH");
        if(width>UIUtils.dip2px(32)){
            HOR_LINE_LENGTH = (int)width;
        }else{
            HOR_LINE_LENGTH = UIUtils.dip2px(32);
        }

        //根据角度判断象限，并且计算各个坐标点
        if (degree > 90 && degree < 180) {//二象限
            endX = startX + SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;
            horEndX = endX + HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
        } else if (degree == 180) {
            startX = centerX;
            startY = centerY + radius;
            endX = startX + SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;


            horEndX = endX + HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
        } else if (degree > 180 && degree < 270) {//三象限
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY + SlASH_LINE_OFFSET;

            horEndX = endX - HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
        } else if (degree == 270) {
            startX = centerX - radius;
            startY = centerY;
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;

            horEndX = endX - HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
        } else if (degree > 270 && degree < 360) {//四象限
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;

            horEndX = endX - HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
        } else if (degree == 360) {
            startX = centerX;
            startY = centerY - radius;
            endX = startX - SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;

            horEndX = endX - HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX - HOR_LINE_LENGTH + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;

        } else if (degree > 360) {//一象限

            if(i>0 && i<5){
                if (lastArcCenterDegree > 360 && angles.get(i)<10){
                    if( i < angles.size()-1 && arcCenterDegrees.get(i+1)>360 && angles.get(i+1)<10){
                        endX = startX ;
                        endY = startY;


                        horEndX = endX + HOR_LINE_LENGTH+SlASH_LINE_OFFSET;
                        horEndY = endY;

                        numberStartX = endX + X_OFFSET+SlASH_LINE_OFFSET;
                        numberStartY = endY - Y_OFFSET;

                        textStartX = endX + X_OFFSET;
                        textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
                    }else {
                        endX = startX + SlASH_LINE_OFFSET;
                        endY = startY + SlASH_LINE_OFFSET;
                        horEndX = endX + HOR_LINE_LENGTH;
                        horEndY = endY;

                        numberStartX = endX + X_OFFSET;
                        numberStartY = endY - Y_OFFSET;

                        textStartX = endX + X_OFFSET;
                        textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
                    }
                }else{
                    endX = startX + SlASH_LINE_OFFSET;
                    endY = startY - SlASH_LINE_OFFSET;

                    horEndX = endX + HOR_LINE_LENGTH;
                    horEndY = endY;

                    numberStartX = endX + X_OFFSET;
                    numberStartY = endY - Y_OFFSET;

                    textStartX = endX + X_OFFSET;
                    textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
                }
               /* angles.get(i-1);
                angles.get(i+1);
                angles.get(i);*/
            }else {
                endX = startX + SlASH_LINE_OFFSET;
                endY = startY - SlASH_LINE_OFFSET;

                horEndX = endX + HOR_LINE_LENGTH;
                horEndY = endY;

                numberStartX = endX + X_OFFSET;
                numberStartY = endY - Y_OFFSET;

                textStartX = endX + X_OFFSET;
                textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;
            }

            /*endX = startX + SlASH_LINE_OFFSET;
            endY = startY - SlASH_LINE_OFFSET;

            horEndX = endX + HOR_LINE_LENGTH;
            horEndY = endY;

            numberStartX = endX + X_OFFSET;
            numberStartY = endY - Y_OFFSET;

            textStartX = endX + X_OFFSET;
            textStartY = endY + dataTextBound.height() + Y_OFFSET / 2;*/

        }
        //canvas.drawLine(centerX, centerY, startX, startY, dataPaint);
        /*double b = Math.sqrt(Math.pow(startX, 2) + Math.pow(startY, 2));
        double c = b+ UIUtils.dip2px(64);
        //startX/b= d/c;
        int dx = (int)(startX/b*c);//endx
        int dy = (int)(startY/b*c);
        canvas.drawLine(startX, startY, dx, dy, dataPaint);*/
        dataPaint.setColor(this.colors[i]);

        //绘制折线
        canvas.drawLine(startX, startY, endX, endY, dataPaint);

        //绘制横线
        canvas.drawLine(endX, endY, horEndX, horEndY, dataPaint);
        canvas.drawCircle(horEndX,horEndY,UIUtils.dip2px(3),dataPaint);
        //绘制数字
        canvas.drawText("￥"+ StringUtil.getResult(numbers[i]) + " ", numberStartX, numberStartY, dataPaint1);
        //float width = dataPaint.measureText(numbers[i] + "");
        //绘制文字
        //canvas.drawText(names[i] + "", textStartX, textStartY, dataPaint);
    }

    /**
     * 绘制扇形
     *
     * @param canvas     画布
     * @param startAngle 开始度数
     * @param angle      扇形的度数
     * @param color      颜色
     */
    private void drawArc(Canvas canvas, float startAngle, float angle, int color) {
        mArcPaint.setColor(color);
        //-0.5和+0.5是为了让每个扇形之间没有间隙
        canvas.drawArc(rectF, startAngle - 0.5f, angle + 0.5f, false, mArcPaint);
    }

    /**
     * 生成随机颜色
     */
    private int randomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    /**
     * 设置数据(使用随机颜色)
     *
     * @param numbers 数字数组
     * @param names   名称数组
     */
    public void setData(double[] numbers, String[] names) {
        if (numbers == null || numbers.length == 0 || names == null || names.length == 0) {
            return;
        }
        if (numbers.length != names.length) {
            return;
        }
        this.numbers = numbers;
        this.names = names;
        colors = new int[numbers.length];
        sum=0;
        for (int i = 0; i < this.numbers.length; i++) {
            //计算总和
            sum += numbers[i];
            //随机颜色
            colors[i] = randomColor();
        }
        //计算总和数字的宽高
        centerTextPaint.getTextBounds(sum + "", 0, (sum + "").length(), centerTextBound);
        invalidate();
    }

    /**
     * 设置数据(自定义颜色)
     *
     * @param numbers 数字数组
     * @param names   名称数组
     * @param colors  颜色数组
     */
    public void setData(double[] numbers, String[] names, int[] colors) {
        if (numbers == null || numbers.length == 0
                || names == null || names.length == 0
                || colors == null || colors.length == 0) {
            return;
        }
        if (numbers.length != names.length || numbers.length != colors.length) {
            return;
        }
        this.numbers = numbers;
        this.names = names;
        this.colors = colors;
        sum = 0;
        for (int i = 0; i < this.numbers.length; i++) {
            //计算总和
            sum += numbers[i];
        }
        //计算总和数字的宽高
        centerTextPaint.getTextBounds(sum + "", 0, (sum + "").length(), centerTextBound);
        invalidate();
    }
}

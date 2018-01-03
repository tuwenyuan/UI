package com.twy.ui.utils.spannablestring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.base.BaseAplication;
import com.twy.ui.utils.UIUtils;
import com.twy.ui.utils.spannablestring.bean.Topic;
import com.twy.ui.utils.spannablestring.bean.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tuwenyuan on 2016/8/31.<br/>
 * 描述：
 * </br>
 */
public class SpanUtils{

    public static class PatternString{
        /**
         * #号括起来的话题#
         */
        public static final String TOPIC_PATTERN = "#[^#]+#";

        /**
         * 表情[大笑]
         */
        public static final String EXPRESSION_PATTERN = "\\[[^\\]]+\\]";

        /**
         * 网址
         */
        public static final String URL_PATTERN = "(([hH]ttp[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";

    }

    /**
     *
     * @param <T>
     */
    public interface SpanClickListener<T>{
        void onSpanClick(T t);
    }


    /**
     * 关键词变色处理
     * @param str
     * @param patterStr 需要变色的关键词 或者 正则表达式
     * @return
     */
    public static SpannableString getKeyWordSpan(int color, String str, String patterStr) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(patterStr, Pattern.CASE_INSENSITIVE);
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }

    /**
     * 自动识别话题并做颜色处理,可点击
     * @param color
     * @param str
     */
    public static SpannableString getTopicSpan(int color, String str, boolean clickable, SpanClickListener spanClickListener, Topic topic) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(PatternString.TOPIC_PATTERN, Pattern.CASE_INSENSITIVE);
        if(clickable){
            dealClick(spannableString, patten, 0, spanClickListener, topic);
        }
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }

    public static SpannableString getKeyWordSpan(int txtColor,int bgColor, String str, String patterStr) throws Exception  {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(patterStr, Pattern.CASE_INSENSITIVE);
        dealPattern(txtColor,bgColor,spannableString, patten, 0,patterStr);
        return spannableString;
    }

    private static void dealPattern(int color, final int bgColor, SpannableString spannableString, Pattern patten, int start, final String patterStr) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            if (matcher.start() < start) {
                continue;
            }
            int end = matcher.start() + key.length();
            //spannableString.setSpan(new AbsoluteSizeSpan(UIUtils.sp2px(14)),matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(BaseAplication.getInstance().getResources().getColor(R.color.transparent)), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            LineBackgroundSpan lineBackgroundSpan = new LineBackgroundSpan() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
                    //ToastUtil.showShortToast(patterStr);
                    //p.setTextSize(UIUtils.sp2px(14));
                    float measureText = p.measureText(patterStr);
                    Rect rect = new Rect(left+ UIUtils.dip2px(5),(int)(top+UIUtils.dip2px(2)),(int)(left+measureText-UIUtils.dip2px(5)),bottom-UIUtils.dip2px(2));
                    Paint paint = new Paint();
                    paint.setColor(bgColor);
                    c.drawRoundRect(left+UIUtils.dip2px(5),(int)(top+UIUtils.dip2px(2)),(int)(left+measureText-UIUtils.dip2px(5)),bottom-UIUtils.dip2px(2),UIUtils.dip2px(3),UIUtils.dip2px(3),paint);

                    Paint textPaint = new Paint();
                    textPaint.setColor(Color.WHITE);
                    textPaint.setTextSize(UIUtils.sp2px(12));
                    textPaint.setStyle(Paint.Style.FILL);
                    //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
                    textPaint.setTextAlign(Paint.Align.CENTER);

                    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                    float top1 = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                    float bottom1 = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

                    int baseLineY = (int) (rect.centerY() - top1/2 - bottom1/2);//基线中间点的y轴计算公式
                    c.drawText(patterStr,rect.centerX(),baseLineY,textPaint);
                    //Rect rect = new Rect(left,top,(int)(left+measureText),bottom);
                    //p.setAntiAlias(true);
                    //c.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
                    //c.drawBitmap(BitmapFactory.decodeResource(UgouApplication.getInstance().getResources(), R.drawable.shape_sale_tag),null,rect,p);
                    //p.setTextSize(UIUtils.sp2px(18));
                }
            };
            spannableString.setSpan(lineBackgroundSpan,matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //spannableString.setSpan(new BackgroundColorSpan(bgColor),matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                dealPattern(color, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * @用户 颜色处理、点击处理
     * @param color 前景色
     * @param str
     * @param clickable 是否可点击
     * @param spanClickListener
     * @param atUsers
     * @return
     * @throws Exception
     */
    public static SpannableString getAtUserSpan(int color, String str, boolean clickable, SpanClickListener spanClickListener, List<User> atUsers) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten;
        for (User u : atUsers) {
            patten = Pattern.compile("@" + u.getName(), Pattern.CASE_INSENSITIVE);
            if(clickable){
                dealClick(spannableString, patten, 0, spanClickListener, u);
            }
            dealPattern(color, spannableString, patten, 0);
        }
        return spannableString;
    }

    /**
     * 表情处理
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getExpressionSpan(Context context, String str) throws Exception {
        return ExpressionConvertUtil.getInstace().getExpressionString(context, str);
    }
    public static SpannableString getExpressionSpan1(Context context, String str) throws Exception {
        return ExpressionConvertUtil.getInstace().getExpressionString1(context, str);
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则将内容变色
     * @param color
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealPattern(int color, SpannableString spannableString, Pattern patten, int start) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            //设置前景色span
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new BackgroundColorSpan(Color.RED),matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealPattern(color, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，将内容设置可点击
     * @param spannableString
     * @param patten
     * @param start
     * @param spanClickListener
     * @param bean
     */
    private static void dealClick(SpannableString spannableString, Pattern patten, int start, final SpanClickListener spanClickListener, final Object bean){
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    spanClickListener.onSpanClick(bean);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置画笔属性
                    ds.setUnderlineText(false);//默认有下划线
                }
            }, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealClick(spannableString, patten, end, spanClickListener, bean);
            }
            break;
        }
    }
}

package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivitySpannableStringBinding;
import com.twy.ui.utils.spannablestring.ExpressionConvertUtil;
import com.twy.ui.utils.spannablestring.SpanUtils;
import com.twy.ui.utils.spannablestring.bean.Topic;
import com.twy.ui.utils.spannablestring.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twy on 2018/1/3.
 */

public class SpannableStringActivity extends AppCompatActivity {

    private ActivitySpannableStringBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_spannable_string,null,false);
        initData();
        setContentView(binding.getRoot());
    }

    private void initData() {
        testColoredKeywd();

        testTopic();

        textAtUsers();

        textExpression();

        textExpression1();

        textTv1();
    }

    /**
     * 关键字变色
     */
    private void testColoredKeywd() {
        String string = "Android一词的本义指“机器人”，同时也是Google于2007年11月5日,Android logo相关图片,Android logo相关图片(36张)\n";
        SpannableString cardText = null;
        try {
            cardText = SpanUtils.getKeyWordSpan(getResources().getColor(R.color.md_green_600), string, "Android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tvKeywordColored.setText(cardText);
    }

    /**
     * 测试话题
     */
    private void testTopic() {
        String topic = "#舌尖上的大连#四种金牌烤芝士吃法爱吃芝士的盆友不要错过了~L秒拍视频\n";
        SpannableString topicText = null;
        try {
            topicText = SpanUtils.getTopicSpan(Color.BLUE, topic, true, new SpanUtils.SpanClickListener<Topic>() {
                @Override
                public void onSpanClick(Topic t) {
                    Toast.makeText(SpannableStringActivity.this, "点击话题:" + t.toString() , Toast.LENGTH_SHORT).show();
                }
            }, new Topic(1, "舌尖上的大连"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tvTopic.setText(topicText);
        //如果想实现点击，必须要设置这个
        binding.tvTopic.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 测试@好友
     */
    private void textAtUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(1, "好友1"));
        users.add(new User(2, "好友2"));
        StringBuilder sb = new StringBuilder("快来看看啊");
        for (User u : users) {
            sb.append("@").append(u.getName());
        }
        sb.append("\n");
        try {
            SpannableString atSpan = SpanUtils.getAtUserSpan(Color.BLUE, sb.toString(), true, new SpanUtils.SpanClickListener<User>() {
                @Override
                public void onSpanClick(User user) {
                    Toast.makeText(SpannableStringActivity.this, "@好友:" + user.toString(), Toast.LENGTH_SHORT).show();
                }
            }, users);

            binding.tvTestAt.setText(atSpan);
            binding.tvTestAt.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试表情显示
     */
    private void textExpression(){
        String exStr = "今天天气很好啊[呲牙],是不是应该做点什么[色]";
        SpannableString span = null;
        try {
            span = SpanUtils.getExpressionSpan(this, exStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tvTextExpression.setText(span);
    }
    /**
     * 测试表情显示 图文混合居中
     */
    private void textExpression1(){
        String exStr = "图文居中[呲牙],图文居中及图片可点击[色]";
        SpannableString span = null;
        try {
            span = SpanUtils.getExpressionSpan1(this, exStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tvTextExpressionCenter.setText(span);
        binding.tvTextExpressionCenter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CharSequence text = ((TextView)v).getText();
                Spannable sText = Spannable.Factory.getInstance().newSpannable(text);
                TextView widget = (TextView) v;

                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ExpressionConvertUtil.MyIm[] imageSpans = sText.getSpans(off, off, ExpressionConvertUtil.MyIm.class);
                    if (imageSpans.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            //imageSpans[0].onClick(widget);
                            Toast.makeText(SpannableStringActivity.this,"点击了图片",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void textTv1(){
        String exStr = "买一送一 自定义标签背景及文案";
        SpannableString span = null;
        try {
            span = SpanUtils.getKeyWordSpan(Color.parseColor("#ffffff"), Color.parseColor("#ea5504"), exStr, "买一送一");
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tv1.setText(span);
    }

}

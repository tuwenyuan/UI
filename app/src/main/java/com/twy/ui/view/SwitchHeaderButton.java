package com.twy.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.twy.ui.R;


/**
 * @描述:
 * @项目名: My Application
 * @包名: com.ugou.switchheaderbutton
 * @类名: ServiceFeedbackActivity
 * @作者: soongkun
 * @创建时间: 2016/4/28 16:00
 */
public class SwitchHeaderButton extends LinearLayout {

    public static final int RIGHT = 2;
    public static final int LEFT = 1;

    private Context context;

    //根布局
    private View mRootView;
    private RadioButton mLeft;
    private RadioButton mRight;
    private RadioGroup mContainer;

    public SwitchHeaderButton(Context context) {
        this(context, null);
    }

    public SwitchHeaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mRootView = LayoutInflater.from(context).inflate(R.layout.switch_header_button, this, true);

        mLeft = (RadioButton) mRootView.findViewById(R.id.switch_header_button_left);
        mRight = (RadioButton) mRootView.findViewById(R.id.switch_header_button_right);
        mContainer = (RadioGroup) mRootView.findViewById(R.id.switch_header_button_container);


        initView(attrs);
        initListner();
    }

    //初始化视图
    private void initView(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SwitchHeaderButton);

        String leftText = typedArray.getString(R.styleable.SwitchHeaderButton_leftText);
        String rightText = typedArray.getString(R.styleable.SwitchHeaderButton_rightText);

        setLeftText(leftText);
        setRightText(rightText);
    }

    public void setRightText(String rightText) {
        mRight.setText(rightText);
    }

    public void setLeftText(String leftText) {
        mLeft.setText(leftText);
    }

    private void initListner() {
        mContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.switch_header_button_right) {

                    if (listener != null) {
                        listener.onSwitched(SwitchHeaderButton.this, SwitchHeaderButton.RIGHT);
                    }

                } else {
                    if (listener != null) {
                        listener.onSwitched(SwitchHeaderButton.this, SwitchHeaderButton.LEFT);
                    }

                }
            }
        });
    }

    private OnSwitchedListener listener;

    public void setOnSwitchedListner(OnSwitchedListener listener) {
        this.listener = listener;
    }

    /**
     * 切换到某个位置
     *
     * @param position
     */
    public void setSwitchPosition(int position) {
        //切换到什么位置
        if (listener != null) {
            listener.onSwitched(this, position);
        }
    }


    /**
     * 改变按钮默认位置，不触发 onSwitched（）事件
     *
     * @param position
     */
    public void changeSwitchPosition(int position) {

        if (position == RIGHT) {      //切换到了右边
            mRight.setChecked(true);
        } else if (position == LEFT) {
            mLeft.setChecked(true);

        }
    }

    /**
     * 反选 浅色系的
     *
     * @param isSelectLight 选择了浅色系的吗
     */
    public void setReverseSelection(boolean isSelectLight) {
        if(isSelectLight){
            mLeft.setBackgroundResource(R.drawable.sel_switch_header_button_left_light);
            ColorStateList csl = getResources().getColorStateList(R.color.text_samp2);

            if(csl!=null){
                mLeft.setTextColor(csl);        //设置颜色选择器
            }

            mRight.setBackgroundResource(R.drawable.sel_switch_header_button_right_light);
            mRight.setTextColor(csl);
        }
    }

    /**
     * 设置红色系的
     */
    public void setRedSeriesStyle(){
        ColorStateList cs3 = getResources().getColorStateList(R.color.text_samp3);
        if(cs3!=null){
            mLeft.setTextColor(cs3);        //设置颜色选择器
        }

        mLeft.setBackgroundResource(R.drawable.sel_switch_header_button_left_red);
        mRight.setBackgroundResource(R.drawable.sel_switch_header_button_right_red);
        mRight.setTextColor(cs3);

    }

    public interface OnSwitchedListener {
        /**
         * @param shb
         * @param switchtype 切换的类型 SwitchHeaderButton.RIGHT 表示右边  SwitchHeaderButton.LEFT 表示左边
         */
        public void onSwitched(SwitchHeaderButton shb, int switchtype);
    }

    public String getRightText(){
       return mRight.getText().toString();
    }

    public String getLeftText(){
       return mLeft.getText().toString();
    }

    /**
     *
     * @return  当前选中的状态
     */
    public int getCheckPosition(){
        if(mLeft.isChecked()){
            return LEFT;
        }else{
            return RIGHT;
        }
    }


    public String getCheckPositionText(){
        return getCheckPosition() == LEFT? getLeftText():getRightText();
    }
}

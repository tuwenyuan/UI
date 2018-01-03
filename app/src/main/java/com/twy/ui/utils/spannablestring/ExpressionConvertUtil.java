package com.twy.ui.utils.spannablestring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情转换工具
 */
public class ExpressionConvertUtil {

	private HashMap<String, String> emojiMap;

	private ExpressionConvertUtil() {

	}

	public static final ExpressionConvertUtil getInstace() {
		return SingtonHolder.INSTANCE;
	}

	private static class SingtonHolder{
		private static final ExpressionConvertUtil INSTANCE = new ExpressionConvertUtil();
	}

	/**
	 * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public SpannableString getExpressionString(Context context, String str) throws Exception {
		if (emojiMap == null) {
			parseData(getEmojiFile(context));
		}
		SpannableString spannableString = new SpannableString(str);
		String zhengze = "\\[[^\\]]+\\]";
		Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);

		dealExpression(context, spannableString, patten, 0);

		return spannableString;
	}


	/**
	 * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
	 * 
	 * @param context
	 * @param spannableString
	 * @param patten
	 * @param start
	 * @throws Exception
	 */
	private void dealExpression(Context context,
								SpannableString spannableString, Pattern patten, int start)
			throws Exception {
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {

			String key = matcher.group();
			if (matcher.start() < start) {
				continue;
			}
			String value = emojiMap.get(key);
			if (TextUtils.isEmpty(value)) {
				continue;
			}
			// 通过上面匹配得到的字符串来生成图片资源id
			int resId = context.getResources().getIdentifier(value, "drawable",
					context.getPackageName());
			if (resId != 0) {
				Drawable emoji = context.getResources().getDrawable(resId);
				int w = (int) (emoji.getIntrinsicWidth() * 0.40);
				int h = (int) (emoji.getIntrinsicHeight() * 0.40);
				emoji.setBounds(0, 0, w, h);
				// 通过图片资源id来得到bitmap，用一个ImageSpan来包装
				ImageSpan imageSpan = new ImageSpan(emoji);
				// 计算该图片名字的长度，也就是要替换的字符串的长度
				int end = matcher.start() + key.length();
				// 将该图片替换字符串中规定的位置中
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				if (end < spannableString.length()) {
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	public SpannableString getExpressionString1(Context context, String str) throws Exception {
		if (emojiMap == null) {
			parseData(getEmojiFile(context));
		}
		SpannableString spannableString = new SpannableString(str);
		String zhengze = "\\[[^\\]]+\\]";
		Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);

		dealExpression1(context, spannableString, patten, 0);

		return spannableString;
	}

	private void dealExpression1(Context context,SpannableString spannableString, Pattern patten, int start)
			throws Exception {
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {

			String key = matcher.group();
			if (matcher.start() < start) {
				continue;
			}
			String value = emojiMap.get(key);
			if (TextUtils.isEmpty(value)) {
				continue;
			}
			// 通过上面匹配得到的字符串来生成图片资源id
			int resId = context.getResources().getIdentifier(value, "drawable",
					context.getPackageName());
			if (resId != 0) {
				Drawable emoji = context.getResources().getDrawable(resId);
				int w = (int) (emoji.getIntrinsicWidth());
				int h = (int) (emoji.getIntrinsicHeight());
				emoji.setBounds(0, 0, w, h);
				// 通过图片资源id来得到bitmap，用一个ImageSpan来包装
				//ImageSpan imageSpan = new ImageSpan(emoji);
				MyIm imageSpan = new MyIm(emoji);
				// 计算该图片名字的长度，也就是要替换的字符串的长度
				int end = matcher.start() + key.length();
				// 将该图片替换字符串中规定的位置中
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				if (end < spannableString.length()) {
					dealExpression1(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	public class MyIm extends ImageSpan
	{

		public MyIm(Drawable d) {
			super(d);
		}

		public int getSize(Paint paint, CharSequence text, int start, int end,
						   Paint.FontMetricsInt fm) {
			Drawable d = getDrawable();
			Rect rect = d.getBounds();
			if (fm != null) {
				Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
				int fontHeight = fmPaint.bottom - fmPaint.top;
				int drHeight=rect.bottom-rect.top;

				int top= drHeight/2 - fontHeight/4;
				int bottom=drHeight/2 + fontHeight/4;

				fm.ascent=-bottom;
				fm.top=-bottom;
				fm.bottom=top;
				fm.descent=top;
			}
			return rect.right;
		}

		@Override
		public void draw(Canvas canvas, CharSequence text, int start, int end,
						 float x, int top, int y, int bottom, Paint paint) {
			Drawable b = getDrawable();
			canvas.save();
			int transY = 0;
			transY = ((bottom-top) - b.getBounds().bottom)/2+top;
			canvas.translate(x, transY);
			b.draw(canvas);
			canvas.restore();
		}

	}

	/**
	 *	初始化表情集合
	 * @param data
	 */
	private void parseData(List<String> data) {
		if (data == null) {
			return;
		}
		if (emojiMap == null) {
			emojiMap = new HashMap<>();
			for (String str : data) {
				String[] text = str.split(",");
				String fileName = text[1].substring(0, text[1].lastIndexOf("."));
				emojiMap.put(text[0], fileName);
			}
		}
	}

	public static List<String> getEmojiFile(Context context) throws IOException {
			List<String> list = new ArrayList<>();
			InputStream in = context.getResources().getAssets().open("faces.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"utf-8"));
			String str;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			return list;
	}

}
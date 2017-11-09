package com.twy.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.twy.ui.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片异步缓存配置
 *
 * @author thomas
 */
public class ImageManager {
    private static DisplayImageOptions userHeadOptions;

    public static ImageLoader getInstance() {
        return ImageLoader.getInstance();
    }

    private static DisplayImageOptions defaultOptions, permitOptions;

    /**
     * 图片加载辅助类
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOptions() {
        if (defaultOptions == null) {
            defaultOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置为RGB565比起默认的ARGB_8888要节省大量的内存
                    .delayBeforeLoading(100)// 载入图片前稍做延时可以提高整体滑动的流畅度
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }
        return defaultOptions;
    }

    /**
     * 图片加载辅助类
     *
     * @return
     */
    public static DisplayImageOptions getPermitOptions() {
        if (permitOptions == null) {
            permitOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置为RGB565比起默认的ARGB_8888要节省大量的内存
                    .delayBeforeLoading(100)// 载入图片前稍做延时可以提高整体滑动的流畅度
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }
        return permitOptions;
    }


    @SuppressWarnings("deprecation")
    public static DisplayImageOptions getUserHeadOptions() {
        if (userHeadOptions == null) {
            userHeadOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置为RGB565比起默认的ARGB_8888要节省大量的内存
                    .delayBeforeLoading(100)// 载入图片前稍做延时可以提高整体滑动的流畅度
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }
        return userHeadOptions;
    }

    private static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            //options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 压缩图片到指定位置(默认JPG格式)
     *
     * @param compressPath 生成文件路径(例如: /storage/imageCache/1.jpg)
     * @param quality      图片质量，0~100
     * @return if true,保存成功
     */

    public static File compressBiamp(String compressPath, int quality) {
        Bitmap bitmap = getimage(compressPath);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        //FileOutputStream stream = null;
        try {
            //stream = new FileOutputStream(new File(compressPath));

            //bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);// (0-100)压缩文件

            return saveFile(bitmap, timeStamp + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        return null;
    }


    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        //// TODO: 2016/8/3
        //String savePath = BaseApplication.mContext.getCacheDir().getAbsolutePath();
        String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File savedir = new File(savePath, "xuecheyi/images");
        String path = savedir.getAbsolutePath();
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}
